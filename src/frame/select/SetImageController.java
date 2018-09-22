package frame.select;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SetImageController {

	@FXML
	private HBox ImageSet;

	@FXML
	private Text Set_name;

	public void InitSet(Set<Path> SetOfImages, String Name_Set) throws IOException {
		Set_name.setText(Name_Set);
		SetOfImages.stream().forEach(e -> {
			try {
				MyImageView iv = new MyImageView(e);
				iv.SelectImageToDelete();
				ImageSet.getChildren().add(iv);

			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});
		((MyImageView) ImageSet.getChildren().stream().findFirst().get()).SelectImageToDelete();

	}

	public List<Path> getAllPathOnSetThatSelected() {
		return ImageSet.getChildren().parallelStream().filter(img -> ((MyImageView) img).isSeletedToDelete())
				.map(img -> ((MyImageView) img).getPath()).collect(Collectors.toList());
	}

}
