-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: j8a405.p.ssafy.io    Database: swing
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` varchar(30) NOT NULL,
  `nickname` char(30) NOT NULL,
  `profile_image_url` char(255) DEFAULT 'https://[버킷 이름].s3.ap-northeast-2.amazonaws.com/images/profile/default.png',
  `sentency_cnt` int NOT NULL,
  `five_cnt` int NOT NULL,
  `coupon` int NOT NULL,
  `refresh_token` text,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_nickname_UNIQUE` (`nickname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `word`
--

DROP TABLE IF EXISTS `word`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `word` (
  `word_id` int unsigned NOT NULL AUTO_INCREMENT,
  `word_image_url` char(255) DEFAULT NULL,
  `content` varchar(45) NOT NULL,
  `meaning_kr` text NOT NULL,
  `meaning_en` text NOT NULL,
  PRIMARY KEY (`word_id`)
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `word_note`
--

DROP TABLE IF EXISTS `word_note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `word_note` (
  `word_note_id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` varchar(30) NOT NULL,
  `word_id` int unsigned NOT NULL,
  `checked` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`word_note_id`),
  KEY `user_has_word_note_idx` (`user_id`),
  KEY `note from word_idx` (`word_id`),
  CONSTRAINT `note of word` FOREIGN KEY (`word_id`) REFERENCES `word` (`word_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_has_word_note` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `sentence`
--

DROP TABLE IF EXISTS `sentence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sentence` (
  `sentence_id` int unsigned NOT NULL AUTO_INCREMENT,
  `sentence_image_url` char(255) NOT NULL,
  `content` text NOT NULL,
  `meaning_kr` text NOT NULL,
  PRIMARY KEY (`sentence_id`)
) ENGINE=InnoDB AUTO_INCREMENT=301 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sentence_note`
--

DROP TABLE IF EXISTS `sentence_note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sentence_note` (
  `sentence_note_id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` varchar(30) NOT NULL,
  `sentence_id` int unsigned NOT NULL,
  `checked` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`sentence_note_id`),
  KEY `user_has_sentence_note_idx` (`user_id`),
  KEY `note from sentence_idx` (`sentence_id`),
  CONSTRAINT `note of sentence` FOREIGN KEY (`sentence_id`) REFERENCES `sentence` (`sentence_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_has_sentence_note` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sentency_rank`
--

DROP TABLE IF EXISTS `sentency_rank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sentency_rank` (
  `sentency_rank_id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` varchar(30) NOT NULL,
  `score` int NOT NULL,
  PRIMARY KEY (`sentency_rank_id`),
  KEY `user_has_sentency_rank_idx` (`user_id`),
  CONSTRAINT `user_has_sentency_rank` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `five_rank`
--

DROP TABLE IF EXISTS `five_rank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `five_rank` (
  `five_rank_id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` varchar(30) NOT NULL,
  `score` int NOT NULL,
  PRIMARY KEY (`five_rank_id`),
  KEY `user_has_hifive_rank_idx` (`user_id`),
  CONSTRAINT `user_has_hifive_rank` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `five_stat`
--

DROP TABLE IF EXISTS `five_stat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `five_stat` (
  `five_stat_id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` varchar(30) NOT NULL,
  `total_score` int NOT NULL DEFAULT '0',
  `total_try` int DEFAULT '0',
  `total_correct` int DEFAULT '0',
  `streak` int DEFAULT '0',
  PRIMARY KEY (`five_stat_id`),
  KEY `user_has_five_stat_idx` (`user_id`),
  CONSTRAINT `user_has_five_stat` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `room_id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `code` char(6) DEFAULT NULL,
  `leader_id` varchar(30) NOT NULL,
  `mode` tinyint NOT NULL DEFAULT '0',
  `started` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`room_id`),
  UNIQUE KEY `room_leader_id_UNIQUE` (`leader_id`),
  CONSTRAINT `user_make_room` FOREIGN KEY (`leader_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=405 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `game`
--

DROP TABLE IF EXISTS `game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game` (
  `game_id` int unsigned NOT NULL AUTO_INCREMENT,
  `room_name` varchar(45) NOT NULL,
  `play_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`game_id`)
) ENGINE=InnoDB AUTO_INCREMENT=230 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `round`
--

DROP TABLE IF EXISTS `round`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `round` (
  `round_id` int unsigned NOT NULL AUTO_INCREMENT,
  `game_id` int unsigned NOT NULL,
  `round_no` tinyint NOT NULL,
  `word_id` int unsigned NOT NULL,
  PRIMARY KEY (`round_id`),
  KEY `keyword_from_word_idx` (`word_id`),
  KEY `game_has_round_idx` (`game_id`),
  CONSTRAINT `game_has_round` FOREIGN KEY (`game_id`) REFERENCES `game` (`game_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `word_from_word` FOREIGN KEY (`word_id`) REFERENCES `word` (`word_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1146 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `history`
--

DROP TABLE IF EXISTS `history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `history` (
  `history_id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` varchar(30) NOT NULL,
  `round_id` int unsigned NOT NULL,
  `game_image_url` text NOT NULL,
  PRIMARY KEY (`history_id`),
  KEY `user_has_history_idx` (`user_id`),
  KEY `history_of_round_idx` (`round_id`),
  CONSTRAINT `history_of_round` FOREIGN KEY (`round_id`) REFERENCES `round` (`round_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_has_history` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_game`
--

DROP TABLE IF EXISTS `user_game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_game` (
  `user_game_id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` varchar(30) NOT NULL,
  `game_id` int unsigned NOT NULL,
  `rank` int unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_game_id`),
  KEY `user_play_game_idx` (`user_id`),
  KEY `game_of_user_idx` (`game_id`),
  CONSTRAINT `game_of_user` FOREIGN KEY (`game_id`) REFERENCES `game` (`game_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_play_game` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_room`
--

DROP TABLE IF EXISTS `user_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_room` (
  `user_room_id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` varchar(30) NOT NULL,
  `room_id` int unsigned NOT NULL,
  PRIMARY KEY (`user_room_id`),
  KEY `user_join_room_idx` (`user_id`),
  KEY `room_has_user_idx` (`room_id`),
  CONSTRAINT `room_has_user` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_join_room` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=737 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Table structure for table `word_today`
--

DROP TABLE IF EXISTS `word_today`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `word_today` (
  `word_id` int unsigned NOT NULL AUTO_INCREMENT,
  `word_image_url` char(255) DEFAULT NULL,
  `content` varchar(45) NOT NULL,
  `meaning_kr` text NOT NULL,
  `meaning_en` text NOT NULL,
  PRIMARY KEY (`word_id`)
) ENGINE=InnoDB AUTO_INCREMENT=190 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'swing'
--
/*!50106 SET @save_time_zone= @@TIME_ZONE */ ;
/*!50106 DROP EVENT IF EXISTS `add_word_today` */;
DELIMITER ;;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;;
/*!50003 SET character_set_client  = utf8mb4 */ ;;
/*!50003 SET character_set_results = utf8mb4 */ ;;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;;
/*!50003 SET @saved_time_zone      = @@time_zone */ ;;
/*!50003 SET time_zone             = 'Asia/Seoul' */ ;;
/*!50106 CREATE*/ /*!50117 DEFINER=`swing`@`%`*/ /*!50106 EVENT `add_word_today` ON SCHEDULE EVERY 1 DAY STARTS '2023-03-31 00:00:01' ON COMPLETION PRESERVE ENABLE COMMENT 'word_today 단어 추가' DO insert into word_today
select * from word
order by rand()
limit 5 */ ;;
/*!50003 SET time_zone             = @saved_time_zone */ ;;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;;
/*!50003 SET character_set_client  = @saved_cs_client */ ;;
/*!50003 SET character_set_results = @saved_cs_results */ ;;
/*!50003 SET collation_connection  = @saved_col_connection */ ;;
/*!50106 DROP EVENT IF EXISTS `reset_five_rank` */;;
DELIMITER ;;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;;
/*!50003 SET character_set_client  = utf8mb4 */ ;;
/*!50003 SET character_set_results = utf8mb4 */ ;;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;;
/*!50003 SET @saved_time_zone      = @@time_zone */ ;;
/*!50003 SET time_zone             = 'Asia/Seoul' */ ;;
/*!50106 CREATE*/ /*!50117 DEFINER=`swing`@`%`*/ /*!50106 EVENT `reset_five_rank` ON SCHEDULE EVERY 1 DAY STARTS '2023-03-31 00:00:00' ON COMPLETION PRESERVE ENABLE COMMENT 'hi-five 랭킹 초기화' DO update five_rank set score = 0 */ ;;
/*!50003 SET time_zone             = @saved_time_zone */ ;;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;;
/*!50003 SET character_set_client  = @saved_cs_client */ ;;
/*!50003 SET character_set_results = @saved_cs_results */ ;;
/*!50003 SET collation_connection  = @saved_col_connection */ ;;
/*!50106 DROP EVENT IF EXISTS `reset_sentency_rank` */;;
DELIMITER ;;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;;
/*!50003 SET character_set_client  = utf8mb4 */ ;;
/*!50003 SET character_set_results = utf8mb4 */ ;;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;;
/*!50003 SET @saved_time_zone      = @@time_zone */ ;;
/*!50003 SET time_zone             = 'Asia/Seoul' */ ;;
/*!50106 CREATE*/ /*!50117 DEFINER=`swing`@`%`*/ /*!50106 EVENT `reset_sentency_rank` ON SCHEDULE EVERY 1 DAY STARTS '2023-03-31 00:00:00' ON COMPLETION PRESERVE ENABLE COMMENT 'sentency 랭킹 초기화' DO update sentency_rank set score = 0 */ ;;
/*!50003 SET time_zone             = @saved_time_zone */ ;;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;;
/*!50003 SET character_set_client  = @saved_cs_client */ ;;
/*!50003 SET character_set_results = @saved_cs_results */ ;;
/*!50003 SET collation_connection  = @saved_col_connection */ ;;
/*!50106 DROP EVENT IF EXISTS `reset_try_cnt` */;;
DELIMITER ;;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;;
/*!50003 SET character_set_client  = utf8mb4 */ ;;
/*!50003 SET character_set_results = utf8mb4 */ ;;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;;
/*!50003 SET @saved_time_zone      = @@time_zone */ ;;
/*!50003 SET time_zone             = 'Asia/Seoul' */ ;;
/*!50106 CREATE*/ /*!50117 DEFINER=`swing`@`%`*/ /*!50106 EVENT `reset_try_cnt` ON SCHEDULE EVERY 1 DAY STARTS '2023-04-03 00:00:00' ON COMPLETION PRESERVE ENABLE COMMENT 'sentency_cnt, five_cnt 초기화' DO update user set sentency_cnt = 3, five_cnt = 1 */ ;;
/*!50003 SET time_zone             = @saved_time_zone */ ;;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;;
/*!50003 SET character_set_client  = @saved_cs_client */ ;;
/*!50003 SET character_set_results = @saved_cs_results */ ;;
/*!50003 SET collation_connection  = @saved_col_connection */ ;;
/*!50106 DROP EVENT IF EXISTS `reset_word_today` */;;
DELIMITER ;;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;;
/*!50003 SET character_set_client  = utf8mb4 */ ;;
/*!50003 SET character_set_results = utf8mb4 */ ;;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;;
/*!50003 SET @saved_time_zone      = @@time_zone */ ;;
/*!50003 SET time_zone             = 'Asia/Seoul' */ ;;
/*!50106 CREATE*/ /*!50117 DEFINER=`swing`@`%`*/ /*!50106 EVENT `reset_word_today` ON SCHEDULE EVERY 1 DAY STARTS '2023-03-31 00:00:00' ON COMPLETION PRESERVE ENABLE COMMENT 'word_today 초기화' DO truncate table word_today */ ;;
/*!50003 SET time_zone             = @saved_time_zone */ ;;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;;
/*!50003 SET character_set_client  = @saved_cs_client */ ;;
/*!50003 SET character_set_results = @saved_cs_results */ ;;
/*!50003 SET collation_connection  = @saved_col_connection */ ;;
DELIMITER ;
/*!50106 SET TIME_ZONE= @save_time_zone */ ;

--
-- Dumping routines for database 'swing'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-06 17:11:27
