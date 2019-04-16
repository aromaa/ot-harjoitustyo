package fi.joniaromaa.p2pchat.network.communication.handler;

import fi.joniaromaa.p2pchat.chat.ChatManager;

public class ClientConnectionHandler extends ConnectionHandler {
	public ClientConnectionHandler(ChatManager chatManager) {
		super(chatManager);
	}
}
