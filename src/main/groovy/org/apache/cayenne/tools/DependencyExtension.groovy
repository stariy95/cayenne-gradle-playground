package org.apache.cayenne.tools

import org.gradle.api.Project
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

/**
 * @since 4.0
 */
class DependencyExtension {
    public static final String GROUP = "org.apache.cayenne"
    private final version = '4.0.M6-SNAPSHOT'

    private final DependencyHandler dependencies
    private final ConfigurationContainer configurations

    DependencyExtension(Project project) {
        this.dependencies = project.dependencies
        this.configurations = project.configurations
    }

    Dependency dependency(String name) {
        dependencies.create("${GROUP}:cayenne-${name}:${version}")
    }

    void addModules(String... names) {
        def conf = configurations.maybeCreate('compile')
        conf.dependencies.add(dependency('server'))
        for(String name : names) {
            conf.dependencies.add(dependency(name))
        }
    }

    void addModulesToConfiguration(String configurationName, String... names) {
        def conf = configurations.maybeCreate(configurationName)
        for(String name : names) {
            conf.dependencies.add(dependency(name))
        }
    }
}
