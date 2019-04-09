package fi.joniaromaa.p2pchat.network.communication.handler;

import fi.joniaromaa.p2pchat.network.communication.IncomingPacket;
import fi.joniaromaa.p2pchat.network.communication.outgoing.PingOutgoingPacket;
import fi.joniaromaa.p2pchat.ui.PanelController;
import fi.joniaromaa.p2pchat.utils.EncryptionUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ConnectionHandler extends SimpleChannelInboundHandler<IncomingPacket> {
	@Getter private final PanelController panel;

	@Getter private Channel channel;

	@Getter private byte[] pendingChallenge;

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		this.channel = ctx.channel();

		ctx.fireChannelActive();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, IncomingPacket msg) throws Exception {
		msg.handle(this);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object e) {
		if (e instanceof IdleStateEvent) {
			IdleStateEvent idleEvent = (IdleStateEvent) e;
			
			if (idleEvent.state() == IdleState.READER_IDLE) {
				this.channel.close();
			} else if (idleEvent.state() == IdleState.WRITER_IDLE) {
				this.channel.writeAndFlush(new PingOutgoingPacket());
			}
		}

		ctx.fireUserEventTriggered(e);
	}

	public void createChallenge() {
		this.pendingChallenge = EncryptionUtils.requestRandomBytes(256);
	}
}
