-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jun 03, 2017 at 11:48 AM
-- Server version: 10.1.16-MariaDB
-- PHP Version: 7.0.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `beaurivage`
--

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`id`, `first_name`, `middle_name`, `last_name`, `phone`, `email`) VALUES
(1, 'sdfsd', 'sdf', 'sdf', '234234', NULL),
(2, 'dsf', 'sdfsf', 'sdfsdf', '23424324', NULL),
(3, 'dsf', 'sdfsf', 'sdfsdf', '23424324', NULL),
(4, 'ывыва', 'ыва', 'ыва', '234234', NULL),
(5, 'ывыва', 'ыва', 'ыва', '234234', NULL),
(6, 'ывывЫВ', '', 'ЫВА', '', NULL),
(7, 'sds', '', '', '', NULL),
(8, '313', '', '12313', '', NULL),
(9, '313', '', '12313', '', NULL),
(10, 'rwer', 'wr', 'wer', '', NULL),
(11, 'выаыаыа', 'ыва', ' ывавыа', '34345346346', NULL),
(12, '35', '6', 'впа', '', NULL),
(13, 'dfgd', '', 'dfgdg', '', NULL),
(14, 'dfgd', '', 'dfgdg', '', NULL),
(15, 'dfgd', '', 'dfgdg', '', NULL),
(16, '13123123', 'sdff', 'sdfdsfds', '8925363737', NULL),
(17, 'ghfhgf', 'jbjh', 'vvhg', '', NULL),
(18, 'ывыа', 'ываыа', 'ыавыва', '', NULL),
(19, 'ывыа', 'ываыа', 'ыавыва', '', NULL),
(20, 'dsfsfs', 'scdf', 'sdcsd', '', NULL),
(21, 'qewqe', '', 'qwe', '', NULL),
(22, 'dwd', '', 'sdsf', '', NULL),
(23, 'sdsd', '', 'sdfsdf', '', NULL),
(24, 'sa', '', '', '', NULL),
(25, 'scds', '', 'sdsdf', '', NULL),
(26, 'cscs', '', '', '', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `record`
--

CREATE TABLE `record` (
  `id` int(11) NOT NULL,
  `customer` int(11) NOT NULL,
  `day` varchar(255) DEFAULT NULL,
  `time_from` varchar(255) DEFAULT NULL,
  `time_to` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `record`
--

INSERT INTO `record` (`id`, `customer`, `day`, `time_from`, `time_to`) VALUES
(21, 21, 'wed', '', ''),
(22, 22, 'wed', '', ''),
(23, 23, 'wed', '', ''),
(24, 24, 'tue', '', ''),
(25, 25, 'thr', '', ''),
(26, 26, 'wed', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
(1, 'ROLE_USER');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`) VALUES
(4, 'beauadmin', '$2a$11$cQ/vreaYEpU1hJ9RcVcvh.hmA0.7cOlF17SwAikcLRw7tMKq059AG');

-- --------------------------------------------------------

--
-- Table structure for table `user_role`
--

CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_role`
--

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
(4, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `customer_id_uindex` (`id`);

--
-- Indexes for table `record`
--
ALTER TABLE `record`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `record_id_uindex` (`id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_role`
--
ALTER TABLE `user_role`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `fk_user_role_roleid_idx` (`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;
--
-- AUTO_INCREMENT for table `record`
--
ALTER TABLE `record`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;
--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `record`
--
ALTER TABLE `record`
  ADD CONSTRAINT `record_customer_id_fk` FOREIGN KEY (`id`) REFERENCES `customer` (`id`);

--
-- Constraints for table `user_role`
--
ALTER TABLE `user_role`
  ADD CONSTRAINT `fk_user_role_roleid` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_user_role_userid` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
