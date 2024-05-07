-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : ven. 03 mai 2024 à 19:41
-- Version du serveur : 10.4.28-MariaDB
-- Version de PHP : 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `genotes1`
--

-- --------------------------------------------------------

--
-- Structure de la table `etudiant`
--

CREATE TABLE `etudiant` (
  `id_etudiant` int(11) NOT NULL,
  `nom_etudiant` varchar(20) DEFAULT NULL,
  `prenom_etudiant` varchar(20) DEFAULT NULL,
  `id_semestre` varchar(20) DEFAULT NULL,
  `id_filiere` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `etudiant`
--

INSERT INTO `etudiant` (`id_etudiant`, `nom_etudiant`, `prenom_etudiant`, `id_semestre`, `id_filiere`) VALUES
(1, 'EL HARRAK', 'AYMANE', 'S2', 'GI'),
(2, 'BENABOUD', 'ADNAN', 'S2', 'GI'),
(3, 'DEHDOUH', 'AYOUB', 'S2', 'GI'),
(21, 'Cristiano', 'Ronaldo', 'S1', 'GI'),
(22, 'Messi', 'Lionel', 'S1', 'GI');

-- --------------------------------------------------------

--
-- Structure de la table `filiere`
--

CREATE TABLE `filiere` (
  `id_filiere` varchar(20) NOT NULL,
  `nom_filiere` varchar(50) DEFAULT NULL,
  `objectif_filiere` varchar(300) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `filiere`
--

INSERT INTO `filiere` (`id_filiere`, `nom_filiere`, `objectif_filiere`) VALUES
('BCG', 'Biologie-Chimie-Geologie', 'Pour les gens ayant une mauvaise note en BAC'),
('GI', 'Géoinformation', 'Le principal objectif de cette filière est de former des ingénieurs \"géo-informaticiens cartographes gestionnaires de l’information spatiale\" polyvalents.'),
('LSI', 'Logiciels et Systèmes Intelligents', 'Former des ingénieurs capables de Comprendre les enjeux des techniques de l’information, Proposer et construire des solutions informatiques et décisionnelles compétitives et réalistes, Intervenir sur des problématiques informatiques complexes, Piloter des projets informatiques et les changements.');

-- --------------------------------------------------------

--
-- Structure de la table `matiere`
--

CREATE TABLE `matiere` (
  `id_matiere` varchar(20) NOT NULL,
  `nom_matiere` varchar(40) DEFAULT NULL,
  `description_matiere` varchar(100) DEFAULT NULL,
  `volume_horaire_matiere` float DEFAULT NULL,
  `coefficient_matiere` float DEFAULT NULL,
  `id_module` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `matiere`
--

INSERT INTO `matiere` (`id_matiere`, `nom_matiere`, `description_matiere`, `volume_horaire_matiere`, `coefficient_matiere`, `id_module`) VALUES
('GI111', 'Algorithmique et programmation', 'Algorithmique et programmation', 60, 1, 'GI11'),
('GI121', 'Analyse numérique', 'Compléments de mathématique et Analyse numérique', 60, 1, 'GI12'),
('GI131', 'Statistique', 'Statistique', 30, 0.5, 'GI13'),
('GI132', 'Analyse des données', 'Analyse des données', 30, 0.5, 'GI13'),
('GI141', 'BPT', 'Bases physiques de la télédétection', 30, 0.5, 'GI14'),
('GI142', 'TS', 'Traitement du signal', 30, 0.5, 'GI14'),
('GI151', 'TEC', 'Communication professionnelle', 60, 1, 'GI15'),
('GI161', 'SI', ' Systèmes d’information ', 30, 0.5, 'GI16'),
('GI162', 'Réseaux', 'Réseaux informatique', 30, 0.5, 'GI16');

-- --------------------------------------------------------

--
-- Structure de la table `module`
--

CREATE TABLE `module` (
  `id_module` varchar(20) NOT NULL,
  `nom_module` varchar(40) DEFAULT NULL,
  `description_module` varchar(100) DEFAULT NULL,
  `id_semestre` varchar(20) DEFAULT NULL,
   `id_filiere` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `module`
--

INSERT INTO `module` (`id_module`, `nom_module`, `description_module`, `id_semestre`, `id_filiere`) VALUES
('GI11', 'Algorithmique et programmation', 'Algorithmique et programmation en Python\r\n', 'S1', 'GI'),
('GI12', 'Analyse numérique', 'Compléments de mathématique et Analyse numérique', 'S1', 'GI'),
('GI13', 'Statistique/Analyse des données', 'Statistique/Analyse des donnée', 'S1', 'GI'),
('GI14', 'BPT/TS', 'Bases physiques de la télédétection/Traitement du signal', 'S1', 'GI'),
('GI15', 'TEC', 'Communication professionnelle', 'S1', 'GI'),
('GI16', 'SI et Réseaux', 'Systèmes d’information et réseaux informatique', 'S1', 'GI');

-- --------------------------------------------------------

--
-- Structure de la table `note`
--

CREATE TABLE `note` (
  `id_etudiant` int(11) NOT NULL,
  `id_matiere` varchar(20) NOT NULL,
  `note_finale` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `note`
--

INSERT INTO `note` (`id_etudiant`, `id_matiere`, `note_finale`) VALUES
(1, 'GI111', 16),
(1, 'GI121', 12),
(1, 'GI131', 13),
(2, 'GI111', 16.25),
(3, 'GI111', 15);

-- --------------------------------------------------------

--
-- Structure de la table `semestre`
--

CREATE TABLE `semestre` (
  `id_semestre` varchar(20) NOT NULL,
  `nom_semestre` varchar(20) DEFAULT NULL,
  `id_filiere` varchar(20) NOT NULL,
  `annee_semestre` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `semestre`
--

INSERT INTO `semestre` (`id_semestre`, `nom_semestre`, `id_filiere`, `annee_semestre`) VALUES
('S1', 'Semestre 1', 'GI', '2023'),
('S2', 'Semestre 2', 'GI', '2023'),
('S3', 'Semestre 3', 'GI', '2023'),
('S4', 'Semestre 4', 'GI', '2023'),
('S5', 'Semestre 5', 'GI', '2023'),
('S6', 'Semestre 6', 'GI', '2023');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `etudiant`
--
ALTER TABLE `etudiant`
  ADD PRIMARY KEY (`id_etudiant`),
  ADD KEY `id_filiere` (`id_filiere`),
  ADD KEY `id_semestre` (`id_semestre`);

--
-- Index pour la table `filiere`
--
ALTER TABLE `filiere`
  ADD PRIMARY KEY (`id_filiere`);

--
-- Index pour la table `matiere`
--
ALTER TABLE `matiere`
  ADD PRIMARY KEY (`id_matiere`),
  ADD KEY `id_module` (`id_module`);

--
-- Index pour la table `module`
--
ALTER TABLE `module`
  ADD PRIMARY KEY (`id_module`),
  ADD KEY `id_semestre` (`id_semestre`);

--
-- Index pour la table `note`
--
ALTER TABLE `note`
  ADD PRIMARY KEY (`id_etudiant`,`id_matiere`),
  ADD KEY `id_matiere` (`id_matiere`);

--
-- Index pour la table `semestre`
--
ALTER TABLE `semestre`
  ADD PRIMARY KEY (`id_semestre`,`id_filiere`, `annee_semestre`),
  ADD KEY `id_filiere` (`id_filiere`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `etudiant`
--
ALTER TABLE `etudiant`
  MODIFY `id_etudiant` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT pour la table `note`
--
ALTER TABLE `note`
  MODIFY `id_etudiant` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `etudiant`
--
ALTER TABLE `etudiant`
  ADD CONSTRAINT `etudiant_ibfk_1` FOREIGN KEY (`id_filiere`) REFERENCES `filiere` (`id_filiere`),
  ADD CONSTRAINT `etudiant_ibfk_2` FOREIGN KEY (`id_semestre`) REFERENCES `semestre` (`id_semestre`);

--
-- Contraintes pour la table `matiere`
--
ALTER TABLE `matiere`
  ADD CONSTRAINT `matiere_ibfk_1` FOREIGN KEY (`id_module`) REFERENCES `module` (`id_module`);

--
-- Contraintes pour la table `module`
--
ALTER TABLE `module`
  ADD CONSTRAINT `module_ibfk_1` FOREIGN KEY (`id_semestre`) REFERENCES `semestre` (`id_semestre`),
  ADD CONSTRAINT `module_ibfk_2` FOREIGN KEY (`id_filiere`) REFERENCES `filiere` (`id_filiere`);

--
-- Contraintes pour la table `note`
--
ALTER TABLE `note`
  ADD CONSTRAINT `note_ibfk_1` FOREIGN KEY (`id_etudiant`) REFERENCES `etudiant` (`id_etudiant`),
  ADD CONSTRAINT `note_ibfk_2` FOREIGN KEY (`id_matiere`) REFERENCES `matiere` (`id_matiere`);

--
-- Contraintes pour la table `semestre`
--
ALTER TABLE `semestre`
  ADD CONSTRAINT `semestre_ibfk_1` FOREIGN KEY (`id_filiere`) REFERENCES `filiere` (`id_filiere`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;