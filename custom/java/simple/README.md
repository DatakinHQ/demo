# Simple Custom Example

This example shows how to use the [`openlineage-java`](https://search.maven.org/artifact/io.openlineage/openlineage-java) lib. to define a custom integration to collect OpenLineage events.

## Building

To build the entire project run:

```bash
$ ./gradlew build
```

## Running the Example

1. Locally install the `openlineage-java` lib (will be released in OpenLineage [`0.6.0`](https://github.com/OpenLineage/OpenLineage/projects/9)):

   ```bash
   $ git clone git@github.com:OpenLineage/OpenLineage.git
   $ cd client/java
   $ ./gradlew publishToMavenLocal
   ```
2. In the background, start an HTTP backend that implements the OpenLineage spec (ex: [`Marquez`](https://github.com/MarquezProject/marquez#quickstart))
   
2. Run the driver test:

   ```bash
   $ ./gradlew test
   ```
