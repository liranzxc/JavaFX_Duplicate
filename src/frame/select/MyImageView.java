package frame.select;

import java.net.MalformedURLException;
import java.nio.file.Path;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MyImageView extends ImageView {

	private Path path;

	public MyImageView(Path path) throws MalformedURLException {
		super(new Image(path.toUri().toURL().toString(), 300, 300, false, false));
		this.path = path;
		this.setOnMouseClicked(e -> {

			// on click

			if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
				System.out.println("double click");
				// open zoom

				try {
					OpenZoomImage();
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		this.setOnMouseEntered(e -> {
			int depth = 70;

			DropShadow borderGlow = new DropShadow();
			borderGlow.setOffsetY(0f);
			borderGlow.setOffsetX(0f);
			borderGlow.setColor(Color.WHITE);
			borderGlow.setWidth(depth);
			borderGlow.setHeight(depth);

			this.setEffect(borderGlow);
		});

		this.setOnMouseExited(e -> {

			this.setEffect(null);
		});
	}

	private void OpenZoomImage() throws MalformedURLException {
		// TODO Auto-generated method stub
		Stage stage = new Stage();
		ImageView iv = new ImageView(new Image(path.toUri().toURL().toString()));
		Pane panel = new Pane(iv);
		ScrollPane s1 = new ScrollPane();
		s1.setContent(panel);

		stage.setTitle("My New Stage Title");
		stage.setScene(new Scene(s1, 800, 800));
		stage.show();

	}
}
