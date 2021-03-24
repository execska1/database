package com.codecool.database;

import java.sql.*;

public class RadioCharts {
    private final String URL;
    private final String USER;
    private final String PASS;

    public RadioCharts(String URL, String USER, String PASS) {
        this.URL = URL;
        this.USER = USER;
        this.PASS = PASS;
    }

    public String getMostPlayedSong() {
        return execute("SELECT song FROM music_broadcast " +
                "GROUP BY song " +
                "ORDER BY SUM(times_aired) DESC LIMIT 1;");
    }

    public String getMostActiveArtist() {
        return execute("SELECT artist FROM music_broadcast " +
                "GROUP BY artist " +
                "ORDER BY COUNT(DISTINCT song) DESC LIMIT 1;");
    }

    private String execute(String SQL) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);
            resultSet.next();
            return resultSet.getString(1);
        } catch (SQLException e) {
            return "";
        }
    }

}