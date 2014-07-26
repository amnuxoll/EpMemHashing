package comparisons;

import hashTesting.WME;

/**
 * EpisodeCache class which is used to store and look up reference episodes for usage in memory recall
 * and reconstruction.
 * 
 * @author Andrew Meyer
 * @author Kevin Bastien
 * @version July 26th, 2014
 */

public class EpisodeCache {
	
	protected WME[][] cache;
	
	/**
	 * Ctor for an EpisodeCache which contains a set of epsiodes that are used for reference.
	 * 
	 * @param sizeOfCache the maximum number of episodes that can be contained within the cache.
	 */
	public EpisodeCache (int sizeOfCache){
		cache = new WME[sizeOfCache][];
	}
	
	/**
	 * Add an episode to the cache and return the removed episode if one exists.
	 * 
	 * @param newEpisode The new episode to be added into the cache. Unless this episode is completely
	 * 		  contained within another episode it will always exist in the cache at the end of the call.
	 * @return The episode that was removed from the cache. Null if no episode was removed (cache is not full).
	 */
	public WME[] addEpisode(WME[] newEpisode){		
		return null;
	}
	
	
	/**
	 * Add an episode to the cache at the specified index and return the removed episode if one exists.
	 * 
	 * @param newEpisode The new episode to be added to the cache.
	 * @param index The index at which the episode will be added.
	 * @return The episode that was removed from the cache. Null if no episode was removed (no episode at that index).
	 */
	public WME[] addEpisode(WME[] newEpisode, int index){
		WME[] ret = cache[index];
		cache[index] = newEpisode;
		
		return ret;
	}
	
	/**
	 * Remove an episode from the cache at the specified index.
	 * 
	 * @param index The index at which to remove the episode.
	 * @return The episode that has been removed.
	 */
	public WME[] removeEpisode(int index){
		WME[] ret = cache[index];
		cache[index] = null;
		return ret;
	}
	
	/**
	 * Find the best matching episode and return it to the calling function.
	 * 
	 * @param cue The reference that is used to find the most appropriate episode.
	 * @return The best matching episode.
	 */
	public WME[] findBestMatch(WME[] cue){
		return null;
	}
	
	/**
	 * Get the episode at the given index in the cache.
	 * 
	 * @param index The index of the episode to be returned.
	 * @return The episode at the specified index. 
	 */
	public WME[] get(int index){
		return cache[index];
	}
	
	/**
	 * Compares two episodes based on how many unique WMEs one has in comparison to the other. 
	 * Ordering does matter, wmeCompare(ep1, ep2) does not necessarily equal wmeCompare(ep2, ep1)
	 * 
	 * @param ep1 The episode that is being compared.
	 * @param ep2 The episode being compared against.
	 * @return The uniqueness score, the lower the number the less unique the first episode is in comparison to
	 * 		   the second episode.
	 */
	protected int wmeCompare(WME[] ep1, WME[] ep2){
		return Integer.MAX_VALUE;
	}
	

}
