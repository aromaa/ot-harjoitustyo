package fi.joniaromaa.p2pchat.network.communication;

import io.netty.buffer.ByteBuf;

/**
 * Outgoing packet from network session.
 */
public interface OutgoingPacket {
	/**
	 * Writes the packet content to {@link ByteBuf}.
	 * 
	 * @param out The {@link ByteBuf} to write to.
	 */
	public void write(ByteBuf out);
}
