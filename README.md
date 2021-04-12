# Spark Clickhouse

抽取Oracle数据写入clickhouse

# clickhouse创建表

```sql
CREATE TABLE kkx_sbd.jkxl ( `id` String, `remark` Nullable(String), `jcdm` String, `jcmc` String, `yxremark` Nullable(String), zyqssj Nullable(DateTime), zyqbysj Nullable(Float32), zyzzsj Nullable(DateTime), zycxsj Nullable(Float32), zzsj Nullable(DateTime) ) ENGINE = MergeTree PRIMARY KEY id ORDER BY (id, jcdm, jcmc)
```

其中order by字段是所有需要参与group by条件的字段

# 注意点
- clickhouse默认没有打开账号密码验证，所以连接clickhouse的properties不需要设置user、password参数
- 默认连接clickhouse的8123 Http端口
- 当前使用Append写入，所以在spark-submit之前需要先清空表

# Spark-submit提交脚本

```shell
#!/bin/bash

echo "start spark-submit."
/usr/hdp/current/spark2-client/bin/spark-submit \
             --packages org.apache.commons:commons-lang3:3.5,ru.yandex.clickhouse:clickhouse-jdbc:0.2.6,com.oracle.database.jdbc:ojdbc8:12.2.0.1 \
             --jars /usr/hdp/current/hive_warehouse_connector/hive-warehouse-connector-assembly-1.0.0.3.1.4.0-315.jar \
             --conf spark.hadoop.hive.llap.daemon.service.hosts=@llap0 \
             --conf spark.sql.hive.hiveserver2.jdbc.url="jdbc:hive2://cdh-m1:2181,cdh-n1:2181,cdh-n2:2181/;password=hdfs;serviceDiscoveryMode=zooKeeper;user=hdfs;zooKeeperNamespace=hiveserver2-interactive" \
             --conf spark.security.credentials.hiveserver2.enabled=false \
             --conf spark.datasource.hive.warehouse.load.staging.dir=/tmp \
             --conf spark.datasource.hive.warehouse.metastoreUri=thrift://cdh-n2:9083 \
             --conf spark.sql.sources.partitionOverwriteMode=static \
             --num-executors 6 \
             --executor-cores 2 \
             --driver-memory 1g \
             --executor-memory 784m \
             --class ScalaDemo \
             --master yarn /usr/hdp/current/test-script/spark-clickhouse-1.0.0.RELEASE.jar "args1-test"
```

