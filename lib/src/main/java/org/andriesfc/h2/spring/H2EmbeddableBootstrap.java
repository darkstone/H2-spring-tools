package org.andriesfc.h2.spring;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(H2EmbeddableSettings.class)
@ConditionalOnClass(org.h2.Driver.class)
public class H2EmbeddableBootstrap {

    @Autowired
    private H2EmbeddableSettings configuration;

    @Autowired(required = false)
    private DataSource dataSource;

    private Server h2Server;

    @PostConstruct
    protected void init() {

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
}
