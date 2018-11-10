# Time Series anomaly Detection
# Algorithm
Calculate moving averages for past 120 mins
Use these values to find the percentage deviation
Flag data points as anomalies when the deviate percentage is more than the threshold
Use these data points to fetch the required summary information.

#Execution
Code in Scala
Build the project dependencies using SBT
Need SBT to run the project 
Start SBT in the root directory, use "run" command to execute the application.
The output is generated as a CSV file under 'Output' folder.

#Forecasting
Moving Averages worked well for anomaly detection with the data presented. Used this method as the process can be statistically explained easily.For a real-time time series data, we expect to see trends, cycles, seasons ... and, given enough amount of data, methods like ARIMA and SH-ESD work very well. 

Arima, which stands for Auto Regressive Integrated Moving Average can be used for forecasting. My current method employs Moving average. This can be extended with auto regression, where we determine the correlation betwen previoud time period values and current value.
