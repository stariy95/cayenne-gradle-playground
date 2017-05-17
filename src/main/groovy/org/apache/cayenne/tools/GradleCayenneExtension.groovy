package org.apache.cayenne.tools

import org.gradle.api.Project
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

/**
 * Cayenne DSL for dependency maangement
 * @since 4.0
 */
class GradleCayenneExtension {

    public static final String GROUP = "org.apache.cayenne"

    private final version
    private final DependencyHandler dependencies
    private final ConfigurationContainer configurations

    GradleCayenneExtension(Project project) {
        this.dependencies = project.dependencies
        this.configurations = project.configurations
        this.version = project.version
    }

    Dependency dependency(String name) {
        dependencies.create("${GROUP}:cayenne-${name}:${version}")
    }

//    void addDefaultDependencies() {
//        addDependencies('server', 'java8')
//    }
//
//    void addDefaultDependencies(String configurationName) {
//        addDependenciesToConfiguration(configurationName, 'server', 'java8')
//    }

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
