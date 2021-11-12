#!/bin/bash
#
# A script that builds, then submits the example Spark application
# to the local cluster. 
#
# Usage: $ ./submit-example-local.sh

set -e

# Build Jar
echo "Building lines-with-a-or-b.jar..."
./gradlew shadowJar > /dev/null

# Submit
echo "Submitting lines-with-a-or-b.jar..."
spark-submit \
  --master spark://localhost:7077 \
  --class example.LinesWithAOrB \
  build/libs/lines-with-a-or-b.jar
