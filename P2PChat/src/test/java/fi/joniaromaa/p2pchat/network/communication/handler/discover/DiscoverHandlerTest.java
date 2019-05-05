package fi.joniaromaa.p2pchat.network.communication.handler.discover;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import fi.joniaromaa.p2pchat.utils.DiscoveryUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.DatagramPacket;

public class DiscoverHandlerTest
{
	@Test
	public void testReadsValidData() throws Exception
	{
		final int connectionPort = 5684;
		
		AtomicInteger callCount = new AtomicInteger();
		AtomicBoolean bool = new AtomicBoolean();
		
		DiscoverHandler handler = new DiscoverHandler(null)
		{
			@Override
			protected void connectTo(InetSocketAddress sender, int port)
			{
				callCount.getAndIncrement();
				
				if (port == connectionPort)
				{
					bool.set(true);
				}
			}
		};
		
		InetSocketAddress sender = InetSocketAddress.createUnresolved("127.0.0.1", 5555);
		
		handler.channelRead0(null, new DatagramPacket(Unpooled.buffer(), sender));
		
		assertEquals(0, callCount.get());
		
		handler.channelRead0(null, new DatagramPacket(Unpooled.wrappedBuffer(new byte[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}), sender));
		
		assertEquals(0, callCount.get());
		
		ByteBuf rightChecksumWrongVersion = Unpooled.copiedBuffer(DiscoveryUtils.DISCOVERY_MAGIC);
		rightChecksumWrongVersion.writeByte(-1);
		
		handler.channelRead0(null, new DatagramPacket(rightChecksumWrongVersion, sender));
		
		assertEquals(0, callCount.get());
		
		ByteBuf rightChecksumRightVersion = Unpooled.copiedBuffer(DiscoveryUtils.DISCOVERY_MAGIC);
		rightChecksumRightVersion.writeByte(0);
		rightChecksumRightVersion.writeShort(connectionPort);
		
		handler.channelRead0(null, new DatagramPacket(rightChecksumRightVersion, sender));
		
		assertEquals(1, callCount.get());
		assertTrue(bool.get());
	}
}
