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

import lombok.Getter;
import lombok.NonNull;
import openlineage.example.storage.Database;
import openlineage.example.storage.Row;
import org.apache.commons.text.StringSubstitutor;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Job {
  @Getter private final String name;

  public Job(@NonNull final String name) {
    this.name = name;
  }

  public Set<Row> sql(
      @NonNull Database database,
      @Nullable String inTable,
      @NonNull String outTable,
      @NonNull String sqlTemplate) {
    final Map<String, String> sqlTemplateValues = new HashMap<>();
    if (inTable != null) {
      sqlTemplateValues.put("inTable", inTable);
    }
    sqlTemplateValues.put("outTable", outTable);
    final String sql = new StringSubstitutor(sqlTemplateValues).replace(sqlTemplate);
    return database.sql(sql);
  }
}
