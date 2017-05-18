package org.apache.cayenne.tools.model;

import org.gradle.api.InvalidUserDataException;

/**
 * @since 4.0
 */
public class DataSourceConfig {

    private String driver;
    private String url;
    private String username;
    private String password;

    public void validate() {
        if (driver == null && url == null && username == null && password == null) {
            throw new InvalidUserDataException("Missing dataSource configuration.");
        }

        if (driver == null) {
            throw new InvalidUserDataException("Missing required 'driver' parameter in dataSource.");
        }

        if (url == null) {
            throw new InvalidUserDataException("Missing required 'url' parameter in dataSource.");
        }
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void driver(String driver) {
        setDriver(driver);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void url(String url) {
        setUrl(url);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void username(String username) {
        setUsername(username);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void password(String password) {
        setPassword(password);
    }

}
