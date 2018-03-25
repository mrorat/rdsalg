package com.quasar.rdsalg.params;

import com.quasar.rdsalg.MySqlParameterDirection;

public class TinyBlob extends Parameter {

	TinyBlob(String name, String type, MySqlParameterDirection direction, int position) {
		super(name, type, direction, position);
	}

	@Override
	public String getJavaType() { return "Blob"; }

	@Override
	public String getCallableStatementType()
	{
		return "Blob";
	}

    @Override
    public String getResultSetTypeGetter()
    {
        return "rs.getBlob(\"" + name + "\")";
    }

	@Override
	public String getTypeFromTypes() {
		return "Types.BLOB";
	}

	@Override
	public String getMethodParameter() {
		return "InputStream " + name;
	}

	@Override
	public boolean getHasImport()
	{
		return true;
	}

	@Override
	public String getImport() {
		return "java.sql.Blob";
	}
}