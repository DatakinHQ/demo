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

package openlineage.example.workflows.lineage;

import io.openlineage.client.OpenLineage;
import io.openlineage.client.OpenLineageClient;
import lombok.NonNull;
import openlineage.example.storage.Database;
import openlineage.example.storage.Row;

import javax.annotation.Nullable;
import java.net.URI;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Job extends openlineage.example.workflows.Job {
  private static final String NAMESPACE = "example";
  private static final URI PRODUCER =
      URI.create("https://github.com/Datakin/demo/tree/0.0.1/custom/java/simple");

  private final OpenLineage openlineage;
  private final OpenLineageClient client;

  public Job(@NonNull String name) {
    super(name);
    this.client = new OpenLineageClient("http://localhost:5000");
    this.openlineage = new OpenLineage(PRODUCER);
  }

  @Override
  public Set<Row> sql(
      @NonNull Database database,
      @Nullable String inTable,
      @NonNull String outTable,
      @NonNull String sqlTemplate) {
    // Run and job info
    final UUID runId = UUID.randomUUID();
    final String jobName = super.getName();

    // (1) Create start event
    final OpenLineage.RunFacets runFacets = openlineage.newRunFacetsBuilder().build();
    final OpenLineage.Run run = openlineage.newRun(runId, runFacets);
    final OpenLineage.JobFacets jobFacets = openlineage.newJobFacetsBuilder().build();
    final OpenLineage.Job job = openlineage.newJob(NAMESPACE, jobName, jobFacets);
    List<OpenLineage.InputDataset> inputs =
        (inTable != null)
            ? Arrays.asList(
                openlineage.newInputDataset(
                    NAMESPACE,
                    inTable,
                    openlineage
                        .newDatasetFacetsBuilder()
                        .dataSource(
                            openlineage
                                .newDatasourceDatasetFacetBuilder()
                                .name(database.getUrl())
                                .uri(URI.create(database.getUrl()))
                                .build())
                        .schema(tableSchemaFor(database, inTable))
                        .build(),
                    null))
            : Collections.emptyList();
    List<OpenLineage.OutputDataset> outputs =
        Arrays.asList(
            openlineage.newOutputDataset(
                NAMESPACE,
                outTable,
                openlineage
                    .newDatasetFacetsBuilder()
                    .dataSource(
                        openlineage
                            .newDatasourceDatasetFacetBuilder()
                            .name(database.getUrl())
                            .uri(URI.create(database.getUrl()))
                            .build())
                    .schema(tableSchemaFor(database, outTable))
                    .build(),
                null));
    final OpenLineage.RunEvent startRun =
        openlineage.newRunEvent("START", newEventTime(), run, job, inputs, outputs);
    client.emit(startRun);

    // (2) Continue call chain
    final Set<Row> rows = super.sql(database, inTable, outTable, sqlTemplate);

    // (3) Create end event
    final OpenLineage.RunEvent completeRun =
        openlineage.newRunEvent("COMPLETE", newEventTime(), run, job, inputs, outputs);
    client.emit(completeRun);

    // (4) Return rows
    return rows;
  }

  private ZonedDateTime newEventTime() {
    return ZonedDateTime.now(ZoneId.of("UTC"));
  }

  private OpenLineage.SchemaDatasetFacet tableSchemaFor(Database database, String table) {
    final List<OpenLineage.SchemaDatasetFacetFields> fields = new ArrayList<>();
    final Set<Row> rows =
        database.sql(
            String.format(
                "SELECT "
                    + " table_name,"
                    + " column_name,"
                    + " data_type "
                    + "FROM information_schema.columns "
                    + "WHERE table_name = '%s';",
                table));
    for (final Row row : rows) {
      final String columnName = (String) row.getColumns().get("table_name");
      final String dataType = (String) row.getColumns().get("data_type");
      final OpenLineage.SchemaDatasetFacetFields field =
          openlineage.newSchemaDatasetFacetFields(columnName, dataType, null);
      fields.add(field);
    }
    return openlineage.newSchemaDatasetFacet(fields);
  }
}
