#!/bin/bash
#
# A script that starts a Spark cluster locally via docker compose.
#
# Usage: $ ./start-cluster-local.sh

set -e

# Change working directory to examples/spark/
project_root=$(git rev-parse --show-toplevel)
cd "${project_root}/examples/spark"

docker-compose up -V --force-recreate
