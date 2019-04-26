package fi.joniaromaa.p2pchat.chat.contacts;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import fi.joniaromaa.p2pchat.event.EventDispatcher;
import fi.joniaromaa.p2pchat.event.contacts.OnContactAddEvent;
import fi.joniaromaa.p2pchat.identity.ContactIdentity;
import fi.joniaromaa.p2pchat.storage.dao.ContactsDao;

/**
 * Keeps track of the many {@link ContactIdentity} in optimized way that no multiple lookups to the {@link ContactsDao} needs to happen.
 * 
 * Also dispatches {@link OnContactAddEvent} using the {@link EventDispatcher}
 */
public class ContactIdentityManager extends EventDispatcher {
	private final ContactsDao dao;
	
	private Map<PublicKey, ContactIdentity> contacts;
	private Map<Integer, ContactIdentity> contactsById;
	
	public ContactIdentityManager(ContactsDao dao) {
		this.dao = dao;
		
		this.contacts = new HashMap<>();
		this.contactsById = new HashMap<>();
	}
	
	/**
	 * Creates new {@link ContactIdentity} using {@link ContactsDao#addContact(PublicKey, String)} with the method parameters.
	 * In case where the {@link ContactIdentity} already exists we instead update the nickname, if changed
	 * 
	 * @param publicKey The public key that represents the {@link ContactIdentity}.
	 * @param nickname The contacts nickname.
	 * 
	 * @return Returns the newly created {@link ContactIdentity} or the cached one.
	 */
	public ContactIdentity addOrUpdate(PublicKey publicKey, String nickname) {
		ContactIdentity identity = this.getOrAdd(publicKey, nickname);
		if (!identity.getNickname().equals(nickname)) {
			identity.setNickname(nickname);
			
			this.dao.updateContact(identity);
		}
		
		return identity;
	}
	
	/**
	 * Returns the cached {@link ContactIdentity} or creates new one by using {@link ContactsDao#addContact(PublicKey, String)} with the method parameters.
	 * 
	 * @param publicKey The public key that represents the {@link ContactIdentity}.
	 * @param nickname The contacts nickname.
	 * 
	 * @return Returns the cached {@link ContactIdentity} or the new that we just created.
	 */
	public ContactIdentity getOrAdd(PublicKey publicKey, String nickname) {
		ContactIdentity contact = this.contacts.get(publicKey); //Is it in our local cache?
		if (contact == null) {
			contact = this.add(publicKey, nickname);
		}
		
		return contact;
	}
	
	private ContactIdentity add(PublicKey publicKey, String nickname) {
		ContactIdentity contact = this.dao.addContact(publicKey, nickname) //First try to add it the storage
						.orElseGet(() -> this.dao.getContact(publicKey) //If that fails, maybe its already in there?
							.orElseThrow(() -> new RuntimeException("PLACEHOLDER"))); //TODO: How to handle this one? This should never happen
		
		this.add(contact);
		
		return contact;
	}
	
	private void add(ContactIdentity contact) {
		this.contacts.put(contact.getPublicKey(), contact);
		this.contactsById.put(contact.getId(), contact);
		
		this.fireEvent(new OnContactAddEvent(contact));
	}
	
	/**
	 * Returns the cached {@link ContactIdentity} that represents {@link PublicKey}.
	 * 
	 * However! Note this does only lookup for the list of cached {@link ContactIdentity} and does not mean it wouldn't exists in the {@link ContactsDao}
	 * 
	 * @param publicKey The public key that represents the {@link ContactIdentity}.
	 * 
	 * @return Returns the {@link ContactIdentity} in the cache, or in case none, returns {@link java.util.Optional#empty()}.
	 */
	public Optional<ContactIdentity> getContact(PublicKey publicKey) {
		return Optional.ofNullable(this.contacts.get(publicKey));
	}
}
