package hashTesting;

import java.util.ArrayList;
import java.util.Arrays;

public class ForgettingDictionary extends Dictionary {

	int dictSizeCap = 0; //means unlimited size
	
	public ForgettingDictionary(int compareType) {
		super(compareType);
	}
	
	public ForgettingDictionary(int compareType, int forgettingSize) {
		super(compareType);
		this.dictSizeCap = forgettingSize;
	}

	public ForgettingDictionary(ArrayList<WME[]> episodeList, int compareType){
		super(episodeList, compareType);
	}
	
	public ForgettingDictionary(ArrayList<WME[]> episodeList, int compareType, int forgettingSize){
		super(episodeList, compareType);
		this.dictSizeCap = forgettingSize;
	}
	
	public void addEpisode(int episodeIndex, WME[] episode){
		ArrayList<WME> newWMEs = checkEpisode(episode);
		cleanAndAdd(newWMEs, episodeIndex);
	}
	
	public int getCapSize(){
		return this.dictSizeCap;
	}

	
	/**
	 * This checks the episode to find WMEs not contained in the dictionary
	 * 
	 * @param episode to be checked
	 * @return WME[] that contains all undiscovered WMEs from a given episode
	 */
	private ArrayList<WME> checkEpisode(WME[] episode) {
		ArrayList<WME> undiscoveredWMEs = new ArrayList<WME>();
		for(WME wme: episode){
			
			if(!dictionary.containsKey(wme)){
				undiscoveredWMEs.add(wme);
			}
		}
		return undiscoveredWMEs;
	}

	
	/**
	 * Cleans the dictionary of unused WMEs by frequency and last use
	 * 
	 * @param newWMEs
	 * @return new list of WMEs to be added to the dictionary
	 */
	private void cleanAndAdd(ArrayList<WME> newWMEs, int episodeIndex) {
		//Add new entries to the dictionary.
		for(WME wme: newWMEs){			
			//increment the occurrences in that episode
			Entry e = new Entry(compareType, wme);
			dictionary.put(wme, e);
			e.addOccurrence(episodeIndex);
		}
		
		if (this.dictSizeCap == 0) return;
		
		//Remove extra entries from the dictionary.
		ArrayList<Entry> sortedList = this.getSortedEntryList();
		Entry entryToRemove = null;
		while(sortedList.size() > dictSizeCap){
			entryToRemove = sortedList.remove(sortedList.size()-1);
			dictionary.remove(entryToRemove.getWME());
		}
	}
}
