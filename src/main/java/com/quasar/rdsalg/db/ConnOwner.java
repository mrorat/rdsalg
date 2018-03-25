package com.quasar.rdsalg.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.quasar.rdsalg.PropertiesLoader;

public class ConnOwner
{
	private final static Properties properties = PropertiesLoader.loadProperties(ClassLoader.getSystemClassLoader());
	private final static String dbHost = PropertiesLoader.getProperty("db.host"),
			dbSchema = PropertiesLoader.getProperty("db.schema"),
			dbUser = PropertiesLoader.getProperty("db.user"),
			dbPassword = PropertiesLoader.getProperty("db.password"),
			dbUserHasAccessToMetadata = PropertiesLoader.getProperty("db.user.has.access.to.metadata");
	
	public static Connection getConnection()
    {
		try
		{
			String connectionString = String.format("jdbc:mysql://%s/%s?user=%s&password=%s%s"
					, dbHost, dbSchema, dbUser, dbPassword,
					Boolean.parseBoolean(dbUserHasAccessToMetadata) ? "" : "&noAccessToProcedureBodies=true");
			return DriverManager.getConnection(connectionString);
		}
		catch (SQLException ex) {
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());
			}
		return null;
	}

	public static void close(Connection conn)
	{
		try
		{
			if (conn != null && !conn.isClosed())
				conn.close();
		}
		catch (SQLException e)
		{
			// shouldn't be closed
			e.printStackTrace();
		}
	}
	
    static public Integer getInteger(ResultSet rs, String strColName ) throws SQLException
    {
        int nValue = rs.getInt( strColName );
        return rs.wasNull() ? null : nValue;
    }
    
	public static Boolean getBoolean(ResultSet rs, String strColName) throws SQLException
	{
        Boolean nValue = rs.getBoolean( strColName );
        return rs.wasNull() ? null : nValue;
	}

	public static Double getDouble(ResultSet rs, String strColName) throws SQLException
	{
		Double nValue = rs.getDouble(strColName);
		return rs.wasNull() ? null : nValue;
	}

	public static Float getFloat(ResultSet rs, String strColName) throws SQLException
	{
		Float nValue = rs.getFloat(strColName);
		return rs.wasNull() ? null : nValue;
	}

	public static Date getDate(ResultSet rs, String strColName) throws SQLException
	{
		Date nValue = rs.getDate(strColName);
		return rs.wasNull() ? null : nValue;
	}
}