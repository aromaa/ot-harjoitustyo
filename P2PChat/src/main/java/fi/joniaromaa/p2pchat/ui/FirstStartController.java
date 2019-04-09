package fi.joniaromaa.p2pchat.ui;

import java.io.IOException;

import fi.joniaromaa.p2pchat.Program;
import fi.joniaromaa.p2pchat.identity.MyIdentity;
import fi.joniaromaa.p2pchat.storage.Storage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class FirstStartController {
	private Storage storage;

	@FXML private TextField nickField;

	@FXML
	private void confirm() {
		MyIdentity identity = MyIdentity.generate(this.nickField.getText());

		if (this.storage.getIdentityDao().save(identity)) {
			try {
				Program.setNode(PanelController.create(this.storage, identity));
			} catch (IOException ignore) {
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR, "Unable to save the identity...");
			alert.show();

			// Should we continue?
		}
	}

	public static Parent create(Storage storage) throws IOException {
		FXMLLoader loader = new FXMLLoader(FirstStartController.class.getResource("FirstStart.fxml"));

		Parent pane = loader.load();

		((FirstStartController) loader.getController()).storage = storage;

		return pane;
	}
}
