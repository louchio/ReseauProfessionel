-- phpMyAdmin SQL Dump
-- version 3.4.9
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le : Mar 22 Mai 2012 à 15:27
-- Version du serveur: 5.5.20
-- Version de PHP: 5.3.9

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `reseauprofesionnel`
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
  `idMembres` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `nom` varchar(10) DEFAULT NULL,
  `prenom` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`idMembres`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Contenu de la table `membres`
--

INSERT INTO `membres` (`idMembres`, `email`, `password`, `nom`, `prenom`) VALUES
(1, 'anas@gmail.com', 'anas', 'khabali', 'anas'),
(2, 'hassan@yahoo.fr', 'hassan', 'elouardi', 'hassan'),
(3, 'adil@gmail.com', 'adil', 'elyamouni', 'adil'),
(4, 'naziha@gmail.com', 'naziha', 'laaz', 'naziha');

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `amis`
--
ALTER TABLE `amis`
  ADD CONSTRAINT `amis_ibfk_1` FOREIGN KEY (`Membres_idMembres`) REFERENCES `membres` (`idMembres`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `amis_ibfk_2` FOREIGN KEY (`idAmis`) REFERENCES `membres` (`idMembres`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `demandes`
--
ALTER TABLE `demandes`
  ADD CONSTRAINT `demandes_ibfk_1` FOREIGN KEY (`Membres_idMembres`) REFERENCES `membres` (`idMembres`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `lieuxfavoris`
--
ALTER TABLE `lieuxfavoris`
  ADD CONSTRAINT `lieuxfavoris_ibfk_1` FOREIGN KEY (`Membres_idMembres`) REFERENCES `membres` (`idMembres`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `markers`
--
ALTER TABLE `markers`
  ADD CONSTRAINT `markers_ibfk_1` FOREIGN KEY (`Membres_idMembres`) REFERENCES `membres` (`idMembres`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
