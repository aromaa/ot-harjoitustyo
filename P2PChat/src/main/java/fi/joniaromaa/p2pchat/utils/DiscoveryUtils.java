package fi.joniaromaa.p2pchat.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class DiscoveryUtils {
	public static final ByteBuf DISCOVERY_MAGIC = Unpooled.copiedBuffer(new byte[]{ 3, -3, 6, -6, 5, -5, 55, -55, 33, -33, 78, -78, 66, -66 });
}
