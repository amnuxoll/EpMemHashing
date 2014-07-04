package unitTests;

import static org.junit.Assert.*;

import hashTesting.WME;

import java.util.ArrayList;

import org.junit.Test;
import hashTesting.*;

public class EntryTwoTest extends MainTest {

	/**
     * Tests Entry Ctor.
     *
     */
    @Test
    public void testEntryTwoConst()
    {
    	// Create a test episodeList and check the value of the first WME
    	// in the first episode.
    	ArrayList<WME[]> testList = makeQuickEpList2();
    	assertEquals(testList.get(0)[0].value, "red");
    	
    	// create a test Entry from the 2nd WME in the first episode
    	EntryTwo testCtor = new EntryTwo(3, testList.get(0)[1]);
    	
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
    	ArrayList<WME[]> testList = makeQuickEpList2();
    	EntryTwo testEntry = new EntryTwo(3, testList.get(0)[1]);
    	
    	// Add some occurrences
    	testEntry.addOccurrence(1);
    	assertEquals(testEntry.getMostRecentOccurrence(), 1);
    	assertEquals(testEntry.getNumOccurrences(), 1);
    	
    	testEntry.addOccurrence(2);
    	testEntry.addOccurrence(2);
    	testEntry.addOccurrence(2);
    	assertEquals(testEntry.getMostRecentOccurrence(), 2);
    	assertEquals(testEntry.getNumOccurrences(), 4);
    	
    	testEntry.addOccurrence(5);
    	assertEquals(testEntry.getMostRecentOccurrence(), 5);
    	assertEquals(testEntry.getNumOccurrences(), 5);
    	
    	testEntry.addOccurrence(7);
    	testEntry.addOccurrence(7);
    	assertEquals(testEntry.getMostRecentOccurrence(), 7);
    	assertEquals(testEntry.getNumOccurrences(), 7);
    	
    	
    }
    
    /**
     * Tests getting the sum of the total occurrences of an entry
     *
     */
    @Test
    public void testGetNumOccur()
    {
    	ArrayList<WME[]> testList = makeQuickEpList2();
    	EntryTwo testEntry = new EntryTwo(3, testList.get(0)[1]);
    	
    	// Add some occurrences
    	testEntry.addOccurrence(1);
    	testEntry.addOccurrence(2);
    	testEntry.addOccurrence(2);
    	testEntry.addOccurrence(2);
    	testEntry.addOccurrence(5);
    	testEntry.addOccurrence(7);
    	testEntry.addOccurrence(7);
    	
    	assertEquals(testEntry.getNumOccurrences(), 7);
    	
    	// Add one more and retry
    	testEntry.addOccurrence(10);
    	
    	assertEquals(testEntry.getNumOccurrences(), 8);
    }
    
    /**
     * Tests compareTo method, which is not testing if they are equal entries,
     * just tests if they have the same number of occurrences, for use by the
     * sorting of the dictionary.
     *
     */
    @Test
    public void testCompareTo()
    {
    	WME test1 = new WME("(A1 ^steak sauce)");
    	WME test2 = new WME("(C2 ^ham bone)");
    	// make 2 entries
    	EntryTwo entry1 = new EntryTwo(3, test1);
    	EntryTwo entry2 = new EntryTwo(3, test2);
    	// add some occurrences
    	entry1.addOccurrence(1);
    	entry1.addOccurrence(2);
    	entry1.addOccurrence(3);
    	
    	entry2.addOccurrence(5);
    	entry2.addOccurrence(5);
    	
    	
    	// they are equal if they have the same number of total occurrences
    	assertEquals(entry1.compareTo(entry2), -1);
    	
    	// make them equal and try again
    	entry2.addOccurrence(8);
    	
    	assertEquals(entry1.compareTo(entry2), 5);
    	assertEquals(entry2.compareTo(entry1), -5);
    }
}


