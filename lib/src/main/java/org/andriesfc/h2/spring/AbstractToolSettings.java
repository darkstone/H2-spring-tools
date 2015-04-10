package org.andriesfc.h2.spring;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;


abstract class AbstractToolSettings extends ToolArgumentBuilder {

    final CoreSettings coreSettings;
    private boolean enabled = false;
    private int port;
    private boolean allowForAll;
    private boolean daemon;
    private boolean ssl;


    @Autowired
    public AbstractToolSettings(CoreSettings coreSettings) {
        this.coreSettings = coreSettings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractToolSettings)) return false;
        AbstractToolSettings that = (AbstractToolSettings) o;
        return Objects.equals(isEnabled(), that.isEnabled()) &&
                Objects.equals(getPort(), that.getPort()) &&
                Objects.equals(isAllowForAll(), that.isAllowForAll()) &&
                Objects.equals(isDaemon(), that.isDaemon()) &&
                Objects.equals(isSsl(), that.isSsl()) &&
                Objects.equals(coreSettings, that.coreSettings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coreSettings, isEnabled(), getPort(), isAllowForAll(), isDaemon(), isSsl());
    }

    public boolean isEnabled() {
        return enabled && coreSettings.isEnabled();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isAllowForAll() {
        return allowForAll;
    }

    public void setAllowForAll(boolean allowForAll) {
        this.allowForAll = allowForAll;
    }


    public boolean isDaemon() {
        return daemon;
    }

    public void setDaemon(boolean daemon) {
        this.daemon = daemon;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    public boolean isSsl() {
        return ssl;
    }

}
