package fi.joniaromaa.p2pchat.network.communication;

import fi.joniaromaa.p2pchat.network.communication.handler.ServerConnectionHandler;
import io.netty.buffer.ByteBuf;

public interface IncomingPacket
{
	public void read(ByteBuf in);
	
	public void handle(ServerConnectionHandler handler) throws Exception;
}
