package hashTesting;

/**
 * A dummy hash function class for testing
 * 
 * @author Nux
 * @version 15 May 2014
 */
public class DummyHashFn extends HashFn
{
    //ctor
    public DummyHashFn(int size)
    {
        super(size);
    }
    

    /**
     * hash
     *
     * simple hash function always returns {0,0,0,...}
     */
    public int[] hash(WME[] episode)
    {
        // create array to represent 32 bit number
        int[] retArray = new int[codeSize];
        
        // set all values to zero
        for (int i = 0; i< codeSize; i++) {
            retArray[i] = 0;
        }
        
        return retArray;
    }


	@Override
	public String getName() {
		return "Dummy Hash Function";
	}
}
