package hashTesting;

import java.util.ArrayList;
import java.util.Collections;

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
	
	/**
	 * ctor
	 * 
	 * 
	 * @param episodeList
	 * @param compareType
	 */
	
	public Dictionary (ArrayList<WME[]> episodeList, int compareType)
	{
		this.dictionary = new ArrayList<Entry>();
		this.compareType = compareType;
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
				dictionary.add(new Entry(compareType,entry));
				wordLoc = dictionary.size() - 1;
			}
			
			//increment the occurrences in that episode
			dictionary.get(wordLoc).addOccurrence(episodeIndex);
			
		}
		
		//resort
		//sortByOccurrence(0, dictionary.size());
		
		Collections.sort(dictionary);
	}//addEpisode
	
	
	/**
	 * sortByOccurrence
	 * 
	 *  recursively performs merge sort on the dictionary so that it is ordered
	 *  from largest number of occurrences to smallest. 
	 *  
	 * @param start - beginning index, inclusive
	 * @param end - end index, exclusive
	 * @return
	 */
	private ArrayList<Entry> sortByOccurrence(int start, int end)
	{
		if(end-start >= 2){
			int midpoint = (start + end) /2;
			
			// sorting each half of the list and creating a copy of each half
			ArrayList<Entry> aList = sortByOccurrence(start, midpoint);
			ArrayList<Entry> bList = sortByOccurrence(midpoint, end);
			
			int aIndex = 0;
			int bIndex = 0;
			
			int i = start;
			
			//merges the two lists
			while(i<end){
		
				// get the occurrences of each element for comparison
				int aOccur = aList.get(aIndex).getSumOccurrences();
				int bOccur = bList.get(bIndex).getSumOccurrences();
	
				//whichever element has the most occurrences insert in dictionary
				//and iterate to observe the next element in that list
				if( aOccur > bOccur){
					dictionary.set(i, aList.get(aIndex));
					aIndex++;
				}
				else{
					dictionary.set(i, bList.get(bIndex));
					bIndex++;
				}
				i++;
			}
			
		}
		return new ArrayList<Entry>(dictionary.subList(start, end));
	}
	
	/**
	 * getEntryAt
	 * 
	 * returns the nth most frequently occuring word
	 * 
	 * @param n
	 * @return
	 */
	
	public Entry getEntryAt(int n)
	{
		return dictionary.get(n);
	}
	
}
	

