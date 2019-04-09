package fi.joniaromaa.p2pchat.network.communication.authentication;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.security.KeyPair;
import java.util.Arrays;

import org.junit.Test;

import fi.joniaromaa.p2pchat.network.communication.incoming.authentication.SolveChallengeIncomingPacket;
import fi.joniaromaa.p2pchat.network.communication.incoming.authentication.WhoAreYouIncomingPacket;
import fi.joniaromaa.p2pchat.network.communication.outgoing.authentication.SolveChallengeOutgoingPacket;
import fi.joniaromaa.p2pchat.network.communication.outgoing.authentication.WhoAreYouOutgoingPacket;
import fi.joniaromaa.p2pchat.utils.EncryptionUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class AuthenticationPacketsTest
{
	@Test
	public void testSolveChallange()
	{
		byte[] challenge = EncryptionUtils.requestRandomBytes(256);
		
		SolveChallengeOutgoingPacket outgoingPacket = new SolveChallengeOutgoingPacket(challenge);
		
		ByteBuf buffer = Unpooled.buffer();
		
		outgoingPacket.write(buffer);
		
		SolveChallengeIncomingPacket incomingPacket = new SolveChallengeIncomingPacket();
		incomingPacket.read(buffer);
		
		assertTrue(Arrays.equals(incomingPacket.getChallenge(), outgoingPacket.getChallenge()));
		
		assertFalse(buffer.isReadable());
	}
	
	@Test
	public void testWhoAreYou()
	{
		KeyPair keyPair = EncryptionUtils.generateKeyPair();
		byte[] challenge = EncryptionUtils.requestRandomBytes(256);
		
		WhoAreYouOutgoingPacket outgoingPacket = new WhoAreYouOutgoingPacket(keyPair.getPublic().getEncoded(), "BIG-BOI-HOI!", challenge);
		
		ByteBuf buffer = Unpooled.buffer();
		
		outgoingPacket.write(buffer);
		
		WhoAreYouIncomingPacket incomingPacket = new WhoAreYouIncomingPacket();
		incomingPacket.read(buffer);

		assertTrue(Arrays.equals(incomingPacket.getIAm(), outgoingPacket.getIAm()));
		assertTrue(incomingPacket.getNickname().equals(outgoingPacket.getNickname()));
		assertTrue(Arrays.equals(incomingPacket.getChallenge(), outgoingPacket.getChallenge()));
		
		assertFalse(buffer.isReadable());
	}
}
