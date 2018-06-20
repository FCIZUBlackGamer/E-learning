
-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 30, 2018 at 03:35 PM
-- Server version: 10.1.31-MariaDB
-- PHP Version: 5.2.17

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `u860954415_learn`
--

-- --------------------------------------------------------

--
-- Table structure for table `english_assay`
--

CREATE TABLE IF NOT EXISTS `english_assay` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quuestion` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `keywords` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

--
-- Dumping data for table `english_assay`
--

INSERT INTO `english_assay` (`id`, `quuestion`, `keywords`) VALUES
(1, 'How Am I?', 'Well'),
(2, 'Test Qustion', 'First Line\nSecond Line');

-- --------------------------------------------------------

--
-- Table structure for table `english_choice`
--

CREATE TABLE IF NOT EXISTS `english_choice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quuestion` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `answer1` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `answer2` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `answer3` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `answer4` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=2 ;

--
-- Dumping data for table `english_choice`
--

INSERT INTO `english_choice` (`id`, `quuestion`, `answer1`, `answer2`, `answer3`, `answer4`) VALUES
(1, 'How Are You?', 'Fine', 'Well', 'Good', 'Bad');

-- --------------------------------------------------------

--
-- Table structure for table `english_tf`
--

CREATE TABLE IF NOT EXISTS `english_tf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quuestion` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `answer` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

--
-- Dumping data for table `english_tf`
--

INSERT INTO `english_tf` (`id`, `quuestion`, `answer`) VALUES
(1, 'How Are You', 'True'),
(2, 'vzhdg', 'True');

-- --------------------------------------------------------

--
-- Table structure for table `introduction_to_computer_assay`
--

CREATE TABLE IF NOT EXISTS `introduction_to_computer_assay` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quuestion` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `keywords` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `introduction_to_computer_choice`
--

CREATE TABLE IF NOT EXISTS `introduction_to_computer_choice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quuestion` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `answer1` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `answer2` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `answer3` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `answer4` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `introduction_to_computer_tf`
--

CREATE TABLE IF NOT EXISTS `introduction_to_computer_tf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quuestion` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `answer` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

--
-- Dumping data for table `introduction_to_computer_tf`
--

INSERT INTO `introduction_to_computer_tf` (`id`, `quuestion`, `answer`) VALUES
(1, 'Computer?', 'True'),
(2, 'what is \n\n\nFri is \n\n\nFri is.  interesting', 'True');

-- --------------------------------------------------------

--
-- Table structure for table `mathematics_assay`
--

CREATE TABLE IF NOT EXISTS `mathematics_assay` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quuestion` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `keywords` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `mathematics_choice`
--

CREATE TABLE IF NOT EXISTS `mathematics_choice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quuestion` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `answer1` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `answer2` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `answer3` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `answer4` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `mathematics_tf`
--

CREATE TABLE IF NOT EXISTS `mathematics_tf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quuestion` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `answer` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=2 ;

--
-- Dumping data for table `mathematics_tf`
--

INSERT INTO `mathematics_tf` (`id`, `quuestion`, `answer`) VALUES
(1, 'غااىةﻻﻻررلللببب', 'True');

-- --------------------------------------------------------

--
-- Table structure for table `organizational_behavior_assay`
--

CREATE TABLE IF NOT EXISTS `organizational_behavior_assay` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quuestion` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `keywords` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=7 ;

--
-- Dumping data for table `organizational_behavior_assay`
--

INSERT INTO `organizational_behavior_assay` (`id`, `quuestion`, `keywords`) VALUES
(1, 'How Am I?', 'Well'),
(2, 'how are you?', 'fine'),
(3, 'are', 'yes'),
(4, 'ىىى', 'تتىى'),
(5, 'ىىى', 'تتىى'),
(6, 'bggggggg', 'vvgbbb');

-- --------------------------------------------------------

--
-- Table structure for table `organizational_behavior_choice`
--

CREATE TABLE IF NOT EXISTS `organizational_behavior_choice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quuestion` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `answer1` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `answer2` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `answer3` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `answer4` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=8 ;

--
-- Dumping data for table `organizational_behavior_choice`
--

INSERT INTO `organizational_behavior_choice` (`id`, `quuestion`, `answer1`, `answer2`, `answer3`, `answer4`) VALUES
(1, 'are', 'you', 'no', 'yes', 'lol'),
(2, 'Your Name is?', 'Mama <3', 'om Galambo', 'om 44', 'on el-masreen'),
(3, 'hfgdgd', 'gdhdh', 'hogshead', 'gaffs', 'highs'),
(4, 'what is html?', 'hyper ', 'a', 'b', 'c'),
(5, 'what is html?', 'hyper ', 'a', 'b', 'c'),
(6, 'what', 'is', 'the', 'the', 'then'),
(7, 'ggv', 'gggv', 'fg', 'gg', 'gg');

-- --------------------------------------------------------

--
-- Table structure for table `organizational_behavior_tf`
--

CREATE TABLE IF NOT EXISTS `organizational_behavior_tf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quuestion` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `answer` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=22 ;

--
-- Dumping data for table `organizational_behavior_tf`
--

INSERT INTO `organizational_behavior_tf` (`id`, `quuestion`, `answer`) VALUES
(1, 'How Are You?', 'False'),
(2, 'are', 'True'),
(3, 'CCTV', 'False'),
(4, 'fggggb', 'True'),
(5, 'ggvggv', 'True'),
(6, 'physics ', 'True'),
(7, 'هل مصر كويسه', 'True'),
(8, 'بحبك يامصؤ', 'False'),
(9, 'بحبك يامصؤلببييييببيي', 'False'),
(10, 'بحبك يامصؤلببييييببييييييبييييييييييييييي', 'False'),
(11, 'بحبك يامصؤلببييييببييييييبيييييييييييييييكمخغثيهعبصقنخعاببفاغغغعع', 'False'),
(12, 'بحبك يامصؤلببييييببييييييبيييييييييييييييكمخغثيهعبصقنخعاببفاغغغعع', 'False'),
(13, 'لبيفغغغغغغ', 'True'),
(14, 'تالاتبباا', 'True'),
(15, '', 'True'),
(16, 'gggghh', 'True'),
(17, 'bvvvvvb', 'True'),
(18, 'ufhdhd', 'True'),
(19, 'dudhdhf', 'True'),
(20, 'jfjdjd', 'True'),
(21, 'hhhjjnmm', 'True');

-- --------------------------------------------------------

--
-- Table structure for table `physics_assay`
--

CREATE TABLE IF NOT EXISTS `physics_assay` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quuestion` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `keywords` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `physics_choice`
--

CREATE TABLE IF NOT EXISTS `physics_choice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quuestion` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `answer1` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `answer2` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `answer3` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `answer4` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `physics_tf`
--

CREATE TABLE IF NOT EXISTS `physics_tf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quuestion` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `answer` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

--
-- Dumping data for table `physics_tf`
--

INSERT INTO `physics_tf` (`id`, `quuestion`, `answer`) VALUES
(1, 'Is Physics easy? ', 'True'),
(2, 'Are We Doing Great? ', 'False');

-- --------------------------------------------------------

--
-- Table structure for table `posts`
--

CREATE TABLE IF NOT EXISTS `posts` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `post_text` varchar(1000) COLLATE utf8_unicode_ci NOT NULL,
  `subject` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=30 ;

--
-- Dumping data for table `posts`
--

INSERT INTO `posts` (`id`, `time`, `post_text`, `subject`) VALUES
(1, '2018-02-25 23:47:20', 'Whatsapp Guys!', 'Organizational Behavior'),
(2, '2018-02-27 18:33:03', 'Hello From Electronics!', 'Physics'),
(3, '2018-02-28 11:02:12', 'hdjgdv', 'Organizational Behavior'),
(4, '2018-02-28 12:20:20', 'Ahmed', 'Organizational Behavior'),
(5, '2018-02-28 12:20:46', 'you', 'English'),
(6, '2018-02-28 12:20:50', 'you', 'English'),
(7, '2018-02-28 12:21:09', 'high', 'English'),
(8, '2018-02-28 12:21:10', 'high', 'English'),
(9, '2018-02-28 12:21:11', 'high', 'English'),
(10, '2018-02-28 12:40:23', 'vbvvvvv', 'Organizational Behavior'),
(11, '2018-02-28 12:41:00', 'nbhjhh', 'Organizational Behavior'),
(12, '2018-02-28 12:41:24', 'CCTV', 'English'),
(13, '2018-02-28 12:41:27', 'CCTV', 'English'),
(14, '2018-03-02 14:40:23', 'Ahmed', 'Introduction to Computer'),
(15, '2018-03-02 14:40:48', 'Mohamed', 'Introduction to Computer'),
(16, '2018-04-01 12:29:26', 'hdfhhhfgcfhjjvffffgbbcccfccfddssssdssddddddddddvgggggg', 'Organizational Behavior'),
(17, '2018-04-01 14:05:30', 'fddfyddhfdghd', 'English'),
(18, '2018-04-01 20:30:49', 'ليفغغبييبللغ', 'English'),
(19, '2018-04-10 07:08:44', 'الحمد لله', 'Organizational Behavior'),
(20, '2018-04-23 18:13:42', 'ىىﻻ', 'Organizational Behavior'),
(21, '2018-04-23 18:13:42', 'ىىﻻ', 'Organizational Behavior'),
(22, '2018-04-23 18:13:43', 'ىىﻻ', 'Organizational Behavior'),
(23, '2018-04-23 18:14:12', 'fffgshsh', 'Organizational Behavior'),
(24, '2018-04-28 04:27:26', 'Hello Guys tomorrow is a new day!', 'Organizational Behavior'),
(25, '2018-04-28 10:08:41', 'كلمغمفخف', 'Organizational Behavior'),
(26, '2018-04-28 10:08:44', 'كلمغمفخف', 'Organizational Behavior'),
(27, '2018-04-28 10:13:40', 'مضلسىيةيتي', 'Organizational Behavior'),
(28, '2018-04-28 10:16:40', 'gfffhg', 'Organizational Behavior'),
(29, '2018-04-28 10:19:24', 'تاللللا', 'Organizational Behavior');

-- --------------------------------------------------------

--
-- Table structure for table `statistics_assay`
--

CREATE TABLE IF NOT EXISTS `statistics_assay` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quuestion` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `keywords` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `statistics_choice`
--

CREATE TABLE IF NOT EXISTS `statistics_choice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quuestion` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `answer1` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `answer2` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `answer3` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `answer4` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `statistics_tf`
--

CREATE TABLE IF NOT EXISTS `statistics_tf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quuestion` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `answer` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_uploads`
--

CREATE TABLE IF NOT EXISTS `tbl_uploads` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `file` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `subject` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=9 ;

--
-- Dumping data for table `tbl_uploads`
--

INSERT INTO `tbl_uploads` (`id`, `time`, `file`, `subject`) VALUES
(8, '2018-02-27 18:33:53', 'http://momenshaheen.16mb.com/e_learning/uploads/33233-img_20180126_232423.jpg', 'Physics'),
(7, '2018-02-26 15:35:35', 'http://momenshaheen.16mb.com/e_learning/uploads/33233-img_20180126_232423.jpg', 'English');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
