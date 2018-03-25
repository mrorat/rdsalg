package com.quasar.rdsalg.params;

import com.quasar.rdsalg.MySqlParameterDirection;

public class DateParameter extends Parameter
{
	DateParameter(String name, String type,
			MySqlParameterDirection direction, int position) {
		super(name, type, direction, position);
		// TODO Auto-generated constructor stub
	}
	public DateParameter(String name, String type,
			MySqlParameterDirection direction, int position,
			boolean customType, boolean multipleRowsReturn) {
		super(name, type, direction, position, customType, multipleRowsReturn);
		// TODO Auto-generated constructor stub
	}
	public DateParameter(String name, String type,
			MySqlParameterDirection direction, int position,
			boolean customType, boolean multipleRowsReturn, boolean adHoc) {
		super(name, type, direction, position, customType, multipleRowsReturn, adHoc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getJavaType() { return "Date"; }

	@Override
	public String getCallableStatementType()
	{
		return "Date";
	}

	@Override
	public String getTypeFromTypes() {
		return "Types.DATE";
	}

	@Override
	public String getMethodParameter() {
		return "Date " + name;
	}

	@Override
	public boolean getHasImport()
	{
		return true;
	}

	@Override
	public String getImport() {
		return "java.sql.Date";
	}
}
