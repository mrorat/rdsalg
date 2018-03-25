DELIMITER $$

DROP PROCEDURE IF EXISTS `insert_tinyblob_return_saved_value` $$
CREATE DEFINER=`malg`@`%` PROCEDURE `insert_tinyblob_return_saved_value`(in p_blob tinyblob)
BEGIN
-- proxy:Test:
-- returns:type_blob:1

	INSERT INTO malgtest.type_blob (type_tinyblob) values (p_blob);
	SELECT * FROM malgtest.type_blob WHERE id_type_blob=LAST_INSERT_ID();

END $$

DELIMITER ;