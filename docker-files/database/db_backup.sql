-- MySQL dump 10.13  Distrib 8.0.35, for Linux (x86_64)
--
-- Host: 172.17.0.2    Database: episodes_task_db
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `characters`
--

DROP TABLE IF EXISTS `characters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `characters` (
  `created` datetime(6) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `gender` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `state_of_origin` varchar(255) DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKro9elw4erspj69ghksli9uega` (`first_name`,`last_name`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `characters`
--

LOCK TABLES `characters` WRITE;
/*!40000 ALTER TABLE `characters` DISABLE KEYS */;
INSERT INTO `characters` VALUES ('2023-11-04 21:08:36.505806',1,'Petyr','MALE','Baelish',NULL,'ACTIVE'),('2023-11-05 09:59:20.515333',2,'Ned','MALE','Stark',NULL,'ACTIVE'),('2023-11-05 10:15:37.017311',3,'Rob','MALE','Stark',NULL,'ACTIVE'),('2023-11-05 10:16:56.702727',4,'Sansa','FEMALE','Stark',NULL,'ACTIVE'),('2023-11-05 10:17:25.454462',5,'Arya','FEMALE','Stark',NULL,'ACTIVE'),('2023-11-05 10:46:03.009723',7,'Jon','MALE','Snow',NULL,'ACTIVE'),('2023-11-05 11:13:40.356830',8,'Tywin','MALE','Lannister',NULL,'ACTIVE'),('2023-11-05 11:15:33.015122',9,'Jaime','MALE','Lannister',NULL,'ACTIVE'),('2023-11-06 07:09:49.330656',10,'Tyrion','MALE','Lannister',NULL,'ACTIVE'),('2023-11-07 19:03:41.601284',11,'Cersei','FEMALE','Lannister',NULL,'ACTIVE'),('2023-11-22 13:11:23.811783',13,'Catelyn','FEMALE','Stark',NULL,'ACTIVE');
/*!40000 ALTER TABLE `characters` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `characters_episodes`
--

DROP TABLE IF EXISTS `characters_episodes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `characters_episodes` (
  `character_id` bigint NOT NULL,
  `episode_id` bigint NOT NULL,
  PRIMARY KEY (`character_id`,`episode_id`),
  KEY `FKoyl1eaj86phlnpx1n65swxovy` (`episode_id`),
  CONSTRAINT `FKl8wa3pd7la6gxeee87igg9f71` FOREIGN KEY (`character_id`) REFERENCES `characters` (`id`),
  CONSTRAINT `FKoyl1eaj86phlnpx1n65swxovy` FOREIGN KEY (`episode_id`) REFERENCES `episodes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `characters_episodes`
--

LOCK TABLES `characters_episodes` WRITE;
/*!40000 ALTER TABLE `characters_episodes` DISABLE KEYS */;
INSERT INTO `characters_episodes` VALUES (4,1),(10,1),(11,1),(1,2),(3,2),(11,2),(4,3),(10,3),(11,3),(4,4),(10,4),(11,4),(4,5),(10,5),(11,5),(4,6),(10,6),(11,6),(4,9),(10,9),(11,9),(13,9);
/*!40000 ALTER TABLE `characters_episodes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `created` datetime(6) NOT NULL,
  `episode_id` bigint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) NOT NULL,
  `ip_address_location` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe1y835b37nm5wwrm2wwoxykhk` (`episode_id`),
  CONSTRAINT `FKe1y835b37nm5wwrm2wwoxykhk` FOREIGN KEY (`episode_id`) REFERENCES `episodes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES ('2023-11-05 11:21:48.080081',1,1,'Thrilling!!!','107.20.5.66'),('2023-11-06 10:28:05.754158',1,2,'Awesome!!!','107.20.5.66'),('2023-11-06 10:28:45.004437',1,3,'I wont forget this series soon!!!','107.20.5.66'),('2023-11-06 10:41:14.447618',2,5,'This took me through a roller coaster of emotions!!!','107.20.5.66'),('2023-11-06 10:42:03.937106',2,6,'Tears, laughter and anxiety','107.20.5.66'),('2023-11-06 10:43:28.071665',2,7,'Game of thrones might be the best tv-series ever made','107.20.5.66'),('2023-11-07 20:08:53.843562',3,8,'Game of thrones might be the best tv-series ever made','107.20.5.66'),('2023-11-07 20:09:40.645507',3,9,'Game of thrones!!!','107.20.5.66'),('2023-11-07 20:09:50.881185',4,10,'Game of thrones!!!','107.20.5.66'),('2023-11-07 20:10:08.611446',4,11,'What a show!!!','107.20.5.66'),('2023-11-07 20:11:24.682895',5,12,'On the edge all through','107.20.5.66'),('2023-11-07 20:11:41.938002',5,13,'Im in awe','107.20.5.66'),('2023-11-07 20:12:15.218945',6,14,'Snow!!!!!!!','107.20.5.66'),('2023-11-07 20:12:45.408012',6,15,'Epic!!!','107.20.5.66'),('2023-11-07 20:13:21.737022',9,16,'Epic!!!','107.20.5.66'),('2023-11-07 20:13:34.514071',9,17,'Tyrion!!!','107.20.5.66'),('2023-11-07 20:14:30.463136',9,18,'Wow! im absolutely speechless','107.20.5.66'),('2023-11-22 13:09:26.145806',9,19,'What a show!','107.20.5.66');
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `episodes`
--

DROP TABLE IF EXISTS `episodes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `episodes` (
  `release_date` date NOT NULL,
  `created` datetime(6) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `episode_code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_d8h1n2k9eanyqccvl7211oi2a` (`episode_code`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `episodes`
--

LOCK TABLES `episodes` WRITE;
/*!40000 ALTER TABLE `episodes` DISABLE KEYS */;
INSERT INTO `episodes` VALUES ('2011-06-19','2023-11-04 21:08:09.077289',1,'S01E10','Fire and Blood'),('2011-04-24','2023-11-06 06:48:24.952081',2,'S01E02','The kingsroad'),('2011-06-12','2023-11-06 07:41:57.582911',3,'S01E09','Baelor'),('2011-05-29','2023-11-07 18:38:37.985396',4,'S01E07','You Win or You Die'),('2011-05-01','2023-11-07 18:39:54.987801',5,'S01E03','Lord Snow'),('2011-04-17','2023-11-07 18:40:55.496414',6,'S01E01','Winter is Coming'),('2011-05-22','2023-11-07 18:59:57.490791',9,'S01E06','A Golden Crown');
/*!40000 ALTER TABLE `episodes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `locations`
--

DROP TABLE IF EXISTS `locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `locations` (
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `characters_id` bigint NOT NULL,
  `created` datetime(6) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9q0jddumlxnlcwx6ohjmdb4ex` (`characters_id`),
  CONSTRAINT `FKc4y89bxrsb0klhpt00hqca4pp` FOREIGN KEY (`characters_id`) REFERENCES `characters` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `locations`
--

LOCK TABLES `locations` WRITE;
/*!40000 ALTER TABLE `locations` DISABLE KEYS */;
INSERT INTO `locations` VALUES (2,5,1,'2023-11-08 09:18:17.163162',1,'Joburg'),(2,5,2,'2023-11-08 09:18:38.177856',2,'Lagos'),(2,5,4,'2023-11-08 09:19:04.053616',4,'Paris'),(2,5,5,'2023-11-08 09:19:23.510048',5,'Sao Tome'),(2,5,7,'2023-11-08 09:19:49.641918',6,'Dublin'),(2,5,8,'2023-11-08 09:20:20.808056',7,'Wembley'),(2,5,9,'2023-11-08 09:20:39.069836',8,'Hampshire'),(2,5,11,'2023-11-08 09:21:11.359530',9,'Hamburg');
/*!40000 ALTER TABLE `locations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-02 15:06:40
