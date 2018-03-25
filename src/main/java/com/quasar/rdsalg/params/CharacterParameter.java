package com.quasar.rdsalg.params;

import com.quasar.rdsalg.MySqlParameterDirection;

public class CharacterParameter extends Parameter {

	CharacterParameter(String name, String type,
			MySqlParameterDirection direction, int position,
			Long characterMaxLength) {
		super(name, type, direction, position);
		this.characterMaxLength = characterMaxLength;
	}

	@Override
	public String getMethodParameter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTypeFromTypes() {
		return "Types.CHAR";
	}

}
