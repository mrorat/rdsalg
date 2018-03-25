package com.quasar.rdsalg.params;

import com.quasar.rdsalg.MySqlParameterDirection;

public class NumericParameter extends Parameter {


	public NumericParameter(String name, String type,
			MySqlParameterDirection direction, int position,
			Long numericPrecision) {
		super(name, type, direction, position);
		this.numericPrecision = numericPrecision;
	}

	@Override
	public String getMethodParameter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTypeFromTypes() {
		return "Types.NUMERIC";
	}

}
