package hashTesting;

/**
 * A hash function that creates a dictionary of the WME's in all of the 
 * episodes and sorts them by number of occurrences to find the most descriptive
 * words for creating a hash function
 * 
 * @author Alexandra Warlen
 * @author Allie Seibert
 * @version Tuesday May 27, 2014
 */

public class SweetSpotHashFn extends HashFn{
	
	
	private Dictionary dictionary;         //list of all WMEs and their frequencies
	private int count;
	
	/**
	 * ctor initializes as per the super and creates a dictionary
	 * @param size
	 */
	public SweetSpotHashFn(int size)
	{
		super(size);
		count = 0;
		dictionary = new Dictionary(WME.ATTR+WME.VAL);
		
	}//ctor
	
	
	/**
	 * hash
	 * 
	 * compiles a dictionary, determines the number of words (n) it should not use
	 * creates a hash formula based off the words below the top n
	 * 
	 */
	public int[] hash(WME[] episode)
	{
		//compile dictionary
		dictionary.addEpisode(count, episode);
		
		//find the magic number  //TODO EXPAND probably will be its own function
		int n = 0; 
		
		
		//create hashFormula and compile hashCode
		int[] hashCode = new int[this.codeSize];
		Entry entry = null;  //the current entry in the dictionary
		for(int i = 0; i < super.codeSize; i++) {
			if(i+n < dictionary.getSize()){
				entry = dictionary.getEntryAt(i+n);
				if(entry.occursIn(count)) {
					hashCode[i] = 1;
				}
				else{
					hashCode[i] = 0;
				}
			}
			else{
				hashCode[i]=0;
			}
		}

		count++;

		return hashCode;
	}//hash
	
	
	/**
	 * getName
	 * 
	 * gives a name for printing
	 */
	public String getName()
	{
		return "Sweetspot Hash Function";
	}//getName
	
}//class SweetSpotHashFn
