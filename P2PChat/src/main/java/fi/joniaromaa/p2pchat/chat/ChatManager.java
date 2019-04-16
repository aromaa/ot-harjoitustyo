package fi.joniaromaa.p2pchat.chat;

import java.util.HashMap;
import java.util.Map;

import fi.joniaromaa.p2pchat.chat.contacts.ContactIdentityManager;
import fi.joniaromaa.p2pchat.chat.conversation.ChatConversationManager;
import fi.joniaromaa.p2pchat.identity.ContactIdentity;
import fi.joniaromaa.p2pchat.identity.MyIdentity;
import fi.joniaromaa.p2pchat.network.communication.handler.ConnectionHandler;
import fi.joniaromaa.p2pchat.storage.Storage;
import io.netty.channel.Channel;
import lombok.Getter;

public class ChatManager {
	@Getter private final MyIdentity identity;
	
	@Getter private final ContactIdentityManager contacts;
	@Getter private final ChatConversationManager conversions;
	
	private Map<Integer, ConnectionHandler> handlers;
	
	public ChatManager(MyIdentity identity, Storage storage) {
		this.identity = identity;
		
		this.contacts = new ContactIdentityManager(storage.getContactsDao());
		this.conversions = new ChatConversationManager(storage.getConversationDao(), this.contacts);
		
		this.handlers = new HashMap<>();
	}
	
	public void onConnection(ConnectionHandler handler) {
		this.handlers.put(handler.getContact().get().getId(), handler);
	}
	
	public void onDisconnect(ContactIdentity contact) {
		this.handlers.remove(contact.getId());
	}
	
	public Channel getChannel(ContactIdentity contact) {
		ConnectionHandler handler = this.handlers.get(contact.getId());
		if (handler != null) {
			return handler.getChannel();
		}
		
		return null;
	}
}
