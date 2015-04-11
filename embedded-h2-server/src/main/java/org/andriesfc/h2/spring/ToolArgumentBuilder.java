package org.andriesfc.h2.spring;

import java.util.stream.Stream;

abstract class ToolArgumentBuilder {

    abstract void buildToolArgs(Stream.Builder<String> cmdLine);

    static String[] toolArgs(ToolArgumentBuilder... settings) {
        Stream.Builder<String> args = Stream.builder();
        Stream.of(settings).forEach(c -> c.buildToolArgs(args));
        return args.build().toArray(String[]::new);
    }


}
