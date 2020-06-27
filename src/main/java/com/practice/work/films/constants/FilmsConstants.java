package com.practice.work.films.constants;

public final class FilmsConstants {

    private FilmsConstants() {
        throw new AssertionError("Attempting to instantiate static utility class.");
    }

    public static final String NAME_REGEX = "[a-zA-Z,.'\\-\\s]+";

}
