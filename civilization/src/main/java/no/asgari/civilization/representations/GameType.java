package no.asgari.civilization.representations;

public enum GameType {
    BASE("Base game"), FAF("Fame and Fortune"), WAW("Wisdom and Warfare");

    private String label;

    GameType(String name) {
        this.label = name;
    }

    @Override
    public String toString() {
        return label;
    }

}
