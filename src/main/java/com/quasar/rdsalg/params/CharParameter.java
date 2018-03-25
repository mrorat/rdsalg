package com.quasar.rdsalg.params;

import com.quasar.rdsalg.MySqlParameterDirection;
import com.quasar.rdsalg.Precision;

public class CharParameter extends CharacterParameter
{
	private final Precision precision;
	
	CharParameter(String name, String type, Precision precision,
			MySqlParameterDirection direction, int position, Long characterMaxLength) {
		super(name, type, direction, position, characterMaxLength);
		this.precision = precision;
		this.characterMaxLength = characterMaxLength;
	}

	@Override
	public String getJavaType()
	{
		if (characterMaxLength == 1)
			return "Character";
		else
			return "String";
	}
	
	public String appendSetCommand()
	{
		if (characterMaxLength == 1)
			return String.format("cStmt.setObject(\"%1$s\", %1$s, Types.CHAR);", name);
		else
			return String.format("cStmt.setString(\"%1$s\", %1$s);", name);
	}
	@Override
	public String getMethodParameter() {
		if (characterMaxLength == null || characterMaxLength == 1)
			return String.format("Character %s", name);
		else
			return String.format("String %s", name);
	}

	@Override
	public boolean getHasImport() {
		return true;
	}

	@Override
	public String getImport() {
		return "java.sql.Types";
	}

	@Override
	public String getCastToType() { return (characterMaxLength == null || characterMaxLength == 1) ? "(Character)" : ""; }

	@Override
	public String getResultSetTypeGetter()
	{
		if (characterMaxLength == 1)
			return "rs.getObject(\"" + name + "\") == null ? null : Character.valueOf(((String)rs.getObject(\"" + name + "\")).charAt(0))";
		else
			return "rs.getString(\"" + name + "\")";
	}

    @Override
    public String getCallableStatementType()
    {
        return "String";
    }

    @Override
    public String getCallableStatementSetParameter()
    {
        return "String.valueOf(" + name + ")";
    }

	public boolean getHasSpecialFormatting()
	{
		return true;
	}

	public String getSpecialFormatting()
	{
		return ", Types.CHAR";
	}
}