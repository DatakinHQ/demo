#!/bin/bash
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# Usage: $ ./init-db.sh

set -eu

# (1) Create user / database
createdb math
createuser newton

# (2) Create 'counter' table
psql -v ON_ERROR_STOP=1 -U "newton" -d "math" > /dev/null <<-EOSQL
  CREATE TABLE IF NOT EXISTS counts (
    value INTEGER
  );
EOSQL

# (3) Create 'sum' table
psql -v ON_ERROR_STOP=1 -U "newton" -d "math" > /dev/null <<-EOSQL
  CREATE TABLE IF NOT EXISTS sums (
    value INTEGER
  );
EOSQL