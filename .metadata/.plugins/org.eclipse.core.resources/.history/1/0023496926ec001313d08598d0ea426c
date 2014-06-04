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

public class SweetSpotHashFn extends HashFn{
	
	
	private Dictionary dictionary;         //list of all WMEs and their frequencies
	private int count;
	private double discardFraction = 0.0;
	private Entry[] hashFormula;
	private int compareType = WME.ATTR+WME.VAL;
	private int n;
	
	
	
	/**
	 * ctor initializes as per the super and creates a dictionary, discardFrac implied at 0.0
	 * @param size
	 */
	public SweetSpotHashFn(int size)
	{
		super(size);
		this.count = 0;
		this.hashFormula = new Entry[this.codeSize];
		this.dictionary = new Dictionary(this.compareType);
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
		dictionary.addEpisode(count, episode);
			
		//create hashFormula
		compileHashFormula();
		
		// compile hashCode
		int[] hashCode = new int[this.codeSize];
		Entry entry = null;  //the current entry in the dictionary
		for(int i = 0; i < super.codeSize; i++) {
			if(i+n < dictionary.getSize()){
				entry = hashFormula[i];
				if(entry.occursIn(count) && entry != null) {
					hashCode[i] = 1;
				}
				else{
					hashCode[i] = 0;
				}
			}
			else{
				hashCode[i]=0;
			}
		}

		count++;

		return hashCode;
	}//hash
	

    /**
     * generateHashFormula
     */
    private Entry[] generateHashFormula()
    {
		//find the magic number 
		n = (int) (discardFraction *dictionary.getSize());

        Entry[] newFormula = new Entry[this.codeSize];

        for(int i = 0; i < this.codeSize; ++i) {
        	newFormula[i] = dictionary.getEntryAt((i + n) % dictionary.getSize());
        }

        return newFormula;

    }//generateHashFormula
    

	/**
	 * compileHashFormulaNux
	 * 
	 * designs the hash f
	 */
	private void compileHashFormulaNux() 
	{
        Entry[] formulaEntries = generateHashFormula();

        //For each entry in the old  hash formula that's not in the new one,
        //set it to null.  Otherwise, set the entry in the new formula to null
        for(int i = 0; i < this.hashFormula.length; ++i) {
            WME oldWME = this.hashFormula[i].getEntry();
            boolean found = false;
            for(int j = 0; j < formulaEntries.length; ++j) {
                WME newWME = formulaEntries[j].getEntry();
                if (oldWME.equalsWithType(newWME, this.compareType)) {
                    found = true;
                    break;
                }
            }//for

            if (!found)
            {
                this.hashFormula[i] = null;
            }
                
        }//for

        //For each entry in the new hash formula that's not in the old one,
        //insert it in a null spot
        for(int j = 0; j < formulaEntries.length; ++j) {
            WME newWME = formulaEntries[j].getEntry();
            boolean found = false;
            for(int i = 0; i < this.hashFormula.length; ++i) {
                WME oldWME = this.hashFormula[i].getEntry();
                if (oldWME == null) continue;
                else if (oldWME.equalsWithType(newWME, this.compareType))
                {
                    found = true;
                }
            }

            //ok insert it
            if (!found) {
                for(int i = 0; i < this.hashFormula.length; ++i) {
                    if (this.hashFormula[i] == null) {
                        this.hashFormula[i] = formulaEntries[j];
                        break;
                    }
                }//for
            }//if
        }//for

    }//compileHashFormula
    
	/**
	 * compileHashFormula
	 * 
	 * designs the hash f
	 */
	private void compileHashFormula() 
	{
		//find the magic number 
		n = (int) (discardFraction *dictionary.getSize()); 
		int j;
		
		
		//determine if hashFormula values are still in the sweetSpot range
		boolean[] inSweetSpot = new boolean[this.codeSize];  
		boolean[] inHashFormula = new boolean[this.codeSize];
		for(int i = 0; i < inHashFormula.length; ++i) {inHashFormula[i] = false;}
		
		for(int i = 0; i< this.codeSize; i++){
			inSweetSpot[i] = false;
			j=0;
			
			while(j<this.codeSize && j+n< dictionary.getSize()){
				if(hashFormula[i] != null )
				{
					WME entry1 = hashFormula[i].getEntry();
					WME entry2 = dictionary.getEntryAt(j+n).getEntry();
					
					if(entry1.equalsWithType(entry2, this.compareType)){
						inSweetSpot[i] = true;
						inHashFormula[j] = true;
						break;
					}
							
				}
				else
				{
					break;
				}
				j++;
			}
		}
		
		//replace hashFormula values with new sweetSpot values
		int i = 0;
		for(j = 0; j< this.codeSize &&  j+n<dictionary.getSize() ; j++){
			if(!inHashFormula[j]){
				
				while(inSweetSpot[i]){
					i++;
				}
				hashFormula[i] = dictionary.getEntryAt(j+n);
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
		return "Sweet Spot(" + (double)((int)(100*discardFraction))/100.0 + ")";
	}//getName
	
}//class SweetSpotHashFn
