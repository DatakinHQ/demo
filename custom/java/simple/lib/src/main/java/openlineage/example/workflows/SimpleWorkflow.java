/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package openlineage.example.workflows;

// Enables OpenLineage
// import openlineage.example.workflows.lineage.Job;
import openlineage.example.storage.Database;

public class SimpleWorkflow implements Workflow {
  private final Database database;

  public SimpleWorkflow() {
    this.database =
        Database.newDatabase("jdbc:postgresql://localhost:5432/math", "newton", "calculus");
  }

  @Override
  public void run() {
    // (1) Add count
    final Job counter = new Job("counter");
    counter.sql(database, null, "counts", "INSERT INTO ${outTable} (value) VALUES (1)");

    // (2) Sum counts
    final Job sum = new Job("sum");
    sum.sql(
        database,
        "counts",
        "sums",
        "INSERT INTO ${outTable} (value) SELECT SUM(c.value) FROM ${inTable} AS c;");
  }
}
