package frame.select;

import java.net.MalformedURLException;
import java.nio.file.Path;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.scene.BoundsAccessor;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MyImageView extends ImageView {

	private Path path;
	private boolean SeletedToDelete = false;

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public boolean isSeletedToDelete() {
		return SeletedToDelete;
	}

	public void setSeletedToDelete(boolean seletedToDelete) {
		SeletedToDelete = seletedToDelete;
	}

	public MyImageView(Path path) throws MalformedURLException {
		super(new Image(path.toUri().toURL().toString(), 300, 300, false, false));
		this.path = path;
		this.setOnMouseClicked(e -> {

			// on click
			SelectImageToDelete();

			if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
				// open zoom

				try {
					OpenZoomImage();
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		});

	}

	public void SelectImageToDelete() {
		// TODO Auto-generated method stub

		this.SeletedToDelete = !this.SeletedToDelete;

		if (this.SeletedToDelete) {

			InnerShadow innerShadow = new InnerShadow(15.0, 15.0, 15.0, Color.LIGHTGREEN);
			this.setEffect(innerShadow);

		} else {
			this.setEffect(null);
		}

	}

	private void OpenZoomImage() throws MalformedURLException {
		// TODO Auto-generated method stub
		Stage stage = new Stage();
		Image img = new Image(path.toUri().toURL().toString());
		ImageView iv = new ImageView(img);
		Pane panel = new Pane(iv);
		ScrollPane s1 = new ScrollPane();
		s1.setContent(panel);

		stage.setTitle("Image");
	
		stage.setScene(new Scene(s1, img.getWidth(),img.getHeight()));
		stage.show();

	}
}
