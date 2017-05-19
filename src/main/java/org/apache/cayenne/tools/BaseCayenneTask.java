package org.apache.cayenne.tools;

import java.io.File;

import org.gradle.api.DefaultTask;
import org.gradle.api.InvalidUserDataException;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputFile;

/**
 * @since 4.0
 */
public class BaseCayenneTask extends DefaultTask {

    private File map;
    private String mapFileName;

    @Optional
    @InputFile
    public File getMap() {
        return map;
    }

    public void setMap(File mapFile) {
        map = mapFile;
    }

    public void setMap(String mapFileName) {
        this.mapFileName = mapFileName;
    }

    public void map(String mapFileName) {
        setMap(mapFileName);
    }

    public void map(File mapFile) {
        setMap(mapFile);
    }

    public File getDataMapFile() {
        if (map != null) {
            return map;
        }

        if (mapFileName == null) {
            mapFileName = getProject().getExtensions().getByType(GradleCayenneExtension.class).getDefaultDataMap();
        }

        if (mapFileName != null) {
            return getProject().file(mapFileName);
        }

        throw new InvalidUserDataException("No datamap found in task or in cayenne.defaultDataMap.");
    }
}
