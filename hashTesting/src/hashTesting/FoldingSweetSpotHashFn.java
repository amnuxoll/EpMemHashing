package hashTesting;

import java.util.ArrayList;
import java.util.Arrays;



/**
 * A hash function that creates a dictionary of the WME's in all of the 
 * episodes and sorts them by number of occurrences to find the most descriptive
 * words for creating a hash function
 * 
 * @author Alexandra Warlen
 * @author Allie Seibert
 * @version Tuesday May 27, 2014
 */

public class FoldingSweetSpotHashFn extends SweetSpotHashFn{
	
	
	/**the list of entries that is reflected by the bit values of the hashCode*/
	protected ArrayList<Entry> hashFormulaArray; 	
	
	
	/**
	 * ctor initializes as per the super and creates a dictionary, discardFrac implied at 0.0
	 * @param size
	 */
	public FoldingSweetSpotHashFn(int size)
	{
		super(size);
		hashFormulaArray = new ArrayList<Entry>();
	}//ctor
	
	
	
	/**
	 * ctor initializes as per the super and creates a dictionary
	 * @param size
	 * @param discardFraction
	 */
	public FoldingSweetSpotHashFn(int size, double discardFraction)
	{
		super(size,discardFraction);
		hashFormulaArray = new ArrayList<Entry>();
	}//ctor

	
	
	@Override
	protected int generateBit(int hashIndex, WME[] episode)
	{
		ArrayList<WME> ep = new ArrayList<WME>(Arrays.asList(episode));
		Entry e = null;
		for(int i=hashIndex; i<hashFormulaArray.size(); i+=this.codeSize){
			e = hashFormulaArray.get(i);
			if(e != null && ep.contains(hashFormulaArray.get(i).getWME()))
				return ENTRY_FOUND;
		}
		return ENTRY_NOT_FOUND;
	}
	

    
	/**
	 * compileHashFormula
	 * 
	 * designs the hash formula
	 */
	@Override
	protected void compileHashFormula() 
	{
		Entry[] newHashFormula = generateHashFormula();
		
		ArrayList<Entry> temp = new ArrayList<Entry>();
		Entry inHashFormulaArray=null; 
		
		for(Entry newEntry: newHashFormula){						
			inHashFormulaArray = newEntry;
			for(Entry entry: hashFormulaArray){	
				if(newEntry == null)
					break;
				else if(newEntry.equals(entry)){
					inHashFormulaArray = null;
					break;
				}
					
			}
			if(inHashFormulaArray != null)
				temp.add(inHashFormulaArray);
		}	
		hashFormulaArray.addAll(temp);

		temp.clear();
	}

	
	
	/**
	 * getName
	 * 
	 * gives a name for printing
	 */
	public String getName()
	{
		return "Folding "+super.getName();
	}//getName
	
}//class FoldingSweetSpotHashFn
