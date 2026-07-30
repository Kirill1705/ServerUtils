package ru.vikhrenko.serverUtils.utils;

public enum ExtendedSound {
    WITCH_SPAWN,
    WITCH_SKELETON,
    FIREBALL1,
    FIREBALL2,
    BOMBER_SPAWN,
    BOMBER_SHOOT,
    BOMBER_DEATH,
    GRAVEYARD_SPELL,
    MONK_ABILITY,
    WITCH_SKELETON2,
    WITCH_HEAL,
    DRAGON_WAND,
    EVO_DEATH,
    GHAST_HEAL;
    public String getName() {
        return toString().toLowerCase();
    }
}
