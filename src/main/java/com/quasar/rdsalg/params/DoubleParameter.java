package com.quasar.rdsalg.params;

import com.quasar.rdsalg.MySqlParameterDirection;

public class DoubleParameter extends FloatParameter {
	DoubleParameter(String name, String type,
			MySqlParameterDirection direction, int position) {
		super(name, type, direction, position);
		// TODO Auto-generated constructor stub
	}
	public DoubleParameter(String name, String type,
			MySqlParameterDirection direction, int position,
			boolean customType, boolean multipleRowsReturn) {
		super(name, type, direction, position, customType, multipleRowsReturn);
		// TODO Auto-generated constructor stub
	}
	public DoubleParameter(String name, String type,
			MySqlParameterDirection direction, int position,
			boolean customType, boolean multipleRowsReturn, boolean adHoc) {
		super(name, type, direction, position, customType, multipleRowsReturn, adHoc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getJavaType() { return "Double"; }

	@Override
	public String getCallableStatementType()
	{
		return "Double";
	}

	public String getResultSetTypeGetter()
	{
		return "rs.getDouble(\"" + name + "\")";
	}

	@Override
	public String getMethodParameter() {
		return "Double " + name;
	}
	
	@Override
	public boolean isNullable()
	{
		return true;
	}
}