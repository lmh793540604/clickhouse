import org.apache.spark.internal.Logging
import org.apache.spark.sql.execution.datasources.jdbc.JDBCOptions
import org.apache.spark.sql.{SaveMode, SparkSession}
import java.util.Properties

import org.apache.spark.SparkConf

object ScalaDemo extends Logging {

  /**
   * 主函数
   *
   * @param args
   */
  def main(args: Array[String]): Unit = {

    // 初始化Spark环境

    val sparkConf = new SparkConf().setMaster("local[*]")

    val spark = SparkSession
      .builder()
      .config(sparkConf)
      .appName(getClass.getName)
      .enableHiveSupport()
      .getOrCreate()

    import spark.implicits._

    logInfo("参数示例: " + args(0))

    val oracleJdbcUrl = "jdbc:oracle:thin:@192.168.7.110:1521:orcl"
    val oracleDsProp = new Properties
    oracleDsProp.setProperty("driver", "oracle.jdbc.driver.OracleDriver")
    oracleDsProp.setProperty("user", "KKX_CENTER")
    oracleDsProp.setProperty("password", "KKX_CENTER")

    val chJdbcUrl = "jdbc:clickhouse://192.168.6.250:8123/kkx_sbd"
    val chDsProp = new Properties
    chDsProp.setProperty("driver", "ru.yandex.clickhouse.ClickHouseDriver")
    // 默认没有密码，所以不需要设置user、password属性
    //chDsProp.setProperty("user", "")
    //chDsProp.setProperty("password", "")
    chDsProp.put("batchsize","100000")
    chDsProp.put("socket_timeout","300000")
    chDsProp.put("numPartitions","8")
    chDsProp.put("rewriteBatchedStatements","true")


    /*
    val demoSql =
      """
        |
        |""".stripMargin
     */
    val yxJkxlDf = spark.read.jdbc(oracleJdbcUrl, "YX_JKXL", oracleDsProp).alias("yxjkxl")
    val zcJkxlDf = spark.read.jdbc(oracleJdbcUrl, "ZC_JKXL", oracleDsProp).alias("zcjkxl")

    val jkxlDf = zcJkxlDf.join(yxJkxlDf, $"zcjkxl.id" === $"yxjkxl.zc_id", "inner")
      .selectExpr("zcjkxl.id as id", "zcjkxl.remark as remark", "zcjkxl.jcdm as jcdm", "zcjkxl.jcmc as jcmc",
        "yxjkxl.remark as yxremark", "yxjkxl.zyqssj as zyqssj", "yxjkxl.zyqbysj as zyqbysj",
        "yxjkxl.zyzzsj as zyzzsj", "yxjkxl.zycxsj as zycxsj", "yxjkxl.zzsj as zzsj")

     jkxlDf.show()

    // clickhouse中创建表
    // CREATE TABLE kkx_sbd.jkxl ( `id` String, `remark` Nullable(String), `jcdm` String,
    // `jcmc` String, `yxremark` Nullable(String), zyqssj Nullable(DateTime),
    // zyqbysj Nullable(Float32), zyzzsj Nullable(DateTime), zycxsj Nullable(Float32), zzsj Nullable(DateTime) )
    // ENGINE = MergeTree PRIMARY KEY id ORDER BY (id, jcdm, jcmc)

    jkxlDf.write.mode(SaveMode.Append)
      .option(JDBCOptions.JDBC_BATCH_INSERT_SIZE, 100000)
      .jdbc(chJdbcUrl, "jkxl", chDsProp)

    spark.stop()
  }
}