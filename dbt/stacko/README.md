# Stackostudy

This is a quick study of the Stack Overflow public dataset in BigQuery.

To use:

1. Create a BigQuery project, choose a dataset name, provision a service account and key, and create the necessary files in ~/.dbt so that there is a `stackostudy` profile to connect to.

2. Install dbt and the OpenLineage integration, along with Great Expectations

If you are using an arm64 Mac, run this and wait a bit:

```
brew install openblas gfortran python3 rust postgres
/opt/homebrew/bin/python3 -m venv dbt-ge-ol
source dbt-ge-ol/bin/activate
export OPENBLAS=/opt/homebrew/opt/openblas/lib/
pip3 install cython pybind11 pythran greenlet
pip3 install --no-binary :all: --no-use-pep517 scipy
pip3 install pyarrow pybigquery google-cloud-bigquery-storage sqlalchemy great_expectations
pip3 install dbt openlineage-dbt
```

Otherwise, run this and don't wait very long at all:

```bash
python3 -m venv dbt-ge-olt
source dbt-ge-ol/bin/activate
pip3 install pyarrow pybigquery google-cloud-bigquery-storage sqlalchemy great_expectations
pip3 install dbt openlineage-dbt
```

3. Configure GE

```bash
% mkdir great_expectations/uncommitted
% cat >> great_expectations/uncommitted/config_variables.yml
stackostudy:
  url: bigquery://stacko-study/stackostudy?credentials_path=/home/rturk/.dbt/stacko-study.json
^D
```

4. Run the models, passing in the URL to your OpenLineage-compatible endpoint.

```bash
% dbt docs generate
% OPENLINEAGE_URL=http://localhost:5000 dbt-ol run
```

5. Run the GE checkpoint

```bash
OPENLINEAGE_URL=http://localhost:5000 great_expectations checkpoint run check_users
```

