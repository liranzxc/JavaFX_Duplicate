package frame;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Main extends Application {

	private static Stage PrimaryStage;
	private AnchorPane mainPane;

	@Override
	public void start(Stage primaryStage) throws IOException {

		PrimaryStage = primaryStage;
		PrimaryStage.setTitle("Duplicate Image");

		FXMLLoader loader = new FXMLLoader();

		loader.setLocation(Main.class.getResource("view/Main_View.fxml"));
		mainPane = loader.load();
		Scene scene = new Scene(mainPane);
		PrimaryStage.setScene(scene);
		PrimaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public static File ChooseDirectory() {

		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Choose a folder to scan");
		File defaultDirectory = new File("C:\\");
		chooser.setInitialDirectory(defaultDirectory);
		File selectedDirectory = chooser.showDialog(PrimaryStage);

		if (selectedDirectory != null) {
			return selectedDirectory;
		} else
			return null;
	}

}

