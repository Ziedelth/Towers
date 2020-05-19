package fr.ziedelth.towers.utils;

public enum GameStates {
    LOBBY,
    PREPARATION,
    GAME,
    END,
    ;

    private static GameStates state;

    public static GameStates getState() {
        return state;
    }

    public static void setState(GameStates state) {
        GameStates.state = state;
    }

    public static boolean isState(GameStates state) {
        return GameStates.state == state;
    }
}
