package hashTesting;

/**
 * A hash function that creates a dictionary of the WME's in all of the 
 * episodes and sorts them by number of occurrences to find the most descriptive
 * words for creating a hash function
 * 
 * @author Alexandra Warlen
 * @author Allie Seibert
 * @version Tuesday May 27, 2014
 */
public class ResistanceSweetSpotHashFn extends SweetSpotHashFn{

	private double resistance = 1.0;
	
	/**
	 * ctor initializes as per the super and creates a dictionary, discardFrac implied at 0.0
	 * @param size
	 */
	public ResistanceSweetSpotHashFn(int size)
	{
		super(size);
	}//ctor
	
	
	
	/**
	 * ctor initializes as per the super and creates a dictionary
	 * @param size
	 * @param discardFraction
	 */
	public ResistanceSweetSpotHashFn(int size, double discardFraction)
	{
		super(size, discardFraction);
	}//ctor
	
	/**
	 * ctor initializes as per the super and creates a dictionary
	 * @param size
	 * @param discardFraction
	 */
	public ResistanceSweetSpotHashFn(int size, double discardFraction, double resistance)
	{
		super(size, discardFraction);
		this.resistance = resistance;
	}//ctor
	    
	/**
	 * compileHashFormula
	 * 
	 * designs the hash f
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
	
		Entry[] newHashFormula = new Entry[this.codeSize];
		
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
					WME entry1 = hashFormula[i].getEntry();
					WME entry2 = newHashFormula[j].getEntry();
						
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
				if(Math.random() < (1.0/Math.pow(episodeIndex, resistance)) || hashFormula[i] == null){
					hashFormula[i] = dictionary.getEntryAt(j+discardNumber);
				}
				i++;
			}
		}
		
	}
		
		
	/**
	 * getName
	 * 
	 * gives a name for printing
	 */
	public String getName()
	{
		return ""+resistance;
		//return ""+(double)((int)(100*discardFraction))/100.0;
	}//getName
		
}//class ResistantSweetSpotHashFn
