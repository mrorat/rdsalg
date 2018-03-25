package com.quasar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;

import com.quasar.gen.proxy.Test_2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.mysql.jdbc.MysqlDataTruncation;
import com.quasar.gen.model.Type_varchar;

@RunWith(JUnit4.class)
public class TypeVarcharTest {

	String allChars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!\"�$%^&*()_+-=[]{};'#:@~,./<>?\\|";

	@Test
	public void test_varchar_50() throws SQLException {
		String varchar50 = getStringWithSpecificLength(50);
		Type_varchar t = Test_2.test_varchar_50(varchar50);
		assertEquals(varchar50, t.getVarchar50());
	}

	/**
	 * Test to make sure we are getting a DataTruncation exception when saving string which is too long for the column
	 * @throws SQLException
	 */
	@Test(expected = MysqlDataTruncation.class)
	public void test_varchar_51_in_50() throws SQLException {
		String varchar51 = getStringWithSpecificLength(51);
		Type_varchar t = Test_2.test_varchar_50(varchar51);
		assertEquals(varchar51, t.getVarchar50());
	}

	@Test
	public void test_varchar_500() throws SQLException {
		String varchar500 = getStringWithSpecificLength(50);
		Type_varchar t = Test_2.test_varchar_500(varchar500);
		assertEquals(varchar500, t.getVarchar500());
		assertNotNull(t.getIdTypeVarchar());
		assertNotNull(t.toString());
	}

	@Test
	public void test_varchar_5000() throws SQLException {
		String varchar5000 = getStringWithSpecificLength(50);
		Type_varchar t = Test_2.test_varchar_5000(varchar5000);
		assertEquals(varchar5000, t.getVarchar5000());
	}

	private String getStringWithSpecificLength(int length)
	{
		String retVal = "";
		while (retVal.length() < length)
		{
			retVal += allChars;
		}
		return retVal.substring(0, length);
	}
}
