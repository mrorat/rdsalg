package com.quasar.rdsalg.params;

import com.quasar.rdsalg.MySqlParameterDirection;

public class BooleanParameter extends TinyIntParameter {

	public BooleanParameter(String name, String type,
			MySqlParameterDirection direction, int position) {
		super(name, type, direction, position);
	}
}