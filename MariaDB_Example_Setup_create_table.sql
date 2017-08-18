-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.2.6-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for testmariadb_java
CREATE DATABASE IF NOT EXISTS `testmariadb_java` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `testmariadb_java`;

-- Dumping structure for table testmariadb_java.spalva
CREATE TABLE IF NOT EXISTS `spalva` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `pavadinimas` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table testmariadb_java.spalva: ~3 rows (approximately)
/*!40000 ALTER TABLE `spalva` DISABLE KEYS */;
INSERT INTO `spalva` (`id`, `pavadinimas`) VALUES
	(1, 'Red'),
	(2, 'Green'),
	(3, 'Blue');
/*!40000 ALTER TABLE `spalva` ENABLE KEYS */;

-- Dumping structure for table testmariadb_java.test_table
CREATE TABLE IF NOT EXISTS `test_table` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `kiekis` int(50) NOT NULL DEFAULT 0,
  `spalva_id` int(50) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK_test_table_spalva` (`spalva_id`),
  CONSTRAINT `FK_test_table_spalva` FOREIGN KEY (`spalva_id`) REFERENCES `spalva` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table testmariadb_java.test_table: ~3 rows (approximately)
/*!40000 ALTER TABLE `test_table` DISABLE KEYS */;
INSERT INTO `test_table` (`id`, `name`, `kiekis`, `spalva_id`) VALUES
	(1, 'Test', 4, 1),
	(2, 'Žemėlapis', 100, 2),
	(3, 'Maps', 3, 2);
/*!40000 ALTER TABLE `test_table` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
