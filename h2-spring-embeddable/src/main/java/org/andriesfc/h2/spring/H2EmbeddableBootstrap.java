package org.andriesfc.h2.spring;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
@EnableConfigurationProperties({CoreSettings.class, JdbcServerSettings.class, WebConsoleSettings.class})
@ConditionalOnClass(org.h2.Driver.class)
public class H2EmbeddableBootstrap  {

    private Logger logger = LoggerFactory.getLogger(H2EmbeddableBootstrap.class);

    @Autowired
    protected CoreSettings coreSettings;

    @Autowired
    protected JdbcServerSettings jdbcSocketServerSettings;

    @Autowired
    protected WebConsoleSettings webConsoleSettings;

    @Bean(destroyMethod = "stop", initMethod = "start")
    @ConditionalOnProperty(name = "h2.embedded.jdbc.enabled", havingValue = "true")
    public Server h2EmbeddedJdbcSocketServer() throws SQLException {
        final String[] toolArgs = ToolArgumentBuilder.toolArgs(coreSettings, jdbcSocketServerSettings);
        final Server tcpServer = Server.createTcpServer(toolArgs);
        logger.info("Initialized h2EmbeddedJdbcSocketServer: [port={}] [url={}]", tcpServer.getPort(), tcpServer.getURL());
        return tcpServer;
    }

    @Bean(destroyMethod = "stop", initMethod = "start")
    @ConditionalOnProperty(name = "h2.embedded.web.enabled", havingValue = "true")
    public Server h2EmbeddedWebConsole() throws SQLException {
        final String[] toolArgs = ToolArgumentBuilder.toolArgs(coreSettings, webConsoleSettings);
        final Server webServer = Server.createWebServer(toolArgs);
        logger.info("Initialized h2EmbeddedWebConsole: [PORT={}] [URL={}]", webServer.getPort(), webServer.getURL());
        return webServer;
    }

}
