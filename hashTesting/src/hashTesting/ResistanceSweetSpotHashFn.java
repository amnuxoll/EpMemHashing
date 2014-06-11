package hashTesting;

/**
 * A hash function that creates a dictionary of the WME's in all of the 
 * episodes and sorts them by number of occurrences to find the most descriptive
 * words for creating a hash function
 * 
 * @author Alexandra Warlen
 * @author Allie Seibert
 * @version Tuesday May 27, 2014
 */
public class ResistanceSweetSpotHashFn extends SweetSpotHashFn{

	private double resistance = 1.0;
	
	
	
	/**
	 * ctor initializes as per the super and creates a dictionary, discardFrac implied at 0.0
	 * @param size
	 */
	public ResistanceSweetSpotHashFn(int size)
	{
		super(size);
	}//ctor
	
	
	
	/**
	 * ctor initializes as per the super and creates a dictionary
	 * @param size
	 * @param discardFraction
	 */
	public ResistanceSweetSpotHashFn(int size, double discardFraction)
	{
		super(size, discardFraction);
	}//ctor
	
	
	
	/**
	 * ctor initializes as per the super and creates a dictionary
	 * @param size
	 * @param discardFraction
	 */
	public ResistanceSweetSpotHashFn(int size, double discardFraction, double resistance)
	{
		super(size, discardFraction);
		this.resistance = resistance;
	}//ctor
	  
	
	
	/**
	 * noResistance
	 * 
	 * used in the super to alte
	 * 
	 * @return
	 */
	@Override
	protected boolean noResistance()
	{
		return Math.random() < (1.0/Math.pow(episodeIndex, resistance));	
	}
	
	
		
	/**
	 * getName
	 * 
	 * gives a name for printing
	 */
	public String getName()
	{
		return resistance + " Resistance " + super.getName();
	}//getName
		
	
	
}//class ResistantSweetSpotHashFn
