package hashTesting;

import java.util.ArrayList;

/**
 * This has function "cheats" by reviewing the entire list of episodes first.  
 * It uses a genetic algorithm to "learn" a subset of N words (where N is
 * the hashcode size) whose presence/absence are good for uniquely identifying
 * a single episode.
 * 
 * Since this function is a cheater, it's used only as a benchmark for legitimate
 * hash functions.
 * 
 * @author Nux
 * @version 29 May 2014
 */
public class GAHashFn extends HashFn {

    //ctor
    public GAHashFn(int size, ArrayList<WME[]> episodeList)
    {
        super(size);
    }	
	
	@Override
	public int[] hash(WME[] episode) {
		return null;
	}

	@Override
	public String getName() {
		return "GA HashFn (cheater)";
	}

}//class GAHashFn
