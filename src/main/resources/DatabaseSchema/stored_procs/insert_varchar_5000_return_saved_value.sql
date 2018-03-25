DELIMITER $$

DROP PROCEDURE IF EXISTS `insert_varchar_5000_return_saved_value` $$
CREATE DEFINER=`malg`@`%` PROCEDURE `insert_varchar_5000_return_saved_value`(in p_varchar varchar(5000))
BEGIN
-- proxy:Test:
-- returns:type_varchar:1

	INSERT INTO malgtest.type_varchar (varchar_5000) values (p_varchar);
	SELECT * FROM malgtest.type_varchar WHERE id_type_varchar=LAST_INSERT_ID();
END $$

DELIMITER ;