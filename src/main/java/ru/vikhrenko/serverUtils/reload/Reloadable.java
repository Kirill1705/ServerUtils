package ru.vikhrenko.serverUtils.reload;

import java.nio.file.Path;

public interface Reloadable {
    void reload(Path file);
}
