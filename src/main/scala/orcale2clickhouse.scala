import java.util.Properties

import ScalaDemo.{getClass, logInfo}
import org.apache.spark.SparkConf
import org.apache.spark.sql.execution.datasources.jdbc.JDBCOptions
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}


object orcale2clickhouse {
  /**
   * 主函数
   *
   * @param args
   */
  def main(args: Array[String]): Unit = {
    // 初始化Spark环境

    val sparkConf = new SparkConf()


    val spark = SparkSession
      .builder()
      .config(sparkConf)
      .appName(getClass.getName)
      .enableHiveSupport()
      .getOrCreate()

    import spark.implicits._
    import spark.sql

    //logInfo("参数示例: " + args(0))

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

    val yxJkxlDf = spark.read.jdbc(oracleJdbcUrl, "YX_JKXL", oracleDsProp)
      .createOrReplaceTempView("YX_JKXL")
    val zcJkxlDf = spark.read.jdbc(oracleJdbcUrl, "ZC_JKXL", oracleDsProp)
      .createOrReplaceTempView("ZC_JKXL")

    val DM_JC = spark.read.jdbc(oracleJdbcUrl, "DM_JC", oracleDsProp)
      .createOrReplaceTempView("DM_JC")
    val DM_XSDW = spark.read.jdbc(oracleJdbcUrl, "DM_XSDW", oracleDsProp)
      .createOrReplaceTempView("DM_XSDW")
    val DM_BDZ = spark.read.jdbc(oracleJdbcUrl, "DM_BDZ", oracleDsProp)
      .createOrReplaceTempView("DM_BDZ")
    val DM_XHGG = spark.read.jdbc(oracleJdbcUrl, "DM_XHGG", oracleDsProp)
      .createOrReplaceTempView("DM_XHGG")
    val DM_QY = spark.read.jdbc(oracleJdbcUrl, "DM_QY", oracleDsProp)
      .createOrReplaceTempView("DM_QY")
    val DM_SJ = spark.read.jdbc(oracleJdbcUrl, "DM_SJ", oracleDsProp)
      .createOrReplaceTempView("DM_SJ")
    val DM_SB = spark.read.jdbc(oracleJdbcUrl, "DM_SB", oracleDsProp)
      .createOrReplaceTempView("DM_SB")

    val DM_JSYY = spark.read.jdbc(oracleJdbcUrl, "DM_JSYY", oracleDsProp)
      .createOrReplaceTempView("DM_JSYY")
    val DM_ZRYY = spark.read.jdbc(oracleJdbcUrl, "DM_ZRYY", oracleDsProp)
      .createOrReplaceTempView("DM_ZRYY")
    val ZC_BYQ = spark.read.jdbc(oracleJdbcUrl, "ZC_BYQ", oracleDsProp)
      .createOrReplaceTempView("ZC_BYQ")
    val yx_byq = spark.read.jdbc(oracleJdbcUrl, "yx_byq", oracleDsProp)
      .createOrReplaceTempView("yx_byq")



    val jkxlDf: DataFrame = spark.sql(
      """
        |SELECT
        |	b1.AZWZDM,
        |	b1.AZWZMC,
        |	b1.BDFL,
        |	b1.BDH,
        |	b1.BDLX,
        |	b1.BDRQ,
        |	b1.BDYY,
        |	b1.BYX,
        |	b1.CCBH,
        |	b1.CCRQ,
        |	b1.CPDH,
        |	b1.DDDW,
        |	b1.DLSHGD,
        |	b1.DLSHGZ,
        |	b1.DLSHZD,
        |	b1.DYDJ,
        |	b1.DYTGXH,
        |	b1.GYTGXH,
        |	b1.ID B_ID,
        |	b1.JC_ID,
        |	b1.JXZB,
        |	b1.KZDL,
        |	b1.KZSH,
        |	b1.NQQ,
        |	b1.QYBXH,
        |	b1.QYLX,
        |	b1.REMARK,
        |	b1.RL,
        |	b1.SBXS,
        |	b1.SBY,
        |	b1.SB_ID,
        |	b1.SDBZ,
        |	b1.SYNC_STATUS B_SYNC_STATUS,
        |	b1.SYXDZ,
        |	b1.SZDW,
        |	b1.TCRQ,
        |	b1.TOUYUNRQ,
        |	b1.TUIYIRQ,
        |	b1.TYFS,
        |	b1.TZ,
        |	nvl(b1.XSDW_ID,"") XSDW_ID,
        |	b1.YZ,
        |	b1.YZ1,
        |	b1.YZTYXH,
        |	b1.ZCRQ,
        |	b1.ZCSX,
        |	b1.ZKDYGD,
        |	b1.ZKDYGZ,
        |	b1.ZKDYZD,
        |	b1.ZXJDFS,
        |	b1.ZYTGXH,
        |	b1.RERUN_FLAG,
        |	nvl(b1.BDZ_ID,"") BDZ_ID,
        |	b1.ZCSX_ID,
        |	b1.DDDW_ID,
        |	b1.SZDW_ID,
        |	b1.XHGG_ID,
        |	nvl(b1.ZZDW_ID,"") ZZDW_ID,
        |	b1.YZTYCJ_ID,
        |	b1.SBXS_ID,
        |	b1.data_type,
        | b2.BJDWBM AS JCDM,
        |	b2.DWMC AS JCMC,
        | b3.BJDWBM AS XSDWDM,
        |	b3.DWMC XSDWMC,
        |	b4.BJDWBM AS BDZDM,
        |	b4.DWMC AS BDZMC,
        |	b5.XHGGDM AS XHGG,
        |	b6.QYMC AS ZZDW,
        |	b8.QYMC AS YZTYCJ,
        |	t1.ID T_ID,
        |	nvl(t1.QSSJ,"9999-12-31 23:00:00") QSSJ,
        |	t1.KY1,
        |	t1.KY2,
        |	t1.SYNC_STATUS T_SYNC_STATUS,
        |	nvl(t1.ZYQSSJ,"9999-12-31 23:00:00") ZYQSSJ,
        |	t1.ZYQBYSJ,
        |	nvl(t1.ZYZZSJ,"9999-12-31 23:00:00") ZYZZSJ,
        |	nvl(t1.ZZSJ,"9999-12-31 23:00:00") ZZSJ,
        |	t1.ZYHBYSJ,
        |	t1.TQZK,
        |	t1.TSYY,
        |	t1.JHTYFL,
        |	t1.JHYQ,
        |	t1.SBGH,
        |	t1.RWH,
        |	t1.RWSM,
        |	t1.CYTJ,
        |	t1.EVENT_ID,
        |	t1.SSLX,
        |CASE
        |		WHEN t1.ZTFL IN ( 'DR', 'PR' ) THEN 0.0
        |   ELSE t1.ZYCXSJ
        |END AS ZYCXSJ,
        |CASE
        |		WHEN t1.ZTFL = 'LO' THEN T1.zycxsj
        |   ELSE ROUND( ( cast(t1.ZZSJ as decimal) - cast(t1.QSSJ as decimal) ) * 24, 2 )
        |	END AS CXSJ,
        |	t1.ZC_ID,
        |	t1.ZTFL,
        |	t2.SJZTMC AS ZTFLMC,
        |	t2.SJZTBM AS ZTFLBM,
        |	t1.TDSBDM_ID,
        |	concat(t3.PARENT_ID,t3.SBDM) AS TDSBDM,
        |	case
        |	  when t3.ZSBLBMC = null then null
        |	  else  concat_ws(',',t3.ZSBLBMC,t3.XTHSBMC,t3.ZXTHBJMC,t3.SBMC)
        |	  end AS TDSBMC,
        |	t1.JSYYDM_ID,
        |	t4.JSYYDM AS JSYYDM,
        |	t4.JSYYMC AS JSYYMC,
        |	t1.ZRYYDM_ID,
        |	concat(t5.ZRYYDM,t6.ZRYYDM) AS ZRYYDM,
        |	concat(t5.ZRYYMC,t6.ZRYYMC) AS ZRYYMC,
        |	t1.ZRYYDMF_ID,
        |	t5.ZRYYMC AS ZRYYFMC
        |FROM
        |	ZC_BYQ b1
        |	left join yx_byq t1 on b1.id = t1.zc_id
        | left join DM_JC b2 on b1.JC_ID = b2.ID
        | left join DM_XSDW b3 on b3.ID = b1.XSDW_ID
        | left join DM_BDZ b4 on b4.ID = b1.BDZ_ID
        | left join DM_XHGG b5 on b5.ID = b1.XHGG_ID
        | left join DM_QY b6 on b6.ID = b1.ZZDW_ID
        | left join DM_QY b8 on b8.ID = b1.YZTYCJ_ID
        | left join DM_SJ t2 on t1.ZTFL = t2.SJZTFH
        | left join DM_SB t3 on t1.TDSBDM_ID = t3.ID
        | left join DM_JSYY t4 on t1.JSYYDM_ID = t4.ID
        | left join DM_ZRYY t5 on t1.ZRYYDMF_ID = t5.ID
        | left join DM_ZRYY t6 on t1.ZRYYDM_ID = t6.ID
        |""".stripMargin)

    //jkxlDf.show

            jkxlDf.write.mode(SaveMode.Append)
              .option(JDBCOptions.JDBC_BATCH_INSERT_SIZE, 100000)
              .jdbc(chJdbcUrl, "zc_yx_byq", chDsProp)

    spark.stop()
  }
}
