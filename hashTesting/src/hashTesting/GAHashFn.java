package hashTesting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * This has function "cheats" by reviewing the entire list of episodes first.  
 * It uses a genetic algorithm to "learn" a subset of N WMEs (where N is
 * the hashcode size) whose presence/absence are best for uniquely identifying
 * a single episode.
 * 
 * Since this function is a cheater, it's used only as a benchmark for legitimate
 * hash functions.
 * 
 * @author Nux
 * @version 29 May 2014
 */
public class GAHashFn extends DummyHashFn {
	
	public static final int POPSIZE = 100; //population size
	public static final int NUMGEN = 100; //number of generations to try
	public static final int MUTATE = 5; //percent chance of mutation
	
	protected ArrayList<WME[]> episodeList;  //list of episodes we'll see
	protected Dictionary dict;               //dictionary of WMEs in episodeList
	protected WME[] bestHasher;              //each of these WMEs corresponds to one bit
	protected WME[][] population;            //a population of hashers to try
	protected Random rand = new Random();    //cause we need random numbers

    //ctor
    public GAHashFn(int size, ArrayList<WME[]> episodeList, int compareType)
    {
        super(size);
        this.episodeList = episodeList;
        this.dict = new Dictionary(episodeList, compareType);
        this.bestHasher = new WME[size];
        evolve();
    }
    
    /** 
     * mate
     * 
     * creates an offspring by generating an offspring from two random members 
     * of the population.
     * 
     * IMPORTANT:  population must be sorted by {@link #sortPop}
     *  
     * @return a new offspring hasher 
     */
    private WME[] mate()
    {
    	//select two random hashers, favoring the fitter ones 
    	int sum = POPSIZE * (POPSIZE + 1) / 2;
    	int choice1 = this.rand.nextInt(sum) + 1;
    	int choice2 = this.rand.nextInt(sum) + 1;
    	int count = 0;
    	int index1 = -1;
    	int index2 = -1;
    	for(int i = 0; i < POPSIZE; ++i) {
    		count += i + 1;
    		if ((index1 < 0) && (count >= choice1))
    		{
    			index1 = i;
    			if (index2 >= 0) break;
    		}
    		if ((index2 < 0) && (count >= choice2))
    		{
    			index2 = i;
    			if (index1 >= 0) break;
    		}
    	}//for
    	
    	//mate them
    	WME[] mama = this.population[index1];
    	WME[] papa = this.population[index2];
    	WME[] newHasher = new WME[this.codeSize];
    	int split = rand.nextInt(this.codeSize);
    	for(int i = 0; i < split; ++i)
    	{
    		newHasher[i] = mama[i];
    	}
    	for(int i = split; i < this.codeSize; ++i)
    	{
    		newHasher[i] = papa[i];
    	}
    	
    	//mutate?
    	if (rand.nextInt(this.MUTATE) < this.MUTATE) {
    		int index = rand.nextInt(this.codeSize);
    		newHasher[index] = dict.getRandomEntry().getEntry();
    	}
    	
    	return newHasher;
    }//mate
    
    /** sorts the population in ascending order based upon fitness */
    private void sortPop(int[] fitnesses) 
    {
    	//insertion sort is fine for this
    	for(int i = 1; i < this.population.length; ++i)
    	{
    		WME[] curr = this.population[i];
    		int currFit = fitnesses[i];
    		for(int j = i-1; j >= 0; --j)
    		{
    			if (fitnesses[j] > currFit) {
    				fitnesses[j+1] = fitnesses[j];
    				this.population[j+1] = this.population[j];
    				if (j == 0)
    				{
    					this.population[0] = curr;
    					fitnesses[0] = currFit;
    				}
    			}
    			else
    			{
    				this.population[j+1] = curr;
    				fitnesses[j+1] = currFit;
    				break;
    			}
    		}//for
    	}//for
    	
    	
    }//sortPop
    
    /** runs the GA algorithm */
    private void evolve() 
    {
    	createInitPopulation();
    	for(int generation = 0; generation < NUMGEN; ++generation)
    	{
			// Create an array of fitness values (initially zero)
			int[] fitnesses = new int[POPSIZE];
			for (int i = 0; i < fitnesses.length; ++i) {
				fitnesses[i] = 0;
			}

			// Generate the hash code each hasher would produce for each
			// episode.
			// Any duplicates indicates a failure (less fitness)
			for (int hashIdx = 0; hashIdx < POPSIZE; ++hashIdx) {
				WME[] hasher = population[hashIdx];
				int[][] codez = new int[episodeList.size()][this.codeSize];
				for (int epIdx = 0; epIdx < episodeList.size(); ++epIdx) { 
					codez[epIdx] = hashWithHasher(episodeList.get(epIdx), hasher);
				}

				// subtract 1 from fitness for each duplicate
				for (int i = 0; i < episodeList.size(); ++i) {
					for (int j = i + 1; j < episodeList.size(); ++j) {
						if (Arrays.equals(codez[i], codez[j])) {
							fitnesses[hashIdx]--;
						}
					}
				}
			}// for

			// create a new population by mating
			WME[][] newPop = new WME[POPSIZE][this.codeSize];
			sortPop(fitnesses);
			//Don't need a new population if this is the last generation
			if (generation < NUMGEN - 1)
			{
				for (int i = 0; i < POPSIZE; ++i) {
					newPop[i] = mate();
				}
				this.population = newPop;
			}
    	}//for (each generation)
    	
    	//set the best hasher
    	this.bestHasher = this.population[POPSIZE - 1];
    		
    }//evolve
    		
    /** generates a hash code with a given hasher */
    private int[] hashWithHasher(WME[] episode, WME[] hasher) 
    {
    	//start with all zeroes
    	int[] result = super.hash(episode);
    	
    	//insert ones for entries in hasher that are in episode
    	for(int i = 0; i < hasher.length; ++i) {
    		for(int j = 0; j < episode.length; ++j) {
    			if (episode[j].equals(hasher[i])) {
    				result[i] = 1;
    				break;
    			}
    		}//for
    	}//for
    	
    	return result;
    }//hashWithHasher
    
    /** create a random population of hashers */
    protected void createInitPopulation()
    {
    	this.population = new WME[POPSIZE][this.codeSize];
    	for(int i = 0; i < POPSIZE; ++i) {
    		for(int j = 0; j < this.codeSize; ++j) {
    			this.population[i][j] = dict.getRandomEntry().getEntry();
    		}
    	}
    }
	
	@Override
	public int[] hash(WME[] episode) {
		return hashWithHasher(episode, this.bestHasher); 
	}

	@Override
	public String getName() {
		return "GA HashFn (cheater)";
	}

}//class GAHashFn
