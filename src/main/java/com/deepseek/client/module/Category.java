package com.deepseek.client.module;

public enum Category {
    COMBAT("Combat", "🗡️"),
    MOVEMENT("Movement", "🏃"),
    VISUAL("Visual", "👁️"),
    PLAYER("Player", "🧑"),
    WORLD("World", "🌍"),
    MISC("Misc", "⚙️");

    private final String name;
    private final String icon;

    Category(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() { return name; }
    public String getIcon() { return icon; }
}
