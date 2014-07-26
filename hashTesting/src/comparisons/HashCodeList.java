package comparisons;

import hashTesting.HashFn;
import hashTesting.WME;

/**
 * HashCode class which is used to store and look up hash codes for usage in memory recall
 * and reconstruction. Used in conjunction with EpisodeCache.
 * 
 * @author Andrew Meyer
 * @author Kevin Bastien
 * @version July 26th, 2014
 */

public class HashCodeList {

	private int[][] hashCodes;
	private int[] refEpisode;
	private EpisodeCache cache;
	private HashFn fn;
	
	/**
	 * Ctor for the HashCodeList which is a list of hash codes that are used
	 * in memory retrieval and reconstruction.
	 * 
	 * @param maxSize Max number of hash codes in list.
	 */
	public HashCodeList(int maxSize){
		
	}
	
	/**
	 * Add a hash code to the list and return the removed code if one exists
	 * 
	 * @param code The new code to be added to the cache.
	 * @return The hash code that was removed.
	 */
	public int[] addCode(int[] code){
		return null;
	}
	
	/**
	 * Find the best matching hash code and return it to the calling function
	 * 
	 * @param code The reference that is used to find the most appropriate code.
	 * @return The best matching hash code.
	 */
	public int[] findBestMatch(int[] code){
		return null;
	}
	
	/**
	 * Get the hash code at the given index.
	 * 
	 * @param index The index of the code to be returned.
	 * @return The hash code at the given index.
	 */
	public int[] get(int index){
		return null;
	}
	
	/**
	 * 
	 * @param cue
	 * @return
	 */
	public WME[] recunstructEpisode(int[] cue){
		return null;
	}
	
	/**
	 * Compares two hash codes based upon their difference in bits (XORed and then finding the number of bits that are still '1').
	 * Ordering does not matter, hashCompare(code1, code2) will always equal hashCompare(code2, code1).
	 * 
	 * @param code1 The first code being compared
	 * @param code2 The second code being compared
	 * @return The uniqueness score, the lower the number the less unique the episodes are in relation to each other.
	 */
	private int hashCompare(int[] code1, int[] code2){
		return Integer.MAX_VALUE;
	}
	
}
