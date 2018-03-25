package com.quasar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.mysql.jdbc.MysqlDataTruncation;
import com.quasar.gen.model.Type_blob;
import com.quasar.gen.proxy.Test_2;

import javax.sql.rowset.serial.SerialBlob;

@RunWith(JUnit4.class)
public class TypeBlobTest {

	@Test
	public void test_blob_100() throws SQLException {
		byte[] bytes = new byte[100];
		for (int i = 99; i>-1; i--)
		{
			bytes[99-i] = (byte)i;
		}
//		InputStream is = new ByteArrayInputStream(bytes);
		Type_blob t = Test_2.test_blob_tinyblob(new SerialBlob(bytes));
		for (int i=0; i<100; i++)
		{
			assertEquals(bytes[i], t.getTypeTinyblob().getBytes(i+1, 1)[0]);
		}
		assertNotNull(t.getIdTypeBlob());
		assertNotNull(t.toString());
	}

	@Test(expected=MysqlDataTruncation.class)
	public void test_blob_256_in_255() throws SQLException {
		byte[] bytes = new byte[256];
		for (int i = 255; i>-1; i--)
		{
			bytes[255-i] = (byte)i;
		}
		InputStream is = new ByteArrayInputStream(bytes);
		Type_blob t = Test_2.test_blob_tinyblob(new SerialBlob(bytes));
		for (int i=0; i<256; i++)
		{
			assertEquals(bytes[i], t.getTypeTinyblob().getBytes(i+1, 1)[0]);
		}
	}
}
