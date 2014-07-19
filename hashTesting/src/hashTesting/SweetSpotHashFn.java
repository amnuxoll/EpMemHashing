package hashTesting;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileWriter;
import java.io.IOException;




/**
 * A hash function that creates a dictionary of the WME's in all of the 
 * episodes and sorts them by number of occurrences to find the most descriptive
 * words for creating a hash function
 * 
 * @author Alexandra Warlen
 * @author Allie Seibert
 * @version Tuesday June 5, 2014
 */
public class SweetSpotHashFn extends HashFn{
	
	
	/**list of all WMEs and their frequencies*/
	protected Dictionary dictionary; 
	
	/**the list of entries that is reflected by the bit values of the hashCode*/
	protected Entry[] hashFormula; 	
	
	/** the current index of the current episode*/
	protected int episodeIndex;	
	
	/** the parts of the wme that will be used for comparison*/
	protected int compareType = WME.ATTR+WME.VAL; 
	
	
	/** the percentage of the sorted dictionary that is ignored*/
	protected double discardFraction = 0.0; 	
	
	/**the number of elements in the dictionary that will be bypassed when 
	 * compiling the hashFormula*/
	protected int discardNumber;
	
	protected final int ENTRY_FOUND = 1;
	
	protected final int ENTRY_NOT_FOUND = 0;
	
	protected int episodeCompletedCount = 0;
	
	
	
	/**
	 * ctor initializes as per the super and creates a dictionary, discardFrac implied at 0.0
	 * @param size
	 */
	public SweetSpotHashFn(int size)
	{
		super(size);
		this.episodeIndex = 0;
		this.hashFormula = new Entry[this.codeSize];
		this.dictionary = new ForgettingDictionary(this.compareType, 10000);
		for(int i = 0; i < this.hashFormula.length; ++i) {hashFormula[i] = null;}
	}//ctor
	
	
	
	/**
	 * ctor initializes as per the super and creates a dictionary
	 * @param size
	 * @param discardFraction
	 */
	public SweetSpotHashFn(int size, double discardFraction)
	{
		this(size);
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
		dictionary.addEpisode(episodeIndex, episode);
		
//		episodeCompletedCount++;
//		
//		if (episodeCompletedCount == 30){
//			//print out dictionary to file
//			FileWriter outputStream = null;
//        	
//        	try {
//        		outputStream = new FileWriter("C:\\Users\\meyer14\\Desktop\\Research\\Raw Data\\SweetSpotDictOutput.txt");
//        		ArrayList<Entry> dict = dictionary.getSortedEntryList();
//        		for(Entry entry : dict){
//        			outputStream.write(entry.getWME().toString());
//        			outputStream.write("\n");
//        		}
//        	} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        	finally {
//        		if (outputStream != null){
//        			try {
//						outputStream.close();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//        		}
//        	}
//		}
			
		//create hashFormula
		compileHashFormula();
		
		
		int[] hashCode = new int[this.codeSize];

		// generate the hashCode
		//TODO: Fix
		for(int i = 0; i < super.codeSize; i++) {
			hashCode[i] = generateBit(i, episode);
		}

		episodeIndex++;
		return hashCode;
	}//hash
	
    
    
	/**
	 * compileHashFormula
	 * 
	 * designs the hash formula
	 */
	protected void compileHashFormula() 
	{
		//find the magic number 
		discardNumber = (int) (discardFraction *dictionary.getSize()); 
		int j;
		
		//determine if hashFormula values are still in the sweetSpot range
		
		boolean[] inSweetSpot = new boolean[this.codeSize];  
		boolean[] inHashFormula = new boolean[this.codeSize];
		for(int i = 0; i < inHashFormula.length; ++i) {inHashFormula[i] = false;}
	
		Entry[] newHashFormula = generateHashFormula();
		
		/*for continuity i is the hashFormula index and j is the
		oldHashFormula index for the next two  for loops*/	
		
		//iterate through the old hashFormula to find matches in the newHashFormula		
		for(int i = 0; i< this.codeSize; i++){
			inSweetSpot[i] = false;
			j=0;
			
			//iterate through newHashFormula for matches if hashFormula[i] is not null
			while(j<this.codeSize && hashFormula[i] != null ){
				
				//check that the current value is not null
				if(newHashFormula[j] != null){
					WME entry1 = hashFormula[i].getWME();
					WME entry2 = newHashFormula[j].getWME();
					
					//if the two values are equal then mark the locations using the boolean values
					if(entry1.equalsWithType(entry2, this.compareType)){
						inSweetSpot[i] = true;
						inHashFormula[j] = true;
						//TODO Update hashFormula??
						break;
					}
				}
				j++;
			}
		}
		
		//replace hashFormula values with new sweetSpot values
		int i = 0;
		for(j = 0; j< this.codeSize &&  j+discardNumber<dictionary.getSize() ; j++){
			if(!inHashFormula[j]){
				while(inSweetSpot[i]){
					i++;
				}
				if(hashFormula[i] == null || noResistance())
					hashFormula[i] = dictionary.getEntryAt(j+discardNumber);
				i++;
			}
		}
		
	}
	

	
    /**
     * generateHashFormula
     */
    protected Entry[] generateHashFormula()
    {
		//find the magic number 
		discardNumber = (int) (discardFraction *dictionary.getSize());

        Entry[] newFormula = new Entry[this.codeSize];

        for(int i = 0; i < this.codeSize; ++i) {    	
        	if(i+discardNumber < dictionary.getSize()){
        		newFormula[i] = dictionary.getEntryAt((i + discardNumber));
        	}
        	else{
        		newFormula[i] = null;
        	}
        	
        }

        return newFormula;

    }//generateHashFormula	
        	
	
    
	/**
	 * generateBit
	 * 
	 * @param int i - the index
	 * @return boolean to return 1
	 */
	protected int generateBit(int i, WME[] episode)
	{
		Entry entry = hashFormula[i];
		ArrayList<WME> ep = new ArrayList<WME>(Arrays.asList(episode));
		return (entry != null && ep.contains(entry.getWME())) ? ENTRY_FOUND : ENTRY_NOT_FOUND;
	}
	
	
    
    /**
	 * noResistance
	 * 
	 * a filter for the resistanceSweetSpot subla
	 * 
	 */
	protected boolean noResistance()
	{
		return true;
	}//noResistance

	
	
	/**
	 * getDictionaryEntry
	 * 
	 * @param index of the dictionary entry
	 * @return returns a string that is the attribute and value of that entry
	 */
	public String getDictionaryEntry(int index)
	{
		String ret = this.dictionary.getEntryAt(index).getWME().getAttrib() +
				" " + this.dictionary.getEntryAt(index).getWME().getVal();
		
		return ret;
	}//getDictionaryEntry
	
	/**
	 * setDictionary
	 * 
	 * @param d The dictionary that the function will use.
	 */
	
	public void setDictionary(Dictionary d){
		this.dictionary = d;
	}
	
	/**
	 * getCompareType
	 * 
	 * @return the compareType of this function
	 *         represented as an integer
	 */
	public int getCompareType(){
		return this.compareType;
	}
	
	/**
	 * getName
	 * 
	 * gives a name for printing
	 */
	public String getName()
	{
		if (! (dictionary instanceof ForgettingDictionary))
		return "Sweet Spot "+ (double)((int)(100*discardFraction))/100.0;
		
		return "Sweet Spot " + (double)((int)(100*discardFraction))/100.0 + " Forget: " + 
				((ForgettingDictionary)dictionary).getCapSize();
	}//getName
	


}//class SweetSpotHashFn
