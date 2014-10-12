package no.asgari.civilization.server.model;

public enum GameType {
    BASE("Base game"), FAF("Fame and Fortune"), WAW("Wisdom and Warfare"), DOC("Dawn of Civilization");

    private String label;

    GameType(String name) {
        this.label = name;
    }

    @Override
    public String toString() {
        return label;
    }

}
