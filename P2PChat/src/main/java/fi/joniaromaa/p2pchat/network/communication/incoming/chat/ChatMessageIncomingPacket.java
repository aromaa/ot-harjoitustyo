package fi.joniaromaa.p2pchat.network.communication.incoming.chat;

import fi.joniaromaa.p2pchat.network.communication.IncomingPacket;
import fi.joniaromaa.p2pchat.network.communication.handler.ConnectionHandler;
import fi.joniaromaa.p2pchat.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

public class ChatMessageIncomingPacket implements IncomingPacket {

	@Getter private String message;
	
	@Override
	public void read(ByteBuf in) {
		this.message = ByteBufUtils.readString(in);
	}

	@Override
	public void handle(ConnectionHandler handler) throws Exception {
		handler.getContact().ifPresent((c) ->
		{
			handler.getChatManager().getConversions().getOrCreate(c).addChatMessage(c, this.message);
		});
	}
}
