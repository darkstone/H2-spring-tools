package org.andriesfc.h2.spring;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@EnableConfigurationProperties(H2EmbeddableSettings.class)
@ConditionalOnClass(org.h2.Driver.class)
public class H2EmbeddableBootstrap  {

    private Logger logger = LoggerFactory.getLogger(H2EmbeddableBootstrap.class);

    @Autowired
    protected H2EmbeddableSettings configuration;

    @Autowired(required = false)
    protected DataSource dataSource;

    @Autowired
    protected ConfigurableBeanFactory configurableBeanFactory;

    private Server embeddedH2Server;
    private boolean enabled;

    void checkConfiguration() throws SQLException {

        enabled = false;

        if (!(configuration.jdbc.enabled || configuration.web.enabled)) {
            logger.debug("Not embedding H2 Server: Neither jdbc or web ports are enable by configuration.");
            return;
        }

        if (dataSource == null && configuration.embeddedDataSourceMustExists) {
            logger.debug("Not embedding H2 server: Configuration requires an existing H2 datasource.");
            return;
        }

        if (dataSource != null) {
            boolean isH2Connection = false;
            try (Connection connection = dataSource.getConnection()) {
                isH2Connection = connection.getMetaData().getURL().toLowerCase().startsWith("jdbc:h2:");
            }

            if (isH2Connection) {
                return;
            }

            if (!configuration.autoConfigureDataSource) {
                logger.debug("Not embedding H2 Server: Configuration requires default data source to be an H2 database.");
                return;
            }
        }

        enabled = true;
   }

    @PostConstruct
    protected void init() throws SQLException {

        checkConfiguration();

        if (enabled) {
            logger.debug("H2 Embedding mode has been enabled. Proceeding to configure H2 server");
        }

        

    }

    public Server embeddedH2Server() {
        return embeddedH2Server;
    }

    public H2EmbeddableSettings getConfiguration() {
        return configuration;
    }

    public void setConfiguration(H2EmbeddableSettings configuration) {
        this.configuration = configuration;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setConfigurableBeanFactory(ConfigurableBeanFactory configurableBeanFactory) {
        this.configurableBeanFactory = configurableBeanFactory;
    }
}
