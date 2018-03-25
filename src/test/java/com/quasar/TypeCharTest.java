package com.quasar;

import com.mysql.jdbc.MysqlDataTruncation;
import com.quasar.gen.model.Type_char;
import com.quasar.gen.proxy.Test_2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class TypeCharTest {

	@Test
	public void test_char_1() throws SQLException {
	    char testChar = 'c';
		Type_char t = Test_2.insert_char_5_return_saved_value(String.valueOf(testChar));
		assertEquals(String.valueOf(testChar), t.getChar5());
		assertNotNull(t.getIdTypeChar());
		assertNotNull(t.toString());
	}

	public static void main(String[] args) {
		int a = 20;
		int var = --a * a++ + a-- - --a;
		System.out.println("a = " + a);
		System.out.println("var = " + var);
	}

	@Test
	public void test_char_5() throws SQLException {
		String s = getUniqueStringWithSpecificSize(5);
		Type_char t = Test_2.test_char_5(s);
		assertEquals(s, t.getChar5());
		assertNotNull(t.getIdTypeChar());
		assertNotNull(t.toString());
	}

	@Test
	public void test_char_255() throws SQLException {
		String s = getUniqueStringWithSpecificSize(255);
		Type_char t = Test_2.test_char_255(s);
		assertEquals(s, t.getChar255());
		assertNotNull(t.getIdTypeChar());
		assertNotNull(t.toString());
	}

	@Test(expected=MysqlDataTruncation.class)
	public void test_char_6_in_5() throws SQLException {
		String s = String.valueOf(System.currentTimeMillis()).substring(0,6);
		Type_char t = Test_2.test_char_5(s);
		assertEquals(s, t.getChar5());
		assertNotNull(t.getIdTypeChar());
	}

	private String getUniqueStringWithSpecificSize(int length) {
		String s = String.valueOf(System.currentTimeMillis());
		while (s.length() < length)
			s += s;
		s = s.substring(0, length);
		return s;
	}
}