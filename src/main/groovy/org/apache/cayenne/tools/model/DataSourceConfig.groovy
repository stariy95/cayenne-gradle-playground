package org.apache.cayenne.tools.model

import org.gradle.api.InvalidUserDataException

/**
 * @since 4.0
 */
class DataSourceConfig {
    String driver
    String url
    String username
    String password

    def validate() {
        if(driver == null && url == null && username == null && password == null) {
            throw new InvalidUserDataException("Missing dataSource configuration.")
        }

        if(driver == null) {
            throw new InvalidUserDataException("Missing required 'driver' parameter in dataSource.")
        }

        if(url == null) {
            throw new InvalidUserDataException("Missing required 'url' parameter in dataSource.")
        }
    }
}
