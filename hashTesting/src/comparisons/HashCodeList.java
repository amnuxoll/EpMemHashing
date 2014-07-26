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

	protected int[][] hashCodes;
	protected int[] refEpisode;
	protected EpisodeCache cache;
	protected HashFn fn;
	
	/**
	 * Ctor for the HashCodeList which is a list of hash codes that are used
	 * in memory retrieval and reconstruction.
	 * 
	 * @param maxSize Max number of hash codes in list.
	 */
	public HashCodeList(int maxSize){
		this.hashCodes = new int[maxSize][];
		this.refEpisode = new int[maxSize];
		this.cache = new EpisodeCache(maxSize);
		
		for(int i = 0; i < maxSize; i++){
			hashCodes[i][0] = -1;
		}
	}
	
	/** 
	 * Ctor for the HashCodeList that takes a function for reconstruction as well.
	 * 
	 * @param maxSize Max number of hash codes in list.
	 * @param func The function that is used for reconstruction.
	 */
	public HashCodeList(int maxSize, HashFn func){
		this(maxSize);
		this.fn = func;
	}
	
	/**
	 * Add a hash code to the list and return the removed code if one exists
	 * 
	 * @param code The new code to be added to the cache.
	 * @return The hash code that was removed. Null if no code was removed.
	 */
	public int[] addCode(int[] code){
		//First check if there are any open spots available.
		for(int i = 0; i < this.hashCodes.length; i++){
			if(this.hashCodes[i][0] == -1){
				this.hashCodes[i] = code;
				return null;
			}
		}
		
		//List is full, find the hash to be replaced.
		int swapIndex = findBestMatchIndex(code);
		int[] retCode = get(swapIndex);
		hashCodes[swapIndex] = code;		
		
		return retCode;
	}
	
	/**
	 * Find the best matching hash code and return it to the calling function
	 * 
	 * @param code The reference that is used to find the most appropriate code.
	 * @return The best matching hash code.
	 */
	public int[] findBestMatch(int[] code){
		int index = findBestMatchIndex(code);
		
		return this.get(index);
	}
	
	/**
	 * 
	 * @param code
	 * @return
	 */
	public int findBestMatchIndex(int[] code){
		int[][][] scores = new int[hashCodes.length][2][];
		for(int i = 0; i < scores.length; i++){
			scores[i][0][0] = hashCompare(code, hashCodes[i]);
			scores[i][1] = hashCodes[i];
		}
		
		int bestScore = Integer.MAX_VALUE;
		int bestIndex = -1;
		
		for(int i = 0; i < scores.length; i++){
			if (scores[i][0][0] < bestScore){
				bestScore = scores[i][0][0];
				bestIndex = i;
			}
		}
		
		return bestIndex;
	}
	
	/**
	 * Get the hash code at the given index.
	 * 
	 * @param index The index of the code to be returned.
	 * @return The hash code at the given index.
	 */
	public int[] get(int index){
		return this.hashCodes[index];
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
	 * Sets the hash function to be used in reconstruction.
	 * @param fn The hash function to be used.
	 */
	public void setHashFn(HashFn func){
		this.fn = func;
	}
	
	/**
	 * Compares two hash codes based upon their difference in bits (XORed and then finding the number of bits that are still '1').
	 * Ordering does not matter, hashCompare(code1, code2) will always equal hashCompare(code2, code1).
	 * 
	 * @param code1 The first code being compared
	 * @param code2 The second code being compared
	 * @return The uniqueness score, the lower the number the less unique the episodes are in relation to each other.
	 */
	protected int hashCompare(int[] code1, int[] code2){
		int[] xorCode = new int[code1.length];
		
		for(int i = 0; i < xorCode.length; i++){
			xorCode[i] = code1[i]^code2[i];
		}
		
		int retVal = 0;
		for(int v: xorCode){
			if (v != 0) retVal++;
		}
		
		return retVal;
	}
	
}
