package feedme.feedme_socket;

import com.corundumstudio.socketio.listener.*;
import com.corundumstudio.socketio.*;

public class ChatLauncher {

	public static void main(String[] args) throws InterruptedException {

		Configuration config = new Configuration();
		
		config.setHostname("localhost");
		config.setPort(9092);

		final SocketIOServer server = new SocketIOServer(config);
		server.addEventListener("chatevent", ChatObject.class, new DataListener<ChatObject>() {
			public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
				// broadcast messages to all clients
				System.out.println(data);
				server.getBroadcastOperations().sendEvent("chatevent", data);
			}
		});

		System.out.println("Start socket");
		
		server.start();

		Thread.sleep(Integer.MAX_VALUE);

		server.stop();
	}

}
