package cap.org.jetfaxbatch.util;


import java.sql.Connection;
import java.sql.SQLException;

public class EbsConnection {
    private Connection ebsConn;
    private static EbsConnection ebsConnection = null;

    //Make constructor private to prevent anyone from instantiating this class.
    private EbsConnection() 
    {
    	ebsConn = null;
        CAPProperties appProp = new CAPProperties();
        try
        {
        	String url= appProp.getProperty("oracle_ebs.jdbc.url");
        	String username= appProp.getProperty("oracle_ebs.jdbc.user");
        	String password= appProp.getProperty("oracle_ebs.jdbc.password");
        	String driver= appProp.getProperty("oracle_ebs.jdbc.driver");
            ebsConn = (new ConnectionFactory()).getConnection(url,username,password,driver);
            ebsConn.setAutoCommit(true);
            return;
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
    }

    //Return handle to unique instance of this class.
    public static EbsConnection getEbsConnection() 
    {
        if(ebsConnection == null)
        {
        	ebsConnection = new EbsConnection();
        }
        return ebsConnection;
    }

    public Connection getConnection()
    {
    	
        return ebsConn;
    }

    public void closeConnection() 
    {
        try
        {
            if(ebsConn != null && !ebsConn.isClosed())
            {
            	ebsConn.close();
            	ebsConnection = null;
                return;
            }
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
           
        }
    }

}
