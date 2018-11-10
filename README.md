# Time Series anomaly Detection
# Algorithm
1. Calculate moving averages for past 120 mins
1. Use these values to find the percentage deviation
1. Flag data points as anomalies when the deviate percentage is more than the threshold
1. Use these data points to fetch the required summary information.

#Execution
1. Code in Scala
1. Build the project dependencies using SBT
1. Need SBT to run the project 
1. Start SBT in the root directory, use "run" command to execute the application.
1. The output is generated as a CSV file under 'Output' folder.

#Forecasting
1. Moving Averages worked well for anomaly detection with the data presented. Used this method as the process can be statistically explained easily.For a real-time time series data, we expect to see trends, cycles, seasons ... and, given enough amount of data, methods like ARIMA and SH-ESD work very well. 

1. Arima, which stands for Auto Regressive Integrated Moving Average can be used for forecasting. My current method employs Moving average. This can be extended with auto regression, where we determine the correlation betwen previoud time period values and current value.
