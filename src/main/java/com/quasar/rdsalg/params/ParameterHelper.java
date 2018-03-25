package com.quasar.rdsalg.params;

public class ParameterHelper
{
	
	private static final String parameterFormat = "\"\\n      %1$s [\" + %1$s +\"] - type: %2$s\"";
	private static final String parameterFormatArray = "\"\\n      %1$s [\" + %1$s +\"] - type: %2$s\"";
	
	public static String getAddInParameterCommand(Parameter p) throws Exception
	{
		int bracketIndex = p.getType().indexOf("(");
		String type = bracketIndex == -1 ? p.getType() : p.getType().substring(0, bracketIndex);
		switch (type)
		{
			case "bigint":
				return String.format("cStmt.setString(\"%1$s\", %1$s.toString());", p.getNameCapitalized());
			case "decimal":
				return String.format("cStmt.setBigDecimal(\"%1$s\", %1$s);", p.getNameCapitalized());
			case "double":
				return String.format("cStmt.setDouble(\"%1$s\", %1$s);", p.getNameCapitalized());
			case "float":
				return String.format("cStmt.setFloat(\"%1$s\", %1$s);", p.getNameCapitalized());
			case "int":
			case "mediumint":
			case "smallint":
				return String.format("cStmt.setInt(\"%1$s\", %1$s);", p.getNameCapitalized());
			case "char":
			case "varchar":
			case "longtext":
			case "mediumtext":
			case "text":
			case "tinytext":
				return String.format("cStmt.setString(\"%1$s\", %1$s);", p.getNameCapitalized());
			case "date":
				return String.format("cStmt.setDate(\"%1$s\", %1$s);", p.getNameCapitalized());
			case "time":
				return String.format("cStmt.setTime(\"%1$s\", %1$s);", p.getNameCapitalized());
			case "datetime":
			case "timestamp":
				return String.format("cStmt.setTimestamp(\"%1$s\", %1$s);", p.getNameCapitalized());
			case "year":
				return String.format("cStmt.setShort(\"%1$s\", %1$s);", p.getNameCapitalized());
			case "bit":
			case "tinyint":
				return String.format("cStmt.setBoolean(\"%1$s\", %1$s);", p.getNameCapitalized());
			case "enum":
				return String.format("cStmt.setString(\"%1$s\", %1$s.name());", p.getNameCapitalized());
				
		}
		throw new Exception("Not implemented type: " + p.getType() + ", in parameter: " + p.getNameCapitalized());
	}
}