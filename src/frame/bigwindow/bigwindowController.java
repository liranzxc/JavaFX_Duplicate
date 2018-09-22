package frame.bigwindow;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import frame.Main;
import frame.select.SetImageController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class bigwindowController {

	@FXML
	private VBox groupofsets;
	int count = 1;
	private List<SetImageController> list_of_Controllers = new ArrayList<>();

	public void initBuild(ConcurrentMap<Long, Set<Path>> key_List_Path) {

		// open get set<Path>
		key_List_Path.values().stream().forEach(set -> {

			groupofsets.getChildren().add(createSetImageScene(set, count));
			count++;
		});

		Button Remove = new Button("Remove all selected");
		Remove.setOnAction(e -> {

			ShowConfirmationMessage();

		});

		groupofsets.getChildren().add(Remove);
		// #1b253e

	}

	private void ShowConfirmationMessage() {
		// TODO Auto-generated method stub

		Alert alert1 = new Alert(AlertType.CONFIRMATION);
		alert1.setTitle("Confirmation Dialog");
		alert1.setHeaderText("Are you sure that you want delete all files");
		alert1.setContentText("after click OK , you cant recover your files");

		Optional<ButtonType> result = alert1.showAndWait();
		if (result.get() == ButtonType.OK) {

			list_of_Controllers.stream().forEach(controller -> {

				DeleteFiles(controller.getAllPathOnSetThatSelected());

			});

			ShowInformationMethod();

		}

	}

	private void ShowInformationMethod() {
		// TODO Auto-generated method stub
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("All Files are Deleted");
		alert.setContentText("Your Filed deleted successfully");

		Optional<ButtonType> result2 = alert.showAndWait();
		if (result2.get() == ButtonType.OK) {

			Platform.exit();
		}

	}

	private void DeleteFiles(List<Path> allPathOnSetThatSelected) {
		// TODO Auto-generated method stub

		allPathOnSetThatSelected.stream().forEach(p -> {
			try {
				Files.deleteIfExists(p);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}

	private Node createSetImageScene(Set<Path> set, int count) {
		// TODO Auto-generated method stub

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("select/SetOfImages.fxml"));
		try {
			AnchorPane pane = loader.load();
			SetImageController controller = loader.getController();
			controller.InitSet(set, "Set " + count);
			list_of_Controllers.add(controller);

			return pane;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
