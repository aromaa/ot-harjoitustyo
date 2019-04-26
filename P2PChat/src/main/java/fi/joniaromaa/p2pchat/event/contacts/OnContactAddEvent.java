package fi.joniaromaa.p2pchat.event.contacts;

import fi.joniaromaa.p2pchat.event.Event;
import fi.joniaromaa.p2pchat.identity.ContactIdentity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Event that is fired by the {@link fi.joniaromaa.p2pchat.chat.contacts.ContactIdentityManager} when new {@link ContactIdentity} was added to the cache.
 */
@RequiredArgsConstructor
public class OnContactAddEvent implements Event {
	@Getter private final ContactIdentity contact;
}
