package com.quasar.rdsalg.params;

import com.quasar.rdsalg.MySqlParameterDirection;
import org.apache.commons.lang3.text.WordUtils;

public abstract class Parameter
{

	Parameter(String name, String type, MySqlParameterDirection direction, int position)
	{
		this(name, type, direction, position, false, false);
	}

	Parameter(String name, String type, MySqlParameterDirection direction, int position, boolean customType, boolean multipleRowsReturn)
	{
		this.name = name;

		int bracketIndex = type.indexOf("(");
		this.type = bracketIndex == -1 ? type : type.substring(0, bracketIndex);

		this.typeDeclared = type;
		this.direction = direction;
		this.position = position;
		this.customType = customType;
		this.multipleRowsReturn = multipleRowsReturn;
	}

	Parameter(String name, String type, MySqlParameterDirection direction, int position, boolean customType, boolean multipleRowsReturn, boolean adHoc)
	{
		this(name, type, direction, position, customType, multipleRowsReturn);
		this.adHoc = adHoc;
	}

	static String parameterFormat = "\"\\n      %1$s [\" + %1$s +\"] - type: %2$s\"";
	static String parameterFormatArray = "\"\\n      %1$s [\" + %1$s +\"] - type: %2$s\"";

	final String name;
	private final String type;
	private final String typeDeclared;
	private final MySqlParameterDirection direction;
	private final int position;
	private final boolean customType;
	private final boolean multipleRowsReturn;
	private boolean adHoc = false;
//	private boolean isArray = false;
	Long characterMaxLength;
	Long numericPrecision;

	public String getName()
	{
		return name;
	}
	
	public String getNameCapitalized()
	{
		if (customType)
			return name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
		else
			return WordUtils.capitalizeFully(name.replace("_", " ")).replace(" ", "");
	}
	
	String getNameCapitalized(String customName)
	{
		if (customType)
			return customName.replaceFirst(customName.substring(0, 1), customName.substring(0, 1).toUpperCase());
		else
			return WordUtils.capitalizeFully(customName.replace("_", " ")).replace(" ", "");
	}
	
	public String getType()
	{
		return type;
	}

	String getTypeDeclared() { return typeDeclared; }

	public String getJavaType()
	{
		try
		{
			return getType();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public MySqlParameterDirection getDirection()
	{
		return direction;
	}
	
	public int getPosition()
	{
		return position;
	}
	
	public boolean isCustomType()
	{
		return customType;
	}
	
	public boolean getMultipleRowsReturn()
	{
		return multipleRowsReturn;
	}
	
	public boolean getAdHoc()
	{
		return adHoc;
	}

	public Long getTypeSepecificPrecision()
	{
		return characterMaxLength != null ? characterMaxLength : numericPrecision;
	}
	
//	public boolean IsArray()
//	{
//		return isArray;
//	}

//	public void setIsArray(boolean isArray)
//	{
//		this.isArray = isArray;
//	}

	public abstract String getMethodParameter();

	public boolean getHasImport() {
		return false;
	}

	public String getImport() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isNullable() {
		// TODO Auto-generated method stub
		return true;
	}

	public String getCastToType()
	{
		return "";
	}

	public String getCallableStatementType()
	{
		return getType();
	}

	public String getResultSetTypeGetter()
	{
		return "rs.get" + getCallableStatementType() + "(\"" + name + "\")";
	}

	public String getCallableStatementSetParameter()
	{
		return name;
	}

	public boolean getIsEnum() { return false; }

	public abstract String getTypeFromTypes();
}