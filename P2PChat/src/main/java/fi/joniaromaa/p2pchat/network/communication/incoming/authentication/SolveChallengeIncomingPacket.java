package fi.joniaromaa.p2pchat.network.communication.incoming.authentication;

import com.google.common.annotations.VisibleForTesting;

import fi.joniaromaa.p2pchat.identity.MyIdentity;
import fi.joniaromaa.p2pchat.network.communication.IncomingPacket;
import fi.joniaromaa.p2pchat.network.communication.handler.ConnectionHandler;
import fi.joniaromaa.p2pchat.network.communication.outgoing.authentication.WhoAreYouOutgoingPacket;
import fi.joniaromaa.p2pchat.utils.ByteBufUtils;
import fi.joniaromaa.p2pchat.utils.EncryptionUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

public class SolveChallengeIncomingPacket implements IncomingPacket {
	@Getter private byte[] challenge;
	
	public SolveChallengeIncomingPacket() {
		
	}
	
	@VisibleForTesting
	SolveChallengeIncomingPacket(byte[] challenge) {
		this.challenge = challenge;
	}

	@Override
	public void read(ByteBuf in) {
		this.challenge = ByteBufUtils.readBytes(in);
	}

	@Override
	public void handle(ConnectionHandler handler) throws Exception {
		MyIdentity identity = handler.getChatManager().getIdentity();

		// TODO: Handle the exceptions?
		byte[] signed = EncryptionUtils.getSignedChallange(identity.getKeyPair().getPrivate(), this.challenge);

		handler.getChannel().writeAndFlush(new WhoAreYouOutgoingPacket(identity.getPublicKeyBytes(), identity.getNickname(), signed));
	}
}
