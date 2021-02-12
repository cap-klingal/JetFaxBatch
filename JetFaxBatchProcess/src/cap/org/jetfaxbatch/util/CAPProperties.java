package cap.org.jetfaxbatch.util;

import java.io.*;
import java.util.*;

public class CAPProperties {
	private Properties _properties;
    /*public String _runtimeEnvironment;*//*Code should be environment non-specific*/
    
    String _operatingEnv = System.getProperty("os.name");
    private static String propFile = null;
   
  
    //A handle to the unique  PropEventInterface instance.
    private static CAPProperties _capProperties = null;

    public CAPProperties() {
        _properties = new Properties();

         try {
         	if (_operatingEnv.startsWith("Windows")) {
         		propFile = "config\\JetFax.properties";//local
     
        	}
         	else
         	{
         		propFile = System.getProperty("PropertiesFile");//unix
         	
         	}
         	
            _properties.load(new FileInputStream(propFile));
          
//            _runtimeEnvironment = _properties.getProperty("runtimeEnvironment");
//            System.out.println("Your Operating System: "+_operatingEnv);
        }
        catch(IOException e)  {
            System.out.println(" Reading property file Error : " + e.getMessage());
            System.out.println("Make sure " + propFile + " exists.");
            System.exit(1);
        }
    }
    
    /**
     * @return The unique instance of this class.
     */
    public static CAPProperties getAppProperties ( ) {
       if(null == _capProperties)
       	_capProperties = new CAPProperties();

       return _capProperties;
    }
    
    /**
     * @param   String  Property for which value requested
     * @return  String  Property value requested
   */
    public String getProperty(String _property)  {
        //return _properties.getProperty(_runtimeEnvironment + "." + _property);
        return _properties.getProperty(_property);
    }

    public String getNoEnvironmentProperty(String _property)  {
        return _properties.getProperty(_property);
    }

    /**
     * @param   String  Property to set value for
     * @param   String  Property value to be set
   */
    public void setProperty(String _property, String _value)  {
        //_properties.setProperty(_runtimeEnvironment + "." + _property, _value);
        _properties.setProperty(_property, _value);
    }

}
