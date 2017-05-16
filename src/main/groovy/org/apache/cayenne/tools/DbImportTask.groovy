package org.apache.cayenne.tools

import org.apache.cayenne.dbsync.reverse.dbimport.ReverseEngineering
import org.apache.cayenne.tools.model.DataSource
import org.apache.cayenne.tools.model.DbImportConfig
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @since 4.0
 */
class DbImportTask extends DefaultTask {

    String map

    DataSource dataSource = new DataSource()

    DbImportConfig config = new DbImportConfig()

    @TaskAction
    def runImport() {
        ReverseEngineering rr = config.toReverseEngineering()
        println rr.toString()
    }

    void dbImport(Closure<?> closure) {
        getProject().configure(config, closure)
    }

    void dbimport(Closure<?> closure) {
        dbImport(closure)
    }

    void dataSource(Closure<?> closure) {
        getProject().configure(dataSource, closure)
    }

}
