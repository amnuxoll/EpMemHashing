package comparisons;

import java.util.ArrayList;
import java.util.Arrays;

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
	 * Ctor for an EpisodeCache which contains a set of episodes that are used for reference.
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
		
		//First check if there are any open spots available.
		for(int i = 0; i < this.cache.length; i++){
			if (this.cache[i] == null){
				this.cache[i] = newEpisode;
				return null;
			}
		}
		
		//List is full, find the episode to be replaced.
		int swapIndex = findBestMatchIndex(newEpisode);
		WME[] retEp = get(swapIndex);
		if (wmeCompare(newEpisode, retEp) == 0){
			//New episode is completely contained within another episode. Do not add to cache.
			return newEpisode;
		}
		
		int scoreSize = (int) ((Math.pow(this.cache.length + 1, 2) - (this.cache.length + 1)));
		int[][] scores = new int[scoreSize][3];
		int curIndex = 0;

		for(int i = 0; i < this.cache.length; i++){
			for(int j = 0; j < this.cache.length; j++){
				if (i == j) continue;
				scores[curIndex][0] = wmeCompare(this.cache[i], this.cache[j]);
				scores[curIndex][1] = i;
				scores[curIndex][2] = j;
				curIndex++;
			}
			scores[curIndex][0] = wmeCompare(this.cache[i], newEpisode);
			scores[curIndex][1] = i;
			scores[curIndex][2] = Integer.MAX_VALUE;
			curIndex++;
			
			scores[curIndex][0] = wmeCompare(newEpisode, this.cache[i]);
			scores[curIndex][1] = Integer.MAX_VALUE;
			scores[curIndex][2] = i;
			curIndex++;
		}
		
		
		int bestScore = Integer.MAX_VALUE;
		int bestIndex = Integer.MAX_VALUE;
		
		for(int i = 0; i < scores.length; i++){
			if(scores[i][0] < bestScore){
				bestScore = scores[i][0];
				bestIndex = scores[i][1];
			}
			else if(scores[i][0] == bestScore && scores[i][1] < bestIndex){
				bestIndex = scores[i][1];
			}
		}
		
		if(bestIndex == Integer.MAX_VALUE){
			return newEpisode;
		}
		
		retEp = get(bestIndex);
		
		for(int i = bestIndex; i < this.cache.length - 1; i++){
			this.cache[i] = this.cache[i+1];
		}
		
		this.cache[this.cache.length - 1] = newEpisode;
		return retEp;
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
		for(int i = index; i < cache.length - 1; i++){
			cache[i] = cache[i+1];
		}
		cache[cache.length - 1] = null;
		return ret;
	}
	
	/**
	 * Find the best matching episode and return it to the calling function.
	 * 
	 * @param cue The reference that is used to find the most appropriate episode.
	 * @return The best matching episode.
	 */
	public WME[] findBestMatch(WME[] cue){
		int index = findBestMatchIndex(cue);
		
		return this.get(index);
	}
	
	/**
	 * 
	 * @param cue
	 * @return
	 */
	public int findBestMatchIndex(WME[] cue){
		int[][] scores = new int[this.cache.length][2];
		
		for(int i = 0; i < this.cache.length; i++){
				scores[i][0] = wmeCompare(this.cache[i], cue);
				scores[i][1] = i;
		}
		
		int bestScore = Integer.MAX_VALUE;
		int bestEp = -1;
		
		for(int i = 0; i < scores.length; i++){
			if (scores[i][0] < bestScore){
				bestScore = scores[i][0];
				bestEp = scores[i][1];
			}
			else if((scores[i][0] == bestScore) && (scores[i][1] < bestEp)){
				bestEp = scores[i][1];
			}
		}
		
		return bestEp;
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
		if (ep1 == null){
			return 0;
		}
		
		if (ep2 == null){
			return ep1.length;
		}
		
		ArrayList<WME> ep1List = new ArrayList<WME>(Arrays.asList(ep1));
		ArrayList<WME> ep2List = new ArrayList<WME>(Arrays.asList(ep2));
		for(WME wme: ep2List){
			if(ep1List.contains(wme)){
				ep1List.remove(wme);
			}
		}
		
		return ep1List.size();
	}
	

}
