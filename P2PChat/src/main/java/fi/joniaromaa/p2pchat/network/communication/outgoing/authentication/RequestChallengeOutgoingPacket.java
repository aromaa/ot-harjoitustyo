package fi.joniaromaa.p2pchat.network.communication.outgoing.authentication;

import fi.joniaromaa.p2pchat.network.communication.OutgoingPacket;
import io.netty.buffer.ByteBuf;

public class RequestChallengeOutgoingPacket implements OutgoingPacket {
	@Override
	public void write(ByteBuf out) {
	}
}
