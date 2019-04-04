package fi.joniaromaa.p2pchat.network.communication.outgoing.authentication;

import fi.joniaromaa.p2pchat.network.communication.OutgoingPacket;
import fi.joniaromaa.p2pchat.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SolveChallengeOutgoingPacket implements OutgoingPacket
{
	private final byte[] challenge;
	
	@Override
	public void write(ByteBuf out)
	{
		ByteBufUtils.writeBytes(out, this.challenge);
	}
}
