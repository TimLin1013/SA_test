-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- 主機： 127.0.0.1
-- 產生時間： 2023-12-28 02:39:02
-- 伺服器版本： 10.4.28-MariaDB
-- PHP 版本： 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 資料庫： `sa`
--

-- --------------------------------------------------------

--
-- 資料表結構 `tbl_announcement`
--

CREATE TABLE `tbl_announcement` (
  `announcement_id` int(11) NOT NULL,
  `announcement_content` varchar(2000) NOT NULL,
  `announcement_time` datetime NOT NULL,
  `member_id` int(11) NOT NULL,
  `announcement_title` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- 傾印資料表的資料 `tbl_announcement`
--

INSERT INTO `tbl_announcement` (`announcement_id`, `announcement_content`, `announcement_time`, `member_id`, `announcement_title`) VALUES
(26, '公告內文', '2023-12-27 09:45:12', 49, '公告標題'),
(27, 'content', '2023-12-28 01:35:27', 49, 'title');

-- --------------------------------------------------------

--
-- 資料表結構 `tbl_article`
--

CREATE TABLE `tbl_article` (
  `article_id` int(11) NOT NULL,
  `title` varchar(50) NOT NULL,
  `article_content` varchar(1000) NOT NULL,
  `article_time` datetime NOT NULL,
  `member_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- 資料表結構 `tbl_borrow_record`
--

CREATE TABLE `tbl_borrow_record` (
  `borrow_record_id` int(11) NOT NULL,
  `borrow_time` datetime NOT NULL,
  `return_time` datetime DEFAULT NULL,
  `instrument_id` int(11) NOT NULL,
  `member_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- 傾印資料表的資料 `tbl_borrow_record`
--

INSERT INTO `tbl_borrow_record` (`borrow_record_id`, `borrow_time`, `return_time`, `instrument_id`, `member_id`) VALUES
(62, '2023-12-27 11:48:00', '2023-12-27 17:32:17', 5, 47);

-- --------------------------------------------------------

--
-- 資料表結構 `tbl_course`
--

CREATE TABLE `tbl_course` (
  `course_id` int(11) NOT NULL,
  `course_name` varchar(50) NOT NULL,
  `course_start_time` datetime NOT NULL,
  `member_id` int(11) NOT NULL,
  `course_time` varchar(100) NOT NULL,
  `course_location` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- 傾印資料表的資料 `tbl_course`
--

INSERT INTO `tbl_course` (`course_id`, `course_name`, `course_start_time`, `member_id`, `course_time`, `course_location`) VALUES
(22, 'B社課', '2024-01-01 10:00:00', 36, '01/01 星期一 10:00 - 01/01 星期一 12:00', '新竹'),
(23, '123123', '2023-12-26 20:36:00', 36, '12/26 星期二 20:36 - 12/26 星期二 22:36', '123123'),
(24, '123123', '2023-12-26 20:37:00', 36, '12/26 星期二 20:37 - 12/26 星期二 22:37', '123123'),
(25, '24234324', '2023-12-26 20:41:00', 36, '12/26 星期二 20:41 - 12/26 星期二 22:41', '234234234'),
(26, '2342342', '2023-12-26 20:42:00', 36, '12/26 星期二 20:42 - 12/26 星期二 22:42', '234234'),
(27, '1231231', '2023-12-26 20:43:00', 36, '12/26 星期二 20:43 - 12/26 星期二 22:43', '1212312'),
(28, '12312313', '2023-12-26 20:47:00', 36, '12/26 星期二 20:47 - 12/26 星期二 22:47', '1231231'),
(29, '123123', '2023-12-26 20:48:00', 36, '12/26 星期二 20:48 - 12/26 星期二 22:48', '123123'),
(30, 'A社課', '2023-12-28 10:00:00', 36, '12/28 星期四 10:00 - 12/28 星期四 12:00', '台灣');

-- --------------------------------------------------------

--
-- 資料表結構 `tbl_course_registration_record`
--

CREATE TABLE `tbl_course_registration_record` (
  `course_registration_record_id` int(11) NOT NULL,
  `member_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- 傾印資料表的資料 `tbl_course_registration_record`
--

INSERT INTO `tbl_course_registration_record` (`course_registration_record_id`, `member_id`, `course_id`) VALUES
(322, 47, 22);

-- --------------------------------------------------------

--
-- 資料表結構 `tbl_course_value`
--

CREATE TABLE `tbl_course_value` (
  `member_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `course_value` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- 資料表結構 `tbl_homework`
--

CREATE TABLE `tbl_homework` (
  `content` varchar(2000) NOT NULL,
  `score` int(11) NOT NULL,
  `homework_time` datetime NOT NULL,
  `member_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- 資料表結構 `tbl_instrument`
--

CREATE TABLE `tbl_instrument` (
  `instrument_id` int(11) NOT NULL,
  `instrument_name` varchar(50) NOT NULL,
  `instrument_quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- 傾印資料表的資料 `tbl_instrument`
--

INSERT INTO `tbl_instrument` (`instrument_id`, `instrument_name`, `instrument_quantity`) VALUES
(1, 'Flute', 0),
(2, 'Piccolo', 0),
(3, 'Clarinet', 0),
(4, 'Oboe', 2),
(5, 'Bassoon', 2),
(6, 'Saxophone', 1),
(7, 'Trumpet', 5),
(8, 'Trombone', 12),
(9, 'Horn', 2),
(10, 'Piano', 1),
(11, 'Double_Bass', 1);

-- --------------------------------------------------------

--
-- 資料表結構 `tbl_member`
--

CREATE TABLE `tbl_member` (
  `member_id` int(11) NOT NULL,
  `member_name` varchar(50) NOT NULL,
  `member_account` varchar(100) NOT NULL,
  `member_password` varchar(45) NOT NULL,
  `member_phone` varchar(10) NOT NULL,
  `member_group` varchar(45) NOT NULL,
  `created_time` datetime NOT NULL,
  `identity` varchar(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- 傾印資料表的資料 `tbl_member`
--

INSERT INTO `tbl_member` (`member_id`, `member_name`, `member_account`, `member_password`, `member_phone`, `member_group`, `created_time`, `identity`) VALUES
(36, '老師', 'teacher@gmail.com', '11111111a', '0903694286', '無', '2023-12-26 06:26:35', '3'),
(47, '林冠廷', '1125899906842624abc@gmail.com', '11111111a', '0903694286', 'triangle', '2023-12-27 03:41:05', '1'),
(48, 'tim _lin', '11258999016842624abc@gmail.com', '11111111a', '1234567899', '無', '2023-12-27 09:16:27', '3'),
(49, 'tim _lin', '2@gmail.com', '11111111a', '123123', '無', '2023-12-27 09:34:24', '2');

-- --------------------------------------------------------

--
-- 資料表結構 `tbl_message`
--

CREATE TABLE `tbl_message` (
  `message_id` int(11) NOT NULL,
  `message_content` varchar(1000) NOT NULL,
  `message_time` datetime NOT NULL,
  `article_id` int(11) NOT NULL,
  `member_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- 資料表結構 `tbl_paid_record`
--

CREATE TABLE `tbl_paid_record` (
  `paid_sequence` int(16) NOT NULL,
  `paid_time` datetime NOT NULL,
  `paid_fee` int(11) NOT NULL,
  `member_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- 已傾印資料表的索引
--

--
-- 資料表索引 `tbl_announcement`
--
ALTER TABLE `tbl_announcement`
  ADD PRIMARY KEY (`announcement_id`,`member_id`),
  ADD KEY `member_announcement_fk_idx` (`member_id`);

--
-- 資料表索引 `tbl_article`
--
ALTER TABLE `tbl_article`
  ADD PRIMARY KEY (`article_id`,`member_id`),
  ADD KEY `member_article_fk_idx` (`member_id`);

--
-- 資料表索引 `tbl_borrow_record`
--
ALTER TABLE `tbl_borrow_record`
  ADD PRIMARY KEY (`borrow_record_id`,`member_id`,`instrument_id`),
  ADD KEY `member_borrow_record_fk_idx` (`member_id`),
  ADD KEY `borrow_record_instrument_fk_idx` (`instrument_id`);

--
-- 資料表索引 `tbl_course`
--
ALTER TABLE `tbl_course`
  ADD PRIMARY KEY (`course_id`,`member_id`),
  ADD KEY `member_course_fk_idx` (`member_id`);

--
-- 資料表索引 `tbl_course_registration_record`
--
ALTER TABLE `tbl_course_registration_record`
  ADD PRIMARY KEY (`course_registration_record_id`,`member_id`,`course_id`),
  ADD KEY `member_course_registratino_record_fk_idx` (`member_id`),
  ADD KEY `course_id_course_registration_record_FK_idx` (`course_id`);

--
-- 資料表索引 `tbl_course_value`
--
ALTER TABLE `tbl_course_value`
  ADD PRIMARY KEY (`member_id`,`course_id`),
  ADD KEY `course_course_value_fk_idx` (`course_id`),
  ADD KEY `member_id_course_value_fk` (`member_id`);

--
-- 資料表索引 `tbl_homework`
--
ALTER TABLE `tbl_homework`
  ADD PRIMARY KEY (`course_id`,`member_id`) USING BTREE,
  ADD KEY `idx_member_homework_member_fk` (`member_id`),
  ADD KEY `idx_member_homework_course_fk` (`course_id`);

--
-- 資料表索引 `tbl_instrument`
--
ALTER TABLE `tbl_instrument`
  ADD PRIMARY KEY (`instrument_id`);

--
-- 資料表索引 `tbl_member`
--
ALTER TABLE `tbl_member`
  ADD PRIMARY KEY (`member_id`);

--
-- 資料表索引 `tbl_message`
--
ALTER TABLE `tbl_message`
  ADD PRIMARY KEY (`message_id`,`member_id`,`article_id`),
  ADD KEY `member_message_fk_idx` (`member_id`),
  ADD KEY `article_message_fk_idx` (`article_id`);

--
-- 資料表索引 `tbl_paid_record`
--
ALTER TABLE `tbl_paid_record`
  ADD PRIMARY KEY (`paid_sequence`,`member_id`),
  ADD KEY `member_paid_record_fk_idx` (`member_id`);

--
-- 在傾印的資料表使用自動遞增(AUTO_INCREMENT)
--

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `tbl_announcement`
--
ALTER TABLE `tbl_announcement`
  MODIFY `announcement_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `tbl_article`
--
ALTER TABLE `tbl_article`
  MODIFY `article_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `tbl_borrow_record`
--
ALTER TABLE `tbl_borrow_record`
  MODIFY `borrow_record_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `tbl_course`
--
ALTER TABLE `tbl_course`
  MODIFY `course_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `tbl_course_registration_record`
--
ALTER TABLE `tbl_course_registration_record`
  MODIFY `course_registration_record_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=323;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `tbl_instrument`
--
ALTER TABLE `tbl_instrument`
  MODIFY `instrument_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `tbl_member`
--
ALTER TABLE `tbl_member`
  MODIFY `member_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `tbl_message`
--
ALTER TABLE `tbl_message`
  MODIFY `message_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- 已傾印資料表的限制式
--

--
-- 資料表的限制式 `tbl_announcement`
--
ALTER TABLE `tbl_announcement`
  ADD CONSTRAINT `member_announcement_fk` FOREIGN KEY (`member_id`) REFERENCES `tbl_member` (`member_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 資料表的限制式 `tbl_article`
--
ALTER TABLE `tbl_article`
  ADD CONSTRAINT `member_article_fk` FOREIGN KEY (`member_id`) REFERENCES `tbl_member` (`member_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 資料表的限制式 `tbl_borrow_record`
--
ALTER TABLE `tbl_borrow_record`
  ADD CONSTRAINT `borrow_record_instrument_fk` FOREIGN KEY (`instrument_id`) REFERENCES `tbl_instrument` (`instrument_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `member_borrow_record_fk` FOREIGN KEY (`member_id`) REFERENCES `tbl_member` (`member_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 資料表的限制式 `tbl_course`
--
ALTER TABLE `tbl_course`
  ADD CONSTRAINT `member_course_fk` FOREIGN KEY (`member_id`) REFERENCES `tbl_member` (`member_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 資料表的限制式 `tbl_course_registration_record`
--
ALTER TABLE `tbl_course_registration_record`
  ADD CONSTRAINT `course_id_course_registration_record_FK` FOREIGN KEY (`course_id`) REFERENCES `tbl_course` (`course_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `member_course_registratino_record_fk` FOREIGN KEY (`member_id`) REFERENCES `tbl_member` (`member_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 資料表的限制式 `tbl_course_value`
--
ALTER TABLE `tbl_course_value`
  ADD CONSTRAINT `course_course_value_fk` FOREIGN KEY (`course_id`) REFERENCES `tbl_course` (`course_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `member_course_value_fk` FOREIGN KEY (`member_id`) REFERENCES `tbl_member` (`member_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 資料表的限制式 `tbl_homework`
--
ALTER TABLE `tbl_homework`
  ADD CONSTRAINT `fk_tbl_homework_course` FOREIGN KEY (`course_id`) REFERENCES `tbl_course` (`course_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_tbl_homework_member` FOREIGN KEY (`member_id`) REFERENCES `tbl_member` (`member_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 資料表的限制式 `tbl_message`
--
ALTER TABLE `tbl_message`
  ADD CONSTRAINT `article_message_fk` FOREIGN KEY (`article_id`) REFERENCES `tbl_article` (`article_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `member_message_fk` FOREIGN KEY (`member_id`) REFERENCES `tbl_member` (`member_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 資料表的限制式 `tbl_paid_record`
--
ALTER TABLE `tbl_paid_record`
  ADD CONSTRAINT `member_paid_record_fk` FOREIGN KEY (`member_id`) REFERENCES `tbl_member` (`member_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
