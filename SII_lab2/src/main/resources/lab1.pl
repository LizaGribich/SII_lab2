game(minecraft).
game(slimerancher).
game(spore).
game(skyrim).

character(steve).
character(alexa).
character(herobrine).
character(villager).

character(beatrix).
character(bob).
character(victor).
character(ogden).

character(may).
character(hook).
character(mitro).
character(ramzi).
character(keter).

character(dovakin).
character(daedra).
character(addhiranirr).

relation(minecraft, steve, playable).
relation(minecraft, alexa, playable).
relation(minecraft, herobrine, antagonist).
relation(minecraft, villager, npc).

relation(slimerancher, beatrix, playable).
relation(slimerancher, bob, npc).
relation(slimerancher, victor, npc).
relation(slimerancher, ogden, npc).

relation(spore, may, playable).
relation(spore, hook, npc).
relation(spore, mitro, npc).
relation(spore, ramzi, npc).
relation(spore, keter, antagonist).

relation(skyrim, dovakin, playable).
relation(skyrim, daedra, antagonist).
relation(skyrim, addhiranirr, npc).

rating(minecraft, 6).
rating(slimerancher, 6).
rating(spore, 12).
rating(skyrim, 16).

difficulty(minecraft, low).
difficulty(slimerancher, low).
difficulty(spore, medium).
difficulty(skyrim, high).

multiplayer(minecraft, true).
multiplayer(slimerancher, false).
multiplayer(spore, true).
multiplayer(skyrim, false).

genre(minecraft, sandbox).
genre(slimerancher, adventure).
genre(spore, adventure).
genre(skyrim, rpg).

recommend_game_by_genre(UserGenre, Game) :-
    genre(Game, UserGenre).

recommend_game_by_difficulty(UserDifficulty, Game) :-
    difficulty(Game, UserDifficulty).

recommend_game_with_multiplayer(Game) :-
    multiplayer(Game, true).

recommend_game_by_rating(UserRating, Game) :-
    rating(Game, GameRating),
    GameRating =< UserRating.

recommend_game_by_genre_and_difficulty(UserGenre, UserDifficulty, Game) :-
    genre(Game, UserGenre),
    difficulty(Game, UserDifficulty).

recommend_game_with_antagonist(Game) :-
    relation(Game, _, antagonist).
