package unitTests;

import static org.junit.Assert.*;

import hashTesting.WME;

import java.util.ArrayList;

import org.junit.Test;
import hashTesting.*;

public class EntryTest extends MainTest {

	/**
     * Tests Entry Ctor.
     *
     */
    @Test
    public void testEntryConst()
    {
    	// Create a test episodeList and check the value of the first WME
    	// in the first episode.
    	ArrayList<WME[]> testList = makeQuickEpList();
    	assertEquals(testList.get(0)[0].value, "red");
    	
    	// create a test Entry from the 2nd WME in the first episode
    	Entry testCtor = new Entry(3, testList.get(0)[1]);
    	
    	// check that its variables were properly set
    	assertEquals(testCtor.getCompType(), 3);
    	assertEquals(testCtor.getEntry().value, "blue");
    	
	}
    
    /**
     * Tests adding occurrences for a specific entry.
     *
     */
    @Test
    public void testAddOccur()
    {
    	ArrayList<WME[]> testList = makeQuickEpList();
    	Entry testEntry = new Entry(3, testList.get(0)[1]);
    	
    	// Add some occurrences
    	testEntry.addOccurrence(1);
    	testEntry.addOccurrence(2);
    	testEntry.addOccurrence(2);
    	testEntry.addOccurrence(2);
    	testEntry.addOccurrence(5);
    	testEntry.addOccurrence(7);
    	testEntry.addOccurrence(7);
    	
    	// check that they were added properly
    	// should be seen once in episode 1, three times in episode 2
    	// once in ep 5 and twice in ep 7.
    	
    	// first array of ints in ArrayList should be 1,1
    	assertEquals(testEntry.getOccurrences().get(0)[0], 1);
    	assertEquals(testEntry.getOccurrences().get(0)[1], 1);
    	
    	// second entry should be 2,3
    	assertEquals(testEntry.getOccurrences().get(1)[0], 2);
    	assertEquals(testEntry.getOccurrences().get(1)[1], 3);
    	
    	// last entry (index 3) should be 7,2
    	assertEquals(testEntry.getOccurrences().get(3)[0], 7);
    	assertEquals(testEntry.getOccurrences().get(3)[1], 2);
    }
}


