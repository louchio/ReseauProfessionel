-- phpMyAdmin SQL Dump
-- version 3.5.8.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 29, 2014 at 08:45 AM
-- Server version: 5.6.10
-- PHP Version: 5.4.14

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `reseauprofessionnel`
--

-- --------------------------------------------------------

--
-- Table structure for table `amis`
--

CREATE TABLE IF NOT EXISTS `amis` (
  `idAmis` int(11) NOT NULL,
  `Membres_idMembres` int(11) NOT NULL,
  PRIMARY KEY (`idAmis`,`Membres_idMembres`),
  KEY `fk_Amis_Membres1` (`Membres_idMembres`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `amis`
--

INSERT INTO `amis` (`idAmis`, `Membres_idMembres`) VALUES
(2, 1),
(3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `annonce`
--

CREATE TABLE IF NOT EXISTS `annonce` (
  `idannonce` int(11) NOT NULL AUTO_INCREMENT,
  `titreAnnonce` varchar(45) DEFAULT NULL,
  `textAnnonce` varchar(45) DEFAULT NULL,
  `idUtilisateur` int(11) NOT NULL,
  `idProfession` int(11) NOT NULL,
  `destination` int(11) NOT NULL,
  PRIMARY KEY (`idannonce`),
  UNIQUE KEY `idannonce_UNIQUE` (`idannonce`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `annonce`
--

INSERT INTO `annonce` (`idannonce`, `titreAnnonce`, `textAnnonce`, `idUtilisateur`, `idProfession`, `destination`) VALUES
(1, 'location du  materiel', 'bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbssssbbb', 1, 0, 0),
(2, 'location de voiture', 'jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj', 1, 0, 0),
(3, '"mmmmm"', '"ddddd"', 2, 1, 0),
(4, 'TitreAnnonce2', 'TexteAnnonce2', 2, 3, 0),
(5, 'TitreAnnonce3jj', 'TexteAnnonce3', 2, 1, 1),
(6, 'TitreAnnoncea6', 'TexteAnnonce4', 2, 7, 1);

-- --------------------------------------------------------

--
-- Table structure for table `demandes`
--

CREATE TABLE IF NOT EXISTS `demandes` (
  `iddemandes` int(11) NOT NULL AUTO_INCREMENT,
  `sender` int(11) DEFAULT NULL,
  `Membres_idMembres` int(11) NOT NULL,
  PRIMARY KEY (`iddemandes`,`Membres_idMembres`),
  KEY `fk_demandes_Membres1` (`Membres_idMembres`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `lieuxfavoris`
--

CREATE TABLE IF NOT EXISTS `lieuxfavoris` (
  `idLieuxFavoris` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(45) DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `Membres_idMembres` int(11) NOT NULL,
  PRIMARY KEY (`idLieuxFavoris`,`Membres_idMembres`),
  KEY `fk_LieuxFavoris_Membres1` (`Membres_idMembres`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `markers`
--

CREATE TABLE IF NOT EXISTS `markers` (
  `idMarkers` int(11) NOT NULL AUTO_INCREMENT,
  `longitude` double DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `vitesse` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `Membres_idMembres` int(11) NOT NULL,
  PRIMARY KEY (`idMarkers`,`Membres_idMembres`),
  KEY `fk_Markers_Membres` (`Membres_idMembres`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `membres`
--

CREATE TABLE IF NOT EXISTS `membres` (
  `idUtilisateur` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(10) DEFAULT NULL,
  `prenom` varchar(10) DEFAULT NULL,
  `numTel` varchar(45) DEFAULT NULL,
  `arrondissement` int(11) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `estProfessionnel` varchar(45) DEFAULT NULL,
  `login` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `idProfession` int(11) NOT NULL,
  PRIMARY KEY (`idUtilisateur`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `membres`
--

INSERT INTO `membres` (`idUtilisateur`, `nom`, `prenom`, `numTel`, `arrondissement`, `email`, `estProfessionnel`, `login`, `password`, `idProfession`) VALUES
(1, 'khabali', 'anas', '0111', 1, 'anas@gmail.com', '1', 'anassLog', 'anas', 1),
(2, 'elouardi', 'hassan', '01111', 12, 'hassan@yahoo.fr', '0', 'hassanLog', 'hassan', 2),
(3, 'elyamouni', 'adil', NULL, 21, 'adil@gmail.com', '1', 'adilLog', 'adil', 2),
(4, 'laaz', 'naziha', NULL, 10, 'naziha@gmail.com', '1', 'nazihaLog', 'naziha', 1),
(5, 'yassin', 'sssss', '0212', 14, 'nnnnn@hotmail.com', '0', 'aaaa', 'aaaa', 3);

-- --------------------------------------------------------

--
-- Table structure for table `profession`
--

CREATE TABLE IF NOT EXISTS `profession` (
  `idprofession` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idprofession`),
  UNIQUE KEY `idprofession_UNIQUE` (`idprofession`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `profession`
--

INSERT INTO `profession` (`idprofession`, `nom`) VALUES
(1, 'Ambulancier'),
(2, 'Assistant de service social'),
(3, 'Avocat'),
(4, 'Boulanger'),
(5, 'Chauffagiste'),
(6, 'Climaticien'),
(7, 'Electricien'),
(8, 'Mecanicien'),
(9, 'Medecin'),
(10, 'Plombier'),
(11, 'Reparateur automobile');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
