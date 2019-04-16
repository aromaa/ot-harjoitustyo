package fi.joniaromaa.p2pchat.ui;

import java.io.IOException;

import fi.joniaromaa.p2pchat.chat.ChatManager;
import fi.joniaromaa.p2pchat.chat.conversation.ChatConversation;
import fi.joniaromaa.p2pchat.event.EventListener;
import fi.joniaromaa.p2pchat.event.contacts.OnContactAddEvent;
import fi.joniaromaa.p2pchat.network.NetworkHandlerServer;
import fi.joniaromaa.p2pchat.network.communication.outgoing.chat.ChatMessageOutgoingPacket;
import io.netty.channel.Channel;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.Getter;

public class PanelController {
	@Getter private ChatManager chatManager;

	private NetworkHandlerServer networkServer;

	@FXML private Label nicknameLabel;
	@FXML private Label addressLabel;
	
	@FXML private TextField textbox;

	@FXML private ListView<ChatConversation> contacts;
	@FXML private ListView<String> chatView;
	
	private ChatConversation conversation;
	
	private void init() {
		this.networkServer = new NetworkHandlerServer();
		this.networkServer.start(this.chatManager);

		this.nicknameLabel.setText("Hello, " + this.chatManager.getIdentity().getNickname() + "!");
		this.addressLabel.setText(this.networkServer.getChannel().localAddress().toString());
		
		this.initListeners();
	}
	
	private void initListeners() {
		this.chatManager.getContacts().addListener(OnContactAddEvent.class, new EventListener<OnContactAddEvent>() {
			@Override
			public void invoke(OnContactAddEvent event) {
				//Async calls to GUI Thread
				Platform.runLater(() -> {
					PanelController.this.contacts.getItems().add(PanelController.this.chatManager.getConversions().getOrCreate(event.getContact()));
				});
			}
		});
		
		this.contacts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ChatConversation>() {
			@Override
			public void changed(ObservableValue<? extends ChatConversation> observable, ChatConversation oldValue, ChatConversation newValue) {
				PanelController.this.conversation = newValue;
				
				PanelController.this.textbox.setEditable(true);
				
				PanelController.this.chatView.setItems(newValue.getChatHistory());
			}
		});
	}
	
	@FXML
	private void chatSend(KeyEvent event) {
		if (this.conversation == null) {
			return;
		}
		
		if (event.getCode() == KeyCode.ENTER) {
			Channel channel = this.chatManager.getChannel(this.conversation.getContact());
			if (channel == null) {
				Alert alert = new Alert(AlertType.ERROR, "The contact is offline"); //TODO: Get rid of this
				alert.show();
				
				return;
			}
			
			channel.writeAndFlush(new ChatMessageOutgoingPacket(this.textbox.getText()));
			
			this.conversation.addChatMessage(this.chatManager.getIdentity(), this.textbox.getText());
			this.chatView.scrollTo(this.chatView.getItems().size());
			
			this.textbox.clear();
		}
	}

	@FXML
	public void connectTo() {
		try {
			ConnectToHandler.create(this.chatManager);
		} catch (IOException e) {
			// TODO
		}
	}

	public static Parent create(ChatManager chatManager) throws IOException {
		FXMLLoader loader = new FXMLLoader(PanelController.class.getResource("Panel.fxml"));

		Parent pane = loader.load();

		((PanelController) loader.getController()).chatManager = chatManager;
		((PanelController) loader.getController()).init();

		return pane;
	}
}
