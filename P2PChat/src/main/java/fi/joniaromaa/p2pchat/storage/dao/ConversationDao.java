package fi.joniaromaa.p2pchat.storage.dao;

import java.util.Optional;

import javax.annotation.Nonnull;

import fi.joniaromaa.p2pchat.chat.conversation.ChatConversation;
import fi.joniaromaa.p2pchat.identity.ContactIdentity;

public interface ConversationDao {
	public Optional<ChatConversation> create(@Nonnull ContactIdentity contact);
	
	public Optional<ChatConversation> get(@Nonnull ContactIdentity contact);
}
