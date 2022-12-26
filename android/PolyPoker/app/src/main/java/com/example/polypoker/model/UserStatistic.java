package com.example.polypoker.model;

public class UserStatistic {

    private int id;

    private String login;

    private int totalGamesPlayed;
    private int winGames;
    private int currentCoinsCount;
    private int totalEarn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public void setTotalGamesPlayed(int totalGamesPlayed) {
        this.totalGamesPlayed = totalGamesPlayed;
    }

    public int getWinGames() {
        return winGames;
    }

    public void setWinGames(int winGames) {
        this.winGames = winGames;
    }

    public int getCurrentCoinsCount() {
        return currentCoinsCount;
    }

    public void setCurrentCoinsCount(int currentCoinsCount) {
        this.currentCoinsCount = currentCoinsCount;
    }

    public int getTotalEarn() {
        return totalEarn;
    }

    public void setTotalEarn(int totalEarn) {
        this.totalEarn = totalEarn;
    }

    @Override
    public String toString() {
        return "UserStatistic{" +
                "login='" + login + '\'' +
                ", totalGamesPlayed=" + totalGamesPlayed +
                ", winGames=" + winGames +
                ", currentCoinsCount=" + currentCoinsCount +
                ", totalEarn=" + totalEarn +
                '}';
    }
}
