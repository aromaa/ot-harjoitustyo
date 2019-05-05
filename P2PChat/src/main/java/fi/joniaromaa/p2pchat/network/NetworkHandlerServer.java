package fi.joniaromaa.p2pchat.network;

import fi.joniaromaa.p2pchat.chat.ChatManager;
import fi.joniaromaa.p2pchat.network.communication.handler.ServerConnectionHandler;
import fi.joniaromaa.p2pchat.network.communication.manager.PacketManager;
import fi.joniaromaa.p2pchat.utils.NettyUtils;
import fi.joniaromaa.p2pchat.utils.NetworkHandlerUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import lombok.Getter;

/**
 * Listens for incoming {@link NetworkHandlerClient}.
 */
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

	/**
	 * Binds to random port and starts to listen for connections.
	 * 
	 * @param chatManager The {@link ChatManager} to deal with upcoming connections.
	 * 
	 * @return {@link ChannelFuture} to listen when the bind was completed.
	 */
	public ChannelFuture start(ChatManager chatManager) {
		ServerBootstrap boostrap = new ServerBootstrap();
		boostrap.group(this.bossGroup, this.childGroup)
				.channel(NettyUtils.getServerChannel())
				.option(ChannelOption.SO_BACKLOG, 100)
				.childOption(ChannelOption.TCP_NODELAY, true)
				.childHandler(this.createChannelInitializer(chatManager));

		return boostrap.bind(0).addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				NetworkHandlerServer.this.channel = future.channel();
			}
		});
	}

	private ChannelInitializer<SocketChannel> createChannelInitializer(ChatManager chatManager) {
		return new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel channel) throws Exception {
				ChannelPipeline pipeline = channel.pipeline();

				NetworkHandlerUtils.defaultPipeline(pipeline, NetworkHandlerServer.this.packetManager);
				
				pipeline.addLast(new ServerConnectionHandler(chatManager));
			}
		};
	}

	/**
	 * Stops listening for connections and disconnects all clients.
	 */
	public void stop() {
		this.bossGroup.shutdownGracefully();
		this.childGroup.shutdownGracefully();
	}
}
