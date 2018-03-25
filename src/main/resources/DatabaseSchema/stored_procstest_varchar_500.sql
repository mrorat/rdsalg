DELIMITER $$

DROP PROCEDURE IF EXISTS `test_varchar_500` $$
CREATE PROCEDURE `test_varchar_500`(in p_varchar varchar(500))
BEGIN
-- proxy:Test:
-- returns:type_varchar:1

	INSERT INTO malgtest.type_varchar (varchar_500) values (p_varchar);
	SELECT * FROM malgtest.type_varchar WHERE id_type_varchar=LAST_INSERT_ID();
END $$

DELIMITER ;