package fi.joniaromaa.p2pchat;

import java.io.File;
import java.util.Optional;

import fi.joniaromaa.p2pchat.chat.ChatManager;
import fi.joniaromaa.p2pchat.identity.MyIdentity;
import fi.joniaromaa.p2pchat.storage.Storage;
import fi.joniaromaa.p2pchat.storage.sqlite.SqliteStorage;
import fi.joniaromaa.p2pchat.ui.FirstStartController;
import fi.joniaromaa.p2pchat.ui.PanelController;
import fi.joniaromaa.p2pchat.utils.EncryptionUtils;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Program extends Application {
	// TODO: WHAT DO WE DO TO THIS
	private static Scene scene;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		if (!EncryptionUtils.isInitialized()) {
			Alert alert = new Alert(AlertType.ERROR, "Encryption utils is unable to initialize");
			alert.show();

			return;
		}

		Storage storage = new SqliteStorage(new File("test.db"));

		Optional<MyIdentity> identity = storage.getIdentityDao().getIdentity();
		if (identity.isPresent()) {
			primaryStage.setScene(new Scene(PanelController.create(new ChatManager(identity.get(), storage))));
		} else {
			primaryStage.setScene(new Scene(FirstStartController.create(storage)));
		}

		Program.scene = primaryStage.getScene();

		primaryStage.show();
	}
	
	@Override
	public void stop() throws Exception {
		System.exit(-1); //Hacky fix
    }

	public static void setNode(Parent value) {
		Program.scene.setRoot(value);
		Program.scene.getWindow().sizeToScene();
	}
}
