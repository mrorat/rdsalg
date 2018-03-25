DELIMITER $$

DROP PROCEDURE IF EXISTS `omitted_column_test_middle` $$
CREATE DEFINER=`malg`@`%` PROCEDURE `omitted_column_test_middle`(in p_varchar_45 varchar(45), in p_int int)
BEGIN
-- proxy:Test:
-- returns:column_modifications_test:1
-- omitFields:column_1_varchar:

	INSERT INTO column_modifications_test (column_1_varchar, column_2_int)
		VALUES (p_varchar_45, p_int);

	SELECT column_modifications_test_id, column_2_int FROM column_modifications_test WHERE column_modifications_test_id=LAST_INSERT_ID();
END $$

DELIMITER ;