package DFRSApp;

/**
 * Interface definition: DFRS.
 * 
 * @author OpenORB Compiler
 */
public class _DFRSStub extends org.omg.CORBA.portable.ObjectImpl
        implements DFRS
{
    static final String[] _ids_list =
    {
        "IDL:DFRSApp/DFRS:1.0"
    };

    public String[] _ids()
    {
     return _ids_list;
    }

    private final static Class _opsClass = DFRSApp.DFRSOperations.class;

    /**
     * Operation bookFlight
     */
    public String bookFlight(String firstName, String lastName, String address, String phone, String destination, String date, String flightClass)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("bookFlight",true);
                    _output.write_string(firstName);
                    _output.write_string(lastName);
                    _output.write_string(address);
                    _output.write_string(phone);
                    _output.write_string(destination);
                    _output.write_string(date);
                    _output.write_string(flightClass);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("bookFlight",_opsClass);
                if (_so == null)
                   continue;
                DFRSApp.DFRSOperations _self = (DFRSApp.DFRSOperations) _so.servant;
                try
                {
                    return _self.bookFlight( firstName,  lastName,  address,  phone,  destination,  date,  flightClass);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation getBookedFlightCount
     */
    public String getBookedFlightCount(String recordType)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("getBookedFlightCount",true);
                    _output.write_string(recordType);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("getBookedFlightCount",_opsClass);
                if (_so == null)
                   continue;
                DFRSApp.DFRSOperations _self = (DFRSApp.DFRSOperations) _so.servant;
                try
                {
                    return _self.getBookedFlightCount( recordType);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation editFlightRecord
     */
    public void editFlightRecord(String recordID, String fieldName, String newValue)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("editFlightRecord",true);
                    _output.write_string(recordID);
                    _output.write_string(fieldName);
                    _output.write_string(newValue);
                    _input = this._invoke(_output);
                    return;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("editFlightRecord",_opsClass);
                if (_so == null)
                   continue;
                DFRSApp.DFRSOperations _self = (DFRSApp.DFRSOperations) _so.servant;
                try
                {
                    _self.editFlightRecord( recordID,  fieldName,  newValue);
                    return;
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation addFlight
     */
    public String addFlight(int economySeatCount, int businessSeatCount, int fitClassSeatCount, String destination, String date)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("addFlight",true);
                    _output.write_long(economySeatCount);
                    _output.write_long(businessSeatCount);
                    _output.write_long(fitClassSeatCount);
                    _output.write_string(destination);
                    _output.write_string(date);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("addFlight",_opsClass);
                if (_so == null)
                   continue;
                DFRSApp.DFRSOperations _self = (DFRSApp.DFRSOperations) _so.servant;
                try
                {
                    return _self.addFlight( economySeatCount,  businessSeatCount,  fitClassSeatCount,  destination,  date);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation transferReservation
     */
    public void transferReservation(String recordId, String CurrentCity, String OtherCity)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("transferReservation",true);
                    _output.write_string(recordId);
                    _output.write_string(CurrentCity);
                    _output.write_string(OtherCity);
                    _input = this._invoke(_output);
                    return;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("transferReservation",_opsClass);
                if (_so == null)
                   continue;
                DFRSApp.DFRSOperations _self = (DFRSApp.DFRSOperations) _so.servant;
                try
                {
                    _self.transferReservation( recordId,  CurrentCity,  OtherCity);
                    return;
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation resetCount
     */
    public void resetCount()
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("resetCount",true);
                    _input = this._invoke(_output);
                    return;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("resetCount",_opsClass);
                if (_so == null)
                   continue;
                DFRSApp.DFRSOperations _self = (DFRSApp.DFRSOperations) _so.servant;
                try
                {
                    _self.resetCount();
                    return;
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

}
