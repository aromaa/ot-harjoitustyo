package fi.joniaromaa.p2pchat.network.discover;

import java.net.InetSocketAddress;

import fi.joniaromaa.p2pchat.chat.ChatManager;
import fi.joniaromaa.p2pchat.network.communication.handler.discover.DiscoverHandler;
import fi.joniaromaa.p2pchat.utils.DiscoveryUtils;
import fi.joniaromaa.p2pchat.utils.NettyUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.DatagramPacket;

public class NetworkDiscoverHandler {
	private final int listenerPort;
	private ByteBuf discoverRequest;
	
	private EventLoopGroup bossGroup;
	
	private Channel channel;
	
	public NetworkDiscoverHandler(int listenerPort) {
		this.listenerPort = listenerPort;
		
		this.bossGroup = NettyUtils.createEventLoopGroup(1);
		
		this.createDiscoverRequest();
	}
	
	private void createDiscoverRequest() {
		ByteBuf buf = Unpooled.copiedBuffer(DiscoveryUtils.DISCOVERY_MAGIC);
		buf.writeByte(0); //Version number
		buf.writeShort(this.listenerPort);
		
		this.discoverRequest = buf;
	}
	
	public void start(ChatManager chatManager) {
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(this.bossGroup)
			.channel(NettyUtils.getDatagramChannel())
			.option(ChannelOption.SO_BROADCAST, true)
			.option(ChannelOption.SO_REUSEADDR, true)
			.handler(new DiscoverHandler(chatManager));
		
		bootstrap.bind(54321).addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				NetworkDiscoverHandler.this.channel = future.channel();
			}
		});
	}
	
	public void discoverRequest() {
		this.channel.writeAndFlush(new DatagramPacket(this.discoverRequest.retainedSlice(), new InetSocketAddress("255.255.255.255", 54321)));
	}
}
