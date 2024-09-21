package org.sii;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.*;

public class InputHandler {
    private final Scanner scanner;
    private String input;
    private int age;
    private String experienceLevel;
    private String multiplayerPreference;
    private final List<String> genres;

    public InputHandler() {
        scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        genres = new ArrayList<>();
    }

    public void collectUserInput() throws IllegalArgumentException {
        System.out.println("Введите ваши предпочтения (например, 'Мне 12, опыта мало, мультиплейер хочу, мне нравятся: sandbox.'):");
        input = scanner.nextLine();

        extractAge();
        extractExperienceLevel();
        extractMultiplayerPreference();
        extractGenres();
    }

    private void extractAge() throws IllegalArgumentException {
        Pattern agePattern = Pattern.compile("Мне\\s+(\\d+)");
        Matcher ageMatcher = agePattern.matcher(input);
        if (ageMatcher.find()) {
            age = Integer.parseInt(ageMatcher.group(1));
        } else {
            throw new IllegalArgumentException("Возраст не найден.");
        }
    }

    private void extractExperienceLevel() throws IllegalArgumentException {
        Pattern expPattern = Pattern.compile("опыта\\s+(\\p{L}+)", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher expMatcher = expPattern.matcher(input);
        if (expMatcher.find()) {
            experienceLevel = expMatcher.group(1).toLowerCase();
            if (!experienceLevel.equals("много") && !experienceLevel.equals("мало")) {
                experienceLevel = "средне";
            }
        } else {
            throw new IllegalArgumentException("Уровень опыта не найден.");
        }
    }

    private void extractMultiplayerPreference() throws IllegalArgumentException {
        Pattern mpPattern = Pattern.compile("мультиплейер\\s+(\\p{L}+)", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher mpMatcher = mpPattern.matcher(input);
        if (mpMatcher.find()) {
            multiplayerPreference = mpMatcher.group(1).toLowerCase();
            if (!multiplayerPreference.equals("хочу") && !multiplayerPreference.equals("не хочу")) {
                multiplayerPreference = "все равно";
            }
        } else {
            throw new IllegalArgumentException("Предпочтение по мультиплееру не найдено в вводе.");
        }
    }

    private void extractGenres() throws IllegalArgumentException {
        Pattern genrePattern = Pattern.compile("нравятся:\\s*(.+)", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS);
        Matcher genreMatcher = genrePattern.matcher(input);
        if (genreMatcher.find()) {
            String[] genreArray = genreMatcher.group(1).split(",\\s*");
            for (String genre : genreArray) {
                genre = genre.trim().toLowerCase().replaceAll("[\\p{Punct}]+$", "");
                genres.add(genre);
            }
            if (genres.isEmpty()) {
                throw new IllegalArgumentException("Жанры не распознаны.");
            }
        } else {
            throw new IllegalArgumentException("Жанры не найдены в вводе.");
        }
    }

    public int getAge() {
        return age;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public String getMultiplayerPreference() {
        return multiplayerPreference;
    }

    public List<String> getGenres() {
        return genres;
    }
}
