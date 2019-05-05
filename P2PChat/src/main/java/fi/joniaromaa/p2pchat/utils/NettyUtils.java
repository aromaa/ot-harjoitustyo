package fi.joniaromaa.p2pchat.utils;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollDatagramChannel;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Helper class for Netty library.
 */
public class NettyUtils {
	private static final boolean EPOLL = Epoll.isAvailable();

	/**
	 * Creates {@link EventLoopGroup} with automatically selected thread count. This could be {@link EpollEventLoopGroup} when supposed, otherwise {@link NioEventLoopGroup}.
	 * 
	 * @return The {@link EventLoopGroup} that was created.
	 */
	public static EventLoopGroup createEventLoopGroup() {
		return NettyUtils.createEventLoopGroup(0);
	}

	/**
	 * Creates {@link EventLoopGroup}, this could be {@link EpollEventLoopGroup} when supposed, otherwise {@link NioEventLoopGroup}.
	 * 
	 * @param threads Amount of threads to reserve.
	 * 
	 * @return The {@link EventLoopGroup} that was created.
	 */
	public static EventLoopGroup createEventLoopGroup(int threads) {
		return NettyUtils.EPOLL ? new EpollEventLoopGroup(threads) : new NioEventLoopGroup(threads);
	}

	/**
	 * Returns {@link ServerChannel} class. This could be {@link EpollServerSocketChannel} when supported, otherwise {@link NioServerSocketChannel}.
	 * 
	 * @return The {@link ServerChannel} class.
	 */
	public static Class<? extends ServerChannel> getServerChannel() {
		return NettyUtils.EPOLL ? EpollServerSocketChannel.class : NioServerSocketChannel.class;
	}

	/**
	 * Returns {@link SocketChannel} class. This could be {@link EpollSocketChannel} when supported, otherwise {@link NioSocketChannel}.
	 * 
	 * @return The {@link SocketChannel} class.
	 */
	public static Class<? extends SocketChannel> getSocketChannel() {
		return NettyUtils.EPOLL ? EpollSocketChannel.class : NioSocketChannel.class;
	}

	/**
	 * Returns {@link DatagramChannel} class. This could be {@link EpollDatagramChannel} when supported, otherwise {@link NioDatagramChannel}.
	 * 
	 * @return The {@link DatagramChannel} class.
	 */
	public static Class<? extends DatagramChannel> getDatagramChannel() {
		return NettyUtils.EPOLL ? EpollDatagramChannel.class : NioDatagramChannel.class;
	}
}
