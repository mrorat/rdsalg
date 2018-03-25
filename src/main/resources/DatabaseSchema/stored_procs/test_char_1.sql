DELIMITER $$

DROP PROCEDURE IF EXISTS `test_char_1` $$
CREATE DEFINER=`malg`@`%` PROCEDURE `test_char_1`(in p_char char(1))
BEGIN
-- proxy:Test:
-- returns:type_char:1

	INSERT INTO malgtest.type_char (char_1) values (p_char);
	SELECT * FROM malgtest.type_char WHERE id_type_char=LAST_INSERT_ID();

END $$

DELIMITER ;