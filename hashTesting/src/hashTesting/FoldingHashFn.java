package hashTesting;

import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This is an implementation of the "folding" hash algorithm.  Each unique WME
 * that the function ever sees from any episode is assigned to a unique bit in
 * the hash code.  Since you have many more WMEs than bits, assign them
 * sequentially and then "fold over" back to 0 when you run out.  Thus, several
 * WMEs are sharing each bit.
 *
 * To hash an episode, simply set the bits that correspond to the WMEs in the
 * episode.
 * 
 * @author Nux
 * @version 20 May 2014
 */
public class FoldingHashFn extends DummyHashFn
{
    //keep track of all WMEs we've seen in any episode
    private ArrayList<WME> dict = new ArrayList<WME>();
    
    private int episodeCompletedCount = 0;
    
    //ctor
    public FoldingHashFn(int size)
    {
        super(size);
    }

    /**
     * hash
     *
     * (see the comment at the top of this file)
     * @throws IOException 
     */
	@Override
    public int[] hash(WME[] episode)
    {
        //Add all the WMEs to the dictionary
        for (WME wme : episode) {
            if (! dict.contains(wme)) {
                dict.add(wme);
            }
        }//for
        episodeCompletedCount++;
        
        if (episodeCompletedCount == 30){
        	//print dictionary to file
        	FileWriter outputStream = null;
        	
        	try {
        		outputStream = new FileWriter("C:\\Users\\meyer14\\Desktop\\Research\\Raw Data\\FoldingDictOutput.txt");
        		for(WME wme : dict){
        			outputStream.write(wme.toString()); 
        			outputStream.write("\n");
        		}
        	} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	finally {
        		if (outputStream != null){
        			try {
						outputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        	}
        }

        //Generate an empty code (all zeroes)
        int[] code = super.hash(episode);

        //Set the bit corresponding to each episode
        for (WME wme : episode) {
            //find the index of this wme in the dictionary
            int index = dict.indexOf(wme);
            index = index % codeSize;  //fold

            //set the bit
            code[index] = 1;
        }//for

        return code;
        
    }//hash


	@Override
	public String getName() {
		return "Folding Hash Function";
	}
    
}//class