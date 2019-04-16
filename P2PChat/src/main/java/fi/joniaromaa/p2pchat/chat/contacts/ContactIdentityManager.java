package fi.joniaromaa.p2pchat.chat.contacts;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import fi.joniaromaa.p2pchat.event.EventDispatcher;
import fi.joniaromaa.p2pchat.event.contacts.OnContactAddEvent;
import fi.joniaromaa.p2pchat.identity.ContactIdentity;
import fi.joniaromaa.p2pchat.storage.dao.ContactsDao;

public class ContactIdentityManager extends EventDispatcher {
	private final ContactsDao dao;
	
	private Map<PublicKey, ContactIdentity> contacts;
	private Map<Integer, ContactIdentity> contactsById;
	
	public ContactIdentityManager(ContactsDao dao) {
		this.dao = dao;
		
		this.contacts = new HashMap<>();
		this.contactsById = new HashMap<>();
	}
	
	public ContactIdentity addOrUpdate(PublicKey publicKey, String nickname) {
		ContactIdentity identity = this.getOrAdd(publicKey, nickname);
		if (!identity.getNickname().equals(nickname)) {
			identity.setNickname(nickname);
			
			this.dao.updateContact(identity);
		}
		
		return identity;
	}
	
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
	
	public Optional<ContactIdentity> getContact(PublicKey publicKey) {
		return Optional.ofNullable(this.contacts.get(publicKey));
	}
}
