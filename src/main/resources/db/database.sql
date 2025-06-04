-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: potek
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `pemesanan`
--

DROP TABLE IF EXISTS `pemesanan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pemesanan` (
  `idPesanan` varchar(10) NOT NULL,
  `idTiket` varchar(10) DEFAULT NULL,
  `namaPemesan` varchar(100) DEFAULT NULL,
  `noHpPemesan` varchar(20) DEFAULT NULL,
  `emailPemesan` varchar(100) DEFAULT NULL,
  `namaPenumpang` varchar(100) DEFAULT NULL,
  `noHpPenumpang` varchar(20) DEFAULT NULL,
  `emailPenumpang` varchar(100) DEFAULT NULL,
  `noKursi` int DEFAULT NULL,
  PRIMARY KEY (`idPesanan`),
  KEY `idTiket` (`idTiket`),
  CONSTRAINT `pemesanan_ibfk_1` FOREIGN KEY (`idTiket`) REFERENCES `tiket` (`idTiket`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pemesanan`
--

LOCK TABLES `pemesanan` WRITE;
/*!40000 ALTER TABLE `pemesanan` DISABLE KEYS */;
/*!40000 ALTER TABLE `pemesanan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tiket`
--

DROP TABLE IF EXISTS `tiket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tiket` (
  `idTiket` varchar(10) NOT NULL,
  `keberangkatan` varchar(50) DEFAULT NULL,
  `tujuan` varchar(50) DEFAULT NULL,
  `tanggal` date DEFAULT NULL,
  `jam` time DEFAULT NULL,
  `harga` int DEFAULT NULL,
  `total_kursi` int DEFAULT NULL,
  `tersedia_kursi` int DEFAULT NULL,
  PRIMARY KEY (`idTiket`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tiket`
--

LOCK TABLES `tiket` WRITE;
/*!40000 ALTER TABLE `tiket` DISABLE KEYS */;
INSERT INTO `tiket` VALUES ('T001','Jakarta','Bandung','2025-06-10','08:00:00',75000,10,8),('T002','Jakarta','Surabaya','2025-06-11','14:30:00',90000,12,12),('T003','Bandung','Jakarta','2025-06-10','10:00:00',80000,10,5);
INSERT INTO `tiket` VALUES ('T004','Jakarta','Bandung','2025-06-10','18:00:00',75000,10,8),('T005','Jakarta','Surabaya','2025-06-11','15:30:00',90000,12,12),('T006','Bandung','Jakarta','2025-06-10','11:00:00',80000,10,5);
INSERT INTO `tiket` VALUES ('T007','Jakarta','Bandung','2025-06-10','09:00:00',70000,10,9),('T008','Jakarta','Surabaya','2025-06-11','19:30:00',80000,12,12));
/*!40000 ALTER TABLE `tiket` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-03 22:12:54
