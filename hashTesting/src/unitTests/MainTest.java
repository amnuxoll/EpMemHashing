package unitTests;

/**
 * Test Main class
 * 
 * @author Nux
 * @author Alexandra Warlen
 * @author Allie Seibert
 * @version Friday May 16, 2014
 */
import static org.junit.Assert.*;
import hashTesting.*;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class MainTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class MainTest extends Main
{
    /**
     * Default constructor for test class MainTest
     */
    public MainTest()
    {
        super(123456);
    }
    
    /**
     * a helpful method for quickly generating a list of two episodes
     */
    public static ArrayList<WME[]> makeQuickEpList2()
    {
        // create test episode
        WME testWME = new WME("(s1 ^color red)");
        WME test2WME = new WME("(s1 ^color blue)");
        WME[] episode1 = new WME[2];
        episode1[0] = testWME;
        episode1[1] = test2WME;
        
        // create another test episode
        testWME = new WME("(s1 ^color green)");
        test2WME = new WME("(s1 ^alligator eats)");
        WME[] episode2 = new WME[2];
        episode2[0] = testWME;
        episode2[1] = test2WME;

        //add those to the episodeList
        ArrayList<WME[]> epList = new ArrayList<WME[]>();  
        epList.add(episode1);
        epList.add(episode2);
        
        
        return epList;
    	
    }//makeQuickEpList

    /**
     * a helpful method for quickly generating a list of 4 episodes
     */
    public static ArrayList<WME[]> makeQuickEpList4()
    {
    	//start with the quick list
    	ArrayList<WME[]> epList = makeQuickEpList2();  
    	
        // create a 3rd test episode
        WME testWME = new WME("(s1 ^color red)");
        WME test2WME = new WME("(s1 ^newbie one)");
        WME[] episode1 = new WME[2];
        episode1[0] = testWME;
        episode1[1] = test2WME;
        
        // create a 4th test episode
        testWME = new WME("(s1 ^color green)");
        test2WME = new WME("(s1 ^newbie two)");
        WME[] episode2 = new WME[2];
        episode2[0] = testWME;
        episode2[1] = test2WME;

        //add those to the episodeList
        epList.add(episode1);
        epList.add(episode2);
        
        
        return epList;
    	
    }//makeQuickEpList

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
     * testLoadEpisodes
     */
    @Test
    public void testLoadEpisodes()
    {
        loadEpisodes("data.txt");

        //should have loaded 60 episodes
        assertEquals(episodeList.size(), 60);

        //make sure the first and last episodes actually contain data
        WME[] ep = episodeList.get(0);
        assertTrue(ep.length > 0);
        ep = episodeList.get(59);
        assertTrue(ep.length > 0);

        //spot check one of those episodes
        ep = episodeList.get(32);
        WME wme = ep[21];
        System.out.println(wme);
        assertEquals(wme.id, "E2");
        assertEquals(wme.attribute, "x");
        assertEquals(wme.value, "10");
        
        //Try a delta-based file
        loadEpisodes("log_eaters_changesonly2.txt");
        assertEquals(episodeList.size(), 580);  //should have 580 episodes in it
        
    }//testLoadEpisodes
    
    /**
     * testGenEpisodes
     * 
     */
    @Test
    public void testGenEpisodes()
    {
    	//Generate 40 more episodes
    	loadEpisodes("data.txt");
    	int numEpsBefore = episodeList.size();
    	genEpisodes(40);
    	assertEquals(numEpsBefore + 40, episodeList.size());
    	
    	//Verify that there is some real data in there
    	WME[] ep = episodeList.get(numEpsBefore + 1);
    	WME w = ep[ep.length / 2];
    	assertEquals(w.isValid(), true);
    	
    }//testGenEpisodes

    /**
     * testFindHash
     *
     */
    @Test
    public void testFindHash()
    {
        int[] hash1 = new int[HashFn.CODE_SIZE];
        int[] hash2 = new int[HashFn.CODE_SIZE];
        int[] hash3 = new int[HashFn.CODE_SIZE];
        
        //fill hash1 with 0's, hash2 with 1's, and hash3 with half of each
        for(int i=0; i<HashFn.CODE_SIZE; i++){
        	hash1[i] = 0;
        	hash2[i] = 1;
        	if (i <HashFn.CODE_SIZE/2){hash3[i] = 0;}
        	else {hash3[i] = 1;}
        }

        
        hashCodeList.add(hash1);
        hashCodeList.add(hash2);
        
        //it should not find hash3 in the list, but it will return the index loc
        //of the other two
        assertEquals(findHash(hash3),-1);
        assertEquals(findHash(hash1),0);
        assertEquals(findHash(hash2),1);
        
        //once its added hash3, it will be at loc 2
        hashCodeList.add(hash3);
        assertEquals(findHash(hash3),2);
    }
    
    /**
     * testFindSimilarity
     *
     */
    public void testFindSimilarity()
    {
    	int[] test1 = {0, 0, 1, 0, 1, 0};
    	int[] test2 = {0, 0, 0, 1, 1, 1};
    	int[] test3 = {1, 0, 1, 0, 1, 1};
    	
    	hashCodeList.add(test1);
    	hashCodeList.add(test2);
    	
    	assertEquals(findSimilarity(test3, 6,3)[0], .66667, .001);
    	assertEquals(findSimilarity(test3, 6,3)[1], 0, .001);
    	
    	hashCodeList.add(test3);
    	assertEquals(findSimilarity(test3, 6,3)[0], 1.0, .000001);
    	
    	
    }
    // took out this test (for now) since it does not agree with new way of 
    // calculating success
    /**
     * tests calculateSuccess method
     
    @Test
    public void testCalcSuccess()
    {
    	episodeList.addAll(makeQuickEpList2());
    	
        //Since we used a fixed random number seed, these results should always
        //be the same
        RandomHashFn myFn = new RandomHashFn(128);
        double[] results = calculateSuccess(myFn);
        assertEquals(results[0], 1.0, 0.001);
        assertEquals(results[1], 0.0, 0.001);
        
        
    }
    */
   
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
}
