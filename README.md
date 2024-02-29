# GA4GH Starter Kit Utils
Command line utilities and helper functions
## Run Starter Kit Utils
## 1. Natively
First, clone the repository from Github:
```
git clone https://github.com/ga4gh/ga4gh-starter-kit-utils.git
cd ga4gh-starter-kit-utils
```

The service can be run in development mode directly via gradle:
```
./gradlew bootRun --args="database create-tables -d jdbc:sqlite:starter-kit-data-connect.dev.db https://raw.githubusercontent.com/ga4gh/ga4gh-starter-kit-data-connect/v0.1.1/database/sqlite/create-tables.sql"
```

The "--args" argument can be modified to run any of the supported commands. The available commands are listed towards the end of this README file. 

## 2. Alternatively, Starter Kit Utils can be built as a jar and run:
Build jar
```
./gradlew bootJar
```
### Example: Create a database and tables
#### i. When source is a URL
```
java -jar build/libs/ga4gh-starter-kit-utils-0.1.2.jar database create-tables -d jdbc:sqlite:starter-kit-data-connect.dev.db https://raw.githubusercontent.com/ga4gh/ga4gh-starter-kit-data-connect/v0.1.1/database/sqlite/create-tables.sql
```
#### ii. When source is a GA4GH Starter Kit API signature
```
java -jar build/libs/ga4gh-starter-kit-utils-0.1.2.jar database create-tables -d jdbc:sqlite:starter-kit-data-connect.dev.db data-connect@v0.1.1
```
#### iii. When source is a filepath
```
java -jar build/libs/ga4gh-starter-kit-utils-0.1.2.jar database create-tables -d jdbc:sqlite:starter-kit-data-connect.dev.db ./db-scripts/create-tables.sql
```

## Run Starter Kit Utils using Docker

### Example: Create a database and tables

#### i. When source is a URL
```
docker run -v ./resources/drs/db:/db -v ./resources/drs/db-scripts:/db-scripts ga4gh/ga4gh-starter-kit-utils:test database create-tables -d jdbc:sqlite:/db/data-connect.db https://raw.githubusercontent.com/ga4gh/ga4gh-starter-kit-data-connect/v0.1.1/database/sqlite/create-tables.sql
```

#### ii. When source is a GA4GH Starter Kit API signature
```
docker run -v ./resources/drs/db:/db -v ./resources/drs/db-scripts:/db-scripts ga4gh/ga4gh-starter-kit-utils:test database create-tables -d jdbc:sqlite:/db/data-connect.db data-connect@v0.1.1
```

#### iii. When source is a filepath
```
docker run -v ./resources/drs/db:/db -v ./resources/drs/db-scripts:/db-scripts ga4gh/ga4gh-starter-kit-utils:test database create-tables -d jdbc:sqlite:/db/data-connect.db /db-scripts/create-tables.sql
```
## List of supported GA4GH APIs in the API signature field

| API Signature | GA4GH API Name | Description | Github |
|---------------|----------------|-------------|--------|
| drs | Data Repository Service | Generic interface to different data repository types | https://github.com/ga4gh/ga4gh-starter-kit-drs |
| wes | Workflow Execution Service | Submit and monitor workflow run requests | https://github.com/ga4gh/ga4gh-starter-kit-wes |
| data-connect | Data Connect API| Discover and search datasets | https://github.com/ga4gh/ga4gh-starter-kit-data-connect |
| passport-broker | Passport Broker | provides Passport jwt token | https://github.com/ga4gh/ga4gh-starter-kit-passport-broker |

## List of supported commands

The following commands can be run using either using `./gradlew bootRun` or `docker run` or using a jar as described in the [Run Starter Kit Utils](#-run-starter-kit-utils) section.

### Command: `database`
### Subcommands:
### i. `list-apis`

**Example:**  
```
./gradlew bootRun --args="database list-apis"
```
### ii. `list-migrations`

**Paramters:**

| Parameter Label | Parameter Name | Description | Required |
|-----------------|----------------|-------------|----------|
| API Signature |  | GA4GH API name. Get the list of available APIs using `database list-apis` command. | Yes |

**Example:**
```
./gradlew bootRun --args="database list-migrations data-connect"
```
### iii. `create-tables`

| Parameter Label | Parameter Name | Description | Required  | Default Value |
|-----------------|----------------|-------------|-----------|---------------|
| SOURCE |  |Table SQL source to apply. Can be a either a valid API migration signature (e.g. drs@1.0.0), URL (e.g. https://somesite.com/drs-tables.sql), or file path (e.g. ./drs-tables.sql).| Yes |  | |
| DB_URL | `-d`, `--db-url` | "Valid jdbc:// database connection URL" | No | `jdbc:sqlite:./ga4gh-starter-kit.dev.db` | 
| USERNAME | `-u`, `--username` | Database username | No | "" |
| PASSPORT | `-p`, `--password` | Database password | No | "" |

**Example:**
```
./gradlew bootRun --args="database create-tables -d jdbc:sqlite:starter-kit-data-connect.dev.db https://raw.githubusercontent.com/ga4gh/ga4gh-starter-kit-data-connect/v0.1.1/database/sqlite/create-tables.sql"
```
### iv. `add-test-dataset`

| Parameter Label | Parameter Name | Description | Required  | Default Value |
|-----------------|----------------|-------------|-----------|---------------|
| SOURCE |  | Test dataset source to apply. Can be a either a valid API migration signature (e.g. drs@1.0.0), URL (e.g. https://somesite.com/drs-tables.sql), or file path (e.g. ./drs-tables.sql).| Yes |  | |
| DB_URL | `-d`, `--db-url` | "Valid jdbc:// database connection URL" | No | `jdbc:sqlite:./ga4gh-starter-kit.dev.db` | 
| USERNAME | `-u`, `--username` | Database username | No | "" |
| PASSPORT | `-p`, `--password` | Database password | No | "" |

**Example:**
```
./gradlew bootRun --args="database add-test-dataset -d jdbc:sqlite:starter-kit-data-connect.dev.db https://raw.githubusercontent.com/ga4gh/ga4gh-starter-kit-data-connect/v0.1.1/database/sqlite/add-dev-dataset.sql"
```

## Changelog

### v0.1.2
* Bug Fix - Handle filepath as source. Get sql from a locally available file when filepath is specified.
* Add data-connect and passport-broker to supported GA4GH API list.

### v0.1.1
* Patched log4j dependencies to v2.16.0 to avoid [Log4j Vulnerability](https://www.cisa.gov/uscert/apache-log4j-vulnerability-guidance)

## Maintainers

* GA4GH Tech Team [ga4gh-tech-team@ga4gh.org](mailto:ga4gh-tech-team@ga4gh.org)
