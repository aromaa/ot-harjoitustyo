package fi.joniaromaa.p2pchat.network.communication.outgoing;

import fi.joniaromaa.p2pchat.network.communication.OutgoingPacket;
import fi.joniaromaa.p2pchat.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WhoAreYouOutgoingPacket implements OutgoingPacket
{
	private final byte[] iAm;
	private final String nick;

	//TODO: Challange
	
	@Override
	public void write(ByteBuf out)
	{
		ByteBufUtils.writeBytes(out, this.iAm);
		ByteBufUtils.writeString(out, this.nick);
	}
}
