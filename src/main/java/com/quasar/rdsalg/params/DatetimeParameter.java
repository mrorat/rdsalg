package com.quasar.rdsalg.params;

import com.quasar.rdsalg.MySqlParameterDirection;

public class DatetimeParameter extends Parameter
{
	DatetimeParameter(String name, String type,
			MySqlParameterDirection direction, int position) {
		super(name, type, direction, position);
		// TODO Auto-generated constructor stub
	}
	DatetimeParameter(String name, String type,
			MySqlParameterDirection direction, int position,
			boolean customType, boolean multipleRowsReturn) {
		super(name, type, direction, position, customType, multipleRowsReturn);
		// TODO Auto-generated constructor stub
	}
	DatetimeParameter(String name, String type,
			MySqlParameterDirection direction, int position,
			boolean customType, boolean multipleRowsReturn, boolean adHoc) {
		super(name, type, direction, position, customType, multipleRowsReturn, adHoc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getJavaType() { return "Timestamp"; }

	@Override
	public String getCallableStatementType()
	{
		return "Timestamp";
	}

	@Override
	public String getTypeFromTypes() {
		return "Types.DATE";
	}

	@Override
	public String getMethodParameter() {
		return "Timestamp " + name;
	}

	@Override
	public boolean getHasImport()
	{
		return true;
	}
}
