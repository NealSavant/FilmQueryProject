package com.skilldistillery.filmquery.app;

import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
//    app.test();
		app.launch();
	}

//  private void test() {
//    Film film = db.findFilmById(1);
//    System.out.println(film);
////	  Actor actor = db.findActorById(1);
////	  System.out.println(actor);
////	  List<Actor> actors = db.findActorsByFilmId(3);
////	  System.out.println(actors);
//	  
//  }

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		boolean running = true;
		int choice;
		while (running) {
			System.out.println("\n\t~~Welcome to SDVid~~");
			// demands correct user input into terminal
			userEntry: while (true) {
				System.out.println("Choose number from list");
				System.out.println("\t1. Look up a film by its id.");
				System.out.println("\t2. Look up a film by keyword");
				System.out.println("\t3. Exit application");
				try {
					choice = input.nextInt();
					if (choice < 1 || choice > 3)
						throw new Exception();
					else
						break userEntry;
				} catch (Exception e) {
					System.err.println("Choose a number 1, 2, or 3.");
				}
			}
			userChoiceMenu(choice, input);
			if (choice == 3)
				running = false;
		}
	}

	private void userChoiceMenu(int choice, Scanner input) {
		switch (choice) {
		case 1:
			System.out.print("\nEnter film id: ");
			int id = input.nextInt();
			Film filmByID = db.findFilmById(id);
			if (filmByID == null) {
				System.err.println("\tFilm not found in archives.");
			} else {
				System.out.println(filmByID);
			}
			break;
		case 2:
			System.out.println("\nEnter keyword: ");
			String keyword = input.next();
			List<Film> films = db.findFilmByKeyword(keyword);
			System.out.println("\n\t Retrieved " +
								db.getCounter() +
								" Films:" +
								"\n" +
								films);
			break;
		case 3:
			System.out.println("Goodbye.");
			break;

		}

	}

}
