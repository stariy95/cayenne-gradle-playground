package org.apache.cayenne.tools

import org.gradle.api.DefaultTask
import org.gradle.api.InvalidUserDataException

/**
 * @since 4.0
 */
class BaseCayenneTask extends DefaultTask {

    File map

    private String mapFileName

    void setMap(String mapFileName) {
        this.mapFileName = mapFileName
    }

    protected File getDataMapFile() {
        if(map != null) {
            return map
        }

        if(mapFileName == null) {
            mapFileName = getProject().extensions.getByType(GradleCayenneExtension).defaultDataMap
        }

        if(mapFileName != null) {
            return getProject().file(mapFileName)
        }

        throw new InvalidUserDataException("No datamap found in task or in cayenne.defaultDataMap.")
    }
}
