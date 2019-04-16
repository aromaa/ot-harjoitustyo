package fi.joniaromaa.p2pchat.network.communication.outgoing.chat;

import fi.joniaromaa.p2pchat.network.communication.OutgoingPacket;
import fi.joniaromaa.p2pchat.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChatMessageOutgoingPacket implements OutgoingPacket {

	@Getter private final String message;
	
	@Override
	public void write(ByteBuf out) {
		ByteBufUtils.writeString(out, this.message);
	}
}
