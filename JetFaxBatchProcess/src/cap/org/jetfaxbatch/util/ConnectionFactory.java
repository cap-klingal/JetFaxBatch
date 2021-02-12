package cap.org.jetfaxbatch.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

	public ConnectionFactory() {}

    public Connection getConnection(String _url, String _userName, String _password, String _driver)
        throws Exception  {
        Connection lConnection = null;
        Class.forName(_driver).newInstance();
        lConnection = DriverManager.getConnection(_url, _userName, _password);
        return lConnection;
    }

    public Connection getConnection(String _url, String _driver) throws Exception    {
    	Connection lConnection = null;
    	try {
    		Class.forName(_driver).newInstance();
    		lConnection = DriverManager.getConnection(_url);
    	}
    	catch (Exception e){
    		System.out.println("Excepton: "+e);
    	}
    	return lConnection;
    }
}
