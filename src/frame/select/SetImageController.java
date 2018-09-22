package frame.select;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class SetImageController {

	@FXML
	private HBox ImageSet;

	@FXML
	private Text Set_name;

	public void InitSet(Path DirFolder, String Name_Set) throws IOException {
		Set_name.setText(Name_Set);
		ImageSet.setAlignment(Pos.CENTER);
		Files.walk(DirFolder).parallel().filter(f -> f.toFile().isFile()).forEach(e -> {

			try {
				MyImageView iv = new MyImageView(e);
				ImageSet.getChildren().add(iv);

			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});
		
		

	}

}
