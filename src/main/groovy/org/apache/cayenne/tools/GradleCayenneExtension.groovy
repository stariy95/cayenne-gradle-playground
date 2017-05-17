package org.apache.cayenne.tools

import org.gradle.api.Project
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

/**
 * Cayenne DSL for gradle
 *
 * - dependency management
 * - some utility methods
 *
 * @since 4.0
 */
class GradleCayenneExtension {

    public static final String GROUP = 'org.apache.cayenne'
    private static final String VERSION_FILE = '/cayenne.version'

    private final version
    private final DependencyHandler dependencies
    private final ConfigurationContainer configurations

    /**
     * Default data map that will be used in all tasks.
     * Can be overridden per task.
     */
    String defaultDataMap

    // shortcuts to tasks' Classes

    /**
     * Shortcut for the cgen task.
     * Can be used in defining additional tasks like:
     * <pre>{@code
     * task customCgen(type: cayenne.cgen) {
     *     //...
     * }
     * }</pre>
     */
    def cgen = CgenTask

    /**
     * Shortcut for the cdbimport task.
     * Can be used in defining additional tasks like:
     * <pre>{@code
     * task customCdbimport(type: cayenne.cdbimport) {
     *     //...
     * }
     * }</pre>
     */
    def cdbimport = DbImportTask
    def cdbgen = CdbgenTask

    GradleCayenneExtension(Project project) {
        this.dependencies = project.dependencies
        this.configurations = project.configurations
        this.version = getClass().getResource(VERSION_FILE).text.trim()
    }

    Dependency dependency(String name) {
        dependencies.create("${GROUP}:cayenne-${name}:${version}")
    }

    void addDependencies(String... cayenneModules) {
        addDependenciesToConfiguration('compile', cayenneModules)
    }

    void addDependenciesToConfiguration(String configurationName, String... cayenneModules) {
        cayenneModules += ['server', 'java8']
        def conf = configurations.maybeCreate(configurationName)
        for(String name : cayenneModules) {
            conf.dependencies.add(dependency(name))
        }
    }
}
