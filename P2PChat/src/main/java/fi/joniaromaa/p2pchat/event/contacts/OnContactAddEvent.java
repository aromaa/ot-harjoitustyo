package fi.joniaromaa.p2pchat.event.contacts;

import fi.joniaromaa.p2pchat.event.Event;
import fi.joniaromaa.p2pchat.identity.ContactIdentity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OnContactAddEvent implements Event {
	@Getter private final ContactIdentity contact;
}
