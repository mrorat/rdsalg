package com.quasar.rdsalg.params;

import com.quasar.rdsalg.MySqlParameterDirection;

public class TimeParameter extends Parameter
{
	TimeParameter(String name, String type,
			MySqlParameterDirection direction, int position) {
		super(name, type, direction, position);
		// TODO Auto-generated constructor stub
	}
	public TimeParameter(String name, String type,
			MySqlParameterDirection direction, int position,
			boolean customType, boolean multipleRowsReturn) {
		super(name, type, direction, position, customType, multipleRowsReturn);
		// TODO Auto-generated constructor stub
	}
	public TimeParameter(String name, String type,
			MySqlParameterDirection direction, int position,
			boolean customType, boolean multipleRowsReturn, boolean adHoc) {
		super(name, type, direction, position, customType, multipleRowsReturn, adHoc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getJavaType() { return "Time"; }

	public String getCallableStatementType() { return "Time"; }

	@Override
	public String getTypeFromTypes() {
		return "Types.TIME";
	}

	@Override
	public String getMethodParameter() {
		return "Time " + name;
	}
	
	@Override
	public boolean getHasImport()
	{
		return true;
	}

	@Override
	public String getImport() { return"java.sql.Time"; }
}
