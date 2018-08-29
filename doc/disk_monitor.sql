/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.201
Source Server Version : 50640
Source Host           : 192.168.0.201:3306
Source Database       : disk_monitor

Target Server Type    : MYSQL
Target Server Version : 50640
File Encoding         : 65001

Date: 2018-08-29 22:41:38
*/

CREATE database if NOT EXISTS `disk_monitor` default character set utf8mb4 collate utf8mb4_general_ci;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dm_file_count
-- ----------------------------
DROP TABLE IF EXISTS `dm_file_count`;
CREATE TABLE `dm_file_count` (
  `file_count_id` int(11) NOT NULL AUTO_INCREMENT,
  `directory_count` int(11) NOT NULL,
  `file_count` int(11) NOT NULL,
  `record_date` datetime DEFAULT NULL,
  `total_count` int(11) NOT NULL,
  `un_know_file_count` int(11) NOT NULL,
  `actual_count` int(11) NOT NULL,
  PRIMARY KEY (`file_count_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for dm_file_record_date
-- ----------------------------
DROP TABLE IF EXISTS `dm_file_record_date`;
CREATE TABLE `dm_file_record_date` (
  `record_date_id` int(11) NOT NULL AUTO_INCREMENT,
  `end_date` datetime DEFAULT NULL,
  `record_date` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  PRIMARY KEY (`record_date_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for dm_file_size
-- ----------------------------
DROP TABLE IF EXISTS `dm_file_size`;
CREATE TABLE `dm_file_size` (
  `file_size_id` int(11) NOT NULL AUTO_INCREMENT,
  `file_absolute_path` varchar(500) DEFAULT NULL,
  `file_size` bigint(20) NOT NULL,
  `record_date` datetime DEFAULT NULL,
  PRIMARY KEY (`file_size_id`),
  KEY `index_record_date` (`record_date`,`file_size`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;
