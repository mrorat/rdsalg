package com.quasar.rdsalg.params;

import com.quasar.rdsalg.MySqlParameterDirection;

public class CustomParameter extends Parameter {

	CustomParameter(String name, String type,
			MySqlParameterDirection direction, int position, boolean customType, boolean multipleRowsReturn) {
		
		super(name, type, direction, position);
	}

	public boolean isCustomType()
	{
		return true;
	}

	@Override
	public String getMethodParameter() {
		// TODO Auto-generated method stub
		return "CUSTOM===";
	}

	@Override
	public boolean getHasImport() {
		return true;
	}

	@Override
	public String getTypeFromTypes() {
		return "Types.OTHER";
	}
}