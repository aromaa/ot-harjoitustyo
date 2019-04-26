package fi.joniaromaa.p2pchat.utils;

import java.util.concurrent.TimeUnit;

import fi.joniaromaa.p2pchat.network.communication.handler.PacketDecoderHandler;
import fi.joniaromaa.p2pchat.network.communication.handler.PacketEncoderHandler;
import fi.joniaromaa.p2pchat.network.communication.manager.PacketManager;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

public class NetworkHandlerUtils {
	public static void defaultPipeline(ChannelPipeline pipeline, PacketManager packetManager) {

		pipeline.addLast(new LengthFieldPrepender(3));
		pipeline.addLast(new PacketEncoderHandler(packetManager));

		pipeline.addLast(new IdleStateHandler(30, 15, 0, TimeUnit.SECONDS));

		pipeline.addLast(new LengthFieldBasedFrameDecoder(1 << 24, 0, 3, 0, 3));
		pipeline.addLast(new PacketDecoderHandler(packetManager));
	}
}
