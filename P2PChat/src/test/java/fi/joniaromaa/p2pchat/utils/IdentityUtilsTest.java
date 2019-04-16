package fi.joniaromaa.p2pchat.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

public class IdentityUtilsTest
{
	@Test
	public void nameLengthTest()
	{
		assertFalse(IdentityUtils.isValidNickname(null));
		assertFalse(IdentityUtils.isValidNickname(""));
		assertTrue(IdentityUtils.isValidNickname(this.generateString(1)));
		assertTrue(IdentityUtils.isValidNickname(this.generateString(32)));
		assertFalse(IdentityUtils.isValidNickname(this.generateString(33)));
	}
	
	private String generateString(int length)
	{
		return IntStream.range(0, length).mapToObj(i -> "J").collect(Collectors.joining(""));
	}
}
