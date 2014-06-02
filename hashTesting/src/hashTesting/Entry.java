package hashTesting;

import java.util.ArrayList;

/**
 * Tracks all occurances of a particular WME that has appeared in at least one
 * episode.  In particular, tracks how many times it has appeared in a given
 * episode and also how many times in any episode.
 * 
 * @author Alexandra Warlen
 * @author Allie Seibert
 * @version May 2014
 */
public class Entry implements Comparable<Entry> {

	/** the word in question*/
	protected WME entry;
	
	/** an occurrence of WME is represented by an array of int[2] such that:
	 * digit[0] - the episode where it occured
	 * digit[1] - num of occurrences in the episode specified by digit[0]
	 */
	protected ArrayList<int[]> occurrences;
	
	
	/** compareType specifies the parts of the WME to consider when comparing two
     * WMEs (@see WME#equals)*/
	protected int compareType;
	
	/**
	 * ctor
	 * 
	 * @param comparetype
	 * @param entry
	 */
	public Entry(int compareType, WME entry)
	{
		this.compareType = compareType;
		this.entry = entry;
		this.occurrences = new ArrayList<int[]>();
	}
	
	/**
	 * addOccurrence
	 * 
	 * increments the number of occurrences for an entry
	 * 
	 * @param episodeIndex the episode where it occurred
	 */
	public void addOccurrence(int episodeIndex)
	{
		for(int[] digit: occurrences){
			//if this is the episode we want to increment, do so and exit
			if (digit[0] == episodeIndex)
			{
				digit[1]++;
				return;
			}
		}
		
		//if no occurrences have been found for this episode, add a new digit
		int[] newOccurrence = {episodeIndex, 1}; 
		occurrences.add(newOccurrence);
		
	}
	
	/**
	 * allows public access to compareType variable.
	 * @return int value of compare type
	 */
	public int getCompType()
	{
		return this.compareType;
	}
	
	/**
	 * allows public access to occurrences.
	 * @return  occurrences of type ArrayList<int[]> 
	 */
	public ArrayList<int[]> getOccurrences()
	{
		return this.occurrences;
	}
	
	/**
	 * allows public access to entry variable.
	 * @return WME entry
	 */
	public WME getEntry()
	{
		return this.entry;
	}
	
	/**
	 * getSumOccurences
	 * 
	 * returns the total number of occurrences of the specified word
	 * 
	 * 
	 * @return sum
	 */
	
	public int getSumOccurrences()
	{
		int sum = 0;
		for(int[] digit: occurrences){
			sum+= digit[1];
		}
		
		return sum;
	}

	/** compares two entries based upon their total number of occurrences */ 
	@Override
	public int compareTo(Entry otherEntry)
	{
		int myOccur = this.occurrences.size();
		int otherOccur = otherEntry.occurrences.size();
		
		return otherOccur - myOccur;
	}
	
	//TODO header
	public boolean occursIn (int episodeIndex)
	{
		for (int[] digit: occurrences){
			if(digit[0] == episodeIndex){
				return true;
			}
		}
		return false;
	}
	
	
	
	

	
}//class Entry
