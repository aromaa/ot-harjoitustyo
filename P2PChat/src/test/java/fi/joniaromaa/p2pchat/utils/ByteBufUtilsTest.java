package fi.joniaromaa.p2pchat.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ByteBufUtilsTest
{
	private ByteBuf buf;
	
	@Before
	public void setup()
	{
		this.buf = Unpooled.buffer();
	}
	
	@Test
	public void testEncodeAndDecode()
	{
		final String TEST_STRING = "THIS@Awesome§TEST~MAKES?Sure╛Everything-Encodes";
		
		ByteBufUtils.writeString(this.buf, TEST_STRING);
		
		assertEquals(TEST_STRING, ByteBufUtils.readString(this.buf));
	}
}
