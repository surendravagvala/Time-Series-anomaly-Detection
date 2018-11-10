import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
import java.io.File
import scala.reflect.io.Directory

object TimeSeriesAnomalyDetection {

  def main(args:Array[String]): Unit =
  {
    //create Spark Conf and Context object
    val conf = new SparkConf().setAppName("Anomaly_Detection").setMaster("local[*]")
    val sc = new SparkContext(conf)

    //Disable Success files in output folder
    sc.hadoopConfiguration.set("mapreduce.fileoutputcommitter.marksuccessfuljobs", "false")
    sc.hadoopConfiguration.set("parquet.enable.summary-metadata", "false")

    //Build Spark Session
    val spark = SparkSession.builder().appName("Anomaly_Detection").master("local[*]").getOrCreate()

    //Input the timeseries data as a data frame
    val timeseries_df = spark.read.format("csv").option("header", "true").load("input_data.csv")


    //Create Output Dir
    val directory = new Directory(new File("Output"))
    directory.deleteRecursively()

    // Define windows_size for calculating statistics, threshold for flagging anomaly, chosen after experimentation
    val window_size = 120
    val threshold = 75

    //Sliding Window
    val slidingWindow =  Window
      .orderBy(asc("timestamp"))
      .rowsBetween(-window_size, 0)

    //Compute Stats over sliding window
    val moving_Average= s"avg_over_past_${window_size}_min"
    val timeseries_stats_df = timeseries_df
      .withColumn(moving_Average,avg(col("value")).over(slidingWindow))
      .withColumn("deviation",abs(col("value")-col(moving_Average)))
      .withColumn("percentage_deviation",col("deviation") * 100 /col(moving_Average))

    //Flag Anomalies with timestamp info
    val timeseries_anomalies_df = timeseries_stats_df
      .withColumn("Date",col("timestamp").substr(0,10))
      .withColumn("anomaly",when(col("percentage_deviation")>=threshold,true))
      .withColumn("anomalous_timestamp",when(col("anomaly").isNotNull,col("timestamp")))
      .withColumn("anomalous_points",when(col("anomaly").isNotNull,col("value")))

    //Output anomalies grouped by date
    timeseries_anomalies_df
      .groupBy(col("Date"))
      .agg(count(col("anomaly")).alias("Anomaly#"),concat_ws(", ",collect_list(col("anomalous_timestamp")),collect_list(col("anomalous_points"))).alias("Anomalies [Timestamp, Datapoints]"))
      .withColumn("Anomaly_Status", when(col("Anomaly#")>=5,lit("Anomaly Detected")).otherwise(lit("No Anomalies")))
      .coalesce(1)
      .write
      .option("header", "true").csv("Output")

    spark.stop()

  }
}
