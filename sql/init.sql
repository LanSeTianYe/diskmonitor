-- 创建数据库
CREATE DATABASE IF NOT EXISTS disk_monitor default character set utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 文件基本信息
CREATE TABLE `dm_file_base_info` (
  `file_base_info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '文件基础信息ID',
  `file_path` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT ' ' COMMENT '文件路径',
  `file_name` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT ' ' COMMENT '文件名字',
  `file_type` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT ' ' COMMENT '文件类型',
  `is_directory` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否是目录',
  `record_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录日期',
  `file_absolute_path` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT ' ' COMMENT '文件绝对路径',
  PRIMARY KEY (`file_base_info_id`),
  UNIQUE KEY `dm_file_base_info_file_name_uindex` (`file_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文件基本信息表';

-- 文件大小
CREATE TABLE `dm_file_size` (
  `file_size_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `file_base_info_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '文件基础信息id',
  `file_size` int(11) NOT NULL DEFAULT '0' COMMENT '文件大小',
  `record_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录日期',
  PRIMARY KEY (`file_size_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
