package fi.joniaromaa.p2pchat.ui;

import java.io.IOException;

import fi.joniaromaa.p2pchat.Program;
import fi.joniaromaa.p2pchat.chat.ChatManager;
import fi.joniaromaa.p2pchat.identity.MyIdentity;
import fi.joniaromaa.p2pchat.storage.Storage;
import fi.joniaromaa.p2pchat.utils.IdentityUtils;
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
		if (!IdentityUtils.isValidNickname(this.nickField.getText())) {
			Alert alert = new Alert(AlertType.ERROR, "The nickname must be between 1 to 32 chars long");
			alert.show();
			
			return;
		}
		
		MyIdentity identity = IdentityUtils.generateMyIdentity(this.nickField.getText());

		if (this.storage.getIdentityDao().save(identity)) {
			try {
				Program.setNode(PanelController.create(new ChatManager(identity, this.storage)));
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
