package org.andriesfc.h2.spring;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "h2.embedded")
public class H2EmbeddableSettings {

    public static class ExposedPort {

        ExposedPort(boolean enabled) {
            this.enabled = enabled;
        }

        boolean enabled = false;
        int port = -1;
        boolean allowForAll = false;

        public void setPort(int port) {
            this.port = port;
        }

        public void setAllowForAll(boolean allowForAll) {
            this.allowForAll = allowForAll;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public int getPort() {
            return port;
        }

        public boolean isAllowForAll() {
            return allowForAll;
        }

        public boolean isEnabled() {
            return enabled;
        }
    }

    boolean databaseMustExists = true;
    boolean autoConfigureDataSource = true;

    final ExposedPort web = new ExposedPort(true);
    final ExposedPort jdbc = new ExposedPort(true);


    public ExposedPort getJdbc() {
        return jdbc;
    }

    public ExposedPort getWeb() {
        return web;
    }

    public void setDatabaseMustExists(boolean databaseMustExists) {
        this.databaseMustExists = databaseMustExists;
    }

    public boolean isDatabaseMustExists() {
        return databaseMustExists;
    }

    public boolean isAutoConfigureDataSource() {
        return autoConfigureDataSource;
    }

    public void setAutoConfigureDataSource(boolean autoConfigureDataSource) {
        this.autoConfigureDataSource = autoConfigureDataSource;
    }
}
