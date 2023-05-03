package at.fhtw.swen2.tutorial.persistence.utils;

public enum Difficulty {

    SUPER_EASY("super easy"),
    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard"),
    EXTREME("extreme");

    Difficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    public final String difficulty;
}
