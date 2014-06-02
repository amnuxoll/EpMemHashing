package unitTests;

import static org.junit.Assert.*;

import java.util.Random;

import hashTesting.GAHashFn;
import hashTesting.WME;

import org.junit.Test;

/**
 * The GAHashFn class has several methods in it that needs to be tested so 
 * I've split off those tests into this file rather than mashing them all 
 * into the HasnFnTest
 * 
 * @author Nux
 * @version May 2014
 *
 */

public class GAHashFnTest extends GAHashFn {

	
	
	public GAHashFnTest() {
		super(4, MainTest.makeQuickEpList4(), WME.ATTR + WME.VAL);
	}

	/** test that the ctor initializes everything */
	@Test
	public void testCtor() {
		
		//Do some basic checks on the data
		assertEquals(this.codeSize, 4);
        assertEquals(this.episodeList.size(), 4);
        assertEquals(this.dict.getSize(), 6);
        assertNotNull(this.population);
        assertNotNull(this.population[0][0]);
        assertNotNull(this.bestHasher[0]);
	}//testCtor
	
	/** test that initPopulation creates random data */
	@Test
	public void testCreateInitPopulation()
	{
		Random rand = new Random();
		int i = rand.nextInt(this.population.length);
		WME someWME = this.population[i][0];
		
		this.createInitPopulation();
		WME otherWME = this.population[i][0];
		assertFalse(someWME.equals(otherWME));
	}//testCreateInitPopulation


}
