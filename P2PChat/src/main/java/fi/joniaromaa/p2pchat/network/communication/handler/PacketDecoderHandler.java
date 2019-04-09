package fi.joniaromaa.p2pchat.network.communication.handler;

import java.util.List;

import fi.joniaromaa.p2pchat.network.communication.manager.PacketManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PacketDecoderHandler extends ByteToMessageDecoder {
	private final PacketManager packetManager;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		out.add(this.packetManager.readIncomingPacket(in));
	}
}
