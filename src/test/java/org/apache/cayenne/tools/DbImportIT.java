package org.apache.cayenne.tools;

import java.io.File;
import java.io.IOException;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * @since 4.0
 */
public class DbImportIT extends BaseTaskIT {

    @Test
    public void notConfiguredTaskFailure() throws IOException {
        GradleRunner runner = createRunner("dbimport_failure", "cdbimport");

        BuildResult result = runner.buildAndFail();

        assertNotNull(result.task(":cdbimport"));
        assertEquals(TaskOutcome.FAILED, result.task(":cdbimport").getOutcome());
    }

    @Test
    public void emptyDbTaskSuccess() throws IOException {
        GradleRunner runner = createRunner("dbimport_empty_db", "cdbimport");

        BuildResult result = runner.build();

        assertNotNull(result.task(":cdbimport"));
        assertEquals(TaskOutcome.SUCCESS, result.task(":cdbimport").getOutcome());

        File dataMap = new File(projectDir.getAbsolutePath() + "/datamap.map.xml");
        assertTrue(dataMap.exists());
    }

}
