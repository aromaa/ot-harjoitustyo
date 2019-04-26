package fi.joniaromaa.p2pchat.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Test;

import fi.joniaromaa.p2pchat.event.contacts.OnContactAddEvent;

public class EventDispatcherTest
{
	@Test
	public void testEvents()
	{
		EventDispatcher dispatcher = new EventDispatcher()
		{
			//Anonymous class
		};
		
		AtomicBoolean testBoolean = new AtomicBoolean();
		
		dispatcher.addListener(OnContactAddEvent.class, new EventListener<OnContactAddEvent>()
		{
			@Override
			public void invoke(OnContactAddEvent event)
			{
				testBoolean.set(true);
			}
		});
		
		dispatcher.fireEvent(new Event()
		{
			//Anonymous class
		});
		
		assertFalse(testBoolean.get());
		
		dispatcher.fireEvent(new OnContactAddEvent(null));
		
		assertTrue(testBoolean.get());
	}
}
