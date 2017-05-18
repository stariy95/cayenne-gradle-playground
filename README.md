# Cayenne Gradle plugin api demo

## Including Cayenne

Here is example of how you can easily setup cayenne in your project:
```groovy
buildscript {
    // add maven repository
    repositories {
        mavenLocal()
    }
    // add Cayenne Gradle Plugin
    dependencies {
        classpath group: 'org.apache.cayenne.plugins', name: 'cayenne-gradle-plugin', version: '4.0.M6-SNAPSHOT'
    }
}

// apply Cayenne plugin
apply plugin: 'org.apache.cayenne'

// finally configure Cayenne
cayenne {
    addDependencies 'server', 'java8'
    defaultDataMap 'datamap.map.xml'
}
```

## DSL

Cayenne Gradle plugin exports object ``cayenne`` that you can call in your `build.gradle` script.

This object have following methods:
- ``addDependencies(String... additionalModules)`` add required Cayenne dependencies with version corresponding to plugin
- ``addDependenciesToConfiguration(String configurationName, String... cayenneModules)`` add required Cayenne dependencies 
into desired configuration with version corresponding to plugin
- ``defaultDataMap`` set DataMap that will be used in all tasks by default 


## Tasks

### cgen: class generation task 

Configure default `cgen` task:
```groovy
cgen {
    client = false
    mode = 'all'
    overwrite = true
    createPropertiesNames = true
}
```

Define additional cgen task (e.g. for client classes):
```groovy
task clientCgen(type: cayenne.cgen) {
    client = true
}
```

### cdbgen: database generation task
 
Configure default `cdbgen` task:
```groovy
cdbgen {
    
}
```

### cdbimport: database reverse engineering task 

Sample configuration:
```groovy
cdbimport {

    map = 'datamap.map.xml'
    
    dataSource {
        driver = 'com.mysql.cj.jdbc.Driver'
        url = 'jdbc:mysql://127.0.0.1:3306/test?useSSL=false'
        username = 'root'
        password = 'root'
    }

    dbImport {
        catalog 'catalog-1'
        schema 'schema-1'

        catalog {
            name 'catalog-3'
            includeTable 'table0'
            includeTable 'table4'
            //includeTables ['table1', 'table2', 'table3']

            excludeTable '^GENERATED_.*'
        }

        catalog {
            name 'catalog-2'
            schema {
                name 'schema-2'
                includeTable 'test_table'
                includeTable {
                    name 'test_table2'
                    excludeColumn '__excluded'
                }
            }
        }

        includeProcedure 'procedure_test_1'

        includeColumn 'id'
        //includeColumns ['id', 'version']

        tableTypes = ['TABLE', 'VIEW']
    }
}
```

### Bind tasks to project's build lifecycle
To run Cayenne tasks with your project build, just link them in your `build.gradle` file:
```groovy
cgen.dependsOn cdbimport
compileJava.dependsOn cgen
```