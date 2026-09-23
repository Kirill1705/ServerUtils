package ru.vikhrenko.serverUtils.reload;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import ru.vikhrenko.serverUtils.json.ModuleCreator;
import ru.vikhrenko.serverUtils.reload.Reloadable;

import java.io.IOException;
import java.nio.file.Path;

public abstract class YamlAbstractReloadable<T> implements Reloadable {
    private T options;
    private final Class<T> clazz;

    @Override
    public void reload(Path file) {
        YAMLMapper mapper = YAMLMapper.builder()
                .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .addModule(new ModuleCreator().pointModule())
                .build();
        try {
            options = mapper.readValue(file.toFile(), clazz);
            validate();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void validate() {

    }

    protected T getOptions() {
        return options;
    }

    protected YamlAbstractReloadable(T options, Class<T> clazz) {
        this.options = options;
        this.clazz = clazz;
    }
}
