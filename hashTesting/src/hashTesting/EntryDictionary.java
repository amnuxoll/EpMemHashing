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
public class EntryDictionary
{
	/* compareType specifies the parts of the WME to consider when comparing two
     * WMEs (@see WME#equals)*/
	private int compareType;
	
	/* the dictionary catalogs the words that appear in the episodes with their
	 * respective occurrences
	 */
	private Map<WME, EntryTwo> dictionary;
	
	//because random
	Random rand = new Random();
	
	
	
	/**
	 * This ctor is designed to be used by hash functions
	 * 
	 * @param compareType
	 */
	
	public EntryDictionary (int compareType)
	{
		this.dictionary = new HashMap<WME, EntryTwo>();
		this.compareType = compareType;
		
	}//ctor

	/**
	 * This ctor takes an entire dictionary (used for unit testing and similar)(
	 * 
	 * @param episodeList
	 * @param compareType - how to compare two WMEs, (@see WME#equals)
	 */
	
	public EntryDictionary (ArrayList<WME[]> episodeList, int compareType)
	{
		this(compareType);

		//adds the WME's to the dictionary by episode
		for(int i = 0; i < episodeList.size(); i++){
			addEpisode(i, episodeList.get(i));
		}
		
	}//ctor
	
	public int getSize(){
		return dictionary.size();
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
	public EntryTwo findEntry(WME wme)
	{
		//TODO: Not actually receiving an entry, not getting from dictionary
		EntryTwo ret = dictionary.get(wme);
		return ret;
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

			if(dictionary.containsKey(wme)){
				dictionary.get(wme).addOccurrence(episodeIndex);
			}
			//if the word does not exist yet, add the word
			else{
				//increment the occurrences in that episode
				EntryTwo e = new EntryTwo(compareType, wme);
				dictionary.put(wme, e);
				e.addOccurrence(episodeIndex);
			}
		}
	}//addEpisode

	
	/**
	 * getRandomEntry
	 * 
	 * CAVEAT:  This method is pretty expensive...
	 * 
	 * @return a randomly selected entry 
	 */
	
	public EntryTwo getRandomEntry()
	{
		int index = rand.nextInt(dictionary.size());
		EntryTwo[] temp = new EntryTwo[dictionary.size()];
		dictionary.values().toArray(temp);
		return temp[index];
	}	
	
	/**
	 * @return all the entries in this dictionary in sorted by frequency (primary key) and recency (secondary key) 
	 */
	public ArrayList<EntryTwo> getSortedEntryList(){
		ArrayList<EntryTwo> ret = new ArrayList<EntryTwo>(dictionary.values());
		Collections.sort(ret);
		return ret;
	}

	public int getCompareType(){
		return this.compareType;
	}
	
}
	


