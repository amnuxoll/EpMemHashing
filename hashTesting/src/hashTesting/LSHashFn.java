package hashTesting;

import java.util.ArrayList;
import java.util.Random;

/**
 * This is an implementation of the "locale sensitive" hash algorithm.  It
 * divides the hashcode into small subsections.  Each subsection is filled
 * with a random selection of WMEs from the current dictionary.  Whenever
 * the dictionary changes, only subsection is re-inited with a random 
 * selection of WMEs.  
 * 
 * @author Nux
 * @version May 2014
 */
public class LSHashFn extends DummyHashFn
{
    //keep track of all WMEs we've seen in any episode
    protected ArrayList<WME> dict = new ArrayList<WME>();
    protected int subSize = 2;  //size of subsections
    protected int numSubs;      //number of subsections
    protected int currSub = 0;  //which subsection to modify next
	protected WME[] hasher;     //each of these WMEs corresponds to one bit
	protected Random rand = new Random();    //cause we need random numbers
	

    
    /**
     * ctor
     * 
     * @param size    - size of the hash code
     * @param subSize - size of each subsection.  CAVEAT: the code size *must* 
     *                   be evenly divisble by this number
     */
    public LSHashFn(int size, int subSize)
    {
        super(size);
        this.subSize = subSize;
        this.numSubs = size / subSize;
        this.hasher = new WME[this.codeSize];
    }
    
    /**
     * this ctor is for the unit test
     */
    public LSHashFn()
    {
    	this(4,2);
    	this.rand.setSeed(12345);
    }
    
    /**
     * initHasher
     * 
     * initializes a new hasher with random WMEs from the dictionary
     */
    private void initHasher() 
    {
    	for(int i = 0; i < this.hasher.length; ++i) {
			hasher[i] = dict.get(rand.nextInt(dict.size()));
		}
		this.currSub = 0;
    }//initHasher
    
    /**
     * updateHasher
     * 
     * @param dictChanges - how many new WMEs were added to the dictionary
     */
	private void updateHasher(int dictChanges) {
		//If this was the first WME, just init everything
		if (this.dict.size() == dictChanges)
		{
			initHasher();
			return;
		}
		
		//select a random WME from the dictionary for each entry in the curr subsection
		for(int i = 0; i < this.subSize; ++i) {
			int index = this.currSub * this.subSize + i;
			this.hasher[index] = dict.get(rand.nextInt(dict.size()));
		}
		this.currSub = (this.currSub + 1) % this.numSubs;
		
	}//updateHasher

    /**
     * hash
     *
     * (see the comment at the top of this file)
     */
	@Override
    public int[] hash(WME[] episode)
    {
        //Add all the WMEs to the dictionary
		int dictChanges = 0;
        for (WME wme : episode) {
            if (! dict.contains(wme)) {
                dict.add(wme);
                dictChanges++;
            }
        }//for
        
        //If anything was added to the dictionary then update the hasher
        updateHasher(dictChanges);

        //Generate an empty code (all zeroes)
        int[] code = super.hash(episode);

       //insert ones for entries in hasher that are in episode
    	for(int i = 0; i < this.hasher.length; ++i) {
    		for(int j = 0; j < episode.length; ++j) {
    			if (episode[j].equals(hasher[i])) {
    				code[i] = 1;
    				break;
    			}
    		}//for
    	}//for
    	
        return code;
        
    }//hash


	@Override
	public String getName() {
		return "Locale Sensitive Hash Function";
	}
    
}//class