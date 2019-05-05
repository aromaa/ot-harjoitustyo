package fi.joniaromaa.p2pchat.network.communication.handler;

import fi.joniaromaa.p2pchat.network.communication.OutgoingPacket;
import fi.joniaromaa.p2pchat.network.communication.manager.PacketManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.RequiredArgsConstructor;

/**
 * Used to encode {@link OutgoingPacket} to {@link ByteBuf}.
 */
@RequiredArgsConstructor
public class PacketEncoderHandler extends MessageToByteEncoder<OutgoingPacket> {
	private final PacketManager packetManager;

	@Override
	protected void encode(ChannelHandlerContext ctx, OutgoingPacket msg, ByteBuf out) throws Exception {
		this.packetManager.writeOutgoingPacket(msg, out);
	}
}
