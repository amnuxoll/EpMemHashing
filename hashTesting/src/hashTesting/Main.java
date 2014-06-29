package hashTesting;
import java.util.*;
import java.io.*;

/**
 * Main class with main method, will load data from an input file and 
 * hash each episode and store it if it does not already exist, and
 * print an error/message if it already exists.
 * 
 * @author Alexandra Warlen
 * @author Allie Seibert
 * @version Friday May 16, 2014
 */
public class Main
{
    //constants
    public static final double REPEAT_CHANCE = 0.5;
    
    // instance variables
    protected ArrayList<WME[]> episodeList;
    protected ArrayList<int[]> hashCodeList;
    protected ArrayList<HashFn> hashFunctions; //the list of hash fns to test
    protected Random rand = new Random();
    protected ArrayList<Integer> hashCodeEpList; //keeps track of which hashes belong
    										 //to which episodes
    /**
     * ctor inits instance variables
     */
    public Main()
    {
        episodeList = new ArrayList<WME[]>();
        hashCodeList = new ArrayList<int[]>();
        hashFunctions = new ArrayList<HashFn>();

        hashCodeEpList = new ArrayList<Integer>();
        
    }//ctor
    
    /**
     * Special version of ctor that uses a given random number seed. (For testing)
     */
    public Main(int seed)
    {
        this();
        rand = new Random(seed);
    }
    

    /**
     * loadEpisodes
     *
     * loads all the episodes in a given file and puts them in episodeList.  
     * This file can be in one of two formats:
     * 1.  (instance-based) Each line of the file should have one WME on it 
     *     and episodes are separated by blank lines or a line containing 
     *     "--- input phase ---"
     * 2.  (delta-based) the first episode is the same as for an instance-based 
     *     file.  Subsequent episodes are defined by specifying which WMEs to 
     *     add and remove from the previous episode in this format:  
     *        =>WM: (#: E6 ^content wall)
	 *        <=WM: (#: E6 ^content normalfood)
	 *     where '#' can be any positive integer.  Each add/remove must be 
	 *     on a separate line. 
     */
    protected void loadEpisodes(String filename)
    {
        //Open the file
        File inputFile = new File(filename);
        if (! inputFile.exists()) {
            System.err.println("File does not exist: " + filename);
            return; //nothing to load
        }
        Scanner scan = null;
        try {
            scan = new Scanner(inputFile);
        }
        catch(FileNotFoundException fnfe) {
            System.err.println("Could not open file: " + filename);
            return; //can't open file
        }

        //Clear any old episodes in the list
        episodeList.clear();
        
        //Read each episode from the file
        ArrayList<WME> ep = new ArrayList<WME>();
        while(scan.hasNextLine())
        {
            int count = 0; //number of WMEs read so far for this episode
            String line = scan.nextLine();
            line = line.trim();
            while( (line.length() > 0) && (line.indexOf("--- input phase ---") == -1) )
            {
                boolean add = true;  //are we adding or removing this WME?
                
                //Check for WME add/remove
                if (line.indexOf("WM: ") != -1)
                {
                    //are we removing this WME instead of adding it?
                    if (line.indexOf("<=") == 0)
                    {
                        add = false;
                    }

                    //Trim off the non-WME part
                    int index = line.lastIndexOf(": ");
                    line = "(" + line.substring(index + 2);
                }
                else if (count == 0)
                {
                    //if we aren't seeing add/remove then this is a full episode
                    //specification and we want to start with an empty list
                    ep = new ArrayList<WME>();
                }
                
                //create and add/remove a WME with this line
                WME wme = new WME(line);
                if (!wme.isValid())
                {
                	break;
                }
                if (add)
                {
                    ep.add(wme);
                }
                else
                {
                    ep.remove(wme);
                }
                count++;

                //read the next line
                if (! scan.hasNextLine())
                {
                    break;
                }
                line = scan.nextLine();
                line = line.trim();
            }//while

            //We now have all the WMEs for this episode, so convert the
            //ArrayList into an array and add it to the list
            WME[] finalEp = ep.toArray(new WME[ep.size()]);
            this.episodeList.add(finalEp);

        }//while

        //ok we're done
        scan.close();
        
        
    }//loadEpisodes
    
    /**
     * addHashFunctions
     * 
     * inits the hashFunctions list for a given codesize
     */
    public void addHashFunctions(int codeSize)
    {
    	
        //hashFunctions.add(new FoldingHashFn(codeSize));
    	for(double discardFraction = 0.0; discardFraction<= 0.15; discardFraction+=0.03){	
    		hashFunctions.add(new FoldingSweetSpotHashFn(codeSize, discardFraction));
       		for (double modSize = 0.25; modSize <= 1.75; modSize += 0.25)
       			hashFunctions.add(new ModularFSSHFn(codeSize, discardFraction, modSize));
        }
		//hashFunctions.add(new FoldingHashFn(codeSize));

            	
    }//addHashFunctions
    
    /**
     * genOneEpisode
     * 
     * creates a single, randomly generated episode
     * 
     * @param dict a dictionary of WMEs that may be in the episode
     * @param sum a count of all the times a WME appears in any episode
     * 
     * @return a newly minted episode that's pretty similar to the last 
     * one in episodeList
     */
    private WME[] genOneEpisode(Dictionary dict, int sum) {
    	//determine how many of the previous episodes will be added and 
    	//removed.  The values have a 50% chance of being zero, and 
    	//a maximum that is about 5% of the size of the previous episode
    	WME[] lastEp = this.episodeList.get(this.episodeList.size() - 1);
    	int maxChange = Math.max(1, lastEp.length / 20);  
    	int removeAmt = Math.max(0, rand.nextInt(maxChange * 2) - maxChange); 
    	int addAmt = removeAmt;
    	while(addAmt == removeAmt) {
    		addAmt = Math.max(0, rand.nextInt(maxChange * 2) - maxChange);
    	}
    	
    	//Randomly determine the indexes of the ones to remove
    	int[] removeIndexes = {-1};  //default value has no valid indexes
    	if (removeAmt > 0) {
    		removeIndexes = new int[removeAmt];
    		for(int i = 0; i < removeAmt; ++i) {
    			removeIndexes[i] = rand.nextInt(lastEp.length);
    		}
    		Arrays.sort(removeIndexes);
    	}
    	
    	//Create the new episode by coping the previous one without the removed WMEs
    	WME[] newEp = new WME[lastEp.length - removeAmt + addAmt];
    	int currPos = 0;
    	for(int i = 0; i < lastEp.length; ++i) {
    		//is this a remove index?
    		for(int j = 0; j < removeAmt; ++j) {
    			if (removeIndexes[j] == i) {
    				continue;
    			}
    		}
    		
    		//copy the WME
    		newEp[currPos] = new WME(lastEp[i].toString());
    		currPos++;
    		if (currPos == newEp.length) break;
    	}//for
    			
    	//Now add any additional WMEs needed to fill out the new episode
    	while(currPos < newEp.length) {
    		int index = rand.nextInt(sum);  //randomly select a WME
    		int count = 0;
    		//find the randomly selected WME
    		for(int i = 0; i < dict.getSize(); ++i) {
    			Entry entry = dict.getEntryAt(i);
    			count += entry.getSumOccurrences();
    			
    			//Have we found it?
    			if (count > index)
    			{
    				WME newWME = new WME(entry.getEntry().toString());
    				newEp[currPos] = newWME;
    				currPos++;
    				break;
    			}
    		}//for
    	}//while
    	
    	return newEp;

    }//genOneEpisode
	
    
    /**
     * genEpisodes
     * 
     * generates more episodes for the episodeList based upon the ones that are 
     * already there.  This method does these things to try to make its episodes
     * "realistic":
     *  - it honors existing WME frequencies in its selection.  So more frequently
     *    occuring WMEs are more likely to appear in the generated episodes 
     *  - each episode is only a little different than its predecessor
     *  - episodes are around the same size
     *  
     *  NOTE:  if there are no reference episodes this method quietly fails
     *  
     *  @param howMany  how many new episodes to add
     */
    public void genEpisodes(int howMany)
    {
    	//To track frequencies, build up a dictionary of WMEs
    	Dictionary dict = new Dictionary(this.episodeList, WME.ID + WME.ATTR + WME.VAL);
    	
    	//count how many total WMEs are in all episodes
    	int sum = 0;
    	for(int i = 0; i < dict.getSize(); ++i)
    	{
    		Entry entry = dict.getEntryAt(i);
    		sum += entry.getOccurrences().size();
    	}


    	//generate the episodes
    	for(int i = 0; i < howMany; ++i)
    	{
    		WME[] ep = genOneEpisode(dict, sum);
    		this.episodeList.add(ep);
    	}
    	
    }//genEpisodes


    /**
     * findSimilarity
     * 
     * this method is called if the findHash method does not find an identical 
     * hashCode.  
     * 
     *  @param hashVal is an array of ints representing the hashcode to search
     *  for within the hashcode list, 
     *  @return returns a double representing the highest percentage of 
     *  similarity between the given hashcode and the hashcodes within the list 
     * 
     */
    
    public double[] findSimilarity(int[] hashVal, int codeSize, int testEp)
    {
    	double[] ret = new double[2];
    	double highestSimilarity = 0;
    	double similarIndex = -1;
    	double total = codeSize;
    	//loop through the hashCodeList and compare each int in the array
    	for(int i = 0; i < hashCodeList.size(); i++){
    		int similar = 0;
    		for(int j = 0; j < hashVal.length; j++) {
    			//if the ints are the same, add to the similarity value
    			if (hashVal[j] == hashCodeList.get(i)[j]) {
    				similar++;
    			}
    		}
    		if (similar > highestSimilarity){
    			highestSimilarity = similar;
    			similarIndex = hashCodeEpList.get(i);
    		}
    	}
    	ret[0] = highestSimilarity/total;
    	ret[1] = similarIndex-testEp;
    	
    	return ret;
    }
    
	/**
     * findHash
     * 
     * checks hashlist to see if the episode has already been seen.
     * 
     * @param  episode   An array of WMEs represents a single episode
     * @return           return the index of the identical episode
     */
    public int findHash(int[] hashVal)
    {

        //Find out if the hashcode is already in the hashCodeList and record
    	//whether it exists.
    	for(int i = 0; i < hashCodeList.size(); ++i){
    		int[] code = hashCodeList.get(i);
            
    		//if the hashVals are equivalent return the index of the occurrence
    		if(Arrays.equals(code, hashVal)){
    			return i;
    		}
    	}

        //not found
        return -1;
        
    }//findHash

    /**
     * calculateSuccess
     *
     * for a given HashFn, this method tests it against the episodes in episode
     * list.
     *
     * CAVEAT: This methods assumes that all episodes in this.episodeList are
     * unique
     *
     * @param fn  the HashFn object to test
     *
     * @return two doubles, one indicating the correct fraction of new (unique)
     * episodes correctly identified and the other indicating the correct
     * fraction of repeated episodes correctly identified.
     */
    public double[] calculateSuccess(HashFn fn)
    {
        int currEp = 1;  //index of current episode in the episodeList
        int testEp;
        
        int[] hashVal = new int[fn.codeSize];

        double recurTests = 0;
        double uniqueTests = 0;
        double recurSuccesses = 0;
        double uniqueSuccesses = 0;
        double similarSuccess = 0;

        hashCodeList.add(fn.hash(episodeList.get(0)));
        hashCodeEpList.add(new Integer(0));
        
        while(currEp < episodeList.size())
        {
            //Randomly decide whether to test the episode at currEp or repeat a
            //previously tried episode
        	testEp = currEp;
        	boolean testPrevious = rand.nextDouble() < REPEAT_CHANCE;
        	if(testPrevious){
        		testEp = (int)(rand.nextDouble()*currEp);
                recurTests++;
        	}
            else {
                uniqueTests++;
            }

        	//use the hashfunciton to create a hashcode for the given episode
        	hashVal = fn.hash(episodeList.get(testEp));

        	//test the selected episode and record the result
        	if(!testPrevious){
        		if(findHash(hashVal) < 0){
        			uniqueSuccesses++;
        		}
                
        		//Add the hashcode to the list
        		hashCodeList.add(hashVal);
        		hashCodeEpList.add(new Integer(testEp));

                //Advance to next episode
        		currEp++;
        	}//if
        	else if((findHash(hashVal) > 0)&&testPrevious) {
        		recurSuccesses++;
        		similarSuccess++;
        	}
        	else if ((findHash(hashVal)<0)&&testPrevious){
        		if (findSimilarity(hashVal, fn.codeSize, testEp)[1] == 0){
        			similarSuccess = similarSuccess + (findSimilarity(hashVal, fn.codeSize, testEp)[0]);
        		}
        		
        	}
        }//while

        //Step 5:  calculate the return value
        double[] result = new double[3];
        result[0] = uniqueSuccesses / uniqueTests;
        result[1] = recurSuccesses / recurTests;
        result[2] = similarSuccess / recurTests;
        hashCodeList.clear();
        hashCodeEpList.clear();
        return result;
        
    }//calculateSuccess
    
    /**
     * Constructor for objects of class Main
     */
    public static void main(String [] input)
    {
        Main myself = new Main();
        
        //Step 1:  load the data from the file specified in input[0]
        myself.loadEpisodes("log_tanksoar_changesonly.txt");
        
        //Step 2:  Iterate over a range of hash code sizes
        for(int codeSize = 30; codeSize<= 90; codeSize+= 30){ 
        	System.out.println("testing hash functions for hash code size: " + codeSize +"\n");
        	//System.out.print(codeSize +"");
        	
        	myself.hashFunctions = new ArrayList<HashFn>();
        	myself.addHashFunctions(codeSize);

			// Step 3: Test each hash function and print the result
			System.out.println("Discard Fraction, Unique, Recurring, Similar");

			for (HashFn fn : myself.hashFunctions) {
				double[] results = myself.calculateSuccess(fn);
				System.out.println( fn.getName()+", " + String.format("%.5f", results[0]) + ", " 
				+ String.format("%.5f", results[1]) + ", " + String.format("%.5f", results[2]));
			}//for
			
			
			System.out.println("\n\n");
        }//for
        

       

    }//main

    
}//class Main
