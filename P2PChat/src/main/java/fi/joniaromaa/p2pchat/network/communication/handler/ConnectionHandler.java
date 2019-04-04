package fi.joniaromaa.p2pchat.network.communication.handler;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import fi.joniaromaa.p2pchat.network.communication.IncomingPacket;
import fi.joniaromaa.p2pchat.network.communication.outgoing.PingOutgoingPacket;
import fi.joniaromaa.p2pchat.ui.PanelController;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.internal.ThreadLocalRandom;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ConnectionHandler extends SimpleChannelInboundHandler<IncomingPacket>
{
	@Getter private final PanelController panel;
	
	@Getter private Channel channel;
	
	@Getter private byte[] pendingChallenge;
	
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
	
	public void createChallenge()
	{
		Random random;
		
		try
		{
			random = SecureRandom.getInstanceStrong();
		}
		catch (NoSuchAlgorithmException e)
		{
			//TODO: Umh......
			
			random = ThreadLocalRandom.current();
		}
		
		this.pendingChallenge = new byte[256];
		
		random.nextBytes(this.pendingChallenge);
	}
}
