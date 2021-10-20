# Stackostudy

This is a quick study of the Stack Overflow public dataset in BigQuery.

To use:

1. Create a BigQuery project, choose a dataset name, provision a service account and key, and create the necessary files in ~/.dbt so that there is a `stackostudy` profile to connect to.

2. Install dbt and the OpenLineage integration, along with Great Expectations

```bash
python3 -m venv datakin-dbt
source datakin-dbt/bin/activate
pip3 install pybigquery google-cloud-bigquery-storage sqlalchemy great_expectations dbt openlineage-dbt
```

3. Run the models, passing in the URL to your OpenLineage-compatible endpoint.

```bash
OPENLINEAGE_URL=http://localhost:5000 dbt-ol run
```
4. Configure GE

```bash
% mkdir great_expectations/uncommitted
% cat >> great_expectations/uncommitted/config_variables.yml
stackostudy:
  url: bigquery://stacko-study/stackostudy?credentials_path=/home/rturk/.dbt/stacko-study.json
^D
```

5. Run the GE checkpoint

```bash
OPENLINEAGE_URL=http://localhost:5000 great_expectations checkpoint run check_users
```

