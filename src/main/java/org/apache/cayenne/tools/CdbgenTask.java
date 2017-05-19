package org.apache.cayenne.tools;

import java.io.File;

import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

/**
 * @since 4.0
 */
public class CdbgenTask extends BaseCayenneTask {

    @TaskAction
    public void generate() {

    }

    @InputFile
    public File getDataMapFile() {
        return super.getDataMapFile();
    }
}
