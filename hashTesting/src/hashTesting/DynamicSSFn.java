package hashTesting;


public class DynamicSSFn extends ModularFSSHFn {

	public DynamicSSFn(int size, double discardFraction, double modSize) {
		super(size, discardFraction, modSize);
	}
	
	/**
     * generateHashFormula
     */
	@Override
    protected Entry[] generateHashFormula()
    {
		//find the magic number 
		discardNumber = (int) (discardFraction * dictionary.getSize());

		int sweetSpotSize = (int) (dictionary.getSize() * this.modSize);
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
	

}
