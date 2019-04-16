package fi.joniaromaa.p2pchat.network;

import java.util.concurrent.TimeUnit;

import fi.joniaromaa.p2pchat.chat.ChatManager;
import fi.joniaromaa.p2pchat.network.communication.handler.PacketDecoderHandler;
import fi.joniaromaa.p2pchat.network.communication.handler.PacketEncoderHandler;
import fi.joniaromaa.p2pchat.network.communication.handler.ServerConnectionHandler;
import fi.joniaromaa.p2pchat.network.communication.manager.PacketManager;
import fi.joniaromaa.p2pchat.utils.NettyUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.Getter;

public class NetworkHandlerServer {
	private PacketManager packetManager;

	private EventLoopGroup bossGroup;
	private EventLoopGroup childGroup;
	
	@Getter private Channel channel;

	public NetworkHandlerServer() {
		this.packetManager = new PacketManager();

		this.bossGroup = NettyUtils.createEventLoopGroup(1);
		this.childGroup = NettyUtils.createEventLoopGroup();
	}

	public void start(ChatManager chatManager) {
		ServerBootstrap boostrap = new ServerBootstrap();
		boostrap.group(this.bossGroup, this.childGroup)
				.channel(NettyUtils.getServerChannel())
				.option(ChannelOption.SO_BACKLOG, 100)
				.childOption(ChannelOption.TCP_NODELAY, true)
				.childHandler(this.createChannelInitializer(chatManager));

		this.channel = boostrap.bind(0).syncUninterruptibly().channel();
	}

	private ChannelInitializer<SocketChannel> createChannelInitializer(ChatManager chatManager) {
		return new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel channel) throws Exception {
				ChannelPipeline pipeline = channel.pipeline();

				pipeline.addLast(new LengthFieldPrepender(3));
				pipeline.addLast(new PacketEncoderHandler(NetworkHandlerServer.this.packetManager));

				pipeline.addLast(new IdleStateHandler(30, 15, 0, TimeUnit.SECONDS));

				pipeline.addLast(new LengthFieldBasedFrameDecoder(1 << 24, 0, 3, 0, 3));
				pipeline.addLast(new PacketDecoderHandler(NetworkHandlerServer.this.packetManager));
				pipeline.addLast(new ServerConnectionHandler(chatManager));
			}
		};
	}

	public void stop() {
		this.bossGroup.shutdownGracefully();
		this.childGroup.shutdownGracefully();
	}
}
