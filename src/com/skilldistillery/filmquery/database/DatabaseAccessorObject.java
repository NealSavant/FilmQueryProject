package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Film findFilmById(int filmId) {
		Film film = null;
		String sql = "SELECT * FROM film WHERE id = ?";
		try (Connection connection = DriverManager.getConnection(URL, "student", "student")) {
			PreparedStatement s = connection.prepareStatement(sql);
			s.setInt(1, filmId);
			ResultSet r = s.executeQuery();
			while (r.next()) {
				int id = (r.getInt(1));
				String title = r.getString(2);
				String desc = r.getString(3);
				short rYear = r.getShort(4);
				short langId = r.getShort(5);
				byte rDuration = r.getByte(6);
				double rRate = r.getDouble(7);
				short length = r.getShort(8);
				double rCost = r.getDouble(9);
				String rating = r.getString(10);
				String features = r.getString(11);
				List<Actor> actors = findActorsByFilmId(filmId);
				film = new Film(id, title, desc, rYear, langId, rDuration, rRate, length, rCost, rating, features, actors);
			}
			r.close();
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return film;
	}

	@Override
	public Actor findActorById(int actorId) {
		Actor actor = null;
		String sql = "SELECT * FROM actor WHERE id = ?";
		try (Connection connection = DriverManager.getConnection(URL, "student", "student")) {
			PreparedStatement s = connection.prepareStatement(sql);
			s.setInt(1, actorId);
			ResultSet r = s.executeQuery();
			while (r.next()) {
				int id = (r.getInt(1));
				String fName = r.getString(2);
				String lName = r.getString(3);

				actor = new Actor(id, fName, lName);
			}
			r.close();
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList();
		String sql = "SELECT actor.id, actor.first_name, actor.last_name " +
						"FROM actor JOIN film_actor ON film_actor.actor_id =  actor.id " +
						"JOIN film ON film.id = film_actor.film_id " +
						"WHERE film.id = ?";
		try (Connection connection = DriverManager.getConnection(URL, "student", "student")) {
			PreparedStatement s = connection.prepareStatement(sql);
			s.setInt(1, filmId);
			ResultSet r = s.executeQuery();
			while (r.next()) {
				int id = (r.getInt(1));
				String fName = r.getString(2);
				String lName = r.getString(3);

				actors.add(new Actor(id, fName, lName));
			}
			r.close();
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actors;
	}

}
