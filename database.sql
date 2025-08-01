-- MySQL dump 10.13  Distrib 5.7.33, for Win32 (AMD64)
--
-- Host: localhost    Database: clinicmanagementsystem
-- ------------------------------------------------------
-- Server version	5.7.33-log

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
-- Table structure for table `appointment_tbl`
--

DROP TABLE IF EXISTS `appointment_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appointment_tbl` (
  `appointment_id` varchar(50) NOT NULL,
  `patient_id` varchar(50) NOT NULL,
  `phone_number` varchar(15) DEFAULT NULL,
  `doctor_id` varchar(50) NOT NULL,
  `sequence` int(11) DEFAULT NULL,
  `problem` text,
  `get_date_time` datetime DEFAULT NULL,
  `appointment_date` date DEFAULT NULL,
  `appointment_time` time DEFAULT NULL,
  `status` enum('Scheduled','Completed','Cancelled') DEFAULT 'Scheduled',
  PRIMARY KEY (`appointment_id`),
  KEY `fk_patient` (`patient_id`),
  KEY `fk_doctor` (`doctor_id`),
  CONSTRAINT `fk_doctor` FOREIGN KEY (`doctor_id`) REFERENCES `doctors_tbl` (`doctor_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_patient` FOREIGN KEY (`patient_id`) REFERENCES `patient_tbl` (`patient_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment_tbl`
--

LOCK TABLES `appointment_tbl` WRITE;
/*!40000 ALTER TABLE `appointment_tbl` DISABLE KEYS */;
INSERT INTO `appointment_tbl` VALUES ('AP001','P1001','03339200750','70',1,'flu','2025-06-29 19:15:43','2025-10-10','09:00:00','Scheduled'),('AP002','P1001','03333333344','1',1,'kgh','2025-07-02 00:32:42','2025-12-25','09:00:00','Scheduled');
/*!40000 ALTER TABLE `appointment_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bills`
--

DROP TABLE IF EXISTS `bills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bills` (
  `bill_id` int(11) NOT NULL AUTO_INCREMENT,
  `patient_id` varchar(20) DEFAULT NULL,
  `appointment_id` varchar(20) DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  `paid` varchar(5) DEFAULT NULL,
  `billing_date` date DEFAULT NULL,
  `payment_method` varchar(100) NOT NULL DEFAULT 'Cash',
  `total_tax` int(11) DEFAULT NULL,
  PRIMARY KEY (`bill_id`),
  KEY `patient_id` (`patient_id`),
  KEY `appointment_id` (`appointment_id`),
  CONSTRAINT `bills_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patient_tbl` (`patient_id`),
  CONSTRAINT `bills_ibfk_2` FOREIGN KEY (`appointment_id`) REFERENCES `appointment_tbl` (`appointment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bills`
--

LOCK TABLES `bills` WRITE;
/*!40000 ALTER TABLE `bills` DISABLE KEYS */;
INSERT INTO `bills` VALUES (1,'P1001','AP001',213.00,'Yes','2025-07-02','casf',321);
/*!40000 ALTER TABLE `bills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctors_tbl`
--

DROP TABLE IF EXISTS `doctors_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctors_tbl` (
  `doctor_id` varchar(50) NOT NULL,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `designation` varchar(100) DEFAULT NULL,
  `degrees` varchar(255) DEFAULT NULL,
  `department` varchar(100) DEFAULT NULL,
  `specialist` varchar(100) DEFAULT NULL,
  `experience` varchar(150) DEFAULT NULL,
  `service_place` varchar(100) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `gender` enum('Male','Female') DEFAULT NULL,
  `blood_group` enum('A+','A-','B+','B-','AB+','AB-','O+','O-') DEFAULT NULL,
  `address` text,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `employee_id` int(11) DEFAULT NULL,
  `fees` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`doctor_id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `employee_id` (`employee_id`),
  CONSTRAINT `fk_employee_id` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctors_tbl`
--

LOCK TABLES `doctors_tbl` WRITE;
/*!40000 ALTER TABLE `doctors_tbl` DISABLE KEYS */;
INSERT INTO `doctors_tbl` VALUES ('1','Habib Jadoon','habibjadoon@gmail.com','doctor','MBBS','Cardiology','Heart','5 years','Abbottabad','1980-10-12','03339200750','Male','B+','Abbottabad','2025-06-27 10:57:48',NULL,NULL),('2','Dr. Ahsan Khan','ahsan.khan@example.com','Consultant','MBBS, FCPS','Cardiology','Heart Specialist','10 years','City Hospital','1980-05-12','03001234567','Male','A+','Karachi, Pakistan','2025-06-27 11:43:31',NULL,NULL),('3','Dr. Sana Fatima','sana.fatima@example.com','Senior Registrar','MBBS, MCPS','Neurology','Brain Specialist','7 years','Dow Medical Center','1985-08-24','03009876543','Female','B+','Lahore, Pakistan','2025-06-27 11:43:31',NULL,NULL),('4','Dr. Usman Tariq','usman.tariq@example.com','Assistant Professor','MBBS, FCPS','Orthopedics','Bone Specialist','5 years','Jinnah Hospital','1989-01-15','03111222333','Male','O-','Multan, Pakistan','2025-06-27 11:43:31',NULL,NULL),('5','Dr. Ahsan Khan','ahsan1@example.com','Consultant','MBBS, FCPS','Cardiology','Heart','10 years','City Hospital','1980-05-12','03001234561','Male','A+','Karachi','2025-06-27 11:45:44',NULL,NULL),('6','Dr. Sana Fatima','sana2@example.com','Registrar','MBBS, MCPS','Neurology','Brain','7 years','Dow Medical','1985-08-24','03009876542','Female','B+','Lahore','2025-06-27 11:45:44',NULL,NULL),('7','Dr. Usman Tariq','usman3@example.com','Professor','MBBS, FCPS','Orthopedics','Bones','5 years','Jinnah Hospital','1989-01-15','03111222331','Male','O-','Multan','2025-06-27 11:45:44',NULL,NULL),('70','Dr. Ahsan Khan','ahsan.khan1@example.com','Consultant','MBBS, FCPS','Cardiology','Heart Specialist','10 years','City Hospital','1980-05-12','03001234561','Male','A+','Karachi','2025-06-27 11:56:06',NULL,NULL),('71','Dr. Sana Fatima','sana.fatima2@example.com','Registrar','MBBS, MCPS','Neurology','Brain Specialist','7 years','Dow Medical','1985-08-24','03009876542','Female','B+','Lahore','2025-06-27 11:56:06',NULL,NULL),('72','Dr. Usman Tariq','usman.tariq1@example.com','Professor','MBBS, FCPS','Orthopedics','Bone Specialist','5 years','Jinnah Hospital','1989-01-15','03111222331','Male','O-','Multan','2025-06-27 11:56:06',NULL,NULL),('73','Dr. Hira Nawaz','hira.nawaz@example.com','Surgeon','MBBS, FRCS','Surgery','General','8 years','Services Hospital','1982-07-20','03219876541','Female','AB+','Peshawar','2025-06-27 11:56:06',NULL,NULL),('74','Dr. Ali Zafar','ali.zafar@example.com','Resident','MBBS','ENT','ENT Specialist','3 years','Civil Hospital','1990-03-18','03338887771','Male','B-','Quetta','2025-06-27 11:56:06',NULL,NULL),('75','Dr. Zara Siddiqui','zara.sid@example.com','Assistant Professor','MBBS, DGO','Gynaecology','Women\'s Health','6 years','Ziauddin Hospital','1984-10-22','03451234567','Female','A-','Islamabad','2025-06-27 11:56:06',NULL,NULL),('76','Dr. Noman Riaz','noman.riaz@example.com','Lecturer','MBBS, MPhil','Pathology','Diagnostics','4 years','Liaquat National','1987-02-12','03014567890','Male','O+','Faisalabad','2025-06-27 11:56:06',NULL,NULL),('77','Dr. Fatima Noor','fatima.noor@example.com','Consultant','MBBS, FCPS','Dermatology','Skin Specialist','9 years','Shifa Hospital','1983-06-09','03021234567','Female','B+','Hyderabad','2025-06-27 11:56:06',NULL,NULL),('78','Dr. Bilal Haider','bilal.haider@example.com','Senior Registrar','MBBS','Urology','Kidney Specialist','7 years','Aga Khan Hospital','1986-11-25','03033333333','Male','AB-','Bahawalpur','2025-06-27 11:56:06',NULL,NULL),('79','Dr. Maria Saleem','maria.saleem@example.com','Professor','MBBS, FCPS','Pediatrics','Child Specialist','11 years','Children Hospital','1979-12-05','03114445555','Female','O+','Sargodha','2025-06-27 11:56:06',NULL,NULL),('8','Dr. Hira Nawaz','hira4@example.com','Surgeon','MBBS, FRCS','Surgery','General','8 years','Services Hospital','1982-07-20','03219876541','Female','AB+','Peshawar','2025-06-27 11:45:44',NULL,NULL),('80','Dr. Kashif Ali','kashif.ali@example.com','Registrar','MBBS, MD','Radiology','Imaging','5 years','Punjab Institute','1988-08-08','03456667788','Male','B-','Gujranwala','2025-06-27 11:56:06',NULL,NULL),('81','Dr. Samra Riaz','samra.riaz@example.com','Lecturer','MBBS, MPhil','Pharmacology','Drug Therapy','3 years','Rawalpindi Medical','1991-04-14','03218889900','Female','A+','Rawalpindi','2025-06-27 11:56:06',NULL,NULL),('82','Dr. Farhan Iqbal','farhan.iqbal@example.com','Assistant Professor','MBBS, FCPS','Ophthalmology','Eye Specialist','6 years','LRBT','1983-09-17','03005556677','Male','O-','Sialkot','2025-06-27 11:56:06',NULL,NULL),('83','Dr. Huma Shah','huma.shah@example.com','Senior Registrar','MBBS, DCH','Pediatrics','Child Health','8 years','Fatima Memorial','1985-01-22','03119998888','Female','AB+','Abbottabad','2025-06-27 11:56:06',NULL,NULL),('84','Dr. Yasir Nawaz','yasir.nawaz@example.com','Consultant','MBBS, MS','Orthopedics','Joint Specialist','10 years','Ghurki Trust','1980-10-10','03451112222','Male','A-','Okara','2025-06-27 11:56:06',NULL,NULL),('85','Dr. Saima Tariq','saima.tariq@example.com','Registrar','MBBS, FCPS','ENT','ENT Specialist','6 years','CMH','1986-03-03','03225557777','Female','B+','Gilgit','2025-06-27 11:56:06',NULL,NULL),('86','Dr. Junaid Ahmed','junaid.ahmed@example.com','Consultant','MBBS, FCPS','Gastroenterology','Stomach','9 years','Hameed Latif','1984-07-07','03017778888','Male','O+','Rahim Yar Khan','2025-06-27 11:56:06',NULL,NULL),('D007','Dr. asad khan','asad@gmail.com','none','mbbs','orhto','bones','5 years','abbt','2025-05-05','03333333333','Male','AB+','abt','2025-07-01 19:24:54',NULL,NULL),('D008','Irsa Nisar','Irsa.Nisar2003@gmail.com','Resident','MBBS , FCPS (1)','Medicine','Internal Medicine','House Job and Previously worked at a private clinic','Khyber Teaching Hospital, Peshawar','2003-10-12','03219505939','Female','A+','Hayatabad, Peshawar','2025-07-02 12:47:09',NULL,2000.00),('D009','Dr. Irsa Nisar','Irsa.Nisar@gmail.com','Resident','MBBS , FCPS (1)','Medicine','Internal Medicine','House Job and Previously worked at a private clinic','Khyber Teaching Hospital, Peshawar','2003-10-12','03219505939','Female','A+','Hayatabad, Peshawar','2025-07-02 12:54:40',NULL,2000.00),('D010','Dr. haseeb','haseeb@gmail.com','rsm','rsm','rsm','rsm','rsm','rsm','2025-10-12','0333333333','Male','A+','srs','2025-07-02 20:13:04',NULL,2000.00),('D011','Dr. dsfd','askdjh@gmail.com','dsg','sdfsd','sfdsfd','fdssd','fsdsfd','sdfdsf','2025-12-12','0333333333','Male','A+','fgdgfd','2025-07-02 20:15:36',NULL,2000.00);
/*!40000 ALTER TABLE `doctors_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employees` (
  `employee_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `gender` enum('Male','Female','Other') NOT NULL,
  `date_of_birth` date NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone_number` varchar(20) NOT NULL,
  `address` text,
  `role` varchar(50) DEFAULT NULL,
  `hire_date` date NOT NULL,
  `salary` decimal(10,2) NOT NULL,
  `grade` varchar(10) DEFAULT NULL,
  `status` enum('Active','Inactive','On Leave') DEFAULT 'Active',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`employee_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (11,'Ali','Khan','Male','1990-05-12','ali.khan@example.com','03001234567','Karachi','Receptionist','2022-01-10',45000.00,'B','Active','2025-06-30 11:18:53','2025-06-30 11:18:53'),(12,'Sara','Ahmed','Female','1987-03-08','sara.ahmed@example.com','03029874561','Lahore','Nurse','2021-06-15',55000.00,'A','Active','2025-06-30 11:18:53','2025-06-30 11:18:53'),(13,'Bilal','Raza','Male','1992-12-02','bilal.raza@example.com','03111222333','Islamabad','Pharmacist','2020-09-20',60000.00,'A','Inactive','2025-06-30 11:18:53','2025-06-30 11:18:53'),(14,'Fatima','Noor','Female','1995-07-19','fatima.noor@example.com','03215678901','Rawalpindi','Lab Technician','2023-03-01',50000.00,'B','Active','2025-06-30 11:18:53','2025-06-30 11:18:53'),(15,'Usman','Iqbal','Male','1985-11-30','usman.iqbal@example.com','03005551234','Peshawar','Admin','2019-04-25',70000.00,'A','Active','2025-06-30 11:18:53','2025-06-30 11:18:53'),(17,'Ahmed','Ali','Male','1990-05-12','ahmed.ali@example.com','03001234567','123 Main St, Lahore','admin','2023-01-15',85000.00,'A','Active','2025-06-30 12:45:33','2025-06-30 12:45:33'),(18,'Sana','Malik','Female','1987-11-22','sana.malik@example.com','03011234567','456 Canal Rd, Lahore','doctor','2023-02-01',120000.00,'A','Active','2025-06-30 12:45:33','2025-06-30 12:45:33'),(19,'Farhan','Khan','Male','1985-09-18','farhan.khan@example.com','03021234567','789 Gulberg, Lahore','doctor','2023-03-10',115000.00,'B','Active','2025-06-30 12:45:33','2025-06-30 12:45:33'),(20,'Hira','Yousuf','Female','1995-02-14','hira.yousuf@example.com','03031234567','101 DHA, Lahore','receptionist','2023-04-05',45000.00,'C','Active','2025-06-30 12:45:33','2025-06-30 12:45:33'),(21,'Bilal','Abbas','Male','1992-07-07','bilal.abbas@example.com','03041234567','55 Model Town, Lahore','receptionist','2023-04-15',46000.00,'C','Inactive','2025-06-30 12:45:33','2025-06-30 12:45:33'),(22,'Sameer','Shah','Male','1980-03-03','sameer.shah@example.com','03051234567','19 Garden Town, Lahore','doctor','2023-05-01',130000.00,'A','Active','2025-06-30 12:45:33','2025-06-30 12:45:33'),(23,'Zara','Anwar','Female','1991-10-30','zara.anwar@example.com','03061234567','12 Johar Town, Lahore','admin','2023-05-20',88000.00,'B','Active','2025-06-30 12:45:33','2025-06-30 12:45:33'),(28,'ebad','khan','Male','2025-10-12','ebad@gmail.com','033333333','TAD','doctor','2025-10-12',5000.00,'A','Active','2025-07-01 21:39:21','2025-07-01 21:39:21'),(29,'som','som','Male','2024-10-10','recep@gmail.com','03333333','jhsakf','doctor','2024-10-10',213.00,'a','Active','2025-07-01 21:56:30','2025-07-01 21:56:30');
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medical_records`
--

DROP TABLE IF EXISTS `medical_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medical_records` (
  `record_id` int(11) NOT NULL AUTO_INCREMENT,
  `patient_id` varchar(20) DEFAULT NULL,
  `diagnosis` text,
  `treatment` text,
  `date` date DEFAULT NULL,
  `doctor_id` varchar(20) DEFAULT NULL,
  `notes` text,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`record_id`),
  KEY `patient_id` (`patient_id`),
  KEY `doctor_id` (`doctor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medical_records`
--

LOCK TABLES `medical_records` WRITE;
/*!40000 ALTER TABLE `medical_records` DISABLE KEYS */;
INSERT INTO `medical_records` VALUES (1,'1','High blood pressure','Prescribed medication A and advised rest.','2025-06-20','1',NULL,'2025-07-01 19:24:59','2025-07-01 19:24:59'),(2,'2','Skin allergy','Antihistamine prescribed.','2025-06-15','2',NULL,'2025-07-01 19:24:59','2025-07-01 19:24:59'),(3,'3','Knee pain','Referred to physiotherapy.','2025-06-10','3',NULL,'2025-07-01 19:24:59','2025-07-01 19:24:59'),(5,'P1001','gfg','fgdfg','2025-07-01','1','dfgf','2025-07-01 20:08:15','2025-07-01 20:08:15');
/*!40000 ALTER TABLE `medical_records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicine_tbl`
--

DROP TABLE IF EXISTS `medicine_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medicine_tbl` (
  `medicine_id` varchar(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `brand` varchar(100) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `dosage` varchar(50) DEFAULT NULL,
  `description` text,
  `stock_quantity` int(11) NOT NULL DEFAULT '0',
  `price` decimal(10,2) NOT NULL,
  `expiry_date` date DEFAULT NULL,
  `added_date` date DEFAULT NULL,
  PRIMARY KEY (`medicine_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicine_tbl`
--

LOCK TABLES `medicine_tbl` WRITE;
/*!40000 ALTER TABLE `medicine_tbl` DISABLE KEYS */;
INSERT INTO `medicine_tbl` VALUES ('1','Paracetamol','GSK','Tablet','500mg','Used for pain and fever relief',100,2.50,'2026-12-31','2025-07-02'),('2','Amoxicillin','Pfizer','Capsule','250mg','Antibiotic for bacterial infections',50,5.00,'2025-10-15','2025-07-02'),('3','Cough Syrup','Haleon','Syrup','10ml','Relief from dry cough',70,3.75,'2025-08-01','2025-07-02'),('4','Ibuprofen','Bayer','Tablet','200mg','Pain reliever and anti-inflammatory',150,4.25,'2026-04-20','2025-07-02');
/*!40000 ALTER TABLE `medicine_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_tbl`
--

DROP TABLE IF EXISTS `patient_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_tbl` (
  `patient_id` varchar(20) NOT NULL,
  `family_name` varchar(50) NOT NULL,
  `given_name` varchar(50) NOT NULL,
  `patient_email` varchar(100) DEFAULT NULL,
  `patient_phone` varchar(20) DEFAULT NULL,
  `mobile_number` varchar(20) DEFAULT NULL,
  `post_code` varchar(10) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `sex` enum('Male','Female','Other') DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `blood_group` varchar(10) DEFAULT NULL,
  `emg_family_name` varchar(50) DEFAULT NULL,
  `emg_given_name` varchar(50) DEFAULT NULL,
  `emg_phone` varchar(20) DEFAULT NULL,
  `emg_mobile` varchar(20) DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_tbl`
--

LOCK TABLES `patient_tbl` WRITE;
/*!40000 ALTER TABLE `patient_tbl` DISABLE KEYS */;
INSERT INTO `patient_tbl` VALUES ('P1001','jadoon','Ali','ali.khan@example.com','02123456789','03451234567','75500','123 Main Street, Karachi','Male','1985-06-20','B+','Khan','Ahmed','0219998888','03450001122','admin','2025-06-28 18:19:24'),('P1002','ebad','khan','ebad@gmail.com','03339200750','03339200750','22010','abt','Male','2004-12-10','B+','khan','saqib','033333333','033333333','Ebad','2025-07-02 00:00:00');
/*!40000 ALTER TABLE `patient_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prescription_medicines`
--

DROP TABLE IF EXISTS `prescription_medicines`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prescription_medicines` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prescription_id` varchar(20) DEFAULT NULL,
  `medicine_id` varchar(20) DEFAULT NULL,
  `dosage` varchar(50) DEFAULT NULL,
  `frequency` varchar(100) DEFAULT NULL,
  `duration` varchar(100) DEFAULT NULL,
  `instructions` text,
  PRIMARY KEY (`id`),
  KEY `prescription_id` (`prescription_id`),
  KEY `medicine_id` (`medicine_id`),
  CONSTRAINT `prescription_medicines_ibfk_1` FOREIGN KEY (`prescription_id`) REFERENCES `prescriptions_tbl` (`prescription_id`),
  CONSTRAINT `prescription_medicines_ibfk_2` FOREIGN KEY (`medicine_id`) REFERENCES `medicine_tbl` (`medicine_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prescription_medicines`
--

LOCK TABLES `prescription_medicines` WRITE;
/*!40000 ALTER TABLE `prescription_medicines` DISABLE KEYS */;
INSERT INTO `prescription_medicines` VALUES (1,'PR001','1','40',NULL,NULL,NULL),(2,'PR002','1','20',NULL,NULL,NULL),(3,'PR002','2','20',NULL,NULL,NULL),(4,'PR002','3','20',NULL,NULL,NULL),(5,'PR002','4','20',NULL,NULL,NULL);
/*!40000 ALTER TABLE `prescription_medicines` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prescriptions_tbl`
--

DROP TABLE IF EXISTS `prescriptions_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prescriptions_tbl` (
  `prescription_id` varchar(20) NOT NULL,
  `patient_id` varchar(50) NOT NULL,
  `doctor_id` varchar(50) NOT NULL,
  `appointment_id` varchar(20) NOT NULL,
  `date` date NOT NULL,
  `diagnosis` text,
  `notes` text,
  PRIMARY KEY (`prescription_id`),
  KEY `patient_id` (`patient_id`),
  KEY `doctor_id` (`doctor_id`),
  KEY `appointment_id` (`appointment_id`),
  CONSTRAINT `prescriptions_tbl_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patient_tbl` (`patient_id`),
  CONSTRAINT `prescriptions_tbl_ibfk_2` FOREIGN KEY (`doctor_id`) REFERENCES `doctors_tbl` (`doctor_id`),
  CONSTRAINT `prescriptions_tbl_ibfk_3` FOREIGN KEY (`appointment_id`) REFERENCES `appointment_tbl` (`appointment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prescriptions_tbl`
--

LOCK TABLES `prescriptions_tbl` WRITE;
/*!40000 ALTER TABLE `prescriptions_tbl` DISABLE KEYS */;
INSERT INTO `prescriptions_tbl` VALUES ('1','P1001','70','AP001','2025-06-30',NULL,NULL),('PR001','P1001','70','AP001','2025-07-02','ss','ss'),('PR002','P1001','70','AP001','2025-07-02','asa','assa');
/*!40000 ALTER TABLE `prescriptions_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_tbl`
--

DROP TABLE IF EXISTS `users_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_tbl` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `employee_id` int(11) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password_hash` varchar(255) DEFAULT NULL,
  `role` enum('admin','doctor','receptionist') DEFAULT NULL,
  `first_login` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  KEY `employee_id` (`employee_id`),
  CONSTRAINT `users_tbl_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_tbl`
--

LOCK TABLES `users_tbl` WRITE;
/*!40000 ALTER TABLE `users_tbl` DISABLE KEYS */;
INSERT INTO `users_tbl` VALUES (22,11,'ahmed_admin','12345','admin',0),(31,28,'ebad@gmail.com','12345','doctor',0),(32,29,'rep@gmail.com','12345','receptionist',0);
/*!40000 ALTER TABLE `users_tbl` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-03  1:23:36
