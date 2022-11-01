-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: simprun
-- ------------------------------------------------------
-- Server version       8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `briefs`
--

DROP TABLE IF EXISTS `briefs`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `briefs`
(
    `id`          int                        NOT NULL auto_increment,
    `title`       varchar(255)               NOT NULL,
    `description` text                       NOT NULL,
    `deadline`    date                       NOT NULL,
    `status`      enum ('Active','Archived') DEFAULT 'Active',
    `promoID`     int                        NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_brief_promo_id` (`promoID`),
    CONSTRAINT `fk_brief_promo_id` FOREIGN KEY (`promoID`) REFERENCES `promos` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `deliverables`
--

DROP TABLE IF EXISTS `deliverables`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deliverables`
(
    `link`        varchar(255) NOT NULL,
    `createdAt`   datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `apprenantID` int          NOT NULL,
    `briefID`     int          NOT NULL,
    `message`     varchar(255) DEFAULT 'Here\'s my deliverable',
    PRIMARY KEY (`apprenantID`, `briefID`),
    KEY `fk_deliverable_apprenant_id` (`apprenantID`),
    KEY `fk_deliverable_brief_id` (`briefID`),
    CONSTRAINT `fk_deliverable_apprenant_id` FOREIGN KEY (`apprenantID`) REFERENCES `users` (`id`),
    CONSTRAINT `fk_deliverable_brief_id` FOREIGN KEY (`briefID`) REFERENCES `briefs` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `promos`
--

DROP TABLE IF EXISTS `promos`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promos`
(
    `id`          int          NOT NULL auto_increment,
    `name`        varchar(255) NOT NULL,
    `year`        int          NOT NULL,
    `formateurID` int          NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_promo_formateur_id` (`formateurID`),
    CONSTRAINT `fk_promo_formateur_id` FOREIGN KEY (`formateurID`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users`
(
    `id`       int                                    NOT NULL auto_increment,
    `name`     varchar(255)                           NOT NULL,
    `username` varchar(255)                           NOT NULL,
    `email`    varchar(255)                           NOT NULL,
    `password` varchar(255)                           NOT NULL,
    `role`     enum ('Admin','Apprenant','Formateur') DEFAULT 'Apprenant',
    `promoID`  int DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_promo_id` (`promoID`),
    CONSTRAINT `fk_promo_id` FOREIGN KEY (`promoID`) REFERENCES `promos` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;