package application;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import com.sun.media.jfxmedia.logging.Logger;

import java.sql.Connection;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Main extends Application implements EventHandler<ActionEvent> {

	Stage window;
	Scene scene1, scene2;
	public static String url = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
	Counter raven = new Counter();
	static Counter use = new Counter();

	public static void main(String[] args) throws Exception {

		createTable();
		InsertWordsFromArray();
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		window = primaryStage;
		Label label1 = new Label("The Raven's most used Words!");
		label1.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
		Button b1 = new Button("Generate List");
		b1.setOnAction(e -> window.setScene(scene2));

		// Layout 1 - children are laid out in vertical column
		VBox lay1 = new VBox(15);
		lay1.setAlignment(Pos.CENTER);
		lay1.getChildren().addAll(label1, b1);
		scene1 = new Scene(lay1, 325, 200);

		// Button 2
//		List<Entry<String, Integer>> result = raven
//				.getPoemWordFrequency("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm");
		// List<String> pText = get();
		Text c = new Text("In DESC order:");
		Text w = new Text(get().toString());
		c.setFont(Font.font("Helvetica", 15));
		w.setFont(Font.font("Helvetica", 15));
		w.setWrappingWidth(500);
		Button b2 = new Button("Restart");
		b2.setOnAction(e -> window.setScene(scene1));

		// Layout 2
		VBox lay2 = new VBox(20);
		lay2.setAlignment(Pos.CENTER);
		lay2.getChildren().addAll(c, w, b2);
		scene2 = new Scene(lay2, 600, 350);

		window.setScene(scene1);
		window.setTitle("The Raven's Word Frequency");
		window.show();

	}

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	public static void createTable() throws Exception {

		try {
			Connection con = getConnection();
			
			PreparedStatement delete = con
					.prepareStatement("DROP Table if exists words ");
			delete.execute();
			
			PreparedStatement create = con
					.prepareStatement("CREATE TABLE Words ( Id int PRIMARY KEY Auto_increment, word varchar(255)) ");
			create.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("Function Complete");
		}

	}

	public static Connection getConnection() throws Exception {
		
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/word_occurrences";
		String username = "root";
		String password = "otenko";
		Class.forName(driver);

		Connection conn = DriverManager.getConnection(url, username, password);
		System.out.println("Connected");
		System.out.println("");
		return conn;

	}

	public static void InsertWordsFromArray() throws Exception {

		try {
			List<String> stringList = use.GrabUrlText(url);
			Connection con = getConnection();
			for (String word : stringList) {
				PreparedStatement post = con.prepareStatement("INSERT INTO words (word) VALUES ('" + word + "')");
				post.executeUpdate();
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("Insert Completed!");
		}
	}

	public static List<String> get() throws Exception {

		try {
			Connection con = getConnection();
			PreparedStatement st = con.prepareStatement(
					"SELECT word, count(word) as freq FROM words group by word ORDER BY count(word) DESC, word ASC LIMIT 20");

			ResultSet rs = st.executeQuery();

			ArrayList<String> array = new ArrayList<String>();
			while (rs.next()) {
				System.out.println(rs.getString("word") + ": " + rs.getString("freq"));
				array.add(rs.getString("word") + ": " + rs.getString("freq"));
			}
			System.out.println("");
			System.out.println("All records have been selected.");

			return array;

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;

	}
}
