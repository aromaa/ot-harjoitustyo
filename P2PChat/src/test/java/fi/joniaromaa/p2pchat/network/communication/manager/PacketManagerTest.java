package fi.joniaromaa.p2pchat.network.communication.manager;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fi.joniaromaa.p2pchat.network.communication.IncomingPacket;
import fi.joniaromaa.p2pchat.network.communication.incoming.PingIncomingPacket;
import fi.joniaromaa.p2pchat.network.communication.outgoing.PingOutgoingPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class PacketManagerTest
{
	@Test
	public void packetIdTest()
	{
		PacketManager packetManager = new PacketManager()
		{
			{
				this.addIncoming(Byte.MAX_VALUE, PingIncomingPacket.class);
				this.addOutgoing(Byte.MAX_VALUE, PingOutgoingPacket.class);
			}
		};
		
		ByteBuf buffer = Unpooled.buffer();
		
		packetManager.writeOutgoingPacket(new PingOutgoingPacket(), buffer);
		
		IncomingPacket incomingPacket = packetManager.readIncomingPacket(buffer);
		
		assertEquals(PingIncomingPacket.class, incomingPacket.getClass());
	}
}
