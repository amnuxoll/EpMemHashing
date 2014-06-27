package hashTesting;


/**
 * Abstract class HashFn to be implemented in subclasses.  Will hash events into
 * a hashcode to later be stored in a hashcode list.
 * 
 * 
 * @author Alexandra Warlen
 * @author Allie Seibert
 * @version Friday May 16, 2014
 */


public abstract class HashFn
{
    public static final int CODE_SIZE = 32;
    //The number of bits in the hash code
    protected int codeSize = CODE_SIZE;


    /** ctor
     *
     * @param size the required hash code size
     */
    public HashFn(int size)
    {
        this.codeSize = size;
        
    }
    
    /**
     * Hash method.  To be implemented in subclasses.
     *
     */
    public abstract int[] hash(WME[] episode);
    
    public abstract String getName();
    
}//class HashFn
