package unitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import hashTesting.*;

import org.junit.Test;

public class DictionaryTest {
	private Dictionary testDictAttr;
	private Dictionary testDictAttrVal;
	
	public DictionaryTest()
	{
		// a dictionary consisting of comparisons of the wme based only on attribute
		testDictAttr = new Dictionary(MainTest.makeQuickEpList2(), WME.ATTR);
		// a dictionary consisting of comparisons of the wme based on attribute AND value
		testDictAttrVal = new Dictionary(MainTest.makeQuickEpList2(), WME.ATTR+ WME.VAL);
	}
	
	@Test
	public void testCtor() {
		Dictionary testDic1 = new Dictionary(WME.ATTR);
		assertEquals(testDic1.getCompareType(), WME.ATTR);
		
		Dictionary testDic2 = new Dictionary(MainTest.makeQuickEpList2(), WME.VAL);
		assertEquals(testDic2.getCompareType(), WME.VAL);
		WME testWME = new WME("(s1 ^color red)");
		WME test2WME = new WME("(s1 ^alligator eats)");
		assertEquals(testDic2.findEntry(testWME).getWME(), testWME);
		assertEquals(testDic2.findEntry(test2WME).getWME(), test2WME);
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
     
        assertEquals(testDictAttr.findEntry(testWME).getWME(), testWME);
        assertEquals(testDictAttr.findEntry(test2WME).getWME(), test2WME);
        assertEquals(testDictAttr.findEntry(test3WME).getWME(), test3WME);

	}
	
	@Test
	public void testGetSortedEntryList(){
		WME testWME = new WME("(s1 ^alligator eats)");
		WME[] episode = new WME[1];
		episode[0] = testWME;
		testDictAttr.addEpisode(3, episode);
		testDictAttrVal.addEpisode(3, episode);
		ArrayList<Entry> testList1 = new ArrayList<Entry>(testDictAttr.getSortedEntryList());
		ArrayList<Entry> testList2 = testDictAttrVal.getSortedEntryList();
		
		assertEquals(testList1.get(testList1.size() - 1).getWME().attribute, "color");
		assertEquals(testList2.get(testList2.size() - 1).getWME().attribute, "color");
		assertEquals(testList2.get(0).getWME().attribute, "alligator");
		assertEquals(testList2.get(0).getWME().value, "eats");
	} 
	
	/** get a random entry and make sure it's in the list */
	@Test
	public void testGetRandomEntry(){
		Entry entry = testDictAttrVal.getRandomEntry();
		Entry ret = testDictAttrVal.findEntry(entry.getWME());
		assertNotNull(ret);
	}
	
	/** make sure that the dictionary does not go above the size limit */
	@Test
	public void testForgetting(){
		ForgettingDictionary fdAttr = new ForgettingDictionary(MainTest.makeQuickEpList2(), WME.ATTR, 5);
		assertEquals(fdAttr.getCapSize(), 5);
		assertEquals(fdAttr.getSize(), 4);
		
		//create new episode
				WME testWME = new WME("(s1 ^color red)");
		        WME test2WME = new WME("(s1 ^color orange)");
		        WME test3WME = new WME("(s1 ^yolo hashtagswag)");
		        
		        WME[] episode = new WME[3];
		        episode[0] = testWME;
		        episode[1] = test2WME;
		        episode[2] = test3WME;
		        
		fdAttr.addEpisode(3, episode);
		
		assertEquals(fdAttr.getSize(), 5);		
	}

}
