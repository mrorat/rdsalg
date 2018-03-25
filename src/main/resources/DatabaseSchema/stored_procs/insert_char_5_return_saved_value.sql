DELIMITER $$

DROP PROCEDURE IF EXISTS `insert_char_5_return_saved_value` $$
CREATE DEFINER=`malg`@`%` PROCEDURE `insert_char_5_return_saved_value`(in p_char char(5))
BEGIN
-- proxy:Test:
-- returns:type_char:1

	INSERT INTO malgtest.type_char (char_5) values (p_char);
	SELECT * FROM malgtest.type_char WHERE id_type_char=LAST_INSERT_ID();

END $$

DELIMITER ;