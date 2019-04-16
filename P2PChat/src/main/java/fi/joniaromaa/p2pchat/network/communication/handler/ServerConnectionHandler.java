package fi.joniaromaa.p2pchat.network.communication.handler;

import fi.joniaromaa.p2pchat.chat.ChatManager;
import fi.joniaromaa.p2pchat.network.communication.outgoing.authentication.RequestChallengeOutgoingPacket;
import io.netty.channel.ChannelHandlerContext;

public class ServerConnectionHandler extends ConnectionHandler {
	public ServerConnectionHandler(ChatManager chatManager) {
		super(chatManager);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		super.channelActive(ctx);

		this.getChannel().writeAndFlush(new RequestChallengeOutgoingPacket());
	}
}
