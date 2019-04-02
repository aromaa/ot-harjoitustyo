package fi.joniaromaa.p2pchat.network.communication.incoming;

import fi.joniaromaa.p2pchat.network.communication.IncomingPacket;
import fi.joniaromaa.p2pchat.network.communication.handler.ServerConnectionHandler;
import fi.joniaromaa.p2pchat.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;

public class WhoAreYouIncomingPacket implements IncomingPacket
{
	private byte[] iAm;
	private String nickname;

	//TODO: Challange
	
	@Override
	public void read(ByteBuf in)
	{
		this.iAm = ByteBufUtils.readBytes(in);
		this.nickname = ByteBufUtils.readString(in);
	}

	@Override
	public void handle(ServerConnectionHandler handler) throws Exception
	{
		handler.getPanel().getContacts().getItems().add(this.nickname);
	}
}
