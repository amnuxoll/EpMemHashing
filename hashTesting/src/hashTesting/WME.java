package hashTesting;

/**
 * This is a Working Memory Element (WME) class that takes either a single
 * string and creates a WME by pulling out the id, attribute, and value or takes
 * 3 strings which are those values and creates a single Working Memory Element
 * which represents one single memory "event."
 * 
 * @author Alexandra Warlen
 * @version Wednesday May 14, 2014
 */
public class WME
{
     /** catagories for comparing two WMEs to each other */
     public static final int ID = 1;    //compare IDs
     public static final int ATTR = 2;  //compare ATTRs
     public static final int VAL = 4;   //compare VALs

    /** specifies what components of two WMEs are compared when determining if
        they are equal (see {@link #equals}).*/
    public static final int COMPARE_TYPE = ATTR + VAL;
    
    /** instance variables - replace the example below with your own*/
    public String id;
    public String attribute;
    public String value;

    
    
    /**
     * Constructor for WME with no parameters.
     */
    public WME()
    {
        id = null;
        attribute = null;
        value = null;
    }

    
    
    /**
     * Constructor for WME with single string.
     */
    public WME(String line)
    {
        //set some default values
        this();
        parseWME(line);
    }//WME ctor

    
    
    /**
     * Constructor for WME with 3 parameters.
     */
    public WME(String initID, String initAttrib, String initVal)
    {
        // check for errors
        if (initID == null || initID.equals("")) {
            this.id = null;
        }

        if (initAttrib == null || initAttrib.equals("")) {
            this.id = null;
        }

        if (initVal == null || initVal.equals("")) {
            this.id = null;
        }
        
        //if id is not in proper format set all values to null.
        if (!isID(initID)) {
            initID = null;
            initAttrib = null;
            initVal = null;
        }
        
        this.id = initID;
        this.attribute = initAttrib;
        this.value = initVal;
        
    }//ctor

    
    
    /**
     * parseWME
     *
     * Given a WME described as a string, this method parses out the id, attrib
     * and value and initializes the instance variables appropriately
     *
     * @return 0 on success, negative number on failure
     */
    public int parseWME(String line)
    {
        // spot check for syntax errors
        if (line == null) {
            return -1; 
        }
        if (line.indexOf(" ") == -1) {
            return -2; 
        }
        if (line.indexOf("^") == -1) {
            return -3; 
        }
        if (line.indexOf("(") == -1) {
            return -4; 
        }

        String idParse, attribParse, valParse = null;

        // if any indexOf() calls return -1 catch exception and make all parameters null
        try {
            idParse = line.substring(line.indexOf("(")+1, line.indexOf(" "));

            //Advance through the line and find the edge
            line = line.substring(line.indexOf("^"));
            //Save the found edge for now
            attribParse = line.substring(1, line.indexOf(" "));
            //Move the string along the line
            line = line.substring(line.indexOf(" "));
            line = line.trim();
            //Find the second vertex
            valParse = line.substring(0, line.indexOf(")"));
        } catch (IndexOutOfBoundsException e) {
            // there was a formatting error in WME string
            return -5;
        }
        
        // if id is not in correct format set all values to null
        if (!isID(idParse)){
            return -6;
        }

        //Success!
        this.id = idParse;
        this.attribute = attribParse;
        this.value = valParse;

        return 0;

    }//parseWME

    
    
    /**
     * @return true if this WME contains valid data
     */
    public boolean isValid()
    {
        return this.id != null;
    }
    
    
    
    /**
     * isID is a method that checks if a string is in the format of an ID, 
     * meaning that it has on letter followed by a single number, no spaces or other
     * characters within the number.
     * 
     * @param  fieldCheck   parameter string to be checked
     * @return         true or false depending on if parameter is in the format of an ID or not
     */
    public boolean isID(String fieldCheck)
    {
        String field = fieldCheck;
        // check for basic errors
        if (field == null) {
            return false;
        }
        field = field.trim();
        field = field.toLowerCase();
        if (field.equals("")) {
            return false;
        }
        
        // field must contain 2 or more characters
        if (field.length() < 2) {
            return false;
        }
        
        // get first character
        String letter = field.substring(0,1);
        // if that character isn't a letter return false
        if (!letter.matches("[a-z]+")) {
            return false;
        }
        
        field = field.substring(1);
        // if there is a space within the number
        if (field.indexOf(" ") != -1) {
            return false;
        }
        
        // if field contains only numbers return true
        if (field.matches("[0-9]+")) {
            return true;
        }
        
        return false;
    }//isID
    
    
    
    /**
     * This overloaded version uses the value instance variable
     */
    public boolean isID()
    {
        return isID(this.value);
    }
    
    
    
    /**
     * isNum is a method that checks if a string is a number, without any extra 
     * non-numerical characters.
     * 
     * @param  fieldCheck   parameter string to be checked
     * @return true if this is a number
     */
    public boolean isNum(String field)
    {
        // trim any spaces
        field = field.trim();

        // check if number is negative
        if (field.substring(0,1).equals("-")) {
            //take off "-" but keep track if number is negative
            field = field.substring(1);
        }
        
        // if string only contains numbers
        return field.matches("[0-9]+");
    }

    
    
    /** @return human readable version of the WME */
    public String toString()
    {
        return "(" + id + " ^" + attribute + " " + value + ")";
    }//toString

    
    
    /** @return human readable version of the attribute */
    public String getAttrib()
    {
        return this.attribute;
    }//getAttrib
    
    
    
    /** @return human readable version of the value */
    public String getVal()
    {
        return this.value;
    }//getVal
    
    
    
    /** @return true if given WME has the same attr and value as this */
    @Override
    public boolean equals(Object otherObj)
    {
    	return equalsWithType(otherObj, WME.COMPARE_TYPE);
            
    }//equals
    

    @SuppressWarnings("unused")
	@Override
    public int hashCode()
    {
    	String ret = "";
    	if ((WME.COMPARE_TYPE & ID) > 0)
        {
           ret += this.id;
        }
        if ((WME.COMPARE_TYPE & ATTR) > 0)
        {
            ret += this.attribute;
        }
        if ((WME.COMPARE_TYPE & VAL) > 0)
        {
            ret += this.value;
        }
    	return ret.hashCode();
    }
    
    /**
     * compares the components of another WME and this one.
     * 
     * @param otherObj - other WME to compare to
     * @param compareType - which components to compare as a sum of ID, 
     *                       ATTR or VAL (constants defined in this class)
     * 
     *  
     * @return true if given WMEs match*/
    public boolean equalsWithType(Object otherObj, int compareType)
    {
        //make sure we've been given a WME
        if (! (otherObj instanceof WME)) return false;
        WME other = (WME)otherObj;

        //compare them using the directive specified in COMPARE_TYPE
        if ((compareType & ID) > 0)
        {
            if (! other.id.equalsIgnoreCase(this.id)) return false;
        }
        if ((compareType & ATTR) > 0)
        {
            if (! other.attribute.equalsIgnoreCase(this.attribute)) return false;
        }
        if ((compareType & VAL) > 0)
        {
            if (! other.value.equalsIgnoreCase(this.value)) return false;
        }

        return true;
            
    }//equals
    
    
}//class WME
