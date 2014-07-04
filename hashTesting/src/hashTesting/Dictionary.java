package hashTesting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * A collection of WMEs and how often each have appeared
 * 
 * @author Alexandra Warlen
 * @author Allie Seibert
 * @version May 2014
 */
public class Dictionary
{
	/* compareType specifies the parts of the WME to consider when comparing two
     * WMEs (@see WME#equals)*/
	private int compareType;
	
	/* the dictionary catalogs the words that appear in the episodes with their
	 * respective occurrences
	 */
	private ArrayList<Entry> dictionary;
	
	//because random
	Random rand = new Random();
	
	
	
	/**
	 * This ctor is designed to be used by hash functions
	 * 
	 * @param compareType
	 */
	
	public Dictionary (int compareType)
	{
		this.dictionary = new ArrayList<Entry>();
		this.compareType = compareType;
		
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
	 * @param entry - the string translation of the word
	 * @return - index of the word
	 */
	public int findWordLoc(WME entry)
	{
		for(int i=0; i<dictionary.size(); i++){
			if(entry.equalsWithType(dictionary.get(i).entry, this.compareType)){
				return i;
			}
		}
		
		return -1;
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
		int wordLoc;
		
		//recurse through episodes
		for(WME entry: episode){
			
			wordLoc = findWordLoc(entry);
			
			//if the word does not exist yet, add the word
			if(wordLoc < 0){
				Entry temp = new Entry(compareType,entry);
				dictionary.add(temp);
				temp.addOccurrence(episodeIndex);
			}
			else{
				//increment the occurrences in that episode
				dictionary.get(wordLoc).addOccurrence(episodeIndex);
			}
		}
		
		Collections.sort(dictionary);
	}//addEpisode
	
	/**
	 * getEntryAt
	 * 
	 * returns the nth most frequently occurring word
	 * 
	 * @param n
	 * @return
	 */
	
	public Entry getEntryAt(int n)
	{
		return dictionary.get(n);
	}
	
	/**
	 * getRandomEntry
	 * 
	 * @return a randomly selected entry 
	 */
	
	public Entry getRandomEntry()
	{
		int index = rand.nextInt(this.getSize());
		return dictionary.get(index);
	}	
	
}
	

