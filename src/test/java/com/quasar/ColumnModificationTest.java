//package com.quasar;
//
//import java.sql.SQLException;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.JUnit4;
//
//import com.quasar.gen_old.model.Column_modifications_test;
//import com.quasar.gen_old.proxy.Test_2;
//
//@RunWith(JUnit4.class)
//public class ColumnModificationTest {
//
//	@Test
//	public void test_extra_column() {
//		Long long_saved_to_database = 112233L;
//		String text_saved_to_database = "some text";
//		Column_modifications_test cmt = null;
//		try {
//			cmt = Test_2.extra_column_test(text_saved_to_database, long_saved_to_database);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			Assert.fail();
//		}
//		Assert.assertNotNull(cmt);
//		Assert.assertNull(cmt.getExtra2());
//		Assert.assertNotNull(cmt.getColumnModificationsTestId());
//		Assert.assertNotNull(cmt.getColumn1Varchar());
//		Assert.assertEquals(text_saved_to_database, cmt.getColumn1Varchar());
//		Assert.assertEquals(long_saved_to_database, cmt.getColumn2Int());
//		Assert.assertEquals(54321L, cmt.getExtra1().longValue());
//	}
//
//	@Test
//	public void test_omitted_column_middle() {
//		Long l = 445533L;
//		String s = "another text";
//		Column_modifications_test cmt = null;
//		try {
//			cmt = Test_2.omitted_column_test_middle(s, l);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			Assert.fail();
//		}
//		Assert.assertNotNull(cmt);
//		Assert.assertNotNull(cmt.getColumnModificationsTestId());
//		Assert.assertNull(cmt.getColumn1Varchar());
//		Assert.assertEquals(l, cmt.getColumn2Int());
//		Assert.assertNull(cmt.getExtra2());
//	}
//
//}
