package fi.joniaromaa.p2pchat.ui;

import java.io.IOException;

import fi.joniaromaa.p2pchat.identity.MyIdentity;
import fi.joniaromaa.p2pchat.network.NetworkHandlerServer;
import fi.joniaromaa.p2pchat.storage.Storage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import lombok.Getter;

public class PanelController
{
	private Storage storage;
	@Getter private MyIdentity identity;
	
	private NetworkHandlerServer networkServer;
	
	@FXML private Label nicknameLabel;
	
	@Getter @FXML private ListView<Object> contacts;
	
	private void init()
	{
		this.networkServer = new NetworkHandlerServer();
		this.networkServer.start(this);
		
		this.nicknameLabel.setText(this.identity.getNickname());
	}
	
	public static Parent create(Storage storage, MyIdentity identity) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(PanelController.class.getResource("Panel.fxml"));
		
		Parent pane = loader.load();
		
		((PanelController)loader.getController()).storage = storage;
		((PanelController)loader.getController()).identity = identity;
		((PanelController)loader.getController()).init();
		
		return pane;
	}
	
	@FXML
	public void connectTo()
	{
		try
		{
			ConnectToHandler.create(this);
		}
		catch (IOException e)
		{
			//TODO
			e.printStackTrace();
		}
	}
}
