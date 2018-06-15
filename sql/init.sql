-- 创建数据库
CREATE DATABASE IF NOT EXISTS disk_monitor default character set utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 文件基本信息
DROP TABLE file_base_info;

CREATE TABLE `file_base_info` (
  `file_id` VARCHAR(500) COLLATE utf8mb4_unicode_ci NOT NULL,
  `file_name` VARCHAR(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `file_path` VARCHAR(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `file_type` VARCHAR(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_directory` bit(1) DEFAULT NULL,
  `record_date` datetime DEFAULT NULL,
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE file_size;
-- 文件大小表
CREATE TABLE `file_size` (
  `record_date` datetime NOT NULL,
  `file_base_info_id` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL,
  `file_size` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`record_date`,`file_base_info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci