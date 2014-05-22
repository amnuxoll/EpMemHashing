package hashTesting;

import java.util.ArrayList;

public class Word {

	/* the word in question*/
	protected String word;
	
	/*occurrences are an array of int[2] such that:
	 * digit[0] - the episode  
	 * digit[1] - occurrences of the word in the episode specified by digit[0]
	 */
	protected ArrayList<int[]> occurrences;
	
	
	/*typeOfWord specifies the part of the WME the dictionary categorizes*/
	protected int typeOfWord;
	
	/*catagories for typeOfWord*/
	public static final int ID = 0;
	public static final int ATTR = 1;
	public static final int VAL = 2;
	public static final int VAL_ATTR = 3;
	
	/**
	 * ctor
	 * 
	 * @param wordType
	 * @param entry
	 */
	public Word(int wordType, WME entry)
	{
		typeOfWord = wordType;
		word = toWord(typeOfWord, entry);
		occurrences = new ArrayList<int[]>();
	}
	
	
	/**
	 * toWord
	 * 
	 * translates a WME into a word string specified by wordType,
	 * this is static so that it may be used in Dictionary
	 * 
	 * @param wordType
	 * @param entry
	 * @return
	 */
	public static String toWord(int wordType, WME entry)
	{
			if (wordType == ID){
				return entry.id.toString();
			}
			if (wordType == ATTR){
				return entry.attribute;
			}
			if (wordType == VAL){
				return entry.value;
			}
			if (wordType == VAL_ATTR){
				return entry.attribute + " " + entry.value;
			}
			System.err.print("Incorrect Word Type");
			return null; //TODO
	}
	
	
	/**
	 * addOccurrences
	 * 
	 * increments the number of occurrences for an entry
	 * 
	 * @param episodeIndex
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
	 * getSumOccurences
	 * 
	 * returns the total number of occurences of the specified word
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
	

	
}
