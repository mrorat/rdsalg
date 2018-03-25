package com.quasar.rdsalg.params;

import com.quasar.rdsalg.MySqlParameterDirection;

public class YearParameter extends IntegerParameter {

	private final boolean useShort;
//	private enum YearFormat { two, four }
//	private YearFormat yearFormat;
	
	YearParameter(String name, String type, MySqlParameterDirection direction,
			boolean useShort, int position) {
		super(name, type, direction, position);
		this.useShort = useShort;
	}

	public YearParameter(String name, String type,
			MySqlParameterDirection direction, boolean useShort, int position,
			boolean customType, boolean multipleRowsReturn) {
		super(name, type, direction, position, customType, multipleRowsReturn);
		this.useShort = useShort;
	}

	public YearParameter(String name, String type,
			MySqlParameterDirection direction, boolean useShort, int position,
			boolean customType, boolean multipleRowsReturn, boolean adHoc) {
		super(name, type, direction, position, customType, multipleRowsReturn, adHoc);
		this.useShort = useShort;
	}

	@Override
	public String getMethodParameter() {
		if (useShort)
		{
			return "Short " + name;
		}
		else
		{
			return "int " + name;
		}
	}
}
