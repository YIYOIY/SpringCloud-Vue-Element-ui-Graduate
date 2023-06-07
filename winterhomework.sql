-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: winterhomework
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `admin`
--
create database winterhomework;
use winterhomework;
DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `adminId` int NOT NULL AUTO_INCREMENT,
  `adminName` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT (_utf8mb4'admin'),
  `adminPassword` varchar(25) NOT NULL DEFAULT (111111),
  `adminPetName` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT (_utf8mb4'admin'),
  PRIMARY KEY (`adminId`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (21,'primary','11','admin'),(22,'priMain8','1','admin');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bag`
--

DROP TABLE IF EXISTS `bag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bag` (
  `bagId` int NOT NULL AUTO_INCREMENT,
  `bookId` int DEFAULT NULL,
  `userId` int DEFAULT NULL,
  `bookNumber` int DEFAULT NULL,
  `addTime` date DEFAULT (curdate()),
  PRIMARY KEY (`bagId`),
  KEY `bookId` (`bookId`),
  KEY `userId` (`userId`),
  CONSTRAINT `bag_ibfk_1` FOREIGN KEY (`bookId`) REFERENCES `book` (`bookId`),
  CONSTRAINT `bag_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bag`
--

LOCK TABLES `bag` WRITE;
/*!40000 ALTER TABLE `bag` DISABLE KEYS */;
/*!40000 ALTER TABLE `bag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `bookId` int NOT NULL AUTO_INCREMENT,
  `bookNo` int DEFAULT NULL,
  `bookName` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `bookAuthor` varchar(25) DEFAULT NULL,
  `bookPicture` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `bookFactory` varchar(30) DEFAULT NULL,
  `bookPrice` double NOT NULL DEFAULT (0),
  `bookAddDate` date DEFAULT (curdate()),
  `bookSeries` int DEFAULT NULL,
  PRIMARY KEY (`bookId`),
  KEY `bookSeries` (`bookSeries`),
  CONSTRAINT `book_ibfk_1` FOREIGN KEY (`bookSeries`) REFERENCES `bookseries` (`seriesId`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (2,2,'IMMUNSE','诺贝尔','2','中国广播',250,'2022-12-11',1),(3,3,'文化苦旅','希特勒','3','中国作协',300,'2022-12-11',1),(4,4,'一个叫欧文的男人决定去死','曹雪芹','4','战狼文学',250,'2022-12-11',1),(5,50,'鼠疫','希特勒','5','德国作协',100,'2022-12-11',2),(6,6,'红楼梦','周树人','6','美国圣经',300,'2022-12-12',1),(7,11,'雪球专刊','雪球写的','11','莫斯科国立',100,'2022-12-12',1),(8,12,'浮生六记','就是你','12','莫斯科国立',100,'2022-12-12',1),(11,13,'史记','司马迁','13','麻省理工学院',170.54,'2021-12-10',1),(12,18,'我就像一颗秋天的树','author','9','莫斯科国立',100,'2022-12-12',1),(13,14,'底层逻辑','刘润','10','莫斯科国立',170.54,'2022-12-12',1),(16,19,'四面风','author','1','莫斯科国立',10,'2023-01-01',1),(17,14,'Kindle','author','14','出版',10,'2023-01-01',8);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bookseries`
--

DROP TABLE IF EXISTS `bookseries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookseries` (
  `seriesId` int NOT NULL AUTO_INCREMENT,
  `seriesName` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT (_utf8mb4'日常'),
  PRIMARY KEY (`seriesId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookseries`
--

LOCK TABLES `bookseries` WRITE;
/*!40000 ALTER TABLE `bookseries` DISABLE KEYS */;
INSERT INTO `bookseries` VALUES (1,'历史文学'),(2,'疫情文学'),(8,'美国圣经');
/*!40000 ALTER TABLE `bookseries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userId` int NOT NULL AUTO_INCREMENT,
  `userName` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT (_utf8mb4'user'),
  `userPassword` varchar(25) NOT NULL DEFAULT (111111),
  `userPetName` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `userAddress` varchar(100) DEFAULT NULL,
  `userTel` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (18,'第一个1','1','1','2','123'),(21,'2333333','3','3','3','3'),(25,'第一321','123','12311','123','123');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-15 11:36:19
