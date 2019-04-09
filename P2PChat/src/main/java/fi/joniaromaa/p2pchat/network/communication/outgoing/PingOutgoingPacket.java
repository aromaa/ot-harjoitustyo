package fi.joniaromaa.p2pchat.network.communication.outgoing;

import fi.joniaromaa.p2pchat.network.communication.OutgoingPacket;
import io.netty.buffer.ByteBuf;

public class PingOutgoingPacket implements OutgoingPacket {
	@Override
	public void write(ByteBuf out) {
	}
}
