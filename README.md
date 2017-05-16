
```groovy
cdbimport {

    map = 'datamap.map.xml'
    
    dataSource {
        driver = 'com.mysql.cj.jdbc.Driver'
        url = 'jdbc:mysql://127.0.0.1:3306/test?useSSL=false'
        username = 'root'
        password = 'testpwd'
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
