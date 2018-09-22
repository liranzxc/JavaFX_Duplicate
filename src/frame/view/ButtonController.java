package frame.view;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import frame.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

public class ButtonController {
	@FXML
	private Text Path_information;
	@FXML
	private Text Status;

	@FXML
	private ProgressBar process;
	private File directory;
	private Main main;

	private ConcurrentMap<Path, Long> name_key = new ConcurrentHashMap<>();
	private ConcurrentMap<Long, Set<Path>> key_List_Path = new ConcurrentHashMap<>();

	@FXML
	public void DirButton() {

		SetStatusAndProcessBar("Restart", 0);
		directory = main.ChooseDirectory();
		if (directory != null) {
			Path_information.setText(directory.getAbsolutePath());
		} else {
			Path_information.setText("");
		}
	}

	@FXML
	public void AnalayzeButton() throws IOException, InterruptedException {

		if (directory != null) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					Scan_Object_For_duplication scanobj = new Scan_Object_For_duplication();

					try {
						name_key = scanobj.BuildMyMapInformation(directory.getAbsolutePath());

						SetStatusAndProcessBar("finish build a map files", 0.2);

						List<Long> list_of_all_keys_more_than_one = scanobj
								.Get_All_keys_That_Have_more_than_one(name_key);

						SetStatusAndProcessBar("finish get all keys ", 0.4);

						scanobj.Get_All_Path_need_to_deleted(list_of_all_keys_more_than_one, name_key, key_List_Path);

						SetStatusAndProcessBar("finish get files ", 0.6);

						scanobj.Copy_all_Files_to_duplicated(directory.getAbsolutePath(), key_List_Path);

						SetStatusAndProcessBar("finish Copy all files ", 1);

						
						// open Big windows of sets 
						Platform.runLater(new Open());

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}).start();

		}

	}

	private void SetStatusAndProcessBar(String status, double value) {
		Status.setText(status);
		process.setProgress(value);
	}

}
