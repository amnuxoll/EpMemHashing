package unitTests;

import static org.junit.Assert.*;
import hashTesting.*;
import org.junit.Test;

public class DictionaryTest {

	@Test
	public void testCtor() {
		Dictionary testDict = new Dictionary(MainTest.makeQuickEpList(), WME.ATTR);
		
		WME w = new WME("(S1 ^alligator eats)");
		System.out.println(testDict.getEntryAt(0).getEntry().toString()+testDict.getEntryAt(0).getSumOccurrences());
		System.out.println(testDict.getEntryAt(1).getEntry().toString()+testDict.getEntryAt(1).getSumOccurrences());
		assertEquals(testDict.getEntryAt(1).getEntry(), w);
		assertEquals(testDict.getEntryAt(1).getSumOccurrences(), 1);
		assertEquals(testDict.getEntryAt(0).getSumOccurrences(), 3);
		
	}

}
