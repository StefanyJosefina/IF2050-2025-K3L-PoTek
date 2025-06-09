/*M!999999\- enable the sandbox mode */ 
-- MariaDB dump 10.19-11.4.7-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: potek
-- ------------------------------------------------------
-- Server version	11.4.7-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*M!100616 SET @OLD_NOTE_VERBOSITY=@@NOTE_VERBOSITY, NOTE_VERBOSITY=0 */;

--
-- Table structure for table `kamar`
--

DROP TABLE IF EXISTS `kamar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `kamar` (
  `idKamar` varchar(10) NOT NULL,
  `namaHotel` varchar(100) DEFAULT NULL,
  `tipeKamar` enum('Single','Double','Family') DEFAULT NULL,
  `lokasi` enum('Jakarta','Bandung','Surabaya','Yogyakarta','Semarang','Malang','Cirebon') DEFAULT NULL,
  `harga` int(11) DEFAULT NULL,
  `tersedia` int(11) DEFAULT NULL,
  `jumlah_kamar` int(11) DEFAULT NULL,
  PRIMARY KEY (`idKamar`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kamar`
--

LOCK TABLES `kamar` WRITE;
/*!40000 ALTER TABLE `kamar` DISABLE KEYS */;
INSERT INTO `kamar` VALUES
('K001','Hotel Merdeka','Single','Jakarta',350000,8,10),
('K002','Hotel Merdeka','Double','Jakarta',500000,6,8),
('K003','Hotel Merdeka','Family','Jakarta',750000,3,5),
('K004','Hotel Nusantara','Single','Jakarta',360000,10,12),
('K005','Hotel Nusantara','Double','Jakarta',600000,8,8),
('K006','Hotel Nusantara','Family','Jakarta',740000,5,6),
('K007','Hotel Mawar','Single','Bandung',360000,7,10),
('K008','Hotel Mawar','Double','Bandung',510000,5,8),
('K009','Hotel Mawar','Family','Bandung',740000,4,5),
('K010','Hotel Anggrek','Single','Bandung',370000,9,10),
('K011','Hotel Anggrek','Double','Bandung',520000,7,8),
('K012','Hotel Anggrek','Family','Bandung',730000,5,6),
('K013','Hotel Sakura','Single','Yogyakarta',370000,7,9),
('K014','Hotel Sakura','Double','Yogyakarta',520000,6,7),
('K015','Hotel Sakura','Family','Yogyakarta',730000,5,6),
('K016','Hotel Borobudur','Single','Yogyakarta',380000,8,10),
('K017','Hotel Borobudur','Double','Yogyakarta',530000,7,9),
('K018','Hotel Borobudur','Family','Yogyakarta',720000,6,6),
('K019','Hotel Gajah','Single','Surabaya',340000,8,9),
('K020','Hotel Gajah','Double','Surabaya',530000,6,8),
('K021','Hotel Gajah','Family','Surabaya',720000,5,6),
('K022','Hotel Tugu','Single','Surabaya',350000,9,10),
('K023','Hotel Tugu','Double','Surabaya',540000,7,8),
('K024','Hotel Tugu','Family','Surabaya',730000,4,5),
('K025','Hotel Kenanga','Single','Cirebon',330000,6,8),
('K026','Hotel Kenanga','Double','Cirebon',490000,5,7),
('K027','Hotel Kenanga','Family','Cirebon',710000,3,4),
('K028','Hotel Sangkala','Single','Cirebon',340000,7,9),
('K029','Hotel Sangkala','Double','Cirebon',500000,5,7),
('K030','Hotel Sangkala','Family','Cirebon',720000,4,5),
('K031','Hotel Cemara','Single','Semarang',360000,7,9),
('K032','Hotel Cemara','Double','Semarang',520000,6,8),
('K033','Hotel Cemara','Family','Semarang',740000,4,5),
('K034','Hotel Marina','Single','Semarang',370000,9,10),
('K035','Hotel Marina','Double','Semarang',530000,7,9),
('K036','Hotel Marina','Family','Semarang',750000,5,6),
('K037','Hotel Bromo','Single','Malang',380000,7,9),
('K038','Hotel Bromo','Double','Malang',540000,6,7),
('K039','Hotel Bromo','Family','Malang',760000,4,5),
('K040','Hotel Taman','Single','Malang',390000,8,10),
('K041','Hotel Taman','Double','Malang',550000,7,8),
('K042','Hotel Taman','Family','Malang',770000,5,6);
/*!40000 ALTER TABLE `kamar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pesanan`
--

DROP TABLE IF EXISTS `pesanan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `pesanan` (
  `idPesanan` varchar(10) NOT NULL,
  `idUser` varchar(10) DEFAULT NULL,
  `tanggalPesan` date DEFAULT NULL,
  `statusPesanan` enum('Pending','Diproses','Selesai','Dibatalkan') DEFAULT NULL,
  PRIMARY KEY (`idPesanan`),
  KEY `idUser` (`idUser`),
  CONSTRAINT `pesanan_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pesanan`
--

LOCK TABLES `pesanan` WRITE;
/*!40000 ALTER TABLE `pesanan` DISABLE KEYS */;
/*!40000 ALTER TABLE `pesanan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pesanankamar`
--

DROP TABLE IF EXISTS `pesanankamar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `pesanankamar` (
  `idPesananKamar` varchar(10) NOT NULL,
  `idKamar` varchar(10) DEFAULT NULL,
  `idUser` varchar(10) DEFAULT NULL,
  `tanggalCheckIn` date DEFAULT NULL,
  `tanggalCheckOut` date DEFAULT NULL,
  `namaPemesan` varchar(100) DEFAULT NULL,
  `noHpPemesan` varchar(20) DEFAULT NULL,
  `emailPemesan` varchar(100) DEFAULT NULL,
  `jumlahKamar` int(11) DEFAULT NULL,
  `jumlahTamu` int(11) DEFAULT NULL,
  `totalHarga` int(11) DEFAULT NULL,
  PRIMARY KEY (`idPesananKamar`),
  KEY `idKamar` (`idKamar`),
  KEY `idUser` (`idUser`),
  CONSTRAINT `pesanankamar_ibfk_1` FOREIGN KEY (`idKamar`) REFERENCES `kamar` (`idKamar`),
  CONSTRAINT `pesanankamar_ibfk_2` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pesanankamar`
--

LOCK TABLES `pesanankamar` WRITE;
/*!40000 ALTER TABLE `pesanankamar` DISABLE KEYS */;
INSERT INTO `pesanankamar` VALUES
('PK001','K001','U001','2025-06-15','2025-06-17','Alya Pratama','081234567890','alya@gmail.com',1,1,700000),
('PK002','K002','U002','2025-06-16','2025-06-17','Budi Santoso','082345678901','budi@yahoo.com',2,2,1000000),
('PK003','K003','U003','2025-06-14','2025-06-17','Citra Maharani','083456789012','citra@gmail.com',1,3,2250000),
('PK004','K004','U004','2025-06-18','2025-06-19','Dimas Saputra','084567890123','dimas@outlook.com',1,1,360000),
('PK005','K005','U005','2025-06-20','2025-06-22','Eka Wijaya','085678901234','eka@gmail.com',1,2,1200000),
('PK006','K006','U006','2025-06-19','2025-06-21','Fajar Ramadhan','086789012345','fajar@ymail.com',2,4,2960000),
('PK007','K007','U007','2025-06-15','2025-06-16','Gita Sari','087890123456','gita@gmail.com',1,1,360000),
('PK008','K008','U008','2025-06-13','2025-06-16','Hendra Gunawan','088901234567','hendra@live.com',1,2,1530000),
('PK009','K009','U009','2025-06-17','2025-06-19','Indah Kusuma','089012345678','indah@gmail.com',2,4,2960000),
('PK010','K010','U010','2025-06-18','2025-06-19','Joko Widodo','081098765432','jokowi@presiden.id',1,1,370000);
/*!40000 ALTER TABLE `pesanankamar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pesanantiket`
--

DROP TABLE IF EXISTS `pesanantiket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `pesanantiket` (
  `idPesananTiket` varchar(10) NOT NULL,
  `idTiket` varchar(10) DEFAULT NULL,
  `idUser` varchar(10) DEFAULT NULL,
  `namaPemesan` varchar(100) DEFAULT NULL,
  `noHpPemesan` varchar(20) DEFAULT NULL,
  `emailPemesan` varchar(100) DEFAULT NULL,
  `namaPenumpang` varchar(100) DEFAULT NULL,
  `noHpPenumpang` varchar(20) DEFAULT NULL,
  `emailPenumpang` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idPesananTiket`),
  KEY `idTiket` (`idTiket`),
  KEY `idUser` (`idUser`),
  CONSTRAINT `pesanantiket_ibfk_1` FOREIGN KEY (`idTiket`) REFERENCES `tiket` (`idTiket`),
  CONSTRAINT `pesanantiket_ibfk_2` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pesanantiket`
--

LOCK TABLES `pesanantiket` WRITE;
/*!40000 ALTER TABLE `pesanantiket` DISABLE KEYS */;
INSERT INTO `pesanantiket` VALUES
('PT001','T001','U001','Alya Pratama','081234567890','alya@gmail.com','Alya Pratama','081234567890','alya@gmail.com'),
('PT002','T002','U002','Budi Santoso','082345678901','budi@yahoo.com','Budi Santoso','082345678901','budi@yahoo.com'),
('PT003','T003','U003','Citra Maharani','083456789012','citra@gmail.com','Citra Maharani','083456789012','citra@gmail.com'),
('PT004','T004','U004','Dimas Saputra','084567890123','dimas@outlook.com','Eka Saputra','084567890124','eka.s@outlook.com'),
('PT005','T005','U005','Eka Wijaya','085678901234','eka@gmail.com','Fajar Wijaya','085678901235','fajar.w@gmail.com'),
('PT006','T006','U006','Fajar Ramadhan','086789012345','fajar@ymail.com','Fajar Ramadhan','086789012345','fajar@ymail.com'),
('PT007','T007','U007','Gita Sari','087890123456','gita@gmail.com','Hendra Sari','087890123457','hendra.s@gmail.com'),
('PT008','T008','U008','Hendra Gunawan','088901234567','hendra@live.com','Indah Gunawan','088901234568','indah.g@live.com'),
('PT009','T009','U009','Indah Kusuma','089012345678','indah@gmail.com','Indah Kusuma','089012345678','indah@gmail.com'),
('PT010','T010','U010','Joko Widodo','081098765432','jokowi@presiden.id','Alya Widodo','081098765433','alya.w@presiden.id');
/*!40000 ALTER TABLE `pesanantiket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tiket`
--

DROP TABLE IF EXISTS `tiket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `tiket` (
  `idTiket` varchar(10) NOT NULL,
  `keberangkatan` enum('Jakarta','Bandung','Surabaya','Yogyakarta','Semarang','Malang','Cirebon') DEFAULT NULL,
  `tujuan` enum('Jakarta','Bandung','Surabaya','Yogyakarta','Semarang','Malang','Cirebon') DEFAULT NULL,
  `tanggal` date DEFAULT NULL,
  `jam` time DEFAULT NULL,
  `harga` int(11) DEFAULT NULL,
  `total_kursi` int(11) DEFAULT NULL,
  `tersedia_kursi` int(11) DEFAULT NULL,
  PRIMARY KEY (`idTiket`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tiket`
--

LOCK TABLES `tiket` WRITE;
/*!40000 ALTER TABLE `tiket` DISABLE KEYS */;
INSERT INTO `tiket` VALUES
('T001','Jakarta','Bandung','2025-06-15','08:00:00',80000,30,28),
('T002','Jakarta','Surabaya','2025-06-16','09:00:00',90000,30,30),
('T003','Jakarta','Yogyakarta','2025-06-17','10:00:00',95000,30,29),
('T004','Jakarta','Semarang','2025-06-18','11:00:00',87000,30,27),
('T005','Jakarta','Malang','2025-06-19','12:00:00',99000,30,25),
('T006','Jakarta','Cirebon','2025-06-20','13:00:00',78000,30,26),
('T007','Bandung','Jakarta','2025-06-15','08:00:00',82000,30,28),
('T008','Bandung','Surabaya','2025-06-16','09:00:00',91000,30,30),
('T009','Bandung','Yogyakarta','2025-06-17','10:00:00',94000,30,29),
('T010','Bandung','Semarang','2025-06-18','11:00:00',88000,30,27),
('T011','Bandung','Malang','2025-06-19','12:00:00',97000,30,25),
('T012','Bandung','Cirebon','2025-06-20','13:00:00',76000,30,26),
('T013','Surabaya','Jakarta','2025-06-15','08:00:00',93000,30,28),
('T014','Surabaya','Bandung','2025-06-16','09:00:00',92000,30,30),
('T015','Surabaya','Yogyakarta','2025-06-17','10:00:00',95000,30,29),
('T016','Surabaya','Semarang','2025-06-18','11:00:00',87000,30,27),
('T017','Surabaya','Malang','2025-06-19','12:00:00',80000,30,25),
('T018','Surabaya','Cirebon','2025-06-20','13:00:00',76000,30,26),
('T019','Yogyakarta','Jakarta','2025-06-15','08:00:00',91000,30,28),
('T020','Yogyakarta','Bandung','2025-06-16','09:00:00',87000,30,30),
('T021','Yogyakarta','Surabaya','2025-06-17','10:00:00',95000,30,29),
('T022','Yogyakarta','Semarang','2025-06-18','11:00:00',88000,30,27),
('T023','Yogyakarta','Malang','2025-06-19','12:00:00',97000,30,25),
('T024','Yogyakarta','Cirebon','2025-06-20','13:00:00',76000,30,26),
('T025','Semarang','Jakarta','2025-06-15','08:00:00',88000,30,28),
('T026','Semarang','Bandung','2025-06-16','09:00:00',87000,30,30),
('T027','Semarang','Surabaya','2025-06-17','10:00:00',95000,30,29),
('T028','Semarang','Yogyakarta','2025-06-18','11:00:00',88000,30,27),
('T029','Semarang','Malang','2025-06-19','12:00:00',97000,30,25),
('T030','Semarang','Cirebon','2025-06-20','13:00:00',76000,30,26),
('T031','Cirebon','Jakarta','2025-06-15','08:00:00',85000,30,29),
('T032','Cirebon','Bandung','2025-06-16','09:00:00',83000,30,30),
('T033','Cirebon','Surabaya','2025-06-17','10:00:00',98000,30,28),
('T034','Cirebon','Yogyakarta','2025-06-18','11:00:00',90000,30,27),
('T035','Cirebon','Semarang','2025-06-19','12:00:00',87000,30,25),
('T036','Cirebon','Malang','2025-06-20','13:00:00',95000,30,26);
/*!40000 ALTER TABLE `tiket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ulasan`
--

DROP TABLE IF EXISTS `ulasan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `ulasan` (
  `idUlasan` varchar(10) NOT NULL,
  `idUser` varchar(10) DEFAULT NULL,
  `idKamar` varchar(10) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL CHECK (`rating` >= 1 and `rating` <= 5),
  `komentar` text DEFAULT NULL,
  PRIMARY KEY (`idUlasan`),
  KEY `idUser` (`idUser`),
  KEY `idKamar` (`idKamar`),
  CONSTRAINT `ulasan_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`),
  CONSTRAINT `ulasan_ibfk_2` FOREIGN KEY (`idKamar`) REFERENCES `kamar` (`idKamar`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ulasan`
--

LOCK TABLES `ulasan` WRITE;
/*!40000 ALTER TABLE `ulasan` DISABLE KEYS */;
INSERT INTO `ulasan` VALUES
('UL001','U001','K001',5,'Pelayanan sangat memuaskan.'),
('UL002','U002','K002',4,'Kamarnya nyaman, tapi agak berisik.'),
('UL003','U003','K003',3,'Cukup bagus, namun AC kurang dingin.'),
('UL004','U004','K004',5,'Hotel bersih dan staff ramah.'),
('UL005','U005','K005',4,'Lokasi strategis, dekat stasiun.'),
('UL006','U006','K006',2,'Kurang bersih dan pelayanan lambat.'),
('UL007','U007','K007',5,'Sangat puas, fasilitas lengkap.'),
('UL008','U008','K008',3,'Biasa saja, sesuai harga.'),
('UL009','U009','K009',4,'Kamar luas dan nyaman.'),
('UL010','U010','K010',5,'Top banget! Rekomendasi!');
/*!40000 ALTER TABLE `ulasan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `idUser` varchar(10) NOT NULL,
  `nama` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `noHp` varchar(20) DEFAULT NULL,
  `tgl_lahir` date DEFAULT NULL,
  PRIMARY KEY (`idUser`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES
('U001','Alya Pratama','alya@gmail.com','passalya123','081234567890','2002-01-15'),
('U002','Budi Santoso','budi@yahoo.com','budis123','082345678901','1999-06-20'),
('U003','Citra Maharani','citra@gmail.com','citra2025','083456789012','2001-09-05'),
('U004','Dimas Saputra','dimas@outlook.com','dimasSecure!','084567890123','1998-12-12'),
('U005','Eka Wijaya','eka@gmail.com','ekaPass123','085678901234','2000-03-22'),
('U006','Fajar Ramadhan','fajar@ymail.com','fajar321','086789012345','2003-07-30'),
('U007','Gita Sari','gita@gmail.com','gita2024','087890123456','2001-11-18'),
('U008','Hendra Gunawan','hendra@live.com','hendra!pw','088901234567','1997-04-09'),
('U009','Indah Kusuma','indah@gmail.com','indahxx','089012345678','2002-08-25'),
('U010','Joko Widodo','jokowi@presiden.id','ri1indonesia','081098765432','1961-06-21');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*M!100616 SET NOTE_VERBOSITY=@OLD_NOTE_VERBOSITY */;

-- Dump completed on 2025-06-09 12:23:38
