package fi.joniaromaa.p2pchat.network;

import java.util.concurrent.TimeUnit;

import fi.joniaromaa.p2pchat.chat.ChatManager;
import fi.joniaromaa.p2pchat.network.communication.OutgoingPacket;
import fi.joniaromaa.p2pchat.network.communication.handler.ClientConnectionHandler;
import fi.joniaromaa.p2pchat.network.communication.handler.PacketDecoderHandler;
import fi.joniaromaa.p2pchat.network.communication.handler.PacketEncoderHandler;
import fi.joniaromaa.p2pchat.network.communication.manager.PacketManager;
import fi.joniaromaa.p2pchat.utils.NettyUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

public class NetworkHandlerClient {
	private PacketManager packetManager;

	private EventLoopGroup bossGroup;

	private Channel channel;

	public NetworkHandlerClient() {
		this.packetManager = new PacketManager();

		this.bossGroup = NettyUtils.createEventLoopGroup(1);
	}

	public void start(ChatManager chatManager, String ip, int port) {
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(this.bossGroup)
				.channel(NettyUtils.getSocketChannel())
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(this.createChannelInitializer(chatManager));

		this.channel = bootstrap.connect(ip, port).awaitUninterruptibly().channel(); // Change to listener probs
	}

	private ChannelInitializer<SocketChannel> createChannelInitializer(ChatManager chatManager) {
		return new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel channel) throws Exception {
				ChannelPipeline pipeline = channel.pipeline();

				pipeline.addLast(new LengthFieldPrepender(3));
				pipeline.addLast(new PacketEncoderHandler(NetworkHandlerClient.this.packetManager));

				pipeline.addLast(new IdleStateHandler(30, 15, 0, TimeUnit.SECONDS));

				pipeline.addLast(new LengthFieldBasedFrameDecoder(1 << 24, 0, 3, 0, 3));
				pipeline.addLast(new PacketDecoderHandler(NetworkHandlerClient.this.packetManager));
				pipeline.addLast(new ClientConnectionHandler(chatManager));
			}
		};
	}

	public void send(OutgoingPacket packet) {
		this.channel.writeAndFlush(packet);
	}

	public void stop() {
		this.bossGroup.shutdownGracefully();
	}
}
