package hashTesting;

import java.text.DecimalFormat;

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
	private double discardFraction = 0.0;
	
	/**
	 * ctor initializes as per the super and creates a dictionary, discardFrac implied at 0.0
	 * @param size
	 */
	public SweetSpotHashFn(int size)
	{
		super(size);
		this.count = 0;
		this.dictionary = new Dictionary(WME.ATTR+WME.VAL);
	}//ctor
	
	/**
	 * ctor initializes as per the super and creates a dictionary
	 * @param size
	 * @param discardFraction
	 */
	public SweetSpotHashFn(int size, double discardFraction)
	{
		super(size);
		this.count = 0;
		this.dictionary = new Dictionary(WME.ATTR+WME.VAL);
		if(discardFraction <= 1.0 && discardFraction >=0.0)
			this.discardFraction = discardFraction;
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
		
		//find the magic number 
		int n = (int) discardFraction *dictionary.getSize(); 
			
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
		return "" + discardFraction;
	}//getName
	
}//class SweetSpotHashFn
