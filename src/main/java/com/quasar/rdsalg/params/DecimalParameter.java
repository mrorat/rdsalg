package com.quasar.rdsalg.params;

import com.quasar.rdsalg.MySqlParameterDirection;

public class DecimalParameter extends Parameter {
	DecimalParameter(String name, String type,
			MySqlParameterDirection direction, int position) {
		super(name, type, direction, position);
		// TODO Auto-generated constructor stub
	}
	public DecimalParameter(String name, String type,
			MySqlParameterDirection direction, int position,
			boolean customType, boolean multipleRowsReturn) {
		super(name, type, direction, position, customType, multipleRowsReturn);
		// TODO Auto-generated constructor stub
	}
	public DecimalParameter(String name, String type,
			MySqlParameterDirection direction, int position,
			boolean customType, boolean multipleRowsReturn, boolean adHoc) {
		super(name, type, direction, position, customType, multipleRowsReturn, adHoc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getJavaType() { return "BigDecimal"; }

	@Override
	public String getCallableStatementType()
	{
		return "BigDecimal";
	}

	public String getResultSetTypeGetter()
	{
		return "rs.getBigDecimal(\"" + name + "\")";
	}

	@Override
	public String getTypeFromTypes() {
		return "Types.DECIMAL";
	}

	@Override
	public String getMethodParameter() {
		return "BigDecimal " + name;
	}
	@Override
	public boolean getHasImport()
	{
		return true;
	}

	@Override
	public String getImport() {
		return "java.math.BigDecimal";
	}
}
