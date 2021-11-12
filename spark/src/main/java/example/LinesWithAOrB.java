package example;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;

public final class LinesWithAOrB {
  public static void main(final String[] args) {
    final SparkSession spark = SparkSession.builder().appName("WordCount").getOrCreate();
    final Dataset<String> data = spark.read().textFile("src/main/resources/data.txt").cache();

    final long numOfLinesWithAs = data.filter(line -> line.contains("a")).count();
    final long numOfLinesWithBs = data.filter(line -> line.contains("b")).count();

    System.out.println("Lines with a: " + numOfLinesWithAs + ", lines with b: " + numOfLinesWithBs);

    spark.stop();
  }
}
