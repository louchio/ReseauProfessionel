		-- phpMyAdmin SQL Dump
-- version 3.5.8.1
-- http://www.phpmyadmin.net
--
-- Client: 127.0.0.1
-- Généré le: Dim 26 Janvier 2014 à 10:49
-- Version du serveur: 5.6.10
-- Version de PHP: 5.4.14

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `reseauprofessionnel`
--

-- --------------------------------------------------------

--
-- Structure de la table `amis`
--

CREATE TABLE IF NOT EXISTS `amis` (
  `idAmis` int(11) NOT NULL,
  `Membres_idMembres` int(11) NOT NULL,
  PRIMARY KEY (`idAmis`,`Membres_idMembres`),
  KEY `fk_Amis_Membres1` (`Membres_idMembres`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `amis`
--

INSERT INTO `amis` (`idAmis`, `Membres_idMembres`) VALUES
(2, 1),
(3, 1);

-- --------------------------------------------------------

--
-- Structure de la table `annonce`
--

CREATE TABLE IF NOT EXISTS `annonce` (
  `idannonce` int(11) NOT NULL AUTO_INCREMENT,
  `titreAnnonce` varchar(45) DEFAULT NULL,
  `textAnnonce` varchar(45) DEFAULT NULL,
  `idUtilisateur` int(11) NOT NULL,
  PRIMARY KEY (`idannonce`),
  UNIQUE KEY `idannonce_UNIQUE` (`idannonce`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `annonce`
--

INSERT INTO `annonce` (`idannonce`, `titreAnnonce`, `textAnnonce`, `idUtilisateur`) VALUES
(1, 'location du  materiel', 'bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbssssbbb', 1),
(2, 'location de voiture', 'jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj', 1);

-- --------------------------------------------------------

--
-- Structure de la table `demandes`
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
-- Structure de la table `lieuxfavoris`
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
-- Structure de la table `markers`
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
-- Structure de la table `membres`
--

CREATE TABLE IF NOT EXISTS `membres` (
  `idUtilisateur` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(10) DEFAULT NULL,
  `prenom` varchar(10) DEFAULT NULL,
  `numTel` varchar(45) DEFAULT NULL,
  `adresse` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `estProfessionnel` varchar(45) DEFAULT NULL,
  `login` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `idProfession` int(11) NOT NULL,
  PRIMARY KEY (`idUtilisateur`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Contenu de la table `membres`
--

INSERT INTO `membres` (`idUtilisateur`, `nom`, `prenom`, `numTel`, `adresse`, `email`, `estProfessionnel`, `login`, `password`, `idProfession`) VALUES
(1, 'khabali', 'anas', '0111', 'marseille', 'anas@gmail.com', '1', 'anassLog', 'anas', 1),
(2, 'elouardi', 'hassan', NULL, NULL, 'hassan@yahoo.fr', '0', 'hassanLog', 'hassan', 2),
(3, 'elyamouni', 'adil', NULL, NULL, 'adil@gmail.com', '1', 'adilLog', 'adil', 2),
(4, 'laaz', 'naziha', NULL, NULL, 'naziha@gmail.com', '1', 'nazihaLog', 'naziha', 1);

-- --------------------------------------------------------

--
-- Structure de la table `profession`
--

CREATE TABLE IF NOT EXISTS `profession` (
  `idprofession` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idprofession`),
  UNIQUE KEY `idprofession_UNIQUE` (`idprofession`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Contenu de la table `profession`
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
