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
	 *. @param entry - the string translation of the word
	 * @return - index of the word
	 */
	public EntryTwo findEntry(WME entry)
	{
		//TODO: Not actually receiving an entry, not getting from dictionary
		EntryTwo ret = dictionary.get(entry);
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
		for(WME entry: episode){			

			if(dictionary.containsKey(entry)){
				dictionary.get(entry).addOccurrence(episodeIndex);
			}
			//if the word does not exist yet, add the word
			else{
				//increment the occurrences in that episode
				EntryTwo e = new EntryTwo(compareType, entry);
				dictionary.put(entry, e);
				e.addOccurrence(episodeIndex);
			}
		}
	}//addEpisode

	
	/**
	 * getRandomEntry
	 * 
	 * @return a randomly selected entry 
	 */
	
	public EntryTwo getRandomEntry()
	{
		int index = rand.nextInt(dictionary.size());
		EntryTwo[] temp = null;
		dictionary.values().toArray(temp);
		return temp[index];
	}	
	
	public ArrayList<EntryTwo> getSortedEntryList(){
		EntryTwo[] temp = new EntryTwo[dictionary.values().size()];
		dictionary.values().toArray(temp);
		ArrayList<EntryTwo> ret = new ArrayList<EntryTwo>(Arrays.asList(temp));
		Collections.sort(ret);
		return ret;
	}

	public int getCompareType(){
		return this.compareType;
	}
	
}
	


