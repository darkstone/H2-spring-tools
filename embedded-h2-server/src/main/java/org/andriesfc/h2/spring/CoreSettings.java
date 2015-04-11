package org.andriesfc.h2.spring;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.stream.Stream;

@ConfigurationProperties(prefix = "h2.embedded")
public class CoreSettings extends ToolArgumentBuilder {

    @Autowired(required = false)
    DataSource dataSource;

    private boolean trace;
    private File baseDir;
    private boolean enabled = true;
    private boolean requiresDefaultDataSource;
    private boolean allowCreationOfDatabases = false;
    private String key;

    void buildToolArgs(Stream.Builder<String> cmdLine) {

        if (trace) {
            cmdLine.add("-trace");
        }

        if (baseDir != null) {
            cmdLine.add("-baseDir").add(baseDir.toString());
        }

        if (!allowCreationOfDatabases) {
            cmdLine.add("-ifExists");
        }

        if (key != null) {
            cmdLine.add("-key").add(key);
        }

    }

    public boolean isRequiresDefaultDataSource() {
        return requiresDefaultDataSource;
    }

    public void setRequiresDefaultDataSource(boolean requiresDefaultDataSource) {
        this.requiresDefaultDataSource = requiresDefaultDataSource;
    }

    public boolean isEnabled() {

        boolean checked = enabled;

        if (checked && requiresDefaultDataSource) {
            if (dataSource == null) {
                checked = false;
            }
            else try(Connection connection = dataSource.getConnection()) {
                checked = connection.getMetaData().getURL().startsWith("jdbc:h2:");
            }
            catch (SQLException e) {
                checked = false;
            }
        }

        return checked;

    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    public File getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(File baseDir) {
        this.baseDir = baseDir;
    }

    public void setTrace(boolean trace) {
        this.trace = trace;
    }

    public boolean isTrace() {
        return trace;
    }

    public boolean isAllowCreationOfDatabases() {
        return allowCreationOfDatabases;
    }

    public void setAllowCreationOfDatabases(boolean allowCreationOfDatabases) {
        this.allowCreationOfDatabases = allowCreationOfDatabases;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
