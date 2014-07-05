package hashTesting;

/**
 * Tracks most recent occurrence of a particular WME that has appeared in at least one
 * episode.  Tracks how many times it has appeared in all visited episodes.
 * 
 * @author Andrew Meyer
 * @author Kevin Bastien
 * @version July 2014
 */
public class Entry implements Comparable<Entry> {

	/** the word in question*/
	protected WME wme;
	
	/** compareType specifies the parts of the WME to consider when comparing two
     * WMEs (@see WME#equals)*/
	protected int compareType;
	
	/**
	 * numOccurrences determines the frequency of a given WME
	 */
	private int numOccurrences = 0;
	
	/**
	 * mostRecentEpisode determines the most recent episode that occurs 
	 */
	private int mostRecentEpisode = -1;
	
	/**
	 * ctor
	 * 
	 * @param comparetype
	 * @param entry
	 */
	public Entry(int compareType, WME wme)
	{
		this.compareType = compareType;
		this.wme = wme;

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
		numOccurrences+=1;
		mostRecentEpisode = (episodeIndex >= mostRecentEpisode) ? episodeIndex : mostRecentEpisode;
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
	 * @return  mostRecentEpisode of type int
	 */
	public int getMostRecentOccurrence()
	{
		return this.mostRecentEpisode;
	}
	
	/**
	 * allows public access to entry variable.
	 * @return WME entry
	 */
	public WME getWME()
	{
		return this.wme;
	}
	
	/**
	 * getNumOccurrences
	 * 
	 * returns the total number of occurrences of the specified WME
	 * 
	 * @return sum
	 */
	public int getNumOccurrences()
	{		
		return this.numOccurrences;
	}

	/** compares two entries based upon their total number of occurrences and their most recent occurrence*/ 
	@Override
	public int compareTo(Entry otherEntry)
	{
		int myOccur = this.getNumOccurrences();
		int otherOccur = otherEntry.getNumOccurrences();
		if((otherOccur - myOccur) == 0){
			return otherEntry.getMostRecentOccurrence() - this.getMostRecentOccurrence(); 
		}
		return otherOccur - myOccur;
	}
	
	public String toString(){
		return ("Entry: " + this.wme.toString() + ", Compare Type: " + this.compareType +
				", NumOccurences: " + this.numOccurrences + ", Episode: " + this.mostRecentEpisode);
	}
	
}//class EntryTwo
