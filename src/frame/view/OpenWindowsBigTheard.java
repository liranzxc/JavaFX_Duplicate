package frame.view;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

import frame.Main;
import frame.bigwindow.bigwindowController;
import frame.select.SetImageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class OpenWindowsBigTheard implements Runnable {

	private ConcurrentMap<Long, Set<Path>> key_List_Path ;
	public OpenWindowsBigTheard(ConcurrentMap<Long, Set<Path>> key_List_Path) {
		// TODO Auto-generated constructor stub
		this.key_List_Path = key_List_Path;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("bigwindow/bigwindow.fxml"));
		try {
			Scene scene = new Scene(loader.load());
			bigwindowController controller = loader.getController();
			controller.initBuild(this.key_List_Path);
			Stage stage = new Stage();
			stage.setTitle("big Windows");
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
