package dev.tonimatas.ethylene.link.network.chat;

import java.util.stream.Stream;

public interface ComponentStreamLink<T> {
    Stream<T> stream();
}
