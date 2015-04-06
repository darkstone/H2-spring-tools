package org.andriesfc.h2.spring.embeddedsample;


import org.andriesfc.h2.spring.ExposeEmbeddedH2Server;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@ExposeEmbeddedH2Server
public class SampleApp {

    @Bean(destroyMethod = "countDown")
    CountDownLatch shutdown() {
        return new CountDownLatch(1);
    }

    public static void main(String[] args) throws InterruptedException {

        ConfigurableApplicationContext context = new SpringApplicationBuilder()
                .sources(SampleApp.class)
                .registerShutdownHook(true)
                .headless(true)
                .web(false)
                .run(args);

        CountDownLatch shutdown = context.getBean(CountDownLatch.class);
        shutdown.await();
    }
}
