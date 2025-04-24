-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : jeu. 24 avr. 2025 à 16:25
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `gl_java`
--

-- --------------------------------------------------------

--
-- Structure de la table `contrat_location`
--

CREATE TABLE `contrat_location` (
  `id` int(11) NOT NULL,
  `id_logement` int(11) NOT NULL,
  `id_locataire` int(11) NOT NULL,
  `date_debut` date NOT NULL,
  `date_fin` date DEFAULT NULL,
  `loyer_mensuel` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `contrat_location`
--

INSERT INTO `contrat_location` (`id`, `id_logement`, `id_locataire`, `date_debut`, `date_fin`, `loyer_mensuel`) VALUES
(1, 1, 1, '2025-03-12', '2025-03-15', 1452000),
(2, 4, 2, '2025-03-25', '2025-03-30', 1582000),
(3, 3, 4, '2025-04-11', '2025-04-12', 2504000);

-- --------------------------------------------------------

--
-- Structure de la table `entretien`
--

CREATE TABLE `entretien` (
  `id` int(11) NOT NULL,
  `id_logement` int(11) NOT NULL,
  `date_entretien` date NOT NULL,
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `entretien`
--

INSERT INTO `entretien` (`id`, `id_logement`, `date_entretien`, `description`) VALUES
(1, 1, '2025-04-24', 'Entretien 1 de logement 1'),
(4, 3, '2025-04-24', 'Entretien 3 de logement 3'),
(5, 3, '2025-04-24', 'Description');

-- --------------------------------------------------------

--
-- Structure de la table `locataire`
--

CREATE TABLE `locataire` (
  `id` int(11) NOT NULL,
  `nom` varchar(100) DEFAULT NULL,
  `prenom` varchar(100) DEFAULT NULL,
  `cin` varchar(20) DEFAULT NULL,
  `telephone` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `locataire`
--

INSERT INTO `locataire` (`id`, `nom`, `prenom`, `cin`, `telephone`) VALUES
(1, 'BRADA', 'Ntsoa', '102 200 100 151', '0331020522'),
(2, 'ANTONIO', 'Downavan', '512 011 022 009', '038545770'),
(4, 'NIRY', 'Bola', '120 555 222 111', '0331446160');

-- --------------------------------------------------------

--
-- Structure de la table `logement`
--

CREATE TABLE `logement` (
  `id` int(11) NOT NULL,
  `adresse` varchar(255) NOT NULL,
  `type` varchar(50) NOT NULL,
  `superficie` double NOT NULL,
  `loyer` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `logement`
--

INSERT INTO `logement` (`id`, `adresse`, `type`, `superficie`, `loyer`) VALUES
(1, 'Ivory Atsimo', 'Maison', 28.3, 90000),
(3, 'Tanambao', 'Magasin', 40.5, 450000),
(4, 'Andrainjato', 'Cybercafé', 8.8, 100000);

-- --------------------------------------------------------

--
-- Structure de la table `paiement`
--

CREATE TABLE `paiement` (
  `id` int(11) NOT NULL,
  `id_contrat` int(11) NOT NULL,
  `date_paiement` date NOT NULL,
  `montant` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `paiement`
--

INSERT INTO `paiement` (`id`, `id_contrat`, `date_paiement`, `montant`) VALUES
(1, 2, '2025-04-24', 251000),
(2, 1, '2025-04-30', 120000);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `id` int(11) NOT NULL,
  `nom_utilisateur` varchar(100) NOT NULL,
  `mot_de_passe` varchar(255) NOT NULL,
  `role` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `nom_utilisateur`, `mot_de_passe`, `role`) VALUES
(1, 'antonio', 'antonio', 'admin'),
(2, 'brada', 'brada', 'admin');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `contrat_location`
--
ALTER TABLE `contrat_location`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_logement` (`id_logement`),
  ADD KEY `id_locataire` (`id_locataire`);

--
-- Index pour la table `entretien`
--
ALTER TABLE `entretien`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_logement` (`id_logement`);

--
-- Index pour la table `locataire`
--
ALTER TABLE `locataire`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `logement`
--
ALTER TABLE `logement`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `paiement`
--
ALTER TABLE `paiement`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_contrat` (`id_contrat`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nom_utilisateur` (`nom_utilisateur`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `contrat_location`
--
ALTER TABLE `contrat_location`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `entretien`
--
ALTER TABLE `entretien`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `locataire`
--
ALTER TABLE `locataire`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `logement`
--
ALTER TABLE `logement`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `paiement`
--
ALTER TABLE `paiement`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `contrat_location`
--
ALTER TABLE `contrat_location`
  ADD CONSTRAINT `contrat_location_ibfk_1` FOREIGN KEY (`id_logement`) REFERENCES `logement` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `contrat_location_ibfk_2` FOREIGN KEY (`id_locataire`) REFERENCES `locataire` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `entretien`
--
ALTER TABLE `entretien`
  ADD CONSTRAINT `entretien_ibfk_1` FOREIGN KEY (`id_logement`) REFERENCES `logement` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `paiement`
--
ALTER TABLE `paiement`
  ADD CONSTRAINT `paiement_ibfk_1` FOREIGN KEY (`id_contrat`) REFERENCES `contrat_location` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
