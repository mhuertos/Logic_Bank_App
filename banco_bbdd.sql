CREATE DATABASE  IF NOT EXISTS `banco` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `banco`;
-- MariaDB dump 10.19  Distrib 10.4.22-MariaDB, for Win64 (AMD64)
--
-- Host: 127.0.0.1    Database: banco
-- ------------------------------------------------------
-- Server version	10.4.22-MariaDB

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
-- Table structure for table `categoria_gastos`
--

DROP TABLE IF EXISTS `categoria_gastos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoria_gastos` (
  `idCategoria` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `emisor` varchar(45) DEFAULT NULL,
  `periodicidad` varchar(20) DEFAULT NULL,
  `cargo_economico` decimal(9,2) DEFAULT NULL,
  PRIMARY KEY (`idCategoria`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria_gastos`
--

LOCK TABLES `categoria_gastos` WRITE;
/*!40000 ALTER TABLE `categoria_gastos` DISABLE KEYS */;
INSERT INTO `categoria_gastos` VALUES (1,'Luz','Endesa','mensual',50.00),(9,'Gas','Gas_Natural','bimensual',78.00),(10,'Agua','SGAB','trimestral',100.00),(11,'Seguros','Fenix_Directo','anual',120.00),(12,'Internet','Jazztel','mensual',29.00),(13,'Linea_telefono','Yoigo','mensual',14.00),(14,'Cuota_Autonomo','TGSS','mensual',298.00),(15,'Alquiler','Privado','mensual',500.00);
/*!40000 ALTER TABLE `categoria_gastos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoria_ingresos`
--

DROP TABLE IF EXISTS `categoria_ingresos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoria_ingresos` (
  `idTipoIngreso` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) DEFAULT NULL,
  `emisor` varchar(20) DEFAULT NULL,
  `periodicidad` varchar(15) DEFAULT NULL,
  `ingreso_economico` decimal(12,2) DEFAULT NULL,
  PRIMARY KEY (`idTipoIngreso`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria_ingresos`
--

LOCK TABLES `categoria_ingresos` WRITE;
/*!40000 ALTER TABLE `categoria_ingresos` DISABLE KEYS */;
INSERT INTO `categoria_ingresos` VALUES (1,'Nomina_Carmen','Inditex_SA','mensual',1700.00),(2,'Nomina_Falete','A3MEDIA_SA','mensual',3500.00),(3,'Nomina_Carmen','Inditex_SA','mensual',1700.00),(4,'Nomina_Falete','A3MEDIA_SA','mensual',3500.00);
/*!40000 ALTER TABLE `categoria_ingresos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `idCliente` int(11) NOT NULL AUTO_INCREMENT,
  `nif` char(9) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `apellidos` varchar(60) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  PRIMARY KEY (`idCliente`),
  UNIQUE KEY `nif_UNIQUE` (`nif`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credito`
--

DROP TABLE IF EXISTS `credito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `credito` (
  `IdTargetaCredito` int(11) NOT NULL,
  `credito_mensual` decimal(6,2) NOT NULL,
  PRIMARY KEY (`IdTargetaCredito`),
  CONSTRAINT `fk_CREDITO_TARGETA1` FOREIGN KEY (`IdTargetaCredito`) REFERENCES `targeta` (`IdTargeta`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credito`
--

LOCK TABLES `credito` WRITE;
/*!40000 ALTER TABLE `credito` DISABLE KEYS */;
/*!40000 ALTER TABLE `credito` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuenta`
--

DROP TABLE IF EXISTS `cuenta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cuenta` (
  `idCuenta` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `fecha_creacion` date DEFAULT NULL,
  `IBAN` char(28) DEFAULT NULL,
  `tipo_cuenta` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idCuenta`),
  UNIQUE KEY `IBAN_UNIQUE` (`IBAN`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuenta`
--

LOCK TABLES `cuenta` WRITE;
/*!40000 ALTER TABLE `cuenta` DISABLE KEYS */;
/*!40000 ALTER TABLE `cuenta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gastos_asociados`
--

DROP TABLE IF EXISTS `gastos_asociados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gastos_asociados` (
  `idCategoriaGasto` int(11) NOT NULL,
  `idCuenta` int(11) NOT NULL,
  PRIMARY KEY (`idCategoriaGasto`,`idCuenta`),
  KEY `fk_categoria_gastos_has_cuenta_cuenta1_idx` (`idCuenta`),
  KEY `fk_categoria_gastos_has_cuenta_categoria_gastos1_idx` (`idCategoriaGasto`),
  CONSTRAINT `fk_categoria_gastos_has_cuenta_categoria_gastos1` FOREIGN KEY (`idCategoriaGasto`) REFERENCES `categoria_gastos` (`idCategoria`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_categoria_gastos_has_cuenta_cuenta1` FOREIGN KEY (`idCuenta`) REFERENCES `cuenta` (`idCuenta`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gastos_asociados`
--

LOCK TABLES `gastos_asociados` WRITE;
/*!40000 ALTER TABLE `gastos_asociados` DISABLE KEYS */;
/*!40000 ALTER TABLE `gastos_asociados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingresos_asociados`
--

DROP TABLE IF EXISTS `ingresos_asociados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ingresos_asociados` (
  `idTipoIngreso` int(11) NOT NULL,
  `idCuenta` int(11) NOT NULL,
  PRIMARY KEY (`idTipoIngreso`,`idCuenta`),
  KEY `fk_idCuenta` (`idCuenta`),
  CONSTRAINT `fk_idCuenta` FOREIGN KEY (`idCuenta`) REFERENCES `cuenta` (`idCuenta`),
  CONSTRAINT `fk_idTipoIngreso` FOREIGN KEY (`idTipoIngreso`) REFERENCES `categoria_ingresos` (`idTipoIngreso`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingresos_asociados`
--

LOCK TABLES `ingresos_asociados` WRITE;
/*!40000 ALTER TABLE `ingresos_asociados` DISABLE KEYS */;
/*!40000 ALTER TABLE `ingresos_asociados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimiento`
--

DROP TABLE IF EXISTS `movimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movimiento` (
  `IdMovimiento` int(11) NOT NULL AUTO_INCREMENT,
  `idCliente` int(11) NOT NULL,
  `idCuenta` int(11) NOT NULL,
  `saldo` decimal(10,2) DEFAULT NULL,
  `concepto` varchar(50) NOT NULL,
  `entidad_asociada` varchar(60) NOT NULL,
  `fecha_movimiento` date NOT NULL,
  `IdTipoMovimiento` int(11) NOT NULL,
  PRIMARY KEY (`IdMovimiento`,`idCliente`,`idCuenta`),
  KEY `fk_MOVIMIENTO_TIPO_MOVIMIENTO1_idx` (`IdTipoMovimiento`),
  KEY `fk_movimiento_titular_linea1_idx` (`idCliente`,`idCuenta`),
  CONSTRAINT `fk_MOVIMIENTO_TIPO_MOVIMIENTO1` FOREIGN KEY (`IdTipoMovimiento`) REFERENCES `tipo_movimiento` (`IdTipoMovimiento`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_movimiento_titular_linea1` FOREIGN KEY (`idCliente`, `idCuenta`) REFERENCES `titular_linea` (`idCliente`, `idCuenta`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimiento`
--

LOCK TABLES `movimiento` WRITE;
/*!40000 ALTER TABLE `movimiento` DISABLE KEYS */;
/*!40000 ALTER TABLE `movimiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prestamo`
--

DROP TABLE IF EXISTS `prestamo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prestamo` (
  `IdPrestamo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `fecha_solicitud` date DEFAULT NULL,
  `fecha_concesion` date DEFAULT NULL,
  `cantidad` decimal(8,2) NOT NULL,
  `tipo_interes` decimal(4,2) NOT NULL,
  `cuota_meses` int(10) unsigned NOT NULL,
  `idCliente` int(11) NOT NULL,
  `idCuenta` int(11) NOT NULL,
  PRIMARY KEY (`IdPrestamo`,`idCliente`,`idCuenta`),
  KEY `fk_PRESTAMO_TITULAR_LINEA1_idx` (`idCliente`,`idCuenta`),
  CONSTRAINT `fk_PRESTAMO_TITULAR_LINEA1` FOREIGN KEY (`idCliente`, `idCuenta`) REFERENCES `titular_linea` (`idCliente`, `idCuenta`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prestamo`
--

LOCK TABLES `prestamo` WRITE;
/*!40000 ALTER TABLE `prestamo` DISABLE KEYS */;
/*!40000 ALTER TABLE `prestamo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `targeta`
--

DROP TABLE IF EXISTS `targeta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `targeta` (
  `IdTargeta` int(11) NOT NULL AUTO_INCREMENT,
  `nombreTargeta` varchar(45) DEFAULT NULL,
  `tipo_targeta` varchar(45) DEFAULT NULL,
  `numTargeta` varchar(20) DEFAULT NULL,
  `fecha_Creacion` date NOT NULL,
  `fecha_Caducidad` date NOT NULL,
  `pin` char(4) NOT NULL,
  `numSeguridad` char(3) NOT NULL,
  `limite_gasto` int(11) DEFAULT NULL,
  `TAE` decimal(4,2) DEFAULT NULL,
  `idCliente` int(11) NOT NULL,
  `idCuenta` int(11) NOT NULL,
  PRIMARY KEY (`IdTargeta`,`idCliente`,`idCuenta`),
  UNIQUE KEY `numTargeta_UNIQUE` (`numTargeta`),
  KEY `fk_TARGETA_TITULAR_LINEA1_idx` (`idCliente`,`idCuenta`),
  CONSTRAINT `fk_TARGETA_TITULAR_LINEA1` FOREIGN KEY (`idCliente`, `idCuenta`) REFERENCES `titular_linea` (`idCliente`, `idCuenta`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `targeta`
--

LOCK TABLES `targeta` WRITE;
/*!40000 ALTER TABLE `targeta` DISABLE KEYS */;
/*!40000 ALTER TABLE `targeta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_movimiento`
--

DROP TABLE IF EXISTS `tipo_movimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_movimiento` (
  `IdTipoMovimiento` int(11) NOT NULL AUTO_INCREMENT,
  `desc` varchar(60) NOT NULL,
  PRIMARY KEY (`IdTipoMovimiento`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_movimiento`
--

LOCK TABLES `tipo_movimiento` WRITE;
/*!40000 ALTER TABLE `tipo_movimiento` DISABLE KEYS */;
INSERT INTO `tipo_movimiento` VALUES (1,'Transferencia_Bancaria'),(2,'Ingreso_Bancario'),(3,'Bizum'),(4,'Nomina'),(5,'Extraccion_cajero'),(6,'Embargo_Cuenta_Bancaria'),(7,'Cuota_Prestamo'),(8,'Pago_Targeta');
/*!40000 ALTER TABLE `tipo_movimiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `titular_linea`
--

DROP TABLE IF EXISTS `titular_linea`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `titular_linea` (
  `idCliente` int(11) NOT NULL,
  `idCuenta` int(11) NOT NULL,
  `fecha_alta` date NOT NULL,
  PRIMARY KEY (`idCliente`,`idCuenta`),
  KEY `fk_CLIENTE_has_CUENTA_CUENTA1_idx` (`idCuenta`),
  KEY `fk_CLIENTE_has_CUENTA_CLIENTE_idx` (`idCliente`),
  CONSTRAINT `fk_CLIENTE_has_CUENTA_CLIENTE` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_CLIENTE_has_CUENTA_CUENTA1` FOREIGN KEY (`idCuenta`) REFERENCES `cuenta` (`idCuenta`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `titular_linea`
--

LOCK TABLES `titular_linea` WRITE;
/*!40000 ALTER TABLE `titular_linea` DISABLE KEYS */;
/*!40000 ALTER TABLE `titular_linea` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-25 13:38:03
