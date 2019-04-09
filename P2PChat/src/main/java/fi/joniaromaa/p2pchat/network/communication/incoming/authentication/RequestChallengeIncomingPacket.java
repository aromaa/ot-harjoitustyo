package fi.joniaromaa.p2pchat.network.communication.incoming.authentication;

import fi.joniaromaa.p2pchat.network.communication.IncomingPacket;
import fi.joniaromaa.p2pchat.network.communication.handler.ConnectionHandler;
import fi.joniaromaa.p2pchat.network.communication.outgoing.authentication.SolveChallengeOutgoingPacket;
import io.netty.buffer.ByteBuf;

public class RequestChallengeIncomingPacket implements IncomingPacket {
	@Override
	public void read(ByteBuf in) {
	}

	@Override
	public void handle(ConnectionHandler handler) throws Exception {
		handler.createChallenge();

		handler.getChannel().writeAndFlush(new SolveChallengeOutgoingPacket(handler.getPendingChallenge()));
	}
}
