package DFRSApp;

/**
 * Holder class for : DFRS
 * 
 * @author OpenORB Compiler
 */
final public class DFRSHolder
        implements org.omg.CORBA.portable.Streamable
{
    /**
     * Internal DFRS value
     */
    public DFRSApp.DFRS value;

    /**
     * Default constructor
     */
    public DFRSHolder()
    { }

    /**
     * Constructor with value initialisation
     * @param initial the initial value
     */
    public DFRSHolder(DFRSApp.DFRS initial)
    {
        value = initial;
    }

    /**
     * Read DFRS from a marshalled stream
     * @param istream the input stream
     */
    public void _read(org.omg.CORBA.portable.InputStream istream)
    {
        value = DFRSHelper.read(istream);
    }

    /**
     * Write DFRS into a marshalled stream
     * @param ostream the output stream
     */
    public void _write(org.omg.CORBA.portable.OutputStream ostream)
    {
        DFRSHelper.write(ostream,value);
    }

    /**
     * Return the DFRS TypeCode
     * @return a TypeCode
     */
    public org.omg.CORBA.TypeCode _type()
    {
        return DFRSHelper.type();
    }

}
