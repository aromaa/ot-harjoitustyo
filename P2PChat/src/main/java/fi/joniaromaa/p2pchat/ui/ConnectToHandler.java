package fi.joniaromaa.p2pchat.ui;

import java.io.IOException;

import fi.joniaromaa.p2pchat.network.NetworkHandlerClient;
import fi.joniaromaa.p2pchat.network.communication.outgoing.authentication.RequestChallengeOutgoingPacket;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConnectToHandler {
	private PanelController panel;

	@FXML private TextField hostText;
	@FXML private TextField portText;

	@FXML
	public void connect() {
		// TODO: This needs a lot work, keep track of connections PROPERLY, async,
		// disconnection handling
		NetworkHandlerClient client = new NetworkHandlerClient();
		client.start(this.panel, this.hostText.getText(), Integer.parseInt(this.portText.getText()));

		client.send(new RequestChallengeOutgoingPacket());
	}

	public static void create(PanelController panel) throws IOException {
		FXMLLoader loader = new FXMLLoader(ConnectToHandler.class.getResource("ConnectTo.fxml"));

		Parent pane = loader.load();

		((ConnectToHandler) loader.getController()).panel = panel;

		Stage stage = new Stage();
		stage.setTitle("Connect To Peer");
		stage.setScene(new Scene(pane));
		stage.show();
	}
}
