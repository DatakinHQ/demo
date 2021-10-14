# Stackostudy

This is a quick study of the Stack Overflow public dataset in BigQuery.

To use:

1. Create a BigQuery project, choose a dataset name, provision a service account and key, and create the necessary files in ~/.dbt so that there is a `stackostudy` profile to connect to.

2. Install dbt and the OpenLineage integration

```bash
python3 -m venv virtualenv
source virtualenv/bin/activate
pip3 install dbt openlineage-dbt
```

3. Run it, passing in the URL to your OpenLineage-compatible endpoint.

```bash
OPENLINEAGE_URL=http://localhost:5000 dbt-ol run
```
