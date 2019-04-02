package fi.joniaromaa.p2pchat.ui;

import java.io.IOException;

import fi.joniaromaa.p2pchat.identity.MyIdentity;
import fi.joniaromaa.p2pchat.network.NetworkHandlerClient;
import fi.joniaromaa.p2pchat.network.communication.outgoing.WhoAreYouOutgoingPacket;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConnectToHandler
{
	private MyIdentity identity;
	
	@FXML private TextField hostText;
	@FXML private TextField portText;
	
	@FXML
	public void connect()
	{
		//TODO: This needs a lot work, keep track of connections PROPERLY, async, disconnection handling
		NetworkHandlerClient client = new NetworkHandlerClient();
		client.start(this.hostText.getText(), Integer.parseInt(this.portText.getText()));
		
		client.send(new WhoAreYouOutgoingPacket(this.identity.getKeyPair().getPublic().getEncoded(), this.identity.getNickname()));
	}
	
	public static void create(MyIdentity identity) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(ConnectToHandler.class.getResource("ConnectTo.fxml"));
		
		Parent pane = loader.load();
		
		((ConnectToHandler)loader.getController()).identity = identity;
		
		Stage stage = new Stage();
        stage.setTitle("Connect To Peer");
        stage.setScene(new Scene(pane));
        stage.show();
	}
}
