package org.andriesfc.h2.spring;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.stream.Stream;

@ConfigurationProperties(prefix = "h2.embedded.jdbc")
public class JdbcServerSettings extends AbstractToolSettings {

    private String password;

    @Autowired
    public JdbcServerSettings(CoreSettings coreSettings) {
        super(coreSettings);
    }

    @Override
    void buildToolArgs(Stream.Builder<String> cmdLine) {

        if (isSsl()) {
            cmdLine.add("-tcpSSL");
        }

        if (getPort() > 0) {
            cmdLine.add("-tcpPort").add(String.valueOf(getPort()));
        }

        if (getPassword() != null) {
            cmdLine.add("-tcpPassword").add(getPassword());
        }

    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
