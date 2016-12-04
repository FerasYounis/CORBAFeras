package DFRSApp;

/** 
 * Helper class for : DFRS
 *  
 * @author OpenORB Compiler
 */ 
public class DFRSHelper
{
    /**
     * Insert DFRS into an any
     * @param a an any
     * @param t DFRS value
     */
    public static void insert(org.omg.CORBA.Any a, DFRSApp.DFRS t)
    {
        a.insert_Object(t , type());
    }

    /**
     * Extract DFRS from an any
     *
     * @param a an any
     * @return the extracted DFRS value
     */
    public static DFRSApp.DFRS extract( org.omg.CORBA.Any a )
    {
        if ( !a.type().equivalent( type() ) )
        {
            throw new org.omg.CORBA.MARSHAL();
        }
        try
        {
            return DFRSApp.DFRSHelper.narrow( a.extract_Object() );
        }
        catch ( final org.omg.CORBA.BAD_PARAM e )
        {
            throw new org.omg.CORBA.MARSHAL(e.getMessage());
        }
    }

    //
    // Internal TypeCode value
    //
    private static org.omg.CORBA.TypeCode _tc = null;

    /**
     * Return the DFRS TypeCode
     * @return a TypeCode
     */
    public static org.omg.CORBA.TypeCode type()
    {
        if (_tc == null) {
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init();
            _tc = orb.create_interface_tc( id(), "DFRS" );
        }
        return _tc;
    }

    /**
     * Return the DFRS IDL ID
     * @return an ID
     */
    public static String id()
    {
        return _id;
    }

    private final static String _id = "IDL:DFRSApp/DFRS:1.0";

    /**
     * Read DFRS from a marshalled stream
     * @param istream the input stream
     * @return the readed DFRS value
     */
    public static DFRSApp.DFRS read(org.omg.CORBA.portable.InputStream istream)
    {
        return(DFRSApp.DFRS)istream.read_Object(DFRSApp._DFRSStub.class);
    }

    /**
     * Write DFRS into a marshalled stream
     * @param ostream the output stream
     * @param value DFRS value
     */
    public static void write(org.omg.CORBA.portable.OutputStream ostream, DFRSApp.DFRS value)
    {
        ostream.write_Object((org.omg.CORBA.portable.ObjectImpl)value);
    }

    /**
     * Narrow CORBA::Object to DFRS
     * @param obj the CORBA Object
     * @return DFRS Object
     */
    public static DFRS narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof DFRS)
            return (DFRS)obj;

        if (obj._is_a(id()))
        {
            _DFRSStub stub = new _DFRSStub();
            stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
            return stub;
        }

        throw new org.omg.CORBA.BAD_PARAM();
    }

    /**
     * Unchecked Narrow CORBA::Object to DFRS
     * @param obj the CORBA Object
     * @return DFRS Object
     */
    public static DFRS unchecked_narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof DFRS)
            return (DFRS)obj;

        _DFRSStub stub = new _DFRSStub();
        stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
        return stub;

    }

}
