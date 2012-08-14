/**
 * 
 */
package edu.rpi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

 /**
 * @author glosej
 *
 */
public class FAMISConnectionHelper {
    
    private static Logger logger = Logger.getLogger(FAMISConnectionHelper.class);
	
    private  Connection conn = null;
    private  String driverClassName;
    private  String url;
    private  String username;
    private  String password;

    public FAMISConnectionHelper(String driverClassName, String url, String username, String password, boolean autoCommit) {
   		setDriverClassName(driverClassName);
   		setUrl(url);
   		setUsername(username);
   		setPassword(password);
   		if (autoCommit)
   		    setConnectionAutoCommit();
   		else 
   		    setConnectionManualCommit();
   		
	}
   	
    private void setConnectionAutoCommit() {
        try {
            Class.forName(getDriverClassName());
            //Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
            conn.setAutoCommit(true); // Auto commit
            logger.debug("SETTING AUTO COMMIT TO TRUE");
        } catch (ClassNotFoundException e) {
            logger.error("could not find the database driver");
            logger.error(e);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e);
        }           
    }

   	private void setConnectionManualCommit() {
        try {
            Class.forName(getDriverClassName());
            //Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
            conn.setAutoCommit(false); // Must manually set a commit
            logger.debug("SETTING AUTO COMMIT TO FALSE");
        } catch (ClassNotFoundException e) {
            logger.error("could not find the database driver");
            logger.error(e);
        } catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}   		
   	}
	
	public Connection getConn() {
		return conn;
	}
	
	public void rollback() throws SQLException {
		conn.rollback();
	}
	
	public void commit() throws SQLException {
		conn.commit();
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}