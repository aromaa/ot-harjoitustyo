package fi.joniaromaa.p2pchat.event;

public interface EventListener<T extends Event> {
	public void invoke(T event);
}
