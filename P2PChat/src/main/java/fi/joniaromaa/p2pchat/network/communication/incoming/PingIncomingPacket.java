package fi.joniaromaa.p2pchat.network.communication.incoming;

import fi.joniaromaa.p2pchat.network.communication.IncomingPacket;
import fi.joniaromaa.p2pchat.network.communication.handler.ConnectionHandler;
import io.netty.buffer.ByteBuf;

public class PingIncomingPacket implements IncomingPacket {
	@Override
	public void read(ByteBuf in) {
	}

	@Override
	public void handle(ConnectionHandler handler) throws Exception {
	}
}
