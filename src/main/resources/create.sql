CREATE DATABASE  IF NOT EXISTS `tree_test` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `tree_test`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: tree_test
-- ------------------------------------------------------
-- Server version	5.6.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_hierarchy`
--

DROP TABLE IF EXISTS `t_hierarchy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_hierarchy` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `level` int(11) NOT NULL,
  `parent` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_lbrbej4forxcen2vsy7tkb3nt` (`id`,`parent`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_hierarchy`
--

LOCK TABLES `t_hierarchy` WRITE;
/*!40000 ALTER TABLE `t_hierarchy` DISABLE KEYS */;
INSERT INTO `t_hierarchy` VALUES (1,0,0),(2,0,1),(3,0,1),(4,0,2),(5,0,2),(6,0,3),(7,0,3);
/*!40000 ALTER TABLE `t_hierarchy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'tree_test'
--
/*!50003 DROP FUNCTION IF EXISTS `hierarchy_connect_by_parent_eq_prior_id` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `hierarchy_connect_by_parent_eq_prior_id`(value INT) RETURNS int(11)
    READS SQL DATA
BEGIN
		/*
			User defined variables needed before executing this functions:
            @start_with 
            @id
            @level
            
            During execution @id and @level will change (see further method description).
            @start_with will never change. 
            
            Suppose we have the following tree:
            
            id (1) - parent (0)
              id (2) - parent (1)
                id (4) - parent (2)
                id (5) - parent (2)
              id (3) - parent (1)
                id (6) - parent (3)
                id (7) - parent (3)
			
            This function returns the next node id (as function return parameter 
            and user defined variable @id) and level (user defined variable @level) 
            in tree hierarchy.
            
            To understand how this function works it's best to create the tree data 
            mentioned above:
            INSERT INTO `t_hierarchy` VALUES (1,0),(2,1),(3,1),(4,2),(5,2),(6,3),(7,3);
            
            Afterwards you can execute the following selects in exactly this order 
            (note that the user defined variables [@id and @level] are 
            used as input for next select in execution history): 
            
			SELECT hierarchy_connect_by_parent_eq_prior_id(null) FROM (
									SELECT @start_with := 0,
									@id := @start_with,
									@level := 0) var;
			SELECT @id; -- will be set to 1
			SELECT @level; -- will be set to 1
            
			SELECT hierarchy_connect_by_parent_eq_prior_id(null) FROM (
									SELECT @start_with := 0,
									@id := 1,
									@level := 1) var;
			SELECT @id; -- will be set to 2
			SELECT @level; -- will be set to 2

			SELECT hierarchy_connect_by_parent_eq_prior_id(null) FROM (
									SELECT @start_with := 0,
									@id := 2,
									@level := 2) var;
			SELECT @id; -- will be set to 4
			SELECT @level; -- will be set to 3
            
			SELECT hierarchy_connect_by_parent_eq_prior_id(null) FROM (
									SELECT @start_with := 0,
									@id := 4,
									@level := 3) var;
			SELECT @id; -- will be set to 5
			SELECT @level; -- will be set to 3
            
 			SELECT hierarchy_connect_by_parent_eq_prior_id(null) FROM (
									SELECT @start_with := 0,
									@id := 5,
									@level := 3) var;
			SELECT @id; -- will be set to 3
			SELECT @level; -- will be set to 2
            
			SELECT hierarchy_connect_by_parent_eq_prior_id(null) FROM (
									SELECT @start_with := 0,
									@id := 3,
									@level := 2) var;
			SELECT @id; -- will be set to 6
			SELECT @level; -- will be set to 3            
            
			SELECT hierarchy_connect_by_parent_eq_prior_id(null) FROM (
									SELECT @start_with := 0,
									@id := 6,
									@level := 3) var;
			SELECT @id; -- will be set to 7
			SELECT @level; -- will be set to 3
            
			SELECT hierarchy_connect_by_parent_eq_prior_id(null) FROM (
									SELECT @start_with := 0,
									@id := 7,
									@level := 3) var;
			SELECT @id; -- will be set to NULL
			SELECT @level; -- will be set to 1
            
            Another example to understand how to traverse from node id 5 to 3 
            (see last example):
            
      Start params:
      -- @id = 5
      -- @level = 3
            
      SELECT MIN(id) FROM t_hierarchy WHERE  parent = 5 and id > -1;
			-- @id = null

			SELECT  id, parent FROM t_hierarchy WHERE id = 5;
			-- _id = 5
			-- _parent = 2
      -- @level = 2

			SELECT  MIN(id) FROM t_hierarchy WHERE parent = 2 AND id > 5;
			-- @id = null

			SELECT  id, parent FROM t_hierarchy WHERE id = 2;
			-- _id = 2
			-- _parent = 1
      -- @level = 1

			SELECT  MIN(id) FROM t_hierarchy WHERE parent = 1 AND id > 2;
			-- @id = 3
      -- @level = 2  
        */
        
		
        DECLARE _id INT;
        DECLARE _parent INT;
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET @id = NULL;

        SET _parent = @id;
        SET _id = -1;

        IF @id IS NULL THEN
                RETURN NULL;
        END IF;

        LOOP
                SELECT  MIN(id)
                INTO    @id
                FROM    t_hierarchy
                WHERE   parent = _parent
                        AND id > _id;
                IF @id IS NOT NULL OR _parent = @start_with THEN
                        SET @level = @level + 1;
                        RETURN @id;
                END IF;
                SET @level := @level - 1;
                SELECT  id, parent
                INTO    _id, _parent
                FROM    t_hierarchy
                WHERE   id = _parent;
        END LOOP;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `prc_fill_hierarchy` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `prc_fill_hierarchy`(level INT, fill INT)
BEGIN
      /*
          Call this stored procedure to populate the `t_hierarchy`
          table with tree data. You need to delete the old data
          in the table first. In addition you may want to reset the
          want to reset the auto increment value of the table.
          
          By calling this function the root node (level = 1) is created.
          Then parameter#fill direct child nodes are attached to 
          root node. After that parameter#level defines how deep 
          the hierarchy (starting from direct child nodes of root node) 
          will be. Each node with level > 0 that is no leafe node 
          will have exactly parameter#fill child nodes set after
          this procedure has been executed with success.
        */
        DECLARE _level INT;
        DECLARE _fill INT;
        
		-- insert root node - START
        INSERT
        INTO    t_hierarchy (id, parent)
        VALUES  (1, 0);
        -- insert root node
        
        -- insert direct root node childs - START
        SET _fill = 0;
        WHILE _fill < fill DO
                INSERT
                INTO    t_hierarchy (parent)
                VALUES  (1);
                SET _fill = _fill + 1;
        END WHILE;
        -- insert direct root node childs
        
        -- recursively insert childs 
        -- this will be done starting from direct child nodes of root node - START 
        SET _fill = 1;
        SET _level = 0;
        WHILE _level < level DO
                INSERT
                INTO    t_hierarchy (parent)
                SELECT  hn.id
                FROM    t_hierarchy ho STRAIGHT_JOIN t_hierarchy hn
                WHERE   ho.parent = 1
                        AND hn.id > _fill;
                SET _level = _level + 1;
                SET _fill = _fill + POWER(fill, _level);
        END WHILE;
        -- recursively insert childs - END
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-09-25 17:39:56