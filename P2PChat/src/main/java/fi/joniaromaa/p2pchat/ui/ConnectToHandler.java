package fi.joniaromaa.p2pchat.ui;

import java.io.IOException;

import fi.joniaromaa.p2pchat.chat.ChatManager;
import fi.joniaromaa.p2pchat.network.NetworkHandlerClient;
import fi.joniaromaa.p2pchat.network.communication.outgoing.authentication.RequestChallengeOutgoingPacket;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ConnectToHandler {
	private ChatManager chatManager;
	
	@FXML private Pane parentPane;

	@FXML private TextField hostText;
	@FXML private TextField portText;
	
	public void init() {
		//Replace all non-digits
		this.portText.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	ConnectToHandler.this.portText.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
	}

	@FXML
	public void connect(ActionEvent event) {
		this.toggle(true);
		
		NetworkHandlerClient client = new NetworkHandlerClient();
		client.start(this.chatManager, this.getConnectionIp(), this.getConnectionPort()).addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if (future.isSuccess()) {
					future.channel().writeAndFlush(new RequestChallengeOutgoingPacket());

					//Async to GUI Thread
					Platform.runLater(() -> {
						((Stage) ConnectToHandler.this.parentPane.getScene().getWindow()).close();
					});
				} else {
					//Async to GUI Thread
					Platform.runLater(() -> {
						Alert alert = new Alert(AlertType.ERROR, "Oops! Unable to connect to the target machine! Make the host and port you entered are correct! This error may also occuer when the host machine is unacciable from your network!");
						alert.show();
						
						ConnectToHandler.this.toggle(false);
					});
				}
			}
		});
	}
	
	private void toggle(boolean disabled) {
		this.parentPane.disableProperty().set(disabled);
	}
	
	private String getConnectionIp() {
		return this.hostText.getText();
	}
	
	private int getConnectionPort() {
		return Integer.parseInt(this.portText.getText());
	}

	public static void create(ChatManager chatManager) throws IOException {
		FXMLLoader loader = new FXMLLoader(ConnectToHandler.class.getResource("ConnectTo.fxml"));

		Parent pane = loader.load();

		ConnectToHandler handler = (ConnectToHandler) loader.getController();
		handler.chatManager = chatManager;
		handler.init();

		Stage stage = new Stage();
		stage.setTitle("Connect To Peer");
		stage.setScene(new Scene(pane));
		stage.show();
	}
}
