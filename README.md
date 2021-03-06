# Time Series Anomaly Detection
# Algorithm
1. Calculate moving averages over sliding window
1. Use these values to find the percentage deviation
1. Flag data points as anomalies when the deviate percentage is more than the threshold
1. Use these data points to fetch the required summary information.

# Requirements:
* Scala 2.12.7
* Spark 2.4.0 
* SBT

# Execution
1. Code in Scala
1. Build the project dependencies using SBT
1. Need SBT to run the project 
1. Start SBT in the root directory, use "run" command to execute the application.
1. The output is generated as a CSV file under 'Output' folder.

# Design Consideration
* The presented algorithm detects all the anomolies in the data rather than just the first occurence
* The decisive point while choosing one over the other would be how robust the anomaly detection procedure is.
* If the algorithm is robust and not prone to the previous outliers, we would be able to accurately identify anomalies past this step.
* For instance, exponential moving average method, which give more weight to the recently seen data points is susceptible and not idle, therefore not a robust method. Moving averages with a large window size is a better performer.
* More advanced methods like SH-ESD and LSTM networks work well in decomposing the time series to identify trend, seasonality, and work only on the residual part.

# Forecasting
1. Moving Averages worked well for anomaly detection with the data presented. Used this method as the process can be statistically explained easily. In a real-time time series data, we expect to see trends, cycles, seasons ... and, given enough amount of data, methods like ARIMA and SH-ESD work very well. 

1. Arima, which stands for Auto Regressive Integrated Moving Average can be used for forecasting. My current method employs Moving average. This can be extended with auto regression, where we determine the correlation between previous time period values and current value.

1. With huge observed data, training deep learning models (RNN Architecture) with LSTM helps to uncover hidden pattern well and forecast with better accuracies.
