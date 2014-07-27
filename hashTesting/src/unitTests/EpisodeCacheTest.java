package unitTests;

import static org.junit.Assert.*;
import hashTesting.WME;

import org.junit.Test;

import comparisons.EpisodeCache;

public class EpisodeCacheTest extends EpisodeCache{

	public EpisodeCacheTest() {
		super(5);
	}

	@Test
	public void testAddEpisode() {
       
        // create test episodes
        WME test1WME = new WME("(s1 ^color red)");
        WME test2WME = new WME("(s1 ^color blue)");
        WME test3WME = new WME("(s1 ^color green)");
        WME test4WME = new WME("(s1 ^alligator eats)");
        WME test5WME = new WME("(s1 ^color pink)");
        WME test6WME = new WME("(s1 ^alligator sleeps)");
        WME test7WME = new WME("(s1 ^color brown)");
        WME test8WME = new WME("(s1 ^color black)");
        WME test9WME = new WME("(s1 ^go home)");
        
        WME[] episode1 = new WME[1];
        episode1[0] = test1WME;
        

        WME[] episode2 = new WME[2];
        episode2[0] = test1WME;
        episode2[1] = test2WME;
        

        WME[] episode3 = new WME[2];
        episode3[0] = test2WME;
        episode3[1] = test3WME;
        
        WME[] episode4 = new WME[3];
        episode4[0] = test1WME;
        episode4[1] = test2WME;
        episode4[2] = test3WME;
        
        WME[] episode5 = new WME[3];
        episode5[0] = test5WME;
        episode5[1] = test6WME;
        episode5[2] = test7WME;
        
        WME[] episode6 = new WME[4];
        episode6[0] = test2WME;
        episode6[1] = test3WME;
        episode6[2] = test4WME;
        episode6[3] = test5WME;
        
        WME[] episode7 = new WME[4];
        episode7[0] = test1WME;
        episode7[1] = test2WME;
        episode7[2] = test4WME;
        episode7[3] = test5WME;
        
        WME[] episode8 = new WME[4];
        episode8[0] = test6WME;
        episode8[1] = test7WME;
        episode8[2] = test8WME;
        episode8[3] = test9WME;
        
        WME[] episode9 = new WME[4];
        episode9[0] = test4WME;
        episode9[1] = test5WME;
        episode9[2] = test6WME;
        episode9[3] = test7WME;
        
        WME[] episode10 = new WME[3];
        episode10[0] = test1WME;
        episode10[1] = test4WME;
        episode10[2] = test5WME;
        
        WME[] episode11 = new WME[3];
        episode11[0] = test4WME;
        episode11[1] = test5WME;
        episode11[2] = test6WME;
        
        WME[] episode12 = new WME[3];
        episode12[0] = test7WME;
        episode12[1] = test8WME;
        episode12[2] = test9WME;
        
        WME[] episode13 = new WME[3];
        episode13[0] = test2WME;
        episode13[1] = test3WME;
        episode13[2] = test4WME;
        
        addEpisode(episode1);
        assertNotNull(cache[0]);
        
        WME[] retEp = addEpisode(episode2, 0);
        assertArrayEquals(retEp, episode1);
        
        EpisodeCache ec = new EpisodeCache(1);
        ec.addEpisode(episode1);
        
        retEp = ec.addEpisode(episode3);
        assertArrayEquals(retEp, episode1);
        
        EpisodeCache epCache = new EpisodeCache(2);
        epCache.addEpisode(episode2);
        epCache.addEpisode(episode3);
        retEp = epCache.addEpisode(episode1);
        assertArrayEquals(retEp, episode1);
        
        epCache.addEpisode(episode4, 0);
        epCache.addEpisode(episode6, 1);
        retEp = epCache.addEpisode(episode5);
        assertArrayEquals(retEp, episode4);

        epCache.addEpisode(episode7, 0);
        epCache.addEpisode(episode8, 1);
        retEp = epCache.addEpisode(episode4);
        assertArrayEquals(retEp, episode4);
        
        
        epCache.addEpisode(episode9, 0);
        epCache.addEpisode(episode10, 1);
        retEp = epCache.addEpisode(episode4);
        assertArrayEquals(retEp, episode10);
        
        epCache.addEpisode(episode11, 0);
        epCache.addEpisode(episode12, 1);
        retEp = epCache.addEpisode(episode4);
        assertArrayEquals(retEp, episode11);
        
        epCache.addEpisode(episode4, 0);
        epCache.addEpisode(episode13, 1);
        retEp = epCache.addEpisode(episode5);
        assertArrayEquals(retEp, episode4);
      
	}
	
	@Test
	public void testRemoveEpisode(){
        // create test episode
        WME testWME = new WME("(s1 ^color red)");
        WME test2WME = new WME("(s1 ^color blue)");
        WME[] episode = new WME[2];
        episode[0] = testWME;
        episode[1] = test2WME;
        
        addEpisode(episode);
        WME[] retEp = removeEpisode(0);
        
        assertArrayEquals(retEp, episode); 
	}
	
	@Test
	public void testFindBestMatch(){
        // create test episode
        WME testWME = new WME("(s1 ^color red)");
        WME test2WME = new WME("(s1 ^color blue)");
        WME[] episode = new WME[2];
        episode[0] = testWME;
        episode[1] = test2WME;
        
		addEpisode(episode);
		WME[] closestMatch = findBestMatch(episode);
		assertArrayEquals(closestMatch, episode);
	}

	@Test 
	public void testFindMatchIndex(){
        // create test episode
        WME testWME = new WME("(s1 ^color red)");
        WME test2WME = new WME("(s1 ^color blue)");
        WME[] episode = new WME[2];
        episode[0] = testWME;
        episode[1] = test2WME;
        
		addEpisode(episode);
		int testIndex = findBestMatchIndex(episode);
		assertEquals(testIndex, 0);
	}
	
	@Test
	public void testGet(){
		int index = 4;
		assertArrayEquals(this.get(index), this.cache[index]);
	}
	
	@Test
	public void testWMECompare(){
		
		//create new episode
		WME testWME = new WME("(s1 ^color red)");
        WME test2WME = new WME("(s1 ^color blue)");
        WME test3WME = new WME("(s1 ^yolo hashtagswag)");
        WME test4WME = new WME("(s1 ^eat food)");
        WME test5WME = new WME("(s1 ^im hungry)");
        WME test6WME = new WME("(s1 ^nux pizza)");
        WME test7WME = new WME("(s1 ^test wme)");
        
        WME[] episode = new WME[3];
        episode[0] = testWME;
        episode[1] = test2WME;
        episode[2] = test3WME;
        
        WME[] episode2 = new WME[4];
        episode2[0] = test2WME;
        episode2[1] = testWME;
        episode2[2] = test4WME;
        episode2[3] = test5WME;
        
        // Case 1, different sizes, some overlap
        assertEquals(wmeCompare(episode, episode2), 1);
        assertEquals(wmeCompare(episode2, episode), 2);
        
        // Case 2, same episode (shouldn't occur in practice)
        assertEquals(wmeCompare(episode, episode), 0);
        
        // Case 3, one episode completely contained in another.
        WME[] episode3 = new WME[2];
        episode3[0] = test2WME;
        episode3[1] = test3WME;
        
        assertEquals(wmeCompare(episode3, episode), 0);
        assertEquals(wmeCompare(episode, episode3), 1);
        
        // Case 4, same size, some overlap
        WME[] episode4 = new WME[4];
        episode4[0] = test2WME;
        episode4[1] = test3WME;
        episode4[2] = testWME;
        episode4[3] = test6WME;
        
        assertEquals(wmeCompare(episode2, episode4), 2);
        assertEquals(wmeCompare(episode4, episode2), 2);

        // Case 5, no WMEs shared
        WME[] episode5 = new WME[4];
        episode5[0] = test4WME;
        episode5[1] = test5WME;
        episode5[2] = test6WME;
        episode5[3] = test7WME;
        
        assertEquals(wmeCompare(episode, episode5), 3);
        assertEquals(wmeCompare(episode5, episode), 4);
        
        // Case 6, episode contains no WMEs
        WME[] episode6 = new WME[0];
        
        assertEquals(wmeCompare(episode6, episode5), 0);
        assertEquals(wmeCompare(episode5, episode6), 4);
        
	}
        

}
