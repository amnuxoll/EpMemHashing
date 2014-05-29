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
    protected ArrayList<HashFn> hashFunctions; //TODO What??
    protected Random rand = new Random();

    /**
     * ctor inits instance variables
     */
    public Main()
    {
        episodeList = new ArrayList<WME[]>();
        hashCodeList = new ArrayList<int[]>();
        hashFunctions = new ArrayList<HashFn>();
        //hashFunctions.add(new RandomHashFn(HashFn.CODE_SIZE)); 
        //hashFunctions.add(new DummyHashFn(HashFn.CODE_SIZE));
        
        for(int codeSize = 65; codeSize<= 200; codeSize+= 5){
        	hashFunctions.add(new FoldingHashFn(codeSize));
        	//for(double discardFraction = 0.0; discardFraction<1.0; discardFraction+=.01){
        		
        		//hashFunctions.add(new SweetSpotHashFn(codeSize,discardFraction));
        	//}
        }
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
     * Each line of the file should have one WME on it and episodes are
     * separated by blank lines
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
        while(scan.hasNextLine())
        {
            ArrayList<WME> ep = new ArrayList<WME>();
            String line = scan.nextLine();
            line = line.trim();
            while(line.length() > 0)
            {
                //create and add a WME with this line
                WME wme = new WME(line);
                ep.add(wme);

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
        int currEp = 1;  //index of current episiode in the episodeList
        int testEp;
        
        int[] hashVal = new int[fn.codeSize];

        double recurTests = 0;
        double uniqueTests = 0;
        double recurSuccesses = 0;
        double uniqueSuccesses = 0;

        hashCodeList.add(fn.hash(episodeList.get(0)));
        
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
        	//System.out.print("\n");//TODO debug
            //for(int i: hashVal)  {System.out.print(i);}//TODO debug
            //if(testPrevious)      {System.out.print("recur ");}//TODO debug
        	//test the selected episode and record the result
        	if(!testPrevious){
        		if(findHash(hashVal) < 0){
        			uniqueSuccesses++;
        			//System.out.print("success");//TODO debug
        		}
                
        		//Add the hashcode to the list
        		hashCodeList.add(hashVal);

                //Advance to next episode
        		currEp++;
        	}//if
        	else if(findHash(hashVal) > 0) {
        		recurSuccesses++;
        		//System.out.print("success");//TODO debug
        	}
        	
        }//while

        //Step 5:  calculate the return value
        double[] result = new double[2];
        result[0] = uniqueSuccesses / uniqueTests;
        result[1] = recurSuccesses / recurTests;
        return result;
        
    }//calculateSuccess

    
    
    /**
     * Constructor for objects of class Main
     */
    public static void main(String [] input)
    {
        Main myself = new Main();
        
        //Step 1:  load the data from the file specified in input[0]
        myself.loadEpisodes("data.txt");
        
        //Step 2:  Test it and print the result
        System.out.println("Name\tUnique\tRecur");
        System.out.println("----\t------\t-----");
        
        int bookmark= 0;
        for(HashFn fn : myself.hashFunctions) {
        	if (bookmark != fn.codeSize){
            	System.out.print(fn.codeSize);
            	bookmark = fn.codeSize;
        	}
            double[] results = myself.calculateSuccess(fn);
        	System.out.println("\t" + results[0] + "\t" + results[1]);
        }
    }//main

    
}//class Main
