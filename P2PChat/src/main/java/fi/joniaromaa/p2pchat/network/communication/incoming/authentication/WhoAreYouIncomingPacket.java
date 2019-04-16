package fi.joniaromaa.p2pchat.network.communication.incoming.authentication;

import java.security.PublicKey;

import fi.joniaromaa.p2pchat.identity.ContactIdentity;
import fi.joniaromaa.p2pchat.network.communication.IncomingPacket;
import fi.joniaromaa.p2pchat.network.communication.handler.ConnectionHandler;
import fi.joniaromaa.p2pchat.utils.ByteBufUtils;
import fi.joniaromaa.p2pchat.utils.EncryptionUtils;
import fi.joniaromaa.p2pchat.utils.IdentityUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

public class WhoAreYouIncomingPacket implements IncomingPacket {
	@Getter private byte[] iAm;
	@Getter private String nickname;

	@Getter private byte[] challenge;

	@Override
	public void read(ByteBuf in) {
		this.iAm = ByteBufUtils.readBytes(in);
		this.nickname = ByteBufUtils.readString(in);

		this.challenge = ByteBufUtils.readBytes(in);
	}

	@Override
	public void handle(ConnectionHandler handler) throws Exception { 
		if (!IdentityUtils.isValidNickname(this.nickname)) {
			handler.getChannel().disconnect();
			
			return;
		}
		
		PublicKey publicKey = EncryptionUtils.getPublicKey(this.iAm);

		if (EncryptionUtils.verifyChallange(publicKey, handler.getPendingChallenge(), this.challenge)) {
			ContactIdentity contact = handler.getChatManager().getContacts().addOrUpdate(publicKey, this.nickname);
			handler.setContact(contact);
			
			handler.getChatManager().onConnection(handler);
		} else {
			handler.getChannel().disconnect(); // BYE FAKER!
		}
	}
}
