package fi.joniaromaa.p2pchat.storage.dao;

import java.util.Optional;

import javax.annotation.Nonnull;

import fi.joniaromaa.p2pchat.chat.conversation.ChatConversation;
import fi.joniaromaa.p2pchat.identity.ContactIdentity;

/**
 * Represents persistent data storage that deals with {@link ChatConversation}.
 *
 * The way the data is saved differences from the underlying implementation.
 * For example, this could be file system based system or it could use databases.
 */
public interface ConversationDao {
	/**
	 * Creates new {@link ChatConversation} with the {@link ContactIdentity}.
	 * 
	 * @param contact The contact to start new conversation with, does not accept null.
	 * 
	 * @return Returns the newly generated {@link ChatConversation} in case when new conversation was created. If no conversation was created then {@link java.util.Optional#empty()} is returned, this could be due to already existing conversation with the same {@link ContactIdentity}.
	 */
	public Optional<ChatConversation> create(@Nonnull ContactIdentity contact);
	
	/**
	 * Gets {@link ChatConversation} with the {@link ContactIdentity}.
	 * 
	 * @param contact The contact that the conversation is directed to.
	 * 
	 * @return Returns the {@link ChatConversation} that represents the conversation between {@link ContactIdentity}, or in case none, returns {@link java.util.Optional#empty()}.
	 */
	public Optional<ChatConversation> get(@Nonnull ContactIdentity contact);
}
