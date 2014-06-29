package hashTesting;

import java.util.ArrayList;

public class ModularFSSHFn extends FoldingSweetSpotHashFn {
	
	private double modSize = 1.0;

	public ModularFSSHFn(int size, double discardFraction, double modSize){
		super(size, discardFraction);
		this.modSize = modSize;
	}
	
	public ModularFSSHFn(int size, double discardFraction) {
		super(size, discardFraction);
		this.modSize = 1.0;
		// TODO Auto-generated constructor stub
	}

	public ModularFSSHFn(int size) {
		super(size);
		this.modSize = 1.0;
		// TODO Auto-generated constructor stub
	}
	
	/**
     * generateHashFormula
     */
    protected Entry[] generateHashFormula()
    {
		//find the magic number 
		discardNumber = (int) (discardFraction *dictionary.getSize());

		int sweetSpotSize = (int) (this.codeSize * this.modSize);
        Entry[] newFormula = new Entry[sweetSpotSize];

        for(int i = 0; i < sweetSpotSize; ++i) {    	
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
			if (newEntry == null) continue;
			inHashFormulaArray = newEntry;
			if(hashFormulaArray.contains(newEntry))
					inHashFormulaArray = null;
				
		
			if(inHashFormulaArray != null)
				temp.add(inHashFormulaArray);
		}	
		hashFormulaArray.addAll(temp);

		temp.clear();
	}
	
	public String getName(){
		return "Modular (" + modSize * 100 + "%) " + super.getName();
	}
}
