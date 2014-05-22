
package hashTesting;

import java.util.Random;

/**
 * A dummy hash function class for testing
 * 
 * @author Nux
 * @author Alexandra Warlen
 * @author Allie Seibert
 * @version Friday May 16, 2014
 */
public class RandomHashFn extends HashFn
{
	
    //ctor
    public RandomHashFn(int size)
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
        	Random rand = new Random();
            retArray[i] = rand.nextInt(2);
        }
        
        return retArray;
    }


	public String getName() {
		return "Random Hash Function";
	}
}
