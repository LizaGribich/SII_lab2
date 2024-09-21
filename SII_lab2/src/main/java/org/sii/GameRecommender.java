package org.sii;


import org.jpl7.Query;
import org.jpl7.Term;

import java.util.*;

public class GameRecommender {
    private final int age;
    private final String experienceLevel;
    private final String multiplayerPreference;
    private final List<String> genres;

    public GameRecommender(int age, String experienceLevel, String multiplayerPreference, List<String> genres) {
        this.age = age;
        this.experienceLevel = experienceLevel;
        this.multiplayerPreference = multiplayerPreference;
        this.genres = genres;
    }

    public Set<String> getRecommendedGames() {
        String queryStr = buildPrologQuery();
        Query query = new Query(queryStr);
        Set<String> recommendedGames = new HashSet<>();

        while (query.hasMoreSolutions()) {
            Map<String, Term> solution = query.nextSolution();
            String game = solution.get("Game").name();
            recommendedGames.add(game);
        }

        return recommendedGames;
    }

    private String buildPrologQuery() {
        //для жанров
        StringBuilder genreQueryPart = new StringBuilder();
        for (int i = 0; i < genres.size(); i++) {
            String genre = genres.get(i).toLowerCase().replace(" ", "_");
            genreQueryPart.append("genre(Game, ").append(genre).append(")");
            if (i < genres.size() - 1) {
                genreQueryPart.append("; ");
            }
        }

        // маппинг уровня опыта
        List<String> difficultyLevels = mapExperienceToDifficulty();

        // маппинг мультиплеера
        String multiplayerFilter = mapMultiplayerPreference();

        // построение запроса
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("(").append(genreQueryPart).append("), ");
        queryBuilder.append("rating(Game, GameRating), GameRating =< ").append(age);

        if (!difficultyLevels.containsAll(Arrays.asList("low", "medium", "high"))) {
            queryBuilder.append(", (");
            for (int i = 0; i < difficultyLevels.size(); i++) {
                queryBuilder.append("difficulty(Game, ").append(difficultyLevels.get(i)).append(")");
                if (i < difficultyLevels.size() - 1) {
                    queryBuilder.append("; ");
                }
            }
            queryBuilder.append(")");
        }

        if (!multiplayerFilter.isEmpty()) {
            queryBuilder.append(", ").append(multiplayerFilter);
        }

        System.out.println("Запрос: " + queryBuilder);

        return queryBuilder.toString();
    }

    private List<String> mapExperienceToDifficulty() {
        List<String> difficultyLevels;
        switch (experienceLevel) {
            case "много":
                difficultyLevels = Arrays.asList("low", "medium", "high");
                break;
            case "мало":
                difficultyLevels = Collections.singletonList("low");
                break;
            default:
                difficultyLevels = Arrays.asList("low", "medium");
                break;
        }
        return difficultyLevels;
    }

    private String mapMultiplayerPreference() {
        if (multiplayerPreference.equals("хочу")) {
            return "multiplayer(Game, true)";
        } else if (multiplayerPreference.equals("не хочу")) {
            return "multiplayer(Game, false)";
        } else {
            return "";
        }
    }
}
