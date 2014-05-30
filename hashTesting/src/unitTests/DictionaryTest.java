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
		
		//Test the dictionary that compares only attributes
		WME w = new WME("(S1 ^alligator eats)");
		assertEquals(testDictAttr.getEntryAt(1).getEntry(), w);
		assertEquals(testDictAttr.getEntryAt(1).getSumOccurrences(), 1);
		assertEquals(testDictAttr.getEntryAt(0).getSumOccurrences(), 3);
		
		
		//Test the dictionary that compares attribute value combinations		
		WME testWME = new WME("(s1 ^color red)");
        WME test2WME = new WME("(s1 ^color blue)");
		assertEquals(testDictAttrVal.getEntryAt(3).getEntry(), w);
		assertEquals(testDictAttrVal.getEntryAt(3).getSumOccurrences(), 1);
		assertEquals(testDictAttrVal.getEntryAt(0).getEntry(), testWME);
		assertEquals(testDictAttrVal.getEntryAt(1).getEntry(), test2WME);
		
		//Tests the origin of the location
		int[] digit1 = {0, 2};
		int[] digit2 = {1, 1};
		ArrayList<int[]> dummyOccurrences = new ArrayList<int[]>();
		dummyOccurrences.add(digit1);
		dummyOccurrences.add(digit2);
		
		//compare each element to make sure they are equals
		ArrayList<int[]> realOccurrences = testDictAttr.getEntryAt(0).getOccurrences();
		for(int i = 0; i<realOccurrences.size(); i++){
			for(int j=0; j<2; j++){
				assertEquals(realOccurrences.get(i)[j], dummyOccurrences.get(i)[j]);
			}
		}
		

	}
	
	@Test
	public void testFindWordLoc()
	{
		WME w = new WME("(s2 ^color yellow)");
		assertEquals(testDictAttr.findWordLoc(w), 0);
		assertEquals(testDictAttrVal.findWordLoc(w), -1);
	}
	
	@Test
	public void testAddEpisode()
	{
		//create new episode
		WME testWME = new WME("(s1 ^color red)");
        WME test2WME = new WME("(s1 ^color blue)");
        WME test3WME = new WME("s1 ^yolo hashtagswag");
        
        WME[] episode = new WME[3];
        episode[0] = testWME;
        episode[1] = test2WME;
        episode[2] = test3WME;
        
        //this episode has two occurences of the color attribute so adding it to the testDictAttr
        //will bump the number of occurrences of 'color' (at index 0) up to 5
        testDictAttr.addEpisode(2, episode);
        assertEquals(testDictAttr.getEntryAt(0).getSumOccurrences(), 5);
        
	}


}
