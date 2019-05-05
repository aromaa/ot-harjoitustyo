package fi.joniaromaa.p2pchat.network.communication;

import fi.joniaromaa.p2pchat.network.communication.handler.ConnectionHandler;
import io.netty.buffer.ByteBuf;

/**
 * Incoming packet from network session.
 */
public interface IncomingPacket {
	/**
	 * Reads the packet data from {@link ByteBuf}.
	 * 
	 * @param in The {@link ByteBuf} where the data is read from
	 */
	public void read(ByteBuf in);

	/**
	 * Executes the logic the packet has.
	 * 
	 * @param handler The {@link ConnectionHandler} that holds the network session.
	 * 
	 * @throws Exception If packet execution was failed, the session should be terminated in case of exception.
	 */
	public void handle(ConnectionHandler handler) throws Exception;
}
