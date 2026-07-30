package ru.vikhrenko.serverUtils.utils;

import ru.vikhrenko.serverUtils.utils.ExtendedSound;

import java.util.function.Consumer;

public final class SoundUtils {
    public static void playRandomSoundOf(ru.vikhrenko.serverUtils.utils.ExtendedSound[] sounds, Consumer<ExtendedSound> playSoundFunction) {
        int idx = (int) (Math.random()*sounds.length);
        playSoundFunction.accept(sounds[idx]);
    }
}
