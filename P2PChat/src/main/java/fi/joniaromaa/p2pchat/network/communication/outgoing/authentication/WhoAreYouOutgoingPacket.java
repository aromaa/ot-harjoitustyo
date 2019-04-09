package fi.joniaromaa.p2pchat.network.communication.outgoing.authentication;

import fi.joniaromaa.p2pchat.network.communication.OutgoingPacket;
import fi.joniaromaa.p2pchat.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WhoAreYouOutgoingPacket implements OutgoingPacket {
	@Getter private final byte[] iAm;
	@Getter private final String nickname;

	@Getter private final byte[] challenge;

	@Override
	public void write(ByteBuf out) {
		ByteBufUtils.writeBytes(out, this.iAm);
		ByteBufUtils.writeString(out, this.nickname);

		ByteBufUtils.writeBytes(out, this.challenge);
	}
}
