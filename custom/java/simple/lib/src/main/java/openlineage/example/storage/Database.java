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

package openlineage.example.storage;

import lombok.Getter;
import lombok.NonNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Database {
  @Getter private final String url;
  private final String user;
  private final String password;

  private Database(
      @NonNull final String url, @NonNull final String user, @NonNull final String password) {
    this.url = url;
    this.user = user;
    this.password = password;
  }

  public static Database newDatabase(
      @NonNull final String url, @NonNull final String user, @NonNull final String password) {
    return new Database(url, user, password);
  }

  public Set<Row> sql(@NonNull String sql) {
    final Set<Row> rows = new HashSet<>();
    try (final Connection con = DriverManager.getConnection(url, user, password);
        final PreparedStatement statement = con.prepareStatement(sql);
        final ResultSet result = statement.executeQuery()) {
      final ResultSetMetaData resultMeta = result.getMetaData();
      final int numOfColumns = resultMeta.getColumnCount();
      while (result.next()) {
        final Map<String, Object> columns = new HashMap(numOfColumns);
        for (int i = 0; i < numOfColumns; i ++) {
          final String columnName = resultMeta.getColumnName(i + 1);
          final Object columnValue = result.getObject(columnName);
          columns.put(columnName, columnValue);
        }
        rows.add(new Row(columns));
      }
      return rows;
    } catch (SQLException e) {
      // ignore
      return Collections.emptySet();
    }
  }
}
