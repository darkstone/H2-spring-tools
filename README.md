# README #

# What is this #

This repository contains a library to enable complete server embeddable H2 database for spring/spring boot projects.

# Project Structure #

The project as 2 modules. The first one is simply called "lib", and is the 
actual library used to expose H2 to the sample spring boot application.

The 2nd one is a sample app, which demonstrate how to use the library.

# How to use #

##  Annotate your spring boot application

```
#!java
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
```

## Enable the web server, and or JDBC socket via the following properties


```
#!properties
h2.embedded.jdbc.enabled=true
h2.embedded.web.enabled=true
```


