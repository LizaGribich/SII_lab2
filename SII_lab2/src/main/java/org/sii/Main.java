package org.sii;

import java.io.IOException;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        InputHandler inputHandler = new InputHandler();
        inputHandler.collectUserInput();

        String prologFile = "/lab1.pl";
        PrologConnector prologConnector = new PrologConnector(prologFile);
        prologConnector.loadKnowledgeBase();

        GameRecommender recommender = new GameRecommender(
                inputHandler.getAge(),
                inputHandler.getExperienceLevel(),
                inputHandler.getMultiplayerPreference(),
                inputHandler.getGenres()
        );

        Set<String> recommendedGames = recommender.getRecommendedGames();

        if (!recommendedGames.isEmpty()) {
            System.out.println("Рекомендуемые игры:");
            for (String game : recommendedGames) {
                System.out.println("- " + game);
            }
        } else {
            System.out.println("Игры, соответствующие вашим предпочтениям, не найдены.");
        }
    }
}
