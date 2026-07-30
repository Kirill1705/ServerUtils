package ru.vikhrenko.serverUtils.reload;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import ru.vikhrenko.serverUtils.reload.Reloadable;

import java.io.IOException;
import java.nio.file.Path;

public abstract class YamlAbstractReloadable<T> implements Reloadable {
    private T options;
    private final Class<T> clazz;

    public T getOptions() {
        return options;
    }

    protected YamlAbstractReloadable(T options, Class<T> clazz) {
        this.options = options;
        this.clazz = clazz;
    }

    @Override
    public void reload(Path file) {
        YAMLMapper mapper = new YAMLMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            options = mapper.readValue(file.toFile(), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
