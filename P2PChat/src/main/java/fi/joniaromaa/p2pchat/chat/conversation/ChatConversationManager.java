package fi.joniaromaa.p2pchat.chat.conversation;

import java.util.HashMap;
import java.util.Map;

import fi.joniaromaa.p2pchat.chat.contacts.ContactIdentityManager;
import fi.joniaromaa.p2pchat.identity.ContactIdentity;
import fi.joniaromaa.p2pchat.storage.dao.ConversationDao;

/**
 * Keeps track of the many {@link ChatConversation} in optimized way that no multiple lookups to the {@link ConversationDao} needs to happen.
 */
public class ChatConversationManager {
	private final ConversationDao dao;
	
	private final ContactIdentityManager contacts;
	
	private Map<Integer, ChatConversation> conversationById;
	private Map<Integer, ChatConversation> conversationByContactId;
	
	public ChatConversationManager(ConversationDao dao, ContactIdentityManager contacts) {
		this.dao = dao;
		
		this.contacts = contacts;
		
		this.conversationById = new HashMap<>();
		this.conversationByContactId = new HashMap<>();
	}
	
	/**
	 * Returns the cached {@link ChatConversation} or creates new one by using {@link ConversationDao#create(ContactIdentity)}.
	 * 
	 * @param contact The contact that the conversation is directed towards to.
	 * 
	 * @return Returns the cached {@link ChatConversation} or the new that we just created.
	 */
	public ChatConversation getOrCreate(ContactIdentity contact) {
		ChatConversation conversation = this.conversationByContactId.get(contact.getId());
		if (conversation == null) {
			conversation = this.create(contact);
		}
		
		return conversation;
	}

	private ChatConversation create(ContactIdentity contact) {
		ChatConversation conversation = this.dao.create(contact)
								.orElseGet(() -> this.dao.get(contact)
								.orElseThrow(() -> new RuntimeException("PLACEHOLDER"))); //TODO: How to handle this one? This should never happen
		
		this.add(conversation);
		
		return conversation;
	}
	
	private void add(ChatConversation conversation) {
		this.conversationById.put(conversation.getId(), conversation);
		this.conversationByContactId.put(conversation.getContact().getId(), conversation);
	}
}
