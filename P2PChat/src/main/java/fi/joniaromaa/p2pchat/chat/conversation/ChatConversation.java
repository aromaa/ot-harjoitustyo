package fi.joniaromaa.p2pchat.chat.conversation;

import fi.joniaromaa.p2pchat.identity.ContactIdentity;
import fi.joniaromaa.p2pchat.identity.Identity;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

/**
 * Represents conversation between {@link ContactIdentity}.
 */
public class ChatConversation {
	@Getter private final int id;
	@Getter private final ContactIdentity contact;
	
	private ObservableList<String> chatHistory; //TODO: What about this
	
	public ChatConversation(int id, ContactIdentity contact) {
		this.id = id;
		this.contact = contact;
		
		this.chatHistory = FXCollections.observableArrayList();
	}
	
	public void addChatMessage(Identity sender, String message) {
		//Async to GUI Thread
		Platform.runLater(() -> {
			this.chatHistory.add(sender.getDisplayName() + ": " + message);
		});
	}
	
	public ObservableList<String> getChatHistory() {
		return FXCollections.unmodifiableObservableList(this.chatHistory);
	}
	
	@Override
	public String toString() {
		return this.getContact().getDisplayName();
	}
}
