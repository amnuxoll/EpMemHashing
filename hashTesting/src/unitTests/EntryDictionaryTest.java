package unitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import hashTesting.*;

import org.junit.Test;

public class EntryDictionaryTest {
	private EntryDictionary testDictAttr;
	private EntryDictionary testDictAttrVal;
	
	public EntryDictionaryTest()
	{
		// a dictionary consisting of comparisons of the wme based only on attribute
		testDictAttr = new EntryDictionary(MainTest.makeQuickEpList2(), WME.ATTR);
		// a dictionary consisting of comparisons of the wme based on attribute AND value
		testDictAttrVal = new EntryDictionary(MainTest.makeQuickEpList2(), WME.ATTR+ WME.VAL);
	}
	
	@Test
	public void testCtor() {
		EntryDictionary testDic1 = new EntryDictionary(WME.ATTR);
		assertEquals(testDic1.getCompareType(), WME.ATTR);
		
		EntryDictionary testDic2 = new EntryDictionary(MainTest.makeQuickEpList2(), WME.VAL);
		assertEquals(testDic2.getCompareType(), WME.VAL);
		WME testWME = new WME("(s1 ^color red)");
		WME test2WME = new WME("(s1 ^alligator eats)");
		assertEquals(testDic2.findEntry(testWME).getEntry(), testWME);
		assertEquals(testDic2.findEntry(test2WME).getEntry(), test2WME);
	}
	
	@Test
	public void testGetSize(){
		assertEquals(testDictAttr.getSize(), 4);
		assertEquals(testDictAttrVal.getSize(), 4);
	}
	
	@Test
	public void testAddEpisode()
	{
		//create new episode
		WME testWME = new WME("(s1 ^color red)");
        WME test2WME = new WME("(s1 ^color blue)");
        WME test3WME = new WME("(s1 ^yolo hashtagswag)");
        
        WME[] episode = new WME[3];
        episode[0] = testWME;
        episode[1] = test2WME;
        episode[2] = test3WME;
        
        //this episode has two occurrences of the color attribute so adding it to the testDictAttr
        //will bump the number of occurrences of 'color' (at index 0) up to 5
        testDictAttr.addEpisode(2, episode);
     
        assertEquals(testDictAttr.findEntry(testWME).getEntry(), testWME);
        assertEquals(testDictAttr.findEntry(test2WME).getEntry(), test2WME);
        assertEquals(testDictAttr.findEntry(test3WME).getEntry(), test3WME);

	}
	
	@Test
	public void testGetSortedEntryList(){
		WME testWME = new WME("(s1 ^alligator eats)");
		WME[] episode = new WME[1];
		episode[0] = testWME;
		testDictAttr.addEpisode(3, episode);
		testDictAttrVal.addEpisode(3, episode);
		ArrayList<EntryTwo> testList1 = new ArrayList<EntryTwo>(testDictAttr.getSortedEntryList());
		ArrayList<EntryTwo> testList2 = testDictAttrVal.getSortedEntryList();
		
		assertEquals(testList1.get(testList1.size() - 1).getEntry().attribute, "color");
		assertEquals(testList2.get(testList2.size() - 1).getEntry().attribute, "color");
		assertEquals(testList2.get(0).getEntry().attribute, "alligator");
		assertEquals(testList2.get(0).getEntry().value, "eats");
	} 
	
	/** get a random entry and make sure it's in the list */
	@Test
	public void testGetRandomEntry(){
		EntryTwo entry = testDictAttrVal.getRandomEntry();
		EntryTwo ret = testDictAttrVal.findEntry(entry.getEntry());
		assertNotNull(ret);
	}

}
