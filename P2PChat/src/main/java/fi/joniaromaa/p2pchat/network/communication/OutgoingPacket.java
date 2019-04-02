package fi.joniaromaa.p2pchat.network.communication;

import io.netty.buffer.ByteBuf;

public interface OutgoingPacket
{
	public void write(ByteBuf out);
}
