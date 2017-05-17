package org.apache.cayenne.tools

import org.apache.cayenne.dbsync.reverse.dbimport.ReverseEngineering
import org.apache.cayenne.tools.model.DataSourceConfig
import org.apache.cayenne.tools.model.DbImportConfig
import org.gradle.api.DefaultTask
import org.gradle.api.InvalidUserDataException
import org.gradle.api.tasks.TaskAction

/**
 * @since 4.0
 */
class GradleDbImportTask extends DefaultTask {

    String map

    DataSourceConfig dataSource = new DataSourceConfig()

    DbImportConfig config = new DbImportConfig()

    @TaskAction
    def runImport() {
        dataSource.validate()
        ReverseEngineering rr = config.toReverseEngineering()

        if(map == null) {
            throw new InvalidUserDataException("Missing required 'map' parameter.")
        }
        File mapFile = getProject().file(map)
        // TODO: import

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
