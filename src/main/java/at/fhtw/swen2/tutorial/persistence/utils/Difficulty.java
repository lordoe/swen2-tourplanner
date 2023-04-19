package at.fhtw.swen2.tutorial.persistence.utils;

public enum Difficulty {
    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard");

    Difficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    public final String difficulty;
}
