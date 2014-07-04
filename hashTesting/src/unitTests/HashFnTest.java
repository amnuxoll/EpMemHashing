package unitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import hashTesting.*;


/**
 * The test class HashTest tests the various methods in the HashFn class.
 *
 * @author  Alexandra Warlen
 * @version Wednesday May 14, 2014
 */
public class HashFnTest extends HashFn
{
    /**
     * Default constructor for test class HashTest
     */
    public HashFnTest()
    {
        super(HashFn.CODE_SIZE);
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }
    
    
    /**
     * Tests hash() method in HashFn class.
     *
     */
    @Test
    public void testRandomHashFn()
    {
        // create test episode
        WME testWME = new WME();
        WME test2WME = new WME();
        WME[] episode = new WME[2];
        episode[0] = testWME;
        episode[1] = test2WME;
        
        // check to make sure each index in array of 32 bits has been initialized to 0.
        RandomHashFn fn = new RandomHashFn(HashFn.CODE_SIZE);
        int[] test = fn.hash(episode);

        assertTrue(test[0] == 0 || test[0] == 1);
        assertTrue(test[super.codeSize-1] == 0 || test[super.codeSize-1] == 1);
    }
    
    
    @Test
    public void testDummyHashFn()
    {
    	
        // create test episode
        WME testWME = new WME();
        WME test2WME = new WME();
        WME[] episode = new WME[2];
        episode[0] = testWME;
        episode[1] = test2WME;
        
        // check to make sure each index in array of 32 bits has been initialized to 0.
        DummyHashFn fn = new DummyHashFn(HashFn.CODE_SIZE);
        int[] test = fn.hash(episode);

        assertEquals(test[0], 0);
        assertEquals(test[super.codeSize-1], 0);
    }
    
    
    /**
     * tests the hash() method in FoldingHashFn
     */
    @Test
    public void testFoldingHashFn()
    {
        // create first episode
        WME[] ep = new WME[3];
        ep[0] = new WME("(S1 ^foo 1)");  //index 0
        ep[1] = new WME("(S1 ^bar 2)");  //index 1
        ep[2] = new WME("(S1 ^baz 3)");  //index 2

        //Test first episode
        FoldingHashFn fn = new FoldingHashFn(4);
        int[] test = fn.hash(ep);
        assertEquals(test[0], 1);
        assertEquals(test[1], 1);
        assertEquals(test[2], 1);
        assertEquals(test[3], 0);

        // create second episode
        ep = new WME[3];
        ep[0] = new WME("(S1 ^foo 44)");  //index 3
        ep[1] = new WME("(S1 ^bar 2)");   //index 1
        ep[2] = new WME("(S1 ^baz 3)");   //index 2

        //Test second episode
        test = fn.hash(ep);
        assertEquals(test[0], 0);
        assertEquals(test[1], 1);
        assertEquals(test[2], 1);
        assertEquals(test[3], 1);

        // create third episode
        ep = new WME[3];
        ep[0] = new WME("(S1 ^foo 44)"); //index 3
        ep[1] = new WME("(S1 ^bar 2)");  //index 1
        ep[2] = new WME("(S1 ^baz 55)"); //index 0 (reused)

        //Test second episode
        test = fn.hash(ep);
        assertEquals(test[0], 1);
        assertEquals(test[1], 1);
        assertEquals(test[2], 0);
        assertEquals(test[3], 1);

    }//testFoldingHashFn
    
    /**
     * tests the hash() method in SweetSpotHashFn
     */
    @Test
    public void testSweetSpotHashFn()
    {
    	SweetSpotHashFn fn = new SweetSpotHashFn(4);
    	ArrayList<WME[]> testList = new ArrayList<WME[]>();
		testList = MainTest.makeQuickEpList2();
		ArrayList<int[]> hashCodeList = new ArrayList<int[]>();
		
		for(int i = 0; i<testList.size(); i++){
			hashCodeList.add(fn.hash(testList.get(i)));
		}
		
		assertEquals(hashCodeList.get(0)[0], 1);
		assertEquals(hashCodeList.get(0)[1], 1);
		assertEquals(hashCodeList.get(0)[2], 0);
		assertEquals(hashCodeList.get(0)[3], 0);
		
		assertEquals(hashCodeList.get(1)[0], 0);
		assertEquals(hashCodeList.get(1)[1], 0);
		assertEquals(hashCodeList.get(1)[2], 1);
		assertEquals(hashCodeList.get(1)[3], 1);
		
		//Now re-add the second episode.  It's WMEs should "bubble" to the top
		hashCodeList.add(fn.hash(testList.get(1)));
		assertEquals(hashCodeList.get(2)[0], 0);
		assertEquals(hashCodeList.get(2)[1], 0);
		assertEquals(hashCodeList.get(2)[2], 1);
		assertEquals(hashCodeList.get(2)[3], 1);
		
    }//testSweetSpotHashFn
    
    /**
     * tests the hash() method in SweetSpotHashFn
     */
    @Test
    public void testGAHashFn()
    {
    	ArrayList<WME[]> testList = new ArrayList<WME[]>();
		testList = MainTest.makeQuickEpList4();
    	GAHashFn fn = new GAHashFn(4, testList, WME.ATTR + WME.VAL);
		ArrayList<int[]> hashCodeList = new ArrayList<int[]>();
		
		for(int i = 0; i < testList.size(); i++){
			hashCodeList.add(fn.hash(testList.get(i)));
		}

		//No two hash codes should be identical
		assertFalse(Arrays.equals(hashCodeList.get(0), hashCodeList.get(1)));
		assertFalse(Arrays.equals(hashCodeList.get(0), hashCodeList.get(2)));
		assertFalse(Arrays.equals(hashCodeList.get(0), hashCodeList.get(3)));
		assertFalse(Arrays.equals(hashCodeList.get(1), hashCodeList.get(2)));
		assertFalse(Arrays.equals(hashCodeList.get(1), hashCodeList.get(3)));
		assertFalse(Arrays.equals(hashCodeList.get(2), hashCodeList.get(3)));
		
    }//testGAHashFn
    
    /**
     * tests the hash() method in LSHashFn.   
     */
    @Test
    public void testLSHashFn()
    {
        // create first episode
        WME[] ep = new WME[3];
        ep[0] = new WME("(S1 ^foo 1)");  //index 0
        ep[1] = new WME("(S1 ^bar 2)");  //index 1
        ep[2] = new WME("(S1 ^baz 3)");  //index 2

        //Test first episode
        LSHashFn fn = new LSHashFn();
        int[] test = fn.hash(ep);
        String hashStr = "" + test[0] + test[1] + test[2] + test[3];
        assertEquals(hashStr, "1111");

        // create second episode
        ep = new WME[3];
        ep[0] = new WME("(S1 ^foo 44)");  //index 3
        ep[1] = new WME("(S1 ^bar 2)");   //index 1
        ep[2] = new WME("(S1 ^baz 3)");   //index 2

        //Test second episode
        test = fn.hash(ep);
        hashStr = "" + test[0] + test[1] + test[2] + test[3];
        assertEquals(hashStr, "1000");

        // create third episode
        ep = new WME[3];
        ep[0] = new WME("(S1 ^foo 44)"); //index 3
        ep[1] = new WME("(S1 ^bar 2)");  //index 1
        ep[2] = new WME("(S1 ^baz 55)"); //index 0 (reused)

        //Test second episode
        test = fn.hash(ep);
        hashStr = "" + test[0] + test[1] + test[2] + test[3];
        assertEquals(hashStr, "1000");

    }//testLSHashFn
    
    /**
     * tests the generateHashFormula method.   
     */
    @Test
    public void testGenHashForm()
    {
    	// make hashfn and create quick episode list
    	SweetSpotHashFn fn = new SweetSpotHashFn(4, 0.0);
    	ArrayList<WME[]> testList = new ArrayList<WME[]>();
		testList = MainTest.makeQuickEpList4();
    	
		//add color red and color blue
		fn.hash(testList.get(0));
		//color red should be at the top
		assertEquals(fn.getDictionaryEntry(0), "color red");
		
		//add color green and alligator eats
		fn.hash(testList.get(1));
		//add color green and newbie two
		fn.hash(testList.get(3));
		//add color green and alligator eats color green now occurs most, but 
		fn.hash(testList.get(1));
		
		//it should still be below color red so the formula doesn't change too
		//much. color red should be at the top
		assertEquals(fn.getDictionaryEntry(0), "color green");
		assertEquals(fn.getDictionaryEntry(2), "color red");
    }
    
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

	@Override
	public int[] hash(WME[] episode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
    
}

    