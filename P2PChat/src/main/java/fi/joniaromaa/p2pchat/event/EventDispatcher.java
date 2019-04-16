package fi.joniaromaa.p2pchat.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class EventDispatcher {
	private Map<Class<? extends Event>, List<EventListener<?>>> listeners;
	
	protected EventDispatcher() {
		this.listeners = new HashMap<>();
	}
	
	public <T extends Event> void addListener(Class<T> clazz, EventListener<T> listener) {
		List<EventListener<?>> listeners = this.listeners.get(clazz);
		if (listeners == null) {
			this.listeners.put(clazz, listeners = new ArrayList<>());
		}
		
		listeners.add(listener);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Event> void fireEvent(T event) {
		List<EventListener<T>> listeners = (List<EventListener<T>>) (List<?>) this.listeners.get(event.getClass());
		
		listeners.forEach((l) -> l.invoke(event));
	}
}
