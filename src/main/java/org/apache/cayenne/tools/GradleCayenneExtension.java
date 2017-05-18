package org.apache.cayenne.tools;

import java.io.IOException;

import org.codehaus.groovy.runtime.ResourceGroovyMethods;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.dsl.DependencyHandler;

/**
 * Cayenne DSL for gradle
 * <p>
 * - dependency management
 * - utility methods
 *
 * @since 4.0
 */
public class GradleCayenneExtension {

    public static final String GROUP = "org.apache.cayenne";
    private static final String VERSION_FILE = "/cayenne.version";

    private final String version;
    private final DependencyHandler dependencies;

    /**
     * Shortcut for the cgen task.
     * Can be used in defining additional tasks like:
     * <pre>{@code
     * task customCgen(type: cayenne.cgen) {
     *     //...
     * }
     * }</pre>
     */
    private final Class<CgenTask> cgen = CgenTask.class;

    /**
     * Shortcut for the cdbimport task.
     * Can be used in defining additional tasks like:
     * <pre>{@code
     * task customCdbimport(type: cayenne.cdbimport) {
     *     //...
     * }
     * }</pre>
     */
    private final Class<DbImportTask> cdbimport = DbImportTask.class;

    /**
     * Shortcut for the cdbgen task.
     * Can be used in defining additional tasks like:
     * <pre>{@code
     * task customCdbgen(type: cayenne.cdbgen) {
     *     //...
     * }
     * }</pre>
     */
    private final Class<CdbgenTask> cdbgen = CdbgenTask.class;

    /**
     * Default data map that will be used in all tasks.
     * Can be overridden per task.
     */
    private String defaultDataMap;

    public GradleCayenneExtension(Project project) {
        this.dependencies = project.getDependencies();
        try {
            this.version = ResourceGroovyMethods.getText(getClass().getResource(VERSION_FILE)).trim();
        } catch (IOException ex) {
            throw new GradleException("Cayenne version not found", ex);
        }
    }

    public Dependency dependency(final String name) {
        return dependencies.create(GROUP + ":cayenne-" + name + ":" + version);
    }

    public String getDefaultDataMap() {
        return defaultDataMap;
    }

    public void setDefaultDataMap(String defaultDataMap) {
        this.defaultDataMap = defaultDataMap;
    }

    public Class<CgenTask> getCgen() {
        return cgen;
    }

    public Class<DbImportTask> getCdbimport() {
        return cdbimport;
    }

    public Class<CdbgenTask> getCdbgen() {
        return cdbgen;
    }

    public String getVersion() {
        return version;
    }
}
