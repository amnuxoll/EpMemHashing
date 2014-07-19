package hashTesting;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.Random;
import java.util.ArrayList;

/**
 * A collection of WMEs and how often each have appeared
 * 
 * @author Andrew Meyer
 * @author Kevin Bastien
 * @version 6/29/2014
 */
public class Dictionary
{
	/* compareType specifies the parts of the WME to consider when comparing two
     * WMEs (@see WME#equals)*/
	protected int compareType;
	
	/* the dictionary catalogs the words that appear in the episodes with their
	 * respective occurrences
	 */
	protected Map<WME, Entry> dictionary;
	
	protected ArrayList<Entry> sortedEntries;
	
	//because random
	Random rand = new Random();
	
	
	
	/**
	 * This ctor is designed to be used by hash functions
	 * 
	 * @param compareType
	 */
	
	public Dictionary (int compareType)
	{
		this.dictionary = new HashMap<WME, Entry>();
		this.compareType = compareType;
		this.sortedEntries = new ArrayList<Entry>();
		
	}//ctor

	/**
	 * This ctor takes an entire dictionary (used for unit testing and similar)(
	 * 
	 * @param episodeList
	 * @param compareType - how to compare two WMEs, (@see WME#equals)
	 */
	
	public Dictionary (ArrayList<WME[]> episodeList, int compareType)
	{
		this(compareType);
		if(episodeList == null) return;
		//adds the WME's to the dictionary by episode
		for(int i = 0; i < episodeList.size(); i++){
			addEpisode(i, episodeList.get(i));
		}
		
	}//ctor
	
	public int getSize(){
		return this.sortedEntries.size();
	}

	
	/**
	 * findWordLoc
	 * 
	 * returns the location of the of a WME in the dictionary
	 * 
	 * @param wme - the key linked to a desired Entry
	 *
	 * @return - the desired entry or null if not found 
	 */
	public Entry findEntry(WME wme)
	{
		return this.dictionary.get(wme);
	}
	
	
	public Entry getEntryAt(int index){ 
		return sortedEntries.get(index);
	}
	
	/**
	 * addEpisode
	 * 
	 * adds the WME's of the episode to the dictionary
	 * 
	 * @param episodeIndex
	 * @param episode
	 */
	public void addEpisode(int episodeIndex, WME[] episode)
	{
		
		//recurse through episodes
		for(WME wme: episode){			

			if(this.dictionary.containsKey(wme)){
				this.dictionary.get(wme).addOccurrence(episodeIndex);
			}
			//if the word does not exist yet, add the word
			else{
				//increment the occurrences in that episode
				Entry e = new Entry(this.compareType, wme);
				this.dictionary.put(wme, e);
				e.addOccurrence(episodeIndex);
			}
		}
		
		this.sortedEntries = this.getSortedEntryList();
	}//addEpisode

	
	/**
	 * getRandomEntry
	 * 
	 * CAVEAT:  This method is pretty expensive...
	 * 
	 * @return a randomly selected entry 
	 */
	
	public Entry getRandomEntry()
	{
		int index = rand.nextInt(this.dictionary.size());
		Entry[] temp = new Entry[this.dictionary.size()];
		this.dictionary.values().toArray(temp);
		return temp[index];
	}	
	
	/**
	 * @return all the entries in this dictionary in sorted by frequency (primary key) and recency (secondary key) 
	 */
	public ArrayList<Entry> getSortedEntryList(){
		ArrayList<Entry> ret = new ArrayList<Entry>(this.dictionary.values());
		Collections.sort(ret);
		return ret;
	}

	public int getCompareType(){
		return this.compareType;
	}
	
}
	


