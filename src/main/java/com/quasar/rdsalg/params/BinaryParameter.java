package com.quasar.rdsalg.params;

import com.quasar.rdsalg.MySqlParameterDirection;

public class BinaryParameter extends Parameter {
    BinaryParameter(String name, String type,
                    MySqlParameterDirection direction, int position) {
        super(name, type, direction, position);
        // TODO Auto-generated constructor stub
    }

    public BinaryParameter(String name, String type,
                           MySqlParameterDirection direction, int position,
                           boolean customType, boolean multipleRowsReturn) {
        super(name, type, direction, position, customType, multipleRowsReturn);
        // TODO Auto-generated constructor stub
    }

    public BinaryParameter(String name, String type,
                           MySqlParameterDirection direction, int position,
                           boolean customType, boolean multipleRowsReturn, boolean adHoc) {
        super(name, type, direction, position, customType, multipleRowsReturn, adHoc);
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getMethodParameter() {
        return String.format("byte[] %s", name);
    }

    @Override
    public String getTypeFromTypes() {
        return "Types.BINARY";
    }
}
