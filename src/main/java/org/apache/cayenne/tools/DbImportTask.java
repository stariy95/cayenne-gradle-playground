package org.apache.cayenne.tools;

import java.io.File;

import groovy.lang.Closure;
import org.apache.cayenne.dbsync.DbSyncModule;
import org.apache.cayenne.dbsync.reverse.configuration.ToolsModule;
import org.apache.cayenne.dbsync.reverse.dbimport.DbImportAction;
import org.apache.cayenne.dbsync.reverse.dbimport.DbImportConfiguration;
import org.apache.cayenne.dbsync.reverse.dbimport.DbImportConfigurationValidator;
import org.apache.cayenne.dbsync.reverse.dbimport.DbImportModule;
import org.apache.cayenne.dbsync.reverse.dbimport.ReverseEngineering;
import org.apache.cayenne.dbsync.reverse.filters.FiltersConfigBuilder;
import org.apache.cayenne.di.DIBootstrap;
import org.apache.cayenne.di.Injector;
import org.apache.cayenne.tools.model.DataSourceConfig;
import org.apache.cayenne.tools.model.DbImportConfig;
import org.apache.cayenne.util.Util;
import org.gradle.api.Task;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskExecutionException;
import org.slf4j.Logger;

/**
 * @since 4.0
 */
public class DbImportTask extends BaseCayenneTask {

    @Input
    @Optional
    private String adapter;

    private DataSourceConfig dataSource = new DataSourceConfig();

    private DbImportConfig config = new DbImportConfig();

    private ReverseEngineering reverseEngineering;

    public DbImportTask() {
        // this task should be executed every invocation, so it is never up to date.
        getOutputs().upToDateWhen(new Spec<Task>() {
            @Override
            public boolean isSatisfiedBy(Task task) {
                return false;
            }
        });
    }

    @TaskAction
    public void runImport() {
        dataSource.validate();
        reverseEngineering = config.toReverseEngineering();

        getLogger().warn(reverseEngineering.toString());

        DbImportConfiguration config = createConfig(getLogger());
        Injector injector = DIBootstrap.createInjector(new DbSyncModule(), new ToolsModule(getLogger()), new DbImportModule());

        DbImportConfigurationValidator validator = new DbImportConfigurationValidator(reverseEngineering, config, injector);
        try {
            validator.validate();
        } catch (Exception ex) {
            throw new TaskExecutionException(this, ex);
        }

        try {
            injector.getInstance(DbImportAction.class).execute(config);
        } catch (Exception ex) {
            Throwable th = Util.unwindException(ex);

            String message = "Error importing database schema";

            if (th.getLocalizedMessage() != null) {
                message += ": " + th.getLocalizedMessage();
            }

            getLogger().error(message);
            throw new TaskExecutionException(this, th);
        }
    }

    DbImportConfiguration createConfig(Logger logger) {

        DbImportConfiguration config = new DbImportConfiguration();
        config.setAdapter(adapter);
        config.setDefaultPackage(reverseEngineering.getDefaultPackage());
        config.setDriver(dataSource.getDriver());
        config.setFiltersConfig(new FiltersConfigBuilder(reverseEngineering).build());
        config.setForceDataMapCatalog(reverseEngineering.isForceDataMapCatalog());
        config.setForceDataMapSchema(reverseEngineering.isForceDataMapSchema());
        config.setLogger(logger);
        config.setMeaningfulPkTables(reverseEngineering.getMeaningfulPkTables());
        config.setNamingStrategy(reverseEngineering.getNamingStrategy());
        config.setPassword(dataSource.getPassword());
        config.setSkipRelationshipsLoading(reverseEngineering.getSkipRelationshipsLoading());
        config.setSkipPrimaryKeyLoading(reverseEngineering.getSkipPrimaryKeyLoading());
        config.setStripFromTableNames(reverseEngineering.getStripFromTableNames());
        config.setTableTypes(reverseEngineering.getTableTypes());
        config.setTargetDataMap(getDataMapFile());
        config.setUrl(dataSource.getUrl());
        config.setUsername(dataSource.getUsername());
        config.setUsePrimitives(reverseEngineering.isUsePrimitives());
        config.setUseJava7Types(reverseEngineering.isUseJava7Types());

        return config;
    }

    @OutputFile
    public File getDataMapFile() {
        return super.getDataMapFile();
    }

    public void dbImport(Closure<?> closure) {
        getProject().configure(config, closure);
    }

    public void dbimport(Closure<?> closure) {
        dbImport(closure);
    }

    public void dataSource(Closure<?> closure) {
        getProject().configure(dataSource, closure);
    }

    public DataSourceConfig getDataSource() {
        return dataSource;
    }

    public DbImportConfig getConfig() {
        return config;
    }

    public String getAdapter() {
        return adapter;
    }

    public void setAdapter(String adapter) {
        this.adapter = adapter;
    }

    public void adapter(String adapter) {
        setAdapter(adapter);
    }
}
