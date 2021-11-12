# Simple [Spark](https://spark.apache.org) Job

In this example, you'll deploy a self-contained Spark application locally via **Docker**. The application counts the number lines containing `a` and the number containing `b` in a file. Note, the sample code was taken from the Spark [quickstart](https://spark.apache.org/docs/3.0.0-preview/quick-start.html) guide. 

## Requirements

* [Java 11](https://openjdk.java.net/install)+
* [Spark 3.0.0](https://formulae.brew.sh/formula/apache-spark)+

## Running the [Example](https://github.com/DatakinHQ/datakin/tree/main/spark/src/main/java/example/LinesWithAOrB.java) on Local Spark Cluster

1. To start a local Spark cluster, run:

   ```bash
   $ ./scripts/start-cluster-local.sh
   ```
   
   To view the Spark UI and verify it's running, open http://localhost:8080
   
2. Then, submit the job:
   
   ```
   $ ./scripts/submit-example-local.sh
   ```
   
## Running on Databricks

1. Upload the file [data.txt](https://github.com/DatakinHQ/datakin/tree/main/spark/data.txt) to HDFS by following the steps outlined in the [Databricks documentation](https://docs.databricks.com/data/data.html#import-data-1)

2. Modify the file path to `data.txt`:

   ```diff
   -...spark.read().textFile("src/main/resources/data.txt").cache();
   +...spark.read().textFile("dbfs:/FileStore/tables/data.txt").cache();
   ```
   
3. Then, build the `jar`:

   ```bash
   $ ./gradlew shadowJar
   ```
   
   The executable can be found under `build/libs/`

4. Finally, upload the `jar` by [create](https://docs.databricks.com/jobs.html#create-a-job) the job on your Databricks cluster