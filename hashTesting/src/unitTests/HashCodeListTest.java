package unitTests;

import static org.junit.Assert.*;
import hashTesting.HashFn;
import hashTesting.WME;

import org.junit.Test;

import comparisons.HashCodeList;

public class HashCodeListTest extends HashCodeList {

	public HashCodeListTest() {
		super(5);
		assertEquals(this.hashCodes.length, 5);
		assertEquals(this.refEpisode.length, 5);
		assertNotNull(this.cache);
	}
	
//	public HashCodeListTest(int maxSize, HashFn func){
//		super(maxSize, func);
//		assertEquals(this.hashCodes.length, maxSize);
//		assertEquals(this.refEpisode.length, maxSize);
//		assertNotNull(this.cache);
//		assertNotNull(this.fn);
//	}

	@Test
	public void testAddCode() {
		int[] testCode = {0, 1, 1, 0, 1, 1, 0, 1, 0, 1};
		addCode(testCode);
		
		assertEquals(hashCodes[0][0], 0);
		for(int i = 1; i < hashCodes.length; i++){
			assertNull(hashCodes[i]);
		}
	}
	
	
	//TODO: Implement this test
	@Test
	public void testFindBestMatch(){
		int[] testCode = {0, 1, 1, 0, 0, 1, 0, 1, 0, 1};
		addCode(testCode);
		int[] closestMatch = findBestMatch(testCode);
		assertEquals(closestMatch, testCode);
	}

	//TODO: Implement this test
	@Test 
	public void testFindMatchIndex(){
		int[] testCode = {0, 1, 1, 0, 0, 1, 0, 1, 0, 1};
		addCode(testCode);
		int testIndex = findBestMatchIndex(testCode);
		assertEquals(testIndex, 0);
	}
	
	@Test
	public void testGet(){
		int index = 4;
		assertEquals(this.get(index), this.hashCodes[index]);
	}
	
	//TODO: Nux will have to help with this
//	@Test
//	public void testReconstructEpisode(){
//		
//	}
	
//	@Test
//	public void testSetHashFn(){
//		assertNotNull(this.fn);
//	}
	
	@Test
	public void hashCompare(){
		
		//1 XOR 1 = 0
		int[] testHash1 = {1, 1, 1, 1, 1, 1, 1, 1};
		int[] testHash2 = {1, 1, 1, 1, 1, 1, 1, 1};
		int retVal = hashCompare(testHash1, testHash2);
		assertEquals(retVal, 0);
		
		//0 XOR 0 = 0
		int[] testHash3 = {0, 0, 0, 0, 0, 0, 0, 0};
		int[] testHash4 = {0, 0, 0, 0, 0, 0, 0, 0};
		int retVal0 = hashCompare(testHash3, testHash4);
		assertEquals(retVal0, 0);
		
		//1 XOR 0 = 1, should be length of hash
		int[] testHash5 = {1, 1, 1, 1, 1, 1, 1, 1};
		int[] testHash6 = {0, 0, 0, 0, 0, 0, 0, 0};
		int retVal1 = hashCompare(testHash5, testHash6);
		assertEquals(retVal1, 8);
		
		//Mix XOR 1 = number of 0s
		int[] testHash7 = {1, 0, 1, 0, 0, 1, 1, 0};
		int[] testHash8 = {1, 1, 1, 1, 1, 1, 1, 1};
		int retVal2 = hashCompare(testHash7, testHash8);
		assertEquals(retVal2, 4);
		
		//Mix XOR 0 = number of 1s
		int[] testHash9 = {1, 1, 1, 0, 0, 1, 1, 0};
		int[] testHash10 = {0, 0, 0, 0, 0, 0, 0, 0};
		int retVal3 = hashCompare(testHash9, testHash10);
		assertEquals(retVal3, 5);
		
		//Mix XOR Mix
		int[] testHash11 = {1, 1, 0, 0, 1, 1, 1, 0};
		int[] testHash12 = {1, 0, 1, 0, 0, 1, 1, 1};
		int retVal4 = hashCompare(testHash11, testHash12);
		assertEquals(retVal4, 4);
		
		//Mix XOR Mix Round 2
		int[] testHash13 = {0, 1, 1, 0, 1, 1, 1, 0};
		int[] testHash14 = {1, 0, 0, 0, 0, 1, 0, 1};
		int retVal5 = hashCompare(testHash13, testHash14);
		assertEquals(retVal5, 6);
		
		
	}
}
