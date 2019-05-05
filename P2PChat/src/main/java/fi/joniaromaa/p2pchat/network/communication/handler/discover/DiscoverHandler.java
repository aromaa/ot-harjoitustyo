package fi.joniaromaa.p2pchat.network.communication.handler.discover;

import java.net.InetSocketAddress;

import fi.joniaromaa.p2pchat.chat.ChatManager;
import fi.joniaromaa.p2pchat.network.NetworkHandlerClient;
import fi.joniaromaa.p2pchat.network.communication.outgoing.authentication.RequestChallengeOutgoingPacket;
import fi.joniaromaa.p2pchat.utils.DiscoveryUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import lombok.RequiredArgsConstructor;

/**
 * Listens for datagram's that could contain discovery request.
 */
@RequiredArgsConstructor
public class DiscoverHandler extends SimpleChannelInboundHandler<DatagramPacket>  {
	private final ChatManager chatManager;
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
		ByteBuf content = msg.content();
		if (!content.isReadable(DiscoveryUtils.DISCOVERY_MAGIC.readableBytes())) {
			return;
		}

		if (!content.readBytes(DiscoveryUtils.DISCOVERY_MAGIC.readableBytes()).equals(DiscoveryUtils.DISCOVERY_MAGIC)) {
			return;
		}
		
		byte version = content.readByte();
		if (version != 0) {
			return;
		}
		
		InetSocketAddress sender = msg.sender();
		
		int port = content.readUnsignedShort();
		
		this.connectTo(sender, port);
	}
	
	protected void connectTo(InetSocketAddress sender, int port) {
		NetworkHandlerClient client = new NetworkHandlerClient();
		client.start(this.chatManager, sender.getHostName(), port).addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if (future.isSuccess()) {
					future.channel().writeAndFlush(new RequestChallengeOutgoingPacket());
				}
			}
		});
	}
}
