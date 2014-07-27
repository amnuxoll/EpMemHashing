package unitTests;

import static org.junit.Assert.*;
import hashTesting.WME;

import org.junit.Test;

import comparisons.EpisodeCache;

public class EpisodeCacheTest extends EpisodeCache{

	public EpisodeCacheTest(int sizeOfCache) {
		super(sizeOfCache);
	}

	@Test
	public void testAddEpisode() {
       
		//creating test episode
		WME testWME = new WME();
        WME test2WME = new WME();
        WME[] episode = new WME[2];
        episode[0] = testWME;
        episode[1] = test2WME;
        
        addEpisode(episode);
        
        for(int i=0; i < cache.length; i++){
        	if(cache[i][0] == null){
        		
        	}
        }
	}

}
