package fi.joniaromaa.p2pchat.utils;

import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;

public class ByteBufUtils
{
	public static void writeBytes(ByteBuf buf, byte[] value)
	{
		buf.writeShort(value.length); //7bit encoding?
		buf.writeBytes(value);
	}
	
	public static void writeString(ByteBuf buf, String value)
	{
		ByteBufUtils.writeBytes(buf, value.getBytes(StandardCharsets.UTF_8));
	}
	
	public static byte[] readBytes(ByteBuf buf)
	{
		byte[] bytes = new byte[buf.readShort()]; //7bit encoding?
		
		buf.readBytes(bytes);
		
		return bytes;
	}
	
	public static String readString(ByteBuf buf)
	{
		return new String(ByteBufUtils.readBytes(buf), StandardCharsets.UTF_8);
	}
}
