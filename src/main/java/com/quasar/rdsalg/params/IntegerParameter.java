package com.quasar.rdsalg.params;

import com.quasar.rdsalg.MySqlParameterDirection;

public class IntegerParameter extends Parameter {
	IntegerParameter(String name, String type,
			MySqlParameterDirection direction, int position) {
		super(name, type, direction, position);
		// TODO Auto-generated constructor stub
	}
	IntegerParameter(String name, String type,
			MySqlParameterDirection direction, int position,
			boolean customType, boolean multipleRowsReturn) {
		super(name, type, direction, position, customType, multipleRowsReturn);
		// TODO Auto-generated constructor stub
	}
	IntegerParameter(String name, String type,
			MySqlParameterDirection direction, int position,
			boolean customType, boolean multipleRowsReturn, boolean adHoc) {
		super(name, type, direction, position, customType, multipleRowsReturn, adHoc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getMethodParameter() {
		switch (getTypeDeclared())
		{
			case "bigint":
				return "BigInteger " + name;
			case "int":
			case "integer":
				return "Long " + name;
			case "mediumint":
			case "smallint":
				return "Integer " + name;
		}
		return "int " + name;
	}

    @Override
	public boolean isNullable()
	{
		return true;
	}

	@Override
	public boolean getHasImport() {
		switch (getType()) {
			case "bigint":
				return true;
			default:
				return false;
		}
	}

	@Override
	public String getImport() {
		return "java.math.BigInteger";
	}

	public String getJavaType()
	{
		switch (getType())
		{
			case "bigint":
				return "BigInteger";
			case "mediumint":
			case "smallint":
			default:
				return "Integer";
			case "int":
			case "integer":
				return "Long";
			case "tinyint":
				return "Boolean";
		}
	}

	@Override
	public String getCallableStatementType()
	{
		switch (getType())
		{
			case "bigint":
			    return "BigDecimal"; // jdbc does not support BigInteger so we will use BigDecimal instead
			case "mediumint":
			case "smallint":
			default:
				return "Int";
			case "int":
			case "integer":
				return "Long";
			case "tinyint":
				return "Boolean";
		}
	}

	@Override
    public String getCallableStatementSetParameter()
    {
        return getType().equalsIgnoreCase("bigint") ? "BigDecimal.valueOf(" + name + ".intValueExact())" : name;
    }

	public String getResultSetTypeGetter()
	{
		String tempType = "", prefix = "", suffix = "";
		switch (getType())
		{
			case "bigint":
			    prefix = "rs.getBigDecimal(\"" + name + "\") != null ? BigInteger.valueOf(";
			    tempType = "BigDecimal";
			    suffix = ".longValueExact()) : null";
			    break;
			case "mediumint":
			case "smallint":
			default:
				tempType =  "Int";
			    break;
			case "int":
			case "integer":
				tempType =  "Long";
			    break;
			case "tinyint":
				tempType =  "Boolean";
			    break;
		}
		return String.format("%srs.get%s(\"%s\")%s", prefix, tempType, name, suffix);
	}

	public String getTypeFromTypes()
    {
        return getType().equalsIgnoreCase("bigint") ? "Types.BIGINT" : "Types.INTEGER";
    }
}
