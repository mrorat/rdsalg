package com.quasar.rdsalg.params;

import com.quasar.rdsalg.MySqlParameterDirection;

public class VarcharParameter extends Parameter
{
	VarcharParameter(String name, String type,
			MySqlParameterDirection direction, int position) {
		super(name, type, direction, position);
		// TODO Auto-generated constructor stub
	}
	VarcharParameter(String name, String type,
			MySqlParameterDirection direction, int position,
			boolean customType, boolean multipleRowsReturn) {
		super(name, type, direction, position, customType, multipleRowsReturn);
		// TODO Auto-generated constructor stub
	}
	VarcharParameter(String name, String type,
			MySqlParameterDirection direction, int position,
			boolean customType, boolean multipleRowsReturn, boolean adHoc) {
		super(name, type, direction, position, customType, multipleRowsReturn, adHoc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getJavaType() { return "String"; }

	@Override
	public String getCallableStatementType()
	{
		return "String";
	}

	@Override
	public String getResultSetTypeGetter()
	{
		return "rs.getString(\"" + name + "\")";
	}

	@Override
	public String getTypeFromTypes() {
		return "Types.VARCHAR";
	}

	@Override
	public String getMethodParameter() {
		return "String " + name;
	}
}