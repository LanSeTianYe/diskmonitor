## diskmonitor (磁盘文件统计工具)  

### 为什么要开发

C盘空间总是莫名奇妙的变少，通过各种软件很难找出哪些文件夹变大了，因此写了和这个工具读取磁盘文件，把每次读取的文件数据放进数据库中，然后分析哪些文件变大了，哪些文件夹数据太多，哪些可以删除，保证C盘空间可用。

### 功能列表  
* 快速读取磁盘文件信息（文件名，文件大小等）存入数据库，目前通过一些SQL可以简单的查询磁盘文件变化（开发完成）。
* 分析数据，分析目录和文件大小变化情况（开发中。。。）。

### 怎么使用

1. 数据库初始化，MySQL脚本参考项目 `doc` 目录。
2. 修改项目配置文件 `application.properties`

	    spring.datasource.url=jdbc:mysql://hostname:port/disk_monitor?useUnicode=true&characterEncoding=UTF-8
	    spring.datasource.username=username
	    spring.datasource.password=passeord
3. 项目基于SpringBoort，使用  `IDEA` 可直接启动项目，启动成功之后会把磁盘文件数据保存到数据库，读取完成之后项目会自动停止。

### 目前分析文件数据的方法，使用SQL 

#### 文件总数量

	  SELECT COUNT(*) FROM dm_file_size;
#### 查找最近一次更新的文件数量
 
	SELECT count(*)
	FROM dm_file_size
	WHERE record_date = (SELECT dm_file_record_date.record_date
	                   FROM dm_file_record_date
	                   ORDER BY record_date DESC
	                   LIMIT 1);
#### 查找C盘前100大的文件和文件夹

	SELECT *
	FROM dm_file_size
	WHERE record_date = (SELECT dm_file_record_date.record_date
	                   FROM dm_file_record_date
	                   ORDER BY record_date DESC
	                   LIMIT 1) AND file_absolute_path LIKE 'C%'
	ORDER BY file_size DESC
	LIMIT 100;

