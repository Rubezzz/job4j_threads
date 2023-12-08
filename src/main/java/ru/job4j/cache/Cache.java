package ru.job4j.cache;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) throws OptimisticException {
        return memory.putIfAbsent(model.id(), model) == null;
    }

    public boolean update(Base model) throws OptimisticException {
        BiFunction<Integer, Base, Base> func = (a, b) -> {
            if (model.version() != b.version()) {
                throw new OptimisticException("Versions are not equal");
            }
            return new Base(model.id(), model.name(), model.version() + 1);
        };
        return memory.computeIfPresent(model.id(), func) != null;
    }

    public void delete(int id) {
        memory.remove(id);
    }

    public Optional<Base> findById(int id) {
        return Optional.ofNullable(memory.get(id));
    }
}