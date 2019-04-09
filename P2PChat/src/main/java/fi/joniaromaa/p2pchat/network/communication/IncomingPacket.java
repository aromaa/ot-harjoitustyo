package fi.joniaromaa.p2pchat.network.communication;

import fi.joniaromaa.p2pchat.network.communication.handler.ConnectionHandler;
import io.netty.buffer.ByteBuf;

public interface IncomingPacket {
	public void read(ByteBuf in);

	public void handle(ConnectionHandler handler) throws Exception;
}
