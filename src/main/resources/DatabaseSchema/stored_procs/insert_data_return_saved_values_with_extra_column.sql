DELIMITER $$

DROP PROCEDURE IF EXISTS `insert_data_return_saved_values_with_extra_column` $$
CREATE DEFINER=`malg`@`%` PROCEDURE `insert_data_return_saved_values_with_extra_column`(in p_varchar_45 varchar(45), in p_int int)
BEGIN
-- proxy:Test:
-- returns:column_modifications_test:1
-- extraFields:extra1 | int | NOT NULL:

	INSERT INTO column_modifications_test (column_1_varchar, column_2_int)
		VALUES (p_varchar_45, p_int);

	SELECT *, 54321 as extra1 FROM column_modifications_test WHERE column_modifications_test_id=LAST_INSERT_ID();
END $$

DELIMITER ;