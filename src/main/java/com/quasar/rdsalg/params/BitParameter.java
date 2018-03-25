package com.quasar.rdsalg.params;

import com.quasar.rdsalg.MySqlParameterDirection;

import java.security.InvalidParameterException;

public class BitParameter extends Parameter {

	private short numberOfBits = 1;
	private final String booleanResultSetGetter;
	private final String bytesResultSetGetter;

	public BitParameter(String name, String type, MySqlParameterDirection direction, int position)
	{
		this(name, type, direction, position, false);
	}
	
	BitParameter(String name, String type, MySqlParameterDirection direction, int position, boolean isNullable)
	{
		super(name, type, direction, position);
		if (type.contains("("))
		{
			int bracketOpening = type.indexOf('(')+1;
			numberOfBits = Short.parseShort(type.substring(bracketOpening, type.indexOf(')')));
		}
		if (numberOfBits > 64) 
			throw new InvalidParameterException("Bit type length is limited to 64");

		booleanResultSetGetter = "rs.getBoolean(\"" + name + "\")";
		bytesResultSetGetter = "rs.getBytes(\"" + name + "\")";
	}

	@Override
	public String getJavaType() { return "Boolean"; }

	public String getCallableStatementType()
	{
		if (numberOfBits == 1)
			return "Boolean";
		else
			return "Bytes";
	}

	public String getResultSetTypeGetter()
	{
		if (numberOfBits == 1)
			return booleanResultSetGetter;
		else
			return bytesResultSetGetter;
	}

	@Override
	public String getTypeFromTypes() {
		return "Types.BIT";
	}

	@Override
	public String getMethodParameter() {
		if (numberOfBits == 1)
			return String.format("Boolean %s", name);
		else
			return String.format("String %s", name);
	}

}