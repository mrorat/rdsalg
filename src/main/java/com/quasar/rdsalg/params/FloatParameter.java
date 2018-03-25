package com.quasar.rdsalg.params;

import com.quasar.rdsalg.MySqlParameterDirection;

public class FloatParameter extends Parameter {

	FloatParameter(String name, String type,
			MySqlParameterDirection direction, int position) {
		super(name, type, direction, position);
		// TODO Auto-generated constructor stub
	}
	FloatParameter(String name, String type,
			MySqlParameterDirection direction, int position,
			boolean customType, boolean multipleRowsReturn) {
		super(name, type, direction, position, customType, multipleRowsReturn);
		// TODO Auto-generated constructor stub
	}
	FloatParameter(String name, String type,
			MySqlParameterDirection direction, int position,
			boolean customType, boolean multipleRowsReturn, boolean adHoc) {
		super(name, type, direction, position, customType, multipleRowsReturn, adHoc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getJavaType() { return "Float"; }

	@Override
	public String getCallableStatementType()
	{
		return "Float";
	}

	@Override
	public String getTypeFromTypes() {
		return "Types.FLOAT";
	}

	@Override
	public String getMethodParameter() {
		// TODO Auto-generated method stub
		return "Float " + name;
	}
	
	@Override
	public boolean isNullable()
	{
		return true;
	}
}