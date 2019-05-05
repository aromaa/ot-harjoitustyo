package fi.joniaromaa.p2pchat.utils;

import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;

/**
 * Helper class for {@link ByteBuf}.
 */
public class ByteBufUtils {
	/**
	 * Writes bytes to {@link ByteBuf} with length prefix.
	 * 
	 * @param buf The buffer to write to.
	 * @param value The bytes to write.
	 */
	public static void writeBytes(ByteBuf buf, byte[] value) {
		buf.writeShort(value.length); // 7bit encoding?
		buf.writeBytes(value);
	}

	/**
	 * Writes UTF-8 string to {@link ByteBuf} with length prefix.
	 * 
	 * @param buf The buffer to write to.
	 * @param value The string to write.
	 */
	public static void writeString(ByteBuf buf, String value) {
		ByteBufUtils.writeBytes(buf, value.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * Reads length prefixed byte array from {@link ByteBuf}.
	 * 
	 * @param buf The buffer to read from.
	 * 
	 * @return The read byte array.
	 */
	public static byte[] readBytes(ByteBuf buf) {
		byte[] bytes = new byte[buf.readShort()]; // 7bit encoding?

		buf.readBytes(bytes);

		return bytes;
	}

	/**
	 * Reads length prefixed UTF-8 string from {@link ByteBuf}.
	 * 
	 * @param buf The buffer to read from.
	 * 
	 * @return The read UTF-8 string.
	 */
	public static String readString(ByteBuf buf) {
		return new String(ByteBufUtils.readBytes(buf), StandardCharsets.UTF_8);
	}
}
