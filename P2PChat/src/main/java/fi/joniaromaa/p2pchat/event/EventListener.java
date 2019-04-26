package fi.joniaromaa.p2pchat.event;

/**
 * Listens for selected event type.
 * 
 * @param <T> The event type.
 */
public interface EventListener<T extends Event> {
	/**
	 * Called by {@link EventDispatcher} when event was fired.
	 * 
	 * @param event The event.
	 */
	public void invoke(T event);
}
