package org.apache.cayenne.tools;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import groovy.lang.Closure;
import org.apache.cayenne.dbsync.reverse.dbimport.DbImportConfiguration;
import org.apache.cayenne.tools.model.DataSourceConfig;
import org.apache.cayenne.tools.model.DbImportConfig;
import org.gradle.api.InvalidUserDataException;
import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * @since 4.0
 */
public class DbImportTaskTest {

    private DbImportTask task;

    @Before
    public void createTask() {
        Project project = ProjectBuilder.builder().build();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("type", DbImportTask.class);
        task = (DbImportTask)project.task(parameters, "cdbimportTask");
    }

    @Test(expected = InvalidUserDataException.class)
    public void testRunWithNoConfig() {
        task.runImport();
    }

    @Test
    public void testFullConfigCreation() {
        File mockFile = mock(File.class);

        // configure task
        task.setMap(mockFile);
        task.setAdapter("org.apache.cayenne.test.adapter");
        task.dataSource(new Closure<DataSourceConfig>(task, task) {
            DataSourceConfig doCall(DataSourceConfig dataSourceConfig) {
                assertNotNull(dataSourceConfig);

                dataSourceConfig.setUrl("test://url");
                dataSourceConfig.setDriver("org.apache.cayenne.test.driver");
                dataSourceConfig.setUsername("username");
                dataSourceConfig.setPassword("password");
                return dataSourceConfig;
            }
        });
        task.dbImport(new Closure<DataSourceConfig>(task, task) {
            DbImportConfig doCall(DbImportConfig config) {
                assertNotNull(config);

                config.setDefaultPackage("com.example.package");
                config.setMeaningfulPkTables("pk_tables");
                config.setNamingStrategy("com.example.naming");
                config.setSkipPrimaryKeyLoading(true);
                config.setSkipRelationshipsLoading(true);
                config.setStripFromTableNames("strip");
                config.tableTypes("view", "alias");

                config.setForceDataMapCatalog(true);
                config.setForceDataMapSchema(true);
                config.setUseJava7Types(true);
                config.setUsePrimitives(false);

                return config;
            }
        });

        // Testing this:
        DbImportConfiguration dbImportConfiguration = task.createConfig();

        // Check that all values in end configuration is properly set
        assertEquals("org.apache.cayenne.test.adapter", dbImportConfiguration.getAdapter());

        assertEquals("test://url", dbImportConfiguration.getUrl());
        assertEquals("org.apache.cayenne.test.driver", dbImportConfiguration.getDriver());
        assertEquals("username", dbImportConfiguration.getUsername());
        assertEquals("password", dbImportConfiguration.getPassword());

        assertEquals("com.example.package", dbImportConfiguration.getDefaultPackage());
        assertEquals("pk_tables", dbImportConfiguration.getMeaningfulPkTables());
        assertEquals("com.example.naming", dbImportConfiguration.getNamingStrategy());
        assertTrue(dbImportConfiguration.getDbLoaderConfig().isSkipPrimaryKeyLoading());
        assertTrue(dbImportConfiguration.getDbLoaderConfig().isSkipRelationshipsLoading());
        assertEquals("strip", dbImportConfiguration.getStripFromTableNames());
        assertEquals("password", dbImportConfiguration.getPassword());
        assertArrayEquals(new String[]{"view", "alias"}, dbImportConfiguration.getDbLoaderConfig().getTableTypes());

        assertTrue(dbImportConfiguration.isForceDataMapCatalog());
        assertTrue(dbImportConfiguration.isForceDataMapSchema());
        assertTrue(dbImportConfiguration.isUseJava7Types());
        assertFalse(dbImportConfiguration.isUsePrimitives());
    }


}