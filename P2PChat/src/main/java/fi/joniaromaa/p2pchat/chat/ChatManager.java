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

/**
 * Takes care of the current 'active' session, keeps track of connected clients and keeps track of cached conversations and contacts.
 */
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
	
	/**
	 * Adds active connection to the list of active {@link ContactIdentity}.
	 * 
	 * @param handler The incoming connection.
	 */
	public void onConnection(ConnectionHandler handler) {
		this.handlers.put(handler.getContact().get().getId(), handler);
	}
	
	/**
	 * Removes the {@link ContactIdentity} as connected client.
	 * 
	 * @param contact The {@link ContactIdentity} that was disconnected.
	 */
	public void onDisconnect(ContactIdentity contact) {
		this.handlers.remove(contact.getId());
	}
	
	/**
	 * Gets channel that can be used to communicated with {@link ContactIdentity}.
	 * 
	 * @param contact The {@link ContactIdentity} to communicate with.
	 * 
	 * @return Returns {@link Channel} that has the active {@link ContactIdentity} connection, or if none, returns null.
	 */
	public Channel getChannel(ContactIdentity contact) {
		ConnectionHandler handler = this.handlers.get(contact.getId());
		if (handler != null) {
			return handler.getChannel();
		}
		
		return null;
	}
}
