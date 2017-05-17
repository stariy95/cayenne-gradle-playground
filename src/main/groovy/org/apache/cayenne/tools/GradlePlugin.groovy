package org.apache.cayenne.tools

import org.gradle.api.Incubating
import org.gradle.api.Plugin
import org.gradle.api.Project

@Incubating
class GradlePlugin implements Plugin<Project> {

    void apply(Project project) {
        // Add DSL extension
        project.extensions.create("cayenne", GradleCayenneExtension, project)

        // Register tasks
        project.task('cgen',      type: CgenTask)
        project.task('cdbimport', type: DbImportTask)
        project.task('cdbgen',    type: CdbgenTask)
    }
}
