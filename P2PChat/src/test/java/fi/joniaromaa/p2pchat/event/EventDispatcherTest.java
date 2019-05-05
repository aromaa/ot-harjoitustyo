package fi.joniaromaa.p2pchat.event;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;

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
		
		AtomicInteger testCount = new AtomicInteger();
		
		EventListener<OnContactAddEvent> listener = new EventListener<OnContactAddEvent>()
		{
			@Override
			public void invoke(OnContactAddEvent event)
			{
				testCount.getAndIncrement();
			}
		};
		
		dispatcher.addListener(OnContactAddEvent.class, listener);
		
		dispatcher.fireEvent(new Event()
		{
			//Anonymous class
		});
		
		assertEquals(0, testCount.get());
		
		dispatcher.fireEvent(new OnContactAddEvent(null));
		
		assertEquals(1, testCount.get());
		
		dispatcher.addListener(OnContactAddEvent.class, listener);
		dispatcher.fireEvent(new OnContactAddEvent(null));
		
		assertEquals(3, testCount.get());
	}
}
