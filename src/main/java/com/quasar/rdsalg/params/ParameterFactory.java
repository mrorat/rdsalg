package com.quasar.rdsalg.params;

import java.util.Properties;

import com.quasar.rdsalg.PropertiesLoader;
import org.apache.commons.lang3.NotImplementedException;

import com.quasar.rdsalg.MySqlParameterDirection;
import com.quasar.rdsalg.Precision;

public class ParameterFactory
{
	private boolean yearIsDateType = true;
	// TODO implement support for tiny int(1)
	private boolean tinyInt1isBit = true;
	
	public ParameterFactory(Properties properties)
	{
		if (PropertiesLoader.getProperty("yearIsDateType") != null &&
				PropertiesLoader.getProperty("yearIsDateType").equals("false"))
			yearIsDateType = false;
		if (PropertiesLoader.getProperty("tinyInt1isBit") != null &&
				PropertiesLoader.getProperty("tinyInt1isBit").equals("false"))
			tinyInt1isBit = false;
	}
	
	public Parameter createParameter(String name, String type, MySqlParameterDirection direction, int position, String typeIdentifier, 
			Long characterMaxLength, Long numericPrecision, boolean isNullable)
	{
		Precision precision = Precision.getPrecision(type);
		String justTypeName = precision == null && !type.toLowerCase().startsWith("enum(") ? type : type.substring(0, type.indexOf('('));
		switch (justTypeName)
		{
			case "bit":
				return new BitParameter(name, type, direction, position, isNullable);
			case "tinyint":
				return new TinyIntParameter(name, type, direction, position);
			case "tinyblob":
				return new TinyBlob(name, type, direction, position);
			case "bool":
			case "boolean":
				return new BooleanParameter(name, type, direction, position);
			case "smallint":
			case "mediumint":
			case "int":
			case "integer":
				return new IntegerParameter(name, type, direction, position);
			case "bigint":
				return new IntegerParameter(name, type, direction, position);
			case "float":
				return new FloatParameter(name, type, direction, position);
			case "double":
				return new DoubleParameter(name, type, direction, position);
			case "decimal":
				return new DecimalParameter(name, type, direction, position);
			case "date":
				return new DateParameter(name, type, direction, position);
			case "datetime":
				return new DatetimeParameter(name, type, direction, position);
			case "timestamp":
				return new TimestampParameter(name, type, direction, position);
			case "time":
				return new TimeParameter(name, type, direction, position);
			case "year":
				return new YearParameter(name, type, direction, yearIsDateType, position);
			case "char":
				return new CharParameter(name, type, precision, direction, position, characterMaxLength);
			case "varchar":
				return new VarcharParameter(name, type, direction, position);
			case "binary":
				return new BinaryParameter(name, type, direction, position);
			case "tinytext":
			case "text":
			case "mediumtext":
			case "longtext":
				return new TextParameter(name, type, direction, position);
			case "enum":
				return new EnumParameter(name, justTypeName, direction, position, typeIdentifier);

		}
		throw new NotImplementedException(String.format("type: %s not implemented", justTypeName));
//		return new Parameter(name, type, direction, position, false, false);
	}

	public Parameter createParameter(String name, String type, MySqlParameterDirection direction, int position, 
			boolean customType, boolean multipleRowsReturn)
	{
		return createParameter(name, type, direction, position, null, null, null, true);
	}
	
//	public Parameter createParameter(String name, String type, MySqlParameterDirection direction, int position, 
//			boolean customType, boolean multipleRowsReturn, boolean adHoc)
//	{
//		Parameter p = createParameter(name, type, direction, position, null, null, null);
//		
//		return p;	
//	}

	public Parameter createCustomParameter(String customParamName,
			String customParamName2, MySqlParameterDirection direction, int position,
			boolean customType, boolean multipleRowsReturn) {
		String firstLetter = customParamName.substring(0, 1);
		customParamName = customParamName.replaceFirst(firstLetter, firstLetter.toUpperCase());
		return new CustomParameter(customParamName, customParamName, direction, position, customType, multipleRowsReturn);
	}

}
