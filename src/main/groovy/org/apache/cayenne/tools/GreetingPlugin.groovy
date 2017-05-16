package org.apache.cayenne.tools

import org.apache.cayenne.DataRow
import org.gradle.api.Plugin
import org.gradle.api.Project

class GreetingPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.extensions.create("cayenne", DependencyExtension, project)

        project.task('cgen', type: CgenTask)
        project.task('cdbimport', type: DbImportTask)

        project.task('hello') {
            doLast {
                DataRow row = new DataRow(1)
                row.put("test", "Cayenne!")
                println "Hello from the " + row.get("test")
            }
        }
    }
}
