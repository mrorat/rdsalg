DELIMITER $$

DROP PROCEDURE IF EXISTS `test_varchar_5000` $$
CREATE DEFINER=`malg`@`%` PROCEDURE `test_varchar_5000`(in p_varchar varchar(5000))
BEGIN
-- proxy:Test:
-- returns:type_varchar:1

	INSERT INTO malgtest.type_varchar (varchar_5000) values (p_varchar);
	SELECT * FROM malgtest.type_varchar WHERE id_type_varchar=LAST_INSERT_ID();
END $$

DELIMITER ;