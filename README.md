# Queryless

Queryless is a Maven and Gradle plugin that generates Java classes from resource files containing externalized SQL
or other type queries.

## Summary
Aim of this simple project is to answer question that sometimes arises when using string based queries in Java source
code - where to put query text?

Usually there are three main options:
* Write queries text directly in Java code as a constant or directly as string literal.
  * Main advantage is that query text can be quickly located and in case of inline string literal - quickly modified.
  * Main disadvantage of this option is query readability and maintainability, since Java does not support multiline
  strings.
* Externalize queries into properties or other configuration files.
  * Main advantage is that queries and Java code is not mixed also multiline queries can be much more readable.
  * Main disadvantage is that queries text is not easily located and can not be quickly viewed from Java code.
* Use frameworks such as JOOQ.
  * Advantages and disadvantages of this option is kind of obvious, but in general there might be times when we
  can not or don't want to use other frameworks.

Queryless aims to solve this issue by mixing two of the three options mentioned above, keep queries external from
Java code and generate classes with query constants and useful javadocs. This solution provides following advantages:
* Java code and query text is separate.
* Queries can be written in native files (such as `*.sql`) and take advantage of IDE or other tools support.
* Generated classes and constants has javadocs with original query text, so query can be easily viewed from Java code.
* Java code for query constants are automatically generated by Maven or Gradle plugin.
* Plugin supports collecting multiple source files into bundles (query constant classes) and also supports nested
bundles to enrich readability and structure.

## Example
Lets say we have SQL file `queries.sql` located at `/src/main/resources/queries/`:
```sql
-- id: employee-find
-- Finds employees by ID
SELECT
    ID,
    NAME,
    AGE,
    DEPARTMENT
FROM
    EMPLOYEE
WHERE
    ID = :id
```
Using simple Maven plugin configuration:
```xml
<plugin>
    <groupId>queryless</groupId>
    <artifactId>queryless-plugin-maven</artifactId>
    <version>${queryless.version}</version>
    <executions>
        <execution>
            <goals>
                <goal>generate</goal>
            </goals>
            <phase>generate-sources</phase>
            <configuration>
                <sources>
                    <param>queries/*.sql</param>
                </sources>
            </configuration>
        </execution>
    </executions>
</plugin>
```
Or simple Gradle plugin configuration (kotlin):
```kotlin
plugins {
    id("queryless.plugin") version("${queryless.version}")
}

queryless {
    sources = fileTree("src/main/resources/queries").matching {
        include("**/*.sql")
    }
}
```
Would produce autogenerated Java source file:
```java
// Generated by Queryless (https://github.com/paveljakov/queryless)
package queryless.generated;

import java.lang.String;
import javax.annotation.Generated;

/**
 * <i>Queryless query bundle.</i><br/><h1>Queries</h1>
 */
@Generated(
    value = "Generated by Queryless (https://github.com/paveljakov/queryless)",
    date = "2019-01-02T23:01:32.339"
)
public final class Queries {
  /**
   * <h2>Query text:</h2><pre>{@code -- Finds employees by ID
   * SELECT
   *     ID,
   *     NAME,
   *     AGE,
   *     DEPARTMENT
   * FROM
   *     EMPLOYEE
   * WHERE
   *     ID = :id}</pre>
   */
  public static final String EMPLOYEE_FIND = "-- Finds employees by ID\n"
      + "SELECT\n"
      + "    ID,\n"
      + "    NAME,\n"
      + "    AGE,\n"
      + "    DEPARTMENT\n"
      + "FROM\n"
      + "    EMPLOYEE\n"
      + "WHERE\n"
      + "    ID = :id";

  private Queries() {
  }
}
```
This class could be used like this:
```java
jdbcTemplate.queryForObject(Queries.EMPLOYEE_FIND, params, Employee.class);
```
And in Intellij or Eclipse (or STS) IDE it would look like this:

<img src="/resources/example-idea.png?raw=true" alt="Intellij IDEA" width="65%" height="65%">
<img src="/resources/example-eclipse.png?raw=true" alt="Eclipse IDE" width="65%" height="65%">

## Basic usage
To use Queryless Maven/Gradle plugin you just need to add plugin declaration to your build script and configure source query files location(s).

### Maven plugin
```xml
<plugin>
    <groupId>queryless</groupId>
    <artifactId>queryless-plugin-maven</artifactId>
    <version>${queryless.version}</version>
    <executions>
        <execution>
            <goals>
                <goal>generate</goal>
            </goals>
            <phase>generate-sources</phase>
        </execution>
    </executions>
</plugin>
```

### Gradle plugin

_Not yet published in cntral repo._

Kotlin:
```kotlin
plugins {
    id("queryless.plugin") version("${queryless.version}")
}
```
Groovy:
```groovy
plugins {
    id 'queryless.plugin' version '${queryless.version}'
}
```

### Basic configuration
Minimal configuration plugin needs is query source files location(s) configuration.

#### Maven configuration
```xml
<configuration>
    <sources>
        <param>**/*.sql</param>
    </sources>
</configuration>
```

#### Gradle configuration
Kotlin:
```kotlin
queryless {
    sources = fileTree("src/main/resources").matching {
        include("**/*.sql")
    }
}
```
Groovy:
```groovy
queryless {
    sources = fileTree('src/main/resources') {
        include '**/*.sql'
    }
}
```

## Advanced usage
todo

### Query bundles
todo

#### Nested query bundles
todo

### Advanced configuration
todo

## Considerations
todo

### Other projects
