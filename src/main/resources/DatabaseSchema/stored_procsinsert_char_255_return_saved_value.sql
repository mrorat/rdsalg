DELIMITER $$

DROP PROCEDURE IF EXISTS `insert_char_255_return_saved_value` $$
CREATE PROCEDURE `insert_char_255_return_saved_value`(in p__char char(255))
BEGIN
-- proxy:Test:
-- returns:type_char:1
-- omitfields:char_1:char_5

	INSERT INTO malgtest.type_char (char_255) values (p__char);
	SELECT * FROM malgtest.type_char WHERE id_type_char=LAST_INSERT_ID();

END $$

DELIMITER ;