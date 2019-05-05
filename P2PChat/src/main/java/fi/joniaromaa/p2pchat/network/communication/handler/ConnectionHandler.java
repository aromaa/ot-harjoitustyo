package fi.joniaromaa.p2pchat.network.communication.handler;

import java.util.Optional;

import com.google.common.base.Preconditions;

import fi.joniaromaa.p2pchat.chat.ChatManager;
import fi.joniaromaa.p2pchat.identity.ContactIdentity;
import fi.joniaromaa.p2pchat.network.communication.IncomingPacket;
import fi.joniaromaa.p2pchat.network.communication.outgoing.PingOutgoingPacket;
import fi.joniaromaa.p2pchat.utils.EncryptionUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Holds the data of the current network session status.
 */
@RequiredArgsConstructor
public abstract class ConnectionHandler extends SimpleChannelInboundHandler<IncomingPacket> {
	private static final int CHALLENGE_SIZE = 256;
	
	@Getter private final ChatManager chatManager;

	@Getter private Channel channel;

	@Getter private byte[] pendingChallenge;
	private ContactIdentity contact;

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		this.channel = ctx.channel();

		ctx.fireChannelActive();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, IncomingPacket msg) throws Exception {
		msg.handle(this);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object e) {
		if (e instanceof IdleStateEvent) {
			IdleStateEvent idleEvent = (IdleStateEvent) e;
			
			if (idleEvent.state() == IdleState.READER_IDLE) {
				this.channel.close();
			} else if (idleEvent.state() == IdleState.WRITER_IDLE) {
				this.channel.writeAndFlush(new PingOutgoingPacket());
			}
		}

		ctx.fireUserEventTriggered(e);
	}

	/**
	 * Creates new challenge to authenticate the other user with.
	 */
	public void createChallenge() {
		Preconditions.checkState(this.contact == null, "Contact has already been initialized");
		Preconditions.checkState(this.pendingChallenge == null, "createChallenge() should only be called once");
		
		this.pendingChallenge = EncryptionUtils.requestRandomBytes(ConnectionHandler.CHALLENGE_SIZE);
	}
	
	/**
	 * Sets the other user contact.
	 * 
	 * @param contact The {@link ContactIdentity} that we communicate with.
	 */
	public void setContact(ContactIdentity contact) {
		Preconditions.checkState(this.contact == null, "Contact has already been initialized");
		
		this.contact = contact;
		this.pendingChallenge = null;
	}
	
	/**
	 * Gets the {@link ContactIdentity} that we communicate with.
	 * 
	 * @return The {@link ContactIdentity}, or if not initialized yet, {@link Optional#empty()}
	 */
	public Optional<ContactIdentity> getContact() {
		return Optional.ofNullable(this.contact);
	}
}
