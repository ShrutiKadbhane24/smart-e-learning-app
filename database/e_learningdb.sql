-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 08, 2024 at 10:11 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `e_learningdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `class`
--

CREATE TABLE `class` (
  `id` int(11) NOT NULL,
  `class_name` varchar(256) NOT NULL,
  `subject_name` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `class`
--

INSERT INTO `class` (`id`, `class_name`, `subject_name`) VALUES
(1, 'MAD Batch', 'Mobile Application Development'),
(2, 'Python Batch', 'Python Programming'),
(3, 'NIS Batch', 'NIS'),
(4, 'sample', 'sample');

-- --------------------------------------------------------

--
-- Table structure for table `login_admin`
--

CREATE TABLE `login_admin` (
  `id` int(11) NOT NULL,
  `username` varchar(256) NOT NULL,
  `password` varchar(256) NOT NULL,
  `userrole` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `login_admin`
--

INSERT INTO `login_admin` (`id`, `username`, `password`, `userrole`) VALUES
(1, 'admin', 'admin', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `notice_board`
--

CREATE TABLE `notice_board` (
  `id` int(11) NOT NULL,
  `class_id` int(11) NOT NULL,
  `title` varchar(256) NOT NULL,
  `description` varchar(256) NOT NULL,
  `doc` varchar(256) NOT NULL,
  `date` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `notice_board`
--

INSERT INTO `notice_board` (`id`, `class_id`, `title`, `description`, `doc`, `date`) VALUES
(1, 1, 'abcd', 'xyz', 'abcd.pdf', ''),
(2, 1, 'samplet', 'sampled', '', '2024-03-05'),
(3, 1, 'asdasd', 'asdasd', '685087.pdf', '2024-03-05'),
(4, 1, 'ivig8g', 'gug7g', '', '2024-03-12'),
(5, 1, 'evwce', 'evevvwv', '', '2024-03-12'),
(6, 1, 'eveevwg', 'wvsev', '', '2024-03-12'),
(7, 1, 'dekddk', 'rrkrrkrk', '', '2024-03-12');

-- --------------------------------------------------------

--
-- Table structure for table `question_and_answer_paper`
--

CREATE TABLE `question_and_answer_paper` (
  `id` int(11) NOT NULL,
  `branch` varchar(256) NOT NULL,
  `semester` varchar(256) NOT NULL,
  `exam` varchar(256) NOT NULL,
  `title` varchar(256) NOT NULL,
  `doc` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `question_and_answer_paper`
--

INSERT INTO `question_and_answer_paper` (`id`, `branch`, `semester`, `exam`, `title`, `doc`) VALUES
(1, 'Computer Engineering', 'First Sem', 'Winter 2023', 'Winter Exam 2023 Question Paper', 'abcd.pdf'),
(2, 'testt', 'testd', 'exame', 'titlet', ''),
(3, 'Computer Engineering', 'Sixth Sem', 'Winter 2023', 'Winter 2023 Question Paper ', '944212.pdf'),
(4, 'Computer Engineering', 'Sixth Sem', 'Winter 2023', 'Winter 2023', '');

-- --------------------------------------------------------

--
-- Table structure for table `register_student`
--

CREATE TABLE `register_student` (
  `id` int(11) NOT NULL,
  `image` varchar(256) NOT NULL,
  `name` varchar(256) NOT NULL,
  `mobile_no` varchar(256) NOT NULL,
  `email_id` varchar(256) NOT NULL,
  `age` varchar(256) NOT NULL,
  `address` varchar(256) NOT NULL,
  `username` varchar(256) NOT NULL,
  `password` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `register_student`
--

INSERT INTO `register_student` (`id`, `image`, `name`, `mobile_no`, `email_id`, `age`, `address`, `username`, `password`) VALUES
(1, '1712605762069.png', 'Nikhil Shende ', '8208187488', 'nikdroid23@gmail.com', '28', 'address', 'asdasd', 'asdasd'),
(7, '1709408224332.png', 'sbdvd', '787878', 'Vaca', '49494', 'zvsvs', 'zvsv', 'zvsvs');

-- --------------------------------------------------------

--
-- Table structure for table `study_material`
--

CREATE TABLE `study_material` (
  `id` int(11) NOT NULL,
  `class_id` int(11) NOT NULL,
  `title` varchar(256) NOT NULL,
  `description` varchar(256) NOT NULL,
  `doc` varchar(256) NOT NULL,
  `date` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `study_material`
--

INSERT INTO `study_material` (`id`, `class_id`, `title`, `description`, `doc`, `date`) VALUES
(1, 1, 'sample study materials title', 'sample study materials desc', 'abcd.pdf', '03-03-2024'),
(2, 1, 'sample study materials title', 'sample study materials desc', 'Mountreach Solution Pvt Ltd Workshop on Android Development.pdf', '03-03-2024'),
(8, 1, 'azazaz', 'azazaz', 'qaz.pdf.pdf', '2024-03-05'),
(9, 1, 'qqq', 'qwqw', '114407.pdf', '2024-03-05'),
(10, 1, 'ppp', 'pppp', '479931.pdf', '2024-03-05'),
(11, 1, 'hhjj', 'jjjjjjj', '', '2024-03-12'),
(12, 1, 'ihiih8', 'vivigi8g', '', '2024-03-12'),
(13, 1, 'sksssk', 'sksssksk', '', '2024-03-12');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_homework`
--

CREATE TABLE `tbl_homework` (
  `id` int(11) NOT NULL,
  `class_id` int(11) NOT NULL,
  `date` varchar(256) NOT NULL,
  `title` varchar(256) NOT NULL,
  `description` varchar(256) NOT NULL,
  `doc` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_homework`
--

INSERT INTO `tbl_homework` (`id`, `class_id`, `date`, `title`, `description`, `doc`) VALUES
(1, 1, '2024-03-03', 'Complete Splash Screen Design', 'Design Splash Screen in which contain app logo and app title', ''),
(2, 1, '2024-03-03', 'Login Page', 'Design Login Page in which show one cardview inside username and password', ''),
(5, 1, '2024-03-05', 'abd', 'whewuw', '');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_quiz`
--

CREATE TABLE `tbl_quiz` (
  `id` int(11) NOT NULL,
  `question` varchar(256) NOT NULL,
  `option1` varchar(256) NOT NULL,
  `option2` varchar(256) NOT NULL,
  `option3` varchar(256) NOT NULL,
  `option4` varchar(256) NOT NULL,
  `correct_answer` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_quiz`
--

INSERT INTO `tbl_quiz` (`id`, `question`, `option1`, `option2`, `option3`, `option4`, `correct_answer`) VALUES
(1, 'What is Android?', 'a', 'b', 'c', 'd', 'b'),
(2, 'What is Web?', 'q', 'r', 's', 't', 's');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_recording`
--

CREATE TABLE `tbl_recording` (
  `id` int(11) NOT NULL,
  `class_id` int(11) NOT NULL,
  `date` varchar(256) NOT NULL,
  `title` varchar(256) NOT NULL,
  `description` varchar(256) NOT NULL,
  `url` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_recording`
--

INSERT INTO `tbl_recording` (`id`, `class_id`, `date`, `title`, `description`, `url`) VALUES
(1, 1, '03-03-2024', 'Mountreach Solution Pvt. Ltd. ||1\'ST Anniversary Celebration\r\n', 'Mountreach Solution PVT LTD.\r\n1 Year of Family', 'https://youtu.be/EjYL_89YXSA?si=Eihs3bdhhldVnSuV'),
(2, 2, '2024-03-05', 'testt', 'textd', 'https://www.youtube.com/watch?v=EjYL_89YXSA&t=2s'),
(3, 0, '2024-03-05', 'Mountreach ', 'Mountreach ', 'https://youtube.com/playlist?list=PLUMVNamS97nlR742-JVQJyJUh2winkUk9&si=8xYrg3o8h_-wiSkE'),
(4, 1, '2024-03-05', 'Mountreach Solution ', 'Mountreach Solution Pvt Ltd ', 'https://youtube.com/playlist?list=PLUMVNamS97nlR742-JVQJyJUh2winkUk9&si=8xYrg3o8h_-wiSkE');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `class`
--
ALTER TABLE `class`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `login_admin`
--
ALTER TABLE `login_admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `notice_board`
--
ALTER TABLE `notice_board`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `question_and_answer_paper`
--
ALTER TABLE `question_and_answer_paper`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `register_student`
--
ALTER TABLE `register_student`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `study_material`
--
ALTER TABLE `study_material`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_homework`
--
ALTER TABLE `tbl_homework`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_quiz`
--
ALTER TABLE `tbl_quiz`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_recording`
--
ALTER TABLE `tbl_recording`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `class`
--
ALTER TABLE `class`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `login_admin`
--
ALTER TABLE `login_admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `notice_board`
--
ALTER TABLE `notice_board`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `question_and_answer_paper`
--
ALTER TABLE `question_and_answer_paper`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `register_student`
--
ALTER TABLE `register_student`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `study_material`
--
ALTER TABLE `study_material`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `tbl_homework`
--
ALTER TABLE `tbl_homework`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tbl_quiz`
--
ALTER TABLE `tbl_quiz`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tbl_recording`
--
ALTER TABLE `tbl_recording`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
