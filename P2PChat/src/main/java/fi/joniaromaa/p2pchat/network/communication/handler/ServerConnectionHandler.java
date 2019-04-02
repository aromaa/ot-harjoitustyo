package fi.joniaromaa.p2pchat.network.communication.handler;

import fi.joniaromaa.p2pchat.network.communication.IncomingPacket;
import fi.joniaromaa.p2pchat.network.communication.outgoing.PingOutgoingPacket;
import fi.joniaromaa.p2pchat.ui.PanelController;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ServerConnectionHandler extends SimpleChannelInboundHandler<IncomingPacket>
{
	@Getter private final PanelController panel;
	
	@Getter private Channel channel;
	
	@Override
	public void channelActive(ChannelHandlerContext ctx)
	{
		this.channel = ctx.channel();
		
		ctx.fireChannelActive();
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, IncomingPacket msg) throws Exception
	{
		msg.handle(this);
	}
	
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object e)
	{
		if (e instanceof IdleStateEvent)
		{
			this.channel.writeAndFlush(new PingOutgoingPacket());
		}
		
		ctx.fireUserEventTriggered(e);
	}
}
