package org.apache.cayenne.tools

import org.gradle.api.Incubating
import org.gradle.api.Plugin
import org.gradle.api.Project

@Incubating
class GradlePlugin implements Plugin<Project> {

    void apply(Project project) {
        project.extensions.create("cayenne", GradleCayenneExtension, project)

        project.task('cgen',      type: GradleCgenTask)
        project.task('cdbimport', type: GradleDbImportTask)
        project.task('cdbgen',    type: GradleCdbgenTask)
    }
}
