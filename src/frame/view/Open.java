package frame.view;

import java.io.IOException;
import java.nio.file.Paths;

import frame.Main;
import frame.select.SetImageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Open implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("select/SetOfImages.fxml"));
		Scene scene;
		try {
			scene = new Scene(loader.load());
			
			SetImageController controller = loader.getController();
			controller.InitSet(Paths.get("B:\\All_Data_To_Scan\\duplicated\\-541427904030101350"), "liran");
			Stage stage = new Stage();
			stage.setTitle("New Window");
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
