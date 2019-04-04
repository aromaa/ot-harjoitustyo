package fi.joniaromaa.p2pchat.network.communication.handler;

import fi.joniaromaa.p2pchat.network.communication.outgoing.authentication.RequestChallengeOutgoingPacket;
import fi.joniaromaa.p2pchat.ui.PanelController;
import io.netty.channel.ChannelHandlerContext;

public class ServerConnectionHandler extends ConnectionHandler
{
	public ServerConnectionHandler(PanelController panel)
	{
		super(panel);
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx)
	{
		super.channelActive(ctx);
		
		this.getChannel().writeAndFlush(new RequestChallengeOutgoingPacket());
	}
}
