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

1. If you haven't already, setup Datakin on Databricks by following the steps outlined in our [Databricks onboarding documentation](https://demo.datakin.com/onboarding)
2. Then, upload the file [data.txt](https://github.com/DatakinHQ/datakin/tree/main/spark/data.txt) to HDFS by following the steps outlined in the [Databricks documentation](https://docs.databricks.com/data/data.html#import-data-1)
3. Modify the file path to `data.txt` in `LinesWithAOrB.java`:

   ```diff
   -...spark.read().textFile("src/main/resources/data.txt").cache();
   +...spark.read().textFile("dbfs:/FileStore/tables/data.txt").cache();
   ```
   
4. Build the `jar`:

   ```bash
   $ ./gradlew shadowJar
   ```
   
   The executable can be found under `build/libs/`

5. Then, [create](https://docs.databricks.com/jobs.html#create-a-job) a job by uploading the `jar` in **Step 4** to your Databricks cluster
6. Finally, [run](https://docs.databricks.com/jobs.html#run-a-job) your job on your Databricks cluster to begin emitting Spark lineage metadata to Datakin!
