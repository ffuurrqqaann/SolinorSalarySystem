-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.6.17 - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             8.3.0.4694
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for solinor_salary_system
CREATE DATABASE IF NOT EXISTS `solinor_salary_system` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `solinor_salary_system`;


-- Dumping structure for table solinor_salary_system.workinghours
CREATE TABLE IF NOT EXISTS `workinghours` (
  `id` int(255) unsigned NOT NULL,
  `name` varchar(50) NOT NULL,
  `date` date NOT NULL,
  `starttime` varchar(50) NOT NULL,
  `endtime` varchar(50) NOT NULL,
  `evening_compensation` decimal(10,2) NOT NULL,
  `overtime_compensation` decimal(10,2) NOT NULL,
  `total_daily_pay` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='This table contains the working hours of individual employee in the form of starting and ending date';

-- Dumping data for table solinor_salary_system.workinghours: ~0 rows (approximately)
/*!40000 ALTER TABLE `workinghours` DISABLE KEYS */;
/*!40000 ALTER TABLE `workinghours` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
