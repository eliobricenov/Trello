-- MySQL dump 10.13  Distrib 5.6.23, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: trello
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.33-MariaDB

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
-- Table structure for table `boards`
--

DROP TABLE IF EXISTS `boards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `boards` (
  `board_id` int(11) NOT NULL AUTO_INCREMENT,
  `board_name` varchar(30) NOT NULL,
  `user_id` int(11) NOT NULL,
  `board_created_at` datetime NOT NULL,
  PRIMARY KEY (`board_id`),
  KEY `users_boards_fk` (`user_id`),
  CONSTRAINT `users_boards_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `boards`
--

LOCK TABLES `boards` WRITE;
/*!40000 ALTER TABLE `boards` DISABLE KEYS */;
INSERT INTO `boards` VALUES (1,'Prueba',5,'2018-06-26 22:56:50');
/*!40000 ALTER TABLE `boards` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cards`
--

DROP TABLE IF EXISTS `cards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cards` (
  `card_id` int(11) NOT NULL AUTO_INCREMENT,
  `column_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `card_name` varchar(25) NOT NULL,
  `card_description` varchar(252) NOT NULL,
  PRIMARY KEY (`card_id`),
  KEY `users_cards_fk` (`user_id`),
  KEY `columns_cards_fk` (`column_id`),
  CONSTRAINT `columns_cards_fk` FOREIGN KEY (`column_id`) REFERENCES `columns` (`column_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `users_cards_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cards`
--

LOCK TABLES `cards` WRITE;
/*!40000 ALTER TABLE `cards` DISABLE KEYS */;
/*!40000 ALTER TABLE `cards` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `columns`
--

DROP TABLE IF EXISTS `columns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `columns` (
  `column_id` int(11) NOT NULL AUTO_INCREMENT,
  `board_id` int(11) NOT NULL,
  `column_name` varchar(25) NOT NULL,
  PRIMARY KEY (`column_id`),
  KEY `boards_columns_fk` (`board_id`),
  CONSTRAINT `boards_columns_fk` FOREIGN KEY (`board_id`) REFERENCES `boards` (`board_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `columns`
--

LOCK TABLES `columns` WRITE;
/*!40000 ALTER TABLE `columns` DISABLE KEYS */;
/*!40000 ALTER TABLE `columns` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comments` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `card_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `comment_text` varchar(250) NOT NULL,
  `comment_created_at` datetime NOT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `users_comments_fk` (`user_id`),
  KEY `cards_comments_fk` (`card_id`),
  CONSTRAINT `cards_comments_fk` FOREIGN KEY (`card_id`) REFERENCES `cards` (`card_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `users_comments_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `files`
--

DROP TABLE IF EXISTS `files`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `files` (
  `file_id` int(11) NOT NULL AUTO_INCREMENT,
  `card_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `file_url` varchar(250) NOT NULL,
  `file_uploaded_at` datetime NOT NULL,
  `file_name` varchar(25) NOT NULL,
  PRIMARY KEY (`file_id`),
  KEY `users_files_fk` (`user_id`),
  KEY `cards_files_fk` (`card_id`),
  CONSTRAINT `cards_files_fk` FOREIGN KEY (`card_id`) REFERENCES `cards` (`card_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `users_files_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `files`
--

LOCK TABLES `files` WRITE;
/*!40000 ALTER TABLE `files` DISABLE KEYS */;
/*!40000 ALTER TABLE `files` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type_board_user`
--

DROP TABLE IF EXISTS `type_board_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `type_board_user` (
  `type_board_user_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_board_user_desccription` varchar(20) NOT NULL,
  PRIMARY KEY (`type_board_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type_board_user`
--

LOCK TABLES `type_board_user` WRITE;
/*!40000 ALTER TABLE `type_board_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `type_board_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type_user`
--

DROP TABLE IF EXISTS `type_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `type_user` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_des` varchar(20) NOT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type_user`
--

LOCK TABLES `type_user` WRITE;
/*!40000 ALTER TABLE `type_user` DISABLE KEYS */;
INSERT INTO `type_user` VALUES (1,'Normal User');
/*!40000 ALTER TABLE `type_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_board`
--

DROP TABLE IF EXISTS `user_board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_board` (
  `board_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `type_board_user_id` int(11) NOT NULL,
  KEY `type_board_user_user_board_fk` (`type_board_user_id`),
  KEY `users_user_board_fk` (`user_id`),
  KEY `boards_user_board_fk` (`board_id`),
  CONSTRAINT `boards_user_board_fk` FOREIGN KEY (`board_id`) REFERENCES `boards` (`board_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `type_board_user_user_board_fk` FOREIGN KEY (`type_board_user_id`) REFERENCES `type_board_user` (`type_board_user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `users_user_board_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_board`
--

LOCK TABLES `user_board` WRITE;
/*!40000 ALTER TABLE `user_board` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_id` int(11) NOT NULL,
  `user_name` varchar(20) NOT NULL,
  `user_username` varchar(25) NOT NULL,
  `user_last_name` varchar(30) NOT NULL,
  `user_email` varchar(50) NOT NULL,
  `user_password` varchar(60) NOT NULL,
  `user_created_at` datetime NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `type_user_users_fk` (`type_id`),
  CONSTRAINT `type_user_users_fk` FOREIGN KEY (`type_id`) REFERENCES `type_user` (`type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,1,'Jesus','ggsus','Urdaneta','ggsus@gmail.com','$2a$10$OOFXOks/p79mqxZUwxlZweA/V','2018-06-26 22:56:50'),(4,1,'Darianna','dariplinares','Linares','dariplinares@gmail.com','$2a$10$7soJGPMbkQRJLKZqsxEaOe9EhkFS13ZUcam8WhZm88yld1ms2EMzC','2018-06-27 01:25:33'),(5,1,'Elio','eliobricenov','Briceno','eliobricenov@gmail.com','$2a$10$XvT/3/Yr9BiNvoXWgRt9muVSl.bHcEAd4CLct6jK48AKMVykq8jZ2','2018-06-27 01:43:59');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-28 20:05:01
