package hashTesting;

import java.util.ArrayList;

public class Dictionary
{
	
	/*occurrences are an array of int[2] such that:
	 * digit[0] - the episode  
	 * digit[1] - occurrences of the word in the episode specified by digit[0]
	 */
	private int typeOfWord;
	
	/* the dictionary catalogs the words that appear in the episodes with their
	 * respective occurrences
	 */
	private ArrayList<Word> dictionary;
	
	/**
	 * ctor
	 * 
	 * 
	 * @param episodeList
	 * @param wordType
	 */
	
	public Dictionary (ArrayList<WME[]> episodeList, int wordType)
	{
		dictionary = new ArrayList<Word>();
		typeOfWord = wordType;
		//adds the WME's to the dictionary by episode
		for(int i = 0; i < episodeList.size(); i++){
			addEpisode(i, episodeList.get(i));
		}
		
	}//ctor
	
	/**
	 * findWordLoc
	 * 
	 * returns the location of the of the word in the dictionary
	 * 
	 * @param entry - the string translation of the word
	 * @return - index of the word
	 */
	public int findWordLoc(String entry)
	{
		for(int i=0; i<dictionary.size(); i++){
			if(entry.equalsIgnoreCase(dictionary.get(i).word)){
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
			
			wordLoc = findWordLoc(Word.toWord(typeOfWord, entry));
			
			//if the word does not exist yet, add the word
			if(wordLoc < 0){
				dictionary.add(new Word(typeOfWord,entry));
				wordLoc = dictionary.size() - 1;
			}
			
			//increment the occurrences in that episode
			dictionary.get(wordLoc).addOccurrence(episodeIndex);
			
		}
		
		//resort
		sortByOccurrence(0, dictionary.size());
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
	private ArrayList<Word> sortByOccurrence(int start, int end)
	{
		if(end-start >= 2){
			int midpoint = (start + end) /2;
			
			// sorting each half of the list and creating a copy of each half
			ArrayList<Word> aList = sortByOccurrence(start, midpoint);
			ArrayList<Word> bList = sortByOccurrence(midpoint, end);
			
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
		return (ArrayList<Word>) dictionary.subList(start, end);
	}
	
	/**
	 * getWordAt
	 * 
	 * returns the nth most frequently occuring word
	 * 
	 * @param n
	 * @return
	 */
	
	public Word getWordAt(int n)
	{
		sortByOccurrence(0, dictionary.size());
		return dictionary.get(n);
	}
	
}
	

