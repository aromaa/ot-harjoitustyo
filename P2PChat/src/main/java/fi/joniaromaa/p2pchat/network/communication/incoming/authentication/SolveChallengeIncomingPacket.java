package fi.joniaromaa.p2pchat.network.communication.incoming.authentication;

import fi.joniaromaa.p2pchat.identity.MyIdentity;
import fi.joniaromaa.p2pchat.network.communication.IncomingPacket;
import fi.joniaromaa.p2pchat.network.communication.handler.ConnectionHandler;
import fi.joniaromaa.p2pchat.network.communication.outgoing.authentication.WhoAreYouOutgoingPacket;
import fi.joniaromaa.p2pchat.utils.ByteBufUtils;
import fi.joniaromaa.p2pchat.utils.EncryptionUtils;
import io.netty.buffer.ByteBuf;

public class SolveChallengeIncomingPacket implements IncomingPacket
{
	private byte[] challange;
	
	@Override
	public void read(ByteBuf in)
	{
		this.challange = ByteBufUtils.readBytes(in);
	}

	@Override
	public void handle(ConnectionHandler handler) throws Exception
	{
		MyIdentity identity = handler.getPanel().getIdentity();

		//TODO: Handle the exceptions?
		byte[] signed = EncryptionUtils.getSignedChallange(identity.getKeyPair().getPrivate(), this.challange);

		handler.getChannel().writeAndFlush(new WhoAreYouOutgoingPacket(identity.getPublicKeyBytes(), identity.getNickname(), signed));
	}
}
