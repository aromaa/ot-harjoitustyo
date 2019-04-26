package fi.joniaromaa.p2pchat.identity;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;

public class ContactIdentityTest
{
	@Test
	public void testDisplayName()
	{
		ContactIdentity contact = new ContactIdentity(0, null, "Janne");
		
		assertEquals(Optional.empty(), contact.getContactName());
		assertEquals(contact.getNickname(), contact.getDisplayName());
		
		contact.setContactName("Veli");

		assertEquals(contact.getContactName().get(), contact.getDisplayName());
	}
}
