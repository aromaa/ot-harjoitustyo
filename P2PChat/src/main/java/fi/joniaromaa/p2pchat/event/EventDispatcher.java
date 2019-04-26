package fi.joniaromaa.p2pchat.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that can fire {@link Event} that the registered {@link EventListener}'s receive.
 */
public abstract class EventDispatcher {
	private Map<Class<? extends Event>, List<EventListener<?>>> listeners;
	
	protected EventDispatcher() {
		this.listeners = new HashMap<>();
	}
	
	/**
	 * Registers new {@link EventListener} to listen the selected event.
	 * 
	 * @param clazz The event to listen for.
	 * @param listener The event handler.
	 * 
	 * @param <T> The event type.
	 */
	public <T extends Event> void addListener(Class<T> clazz, EventListener<T> listener) {
		List<EventListener<?>> listeners = this.listeners.get(clazz);
		if (listeners == null) {
			this.listeners.put(clazz, listeners = new ArrayList<>());
		}
		
		listeners.add(listener);
	}
	
	/**
	 * Fires event to the registered {@link EventListener}'s that listen for the.
	 * 
	 * @param event The event to be fired.
	 * 
	 * @param <T> The event type.
	 */
	@SuppressWarnings("unchecked")
	protected <T extends Event> void fireEvent(T event) {
		List<EventListener<T>> listeners = (List<EventListener<T>>) (List<?>) this.listeners.get(event.getClass());
		if (listeners == null) {
			return;
		}
		
		listeners.forEach((l) -> l.invoke(event));
	}
}
