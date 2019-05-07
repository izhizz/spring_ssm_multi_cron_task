**1、持久花数据库必备表**
查看必要的定时器sql 语句文件 ：查看 dbInfo.md
<br>
**2、配置文件必须填写配置信息**
application-quartz.xml中如果配置了以下信息<br>
   <property name="dataSource" ref ="dataSourceQuartz" />
   quartz 会自动去数据库中查询
    <property name="startupDelay" value="60"/> 
    这个配置信息是等待项目启动完成在执行定时器，如果项目时间长应当配置时间长一些
    <property name="overwriteExistingJobs" value="true" />
    需要overwrite已经存在的job，如果需要动态的修改已经存在的job，就需要设置为true，否则会以数据库中已经存在的为准
    <property name="autoStartup" value="true" />
    是否自动启动，这个比较好理解