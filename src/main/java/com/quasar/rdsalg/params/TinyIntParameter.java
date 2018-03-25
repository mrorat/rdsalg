package com.quasar.rdsalg.params;

import java.security.InvalidParameterException;

import com.quasar.rdsalg.MySqlParameterDirection;

public class TinyIntParameter extends IntegerParameter {

	TinyIntParameter(String name, String type,
			MySqlParameterDirection direction, int position) {
		super(name, type, direction, position);
		if (type.contains("("))
		{
			int bracketOpening = type.indexOf('(')+1;
			precision = Short.parseShort(type.substring(bracketOpening, type.indexOf(')')));
		}
		if (precision > 64) 
			throw new InvalidParameterException("Bit type length is limited to 64");

	}
	
	public String appendSetCommand()
	{
		return String.format("cStmt.setBoolean(\"%1$s\",  %1$s);", name);
	}
	
	private short precision = 1;

	@Override
	public String getMethodParameter() {
		return precision == 1 ? "Boolean " + name : "Integer " + name;
	}
}
