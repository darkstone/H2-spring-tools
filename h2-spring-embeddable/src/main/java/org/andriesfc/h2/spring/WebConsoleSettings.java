package org.andriesfc.h2.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.stream.Stream;

@ConfigurationProperties(prefix = "h2.embedded.web")
public class WebConsoleSettings extends AbstractToolSettings {

    @Autowired
    public WebConsoleSettings(CoreSettings coreSettings) {
        super(coreSettings);
    }

    @Override
    void buildToolArgs(Stream.Builder<String> cmdLine) {

        if (getPort() > 0) {
            cmdLine.add("-webPort").add(String.valueOf(getPort()));
        }

        if (isSsl()) {
            cmdLine.add("-webSSL");
        }

        if (isAllowForAll()) {
            cmdLine.add("-webAllowOthers");
        }

        if (isDaemon()) {
            cmdLine.add("-webDaemon");
        }

    }
}
