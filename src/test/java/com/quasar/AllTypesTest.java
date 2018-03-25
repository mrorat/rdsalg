package com.quasar;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Optional;

import com.quasar.gen.model.Alltypes;
import com.quasar.gen.model.enums.TypeEnum2NotNullType;
import com.quasar.gen.model.enums.TypeEnumNotNullType;
import com.quasar.gen.proxy.Test_2;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class AllTypesTest {


	@Test
	public void test_with_nulls() throws SQLException {
		BigInteger p_type_big_int_null = null;
		BigInteger p_type_big_int_not_null = new BigInteger("22");
		BigDecimal p_type_decimal_null = null;
		BigDecimal p_type_decimal_not_null = new BigDecimal("44.55");
		Double p_type_double_null = null;
		Double p_type_double_not_null = 66.777888999;
		Float p_type_float_null = null;
		Float p_type_float_not_null = new Float("88.99");
		Long p_type_int_null = null;
		Long p_type_int_not_null = new Long(1000);
		Integer p_type_mediumint_null = null;
		Integer p_type_mediumint_not_null = 1200;
		Integer p_type_smallint_null = null;
		Integer p_type_smallint_not_null = 16;
		Boolean p_type_tinyint_null = null;
		Boolean p_type_tinyint_not_null = false;
		Character p_type_char_null = null;
		Character p_type_char_not_null = 'z';
		String p_type_varchar_45_null = null;
		String p_type_varchar_45_not_null = "efgh";
		Date p_type_date_null = null;
		Date p_type_date_not_null = new Date(698342782);
		Timestamp p_type_datetime_null = null;
		Timestamp p_type_datetime_not_null = new Timestamp(123115156);
		p_type_datetime_not_null.setNanos(0);
		Time p_type_time_null = null;
		Time p_type_time_not_null = new Time(31913113);
		Timestamp p_type_timestamp_null = null;
		Timestamp p_type_timestamp_not_null = new Timestamp(49444444);
		p_type_timestamp_not_null.setNanos(0);
		Integer p_type_year_null = null;
		Integer p_type_year_not_null = new Integer("2000");
		String p_type_longtext_null = null;
		String p_type_longtext_not_null = "Lazy fox2";
		String p_type_mediumtext_null = null;
		String p_type_mediumtext_not_null = "Lazy fox4";
		String p_type_text_null = null;
		String p_type_text_not_null = "Lazy fox6";
		String p_type_tinytext_null = null;
		String p_type_tinytext_not_null = "Lazy fox8";
		Boolean p_type_bit_null = null;
		Boolean p_type_bit_not_null = true;
		TypeEnumNotNullType p_type_enum_null = TypeEnumNotNullType.FALSE;
		TypeEnum2NotNullType p_type_enum2_null = TypeEnum2NotNullType.MALE;
		//assertEquals(1, UsersProxy.getUsers().size());
		Alltypes at = Test_2.testbasictypes(
				p_type_big_int_null,
				p_type_big_int_not_null,
				p_type_decimal_null,
				p_type_decimal_not_null,
				p_type_double_null,
				p_type_double_not_null,
				p_type_float_null,
				p_type_float_not_null,
				p_type_int_null,
				p_type_int_not_null,
				p_type_mediumint_null,
				p_type_mediumint_not_null,
				p_type_smallint_null,
				p_type_smallint_not_null,
				p_type_tinyint_null,
				p_type_tinyint_not_null,
				p_type_char_null,
				p_type_char_not_null,
				p_type_varchar_45_null,
				p_type_varchar_45_not_null,
				p_type_date_null,
				p_type_date_not_null,
				p_type_datetime_null,
				p_type_datetime_not_null,
				p_type_time_null,
				p_type_time_not_null,
				p_type_timestamp_null,
				p_type_timestamp_not_null,
				p_type_year_null,
				p_type_year_not_null,
				p_type_longtext_null,
				p_type_longtext_not_null,
				p_type_mediumtext_null,
				p_type_mediumtext_not_null,
				p_type_text_null,
				p_type_text_not_null,
				p_type_tinytext_null,
				p_type_tinytext_not_null,
				p_type_bit_null,
				p_type_bit_not_null,
				p_type_enum_null,
				p_type_enum2_null);

		assertEquals(p_type_big_int_null, at.getTypeBigIntNull());
		assertEquals(p_type_big_int_not_null.toString(), at.getTypeBigIntNotNull().toString());
		assertEquals(p_type_decimal_null, at.getTypeDecimalNull());
		assertEquals(p_type_decimal_not_null, at.getTypeDecimalNotNull());
		assertEquals(p_type_double_null, at.getTypeDoubleNull());
		assertEquals(p_type_double_not_null, at.getTypeDoubleNotNull());
		assertEquals(p_type_float_null, at.getTypeFloatNull());
		assertEquals(p_type_float_not_null, at.getTypeFloatNotNull());
		assertEquals(p_type_int_null, at.getTypeIntNull());
//		assertEquals(at.getTypeIntNotNull(), p_type_int_not_null);
		assertEquals(p_type_mediumint_null, at.getTypeMediumintNull());
//		assertEquals(at.getTypeMediumintNotNull(), p_type_mediumint_not_null);
		assertEquals(p_type_smallint_null, at.getTypeSmallintNull());
//		assertEquals(at.getTypeSmallintNotNull(), p_type_smallint_not_null);
		assertEquals(p_type_tinyint_null, at.getTypeTinyintNull());
		assertEquals(p_type_tinyint_not_null, at.getTypeTinyintNotNull());
		assertEquals(p_type_char_not_null, at.getTypeCharNotNull());
		assertEquals(p_type_char_null, at.getTypeCharNull());
		assertEquals(p_type_varchar_45_null, at.getTypeVarchar45Null());
		assertEquals(p_type_varchar_45_not_null, at.getTypeVarchar45NotNull());
		Assert.assertNull(at.getTypeDateNull());
		assertEquals(p_type_date_not_null.toString(), at.getTypeDateNotNull().toString());
		assertEquals(p_type_datetime_null, at.getTypeDatetimeNull());
		assertEquals(p_type_datetime_not_null, at.getTypeDatetimeNotNull());
		assertEquals(p_type_time_null, at.getTypeTimeNull());
		assertEquals(p_type_time_not_null.toString(), at.getTypeTimeNotNull().toString());
		assertEquals(p_type_timestamp_null, at.getTypeTimestampNull());
		assertEquals(p_type_timestamp_not_null, at.getTypeTimestampNotNull());
		assertEquals(p_type_year_null, at.getTypeYearNull());
		assertEquals(p_type_year_not_null, at.getTypeYearNotNull());
		assertEquals(p_type_longtext_null, at.getTypeLongtextNull());
		assertEquals(p_type_longtext_not_null, at.getTypeLongtextNotNull());
		assertEquals(p_type_mediumtext_null, at.getTypeMediumtextNull());
		assertEquals(p_type_mediumtext_not_null, at.getTypeMediumtextNotNull());
		assertEquals(p_type_text_null, at.getTypeTextNull());
		assertEquals(p_type_text_not_null, at.getTypeTextNotNull());
		assertEquals(p_type_tinytext_null, at.getTypeTinytextNull());
		assertEquals(p_type_tinytext_not_null, at.getTypeTinytextNotNull());
		assertEquals(p_type_bit_null, at.getTypeBitNull());
		assertEquals(p_type_bit_not_null, at.getTypeBitNotNull());
		assertEquals(p_type_enum_null.name(), at.getTypeEnumNotNullType().name());
	}

	@Test
	public void test_without_nulls() throws SQLException
	{
		BigInteger p_type_big_int_null = new BigInteger("11");
		BigInteger p_type_big_int_not_null = new BigInteger("22");
		BigDecimal p_type_decimal_null = new BigDecimal("33.44");
		BigDecimal p_type_decimal_not_null = new BigDecimal("44.55");
		Double p_type_double_null = 55.666777888999;
		Double p_type_double_not_null = 66.777888999;
		Float p_type_float_null = new Float("77.88");
		Float p_type_float_not_null = new Float("88.99");
		Long p_type_int_null = new Long(999);
		Long p_type_int_not_null = new Long(1000);
		Integer p_type_mediumint_null = 1100;
		Integer p_type_mediumint_not_null = 1200;
		Integer p_type_smallint_null = 15;
		Integer p_type_smallint_not_null = 16;
		Boolean p_type_tinyint_null = true;
		Boolean p_type_tinyint_not_null = false;
		Character p_type_char_null = 'y';
		Character p_type_char_not_null = 'z';
		String p_type_varchar_45_null = "abcd";
		String p_type_varchar_45_not_null = "efgh";
		Date p_type_date_null = new Date(Calendar.getInstance().getTime().getTime());
		Date p_type_date_not_null = new Date(Calendar.getInstance().getTime().getTime());
		Timestamp p_type_timestamp1_null = new Timestamp(Calendar.getInstance().getTime().getTime());
		p_type_timestamp1_null.setNanos(0);
		Timestamp p_type_timestamp1_not_null = new Timestamp(Calendar.getInstance().getTime().getTime());
		p_type_timestamp1_not_null.setNanos(0);
		Time p_type_time_null = new Time(Calendar.getInstance().getTime().getTime());
		Time p_type_time_not_null = new Time(Calendar.getInstance().getTime().getTime());
		Timestamp p_type_timestamp2_null = new Timestamp(Calendar.getInstance().getTime().getTime());
		p_type_timestamp2_null.setNanos(0);
		Timestamp p_type_timestamp2_not_null = new Timestamp(Calendar.getInstance().getTime().getTime());
		p_type_timestamp2_not_null.setNanos(0);
		Integer p_type_year_null = new Integer("1999");
		Integer p_type_year_not_null = new Integer("2000");
		String p_type_longtext_null = "Lazy fox1";
		String p_type_longtext_not_null = "Lazy fox2";
		String p_type_mediumtext_null = "Lazy fox3";
		String p_type_mediumtext_not_null = "Lazy fox4";
		String p_type_text_null = "Lazy fox5";
		String p_type_text_not_null = "Lazy fox6";
		String p_type_tinytext_null = "Lazy fox7";
		String p_type_tinytext_not_null = "Lazy fox8";
		Boolean p_type_bit_null = false;
		Boolean p_type_bit_not_null = true;
		TypeEnumNotNullType p_type_enum_not_null = TypeEnumNotNullType.TRUE;
		TypeEnum2NotNullType p_type_enum2_not_null = TypeEnum2NotNullType.FEMALE;
		//assertEquals(1, UsersProxy.getUsers().size());
		Alltypes at = Test_2.testbasictypes(
				p_type_big_int_null,
				p_type_big_int_not_null,
				p_type_decimal_null,
				p_type_decimal_not_null,
				p_type_double_null,
				p_type_double_not_null,
				p_type_float_null,
				p_type_float_not_null,
				p_type_int_null,
				p_type_int_not_null,
				p_type_mediumint_null,
				p_type_mediumint_not_null,
				p_type_smallint_null,
				p_type_smallint_not_null,
				p_type_tinyint_null,
				p_type_tinyint_not_null,
				p_type_char_null,
				p_type_char_not_null,
				p_type_varchar_45_null,
				p_type_varchar_45_not_null,
				p_type_date_null,
				p_type_date_not_null,
				p_type_timestamp1_null,
				p_type_timestamp1_not_null,
				p_type_time_null,
				p_type_time_not_null,
				p_type_timestamp2_null,
				p_type_timestamp2_not_null,
				p_type_year_null,
				p_type_year_not_null,
				p_type_longtext_null,
				p_type_longtext_not_null,
				p_type_mediumtext_null,
				p_type_mediumtext_not_null,
				p_type_text_null,
				p_type_text_not_null,
				p_type_tinytext_null,
				p_type_tinytext_not_null,
				p_type_bit_null,
				p_type_bit_not_null,
				p_type_enum_not_null,
				p_type_enum2_not_null);
		assertEquals(at.getTypeBigIntNull().toString(), p_type_big_int_null.toString());
		assertEquals(at.getTypeBigIntNotNull().toString(), p_type_big_int_not_null.toString());
		assertEquals(p_type_decimal_null.toString(), at.getTypeDecimalNull().toString());
		assertEquals(p_type_decimal_not_null, at.getTypeDecimalNotNull());
		assertEquals(at.getTypeDoubleNull(), p_type_double_null);
		assertEquals(at.getTypeDoubleNotNull(), p_type_double_not_null);
		assertEquals(at.getTypeFloatNull(), p_type_float_null);
		assertEquals(at.getTypeFloatNotNull(), p_type_float_not_null);
		assertEquals(at.getTypeIntNull(), p_type_int_null);
//		assertEquals(at.getTypeIntNotNull(), p_type_int_not_null);
		assertEquals(at.getTypeMediumintNull(), p_type_mediumint_null);
//		assertEquals(at.getTypeMediumintNotNull(), p_type_mediumint_not_null);
		assertEquals(at.getTypeSmallintNull(), p_type_smallint_null);
//		assertEquals(at.getTypeSmallintNotNull(), p_type_smallint_not_null);
		assertEquals(at.getTypeTinyintNull(), p_type_tinyint_null);
		assertEquals(at.getTypeTinyintNotNull(), p_type_tinyint_not_null);
		assertEquals(at.getTypeCharNotNull(), p_type_char_not_null);
		assertEquals(at.getTypeCharNull(), p_type_char_null);
		assertEquals(at.getTypeVarchar45Null(), p_type_varchar_45_null);
		assertEquals(at.getTypeVarchar45NotNull(), p_type_varchar_45_not_null);
		assertEquals(at.getTypeDateNull().toString(), p_type_date_null.toString());
		assertEquals(at.getTypeDateNotNull().toString(), p_type_date_not_null.toString());
		assertEquals(at.getTypeDatetimeNull(), p_type_timestamp1_null);
		assertEquals(at.getTypeDatetimeNotNull(), p_type_timestamp2_not_null);
		assertEquals(at.getTypeTimeNull().toString(), p_type_time_null.toString());
		assertEquals(at.getTypeTimeNotNull().toString(), p_type_time_not_null.toString());
		assertEquals(at.getTypeTimestampNull().toString(), p_type_timestamp2_null.toString());
		assertEquals(at.getTypeTimestampNotNull(), p_type_timestamp2_not_null);
		assertEquals(at.getTypeYearNull(), p_type_year_null);
		assertEquals(at.getTypeYearNotNull(), p_type_year_not_null);
		assertEquals(at.getTypeLongtextNull(), p_type_longtext_null);
		assertEquals(at.getTypeLongtextNotNull(), p_type_longtext_not_null);
		assertEquals(at.getTypeMediumtextNull(), p_type_mediumtext_null);
		assertEquals(at.getTypeMediumtextNotNull(), p_type_mediumtext_not_null);
		assertEquals(at.getTypeTextNull(), p_type_text_null);
		assertEquals(at.getTypeTextNotNull(), p_type_text_not_null);
		assertEquals(at.getTypeTinytextNull(), p_type_tinytext_null);
		assertEquals(at.getTypeTinytextNotNull(), p_type_tinytext_not_null);
		assertEquals(Optional.ofNullable(at.getTypeBitNull()).orElse(true), p_type_bit_null);
		assertEquals(at.getTypeBitNotNull(), p_type_bit_not_null);
		assertEquals(at.getTypeEnumNotNullType().name(), p_type_enum_not_null.name());
		assertEquals(at.getTypeEnum2NotNullType().name(), p_type_enum2_not_null.name());
	}

}
