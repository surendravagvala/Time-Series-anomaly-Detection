name := "TimeSeriesAnomalyDetection"

version := "0.1"

scalaVersion := "2.12.7"


libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.0"
fork in run := true