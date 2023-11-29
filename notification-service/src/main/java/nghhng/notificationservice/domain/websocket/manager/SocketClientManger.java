package nghhng.notificationservice.domain.websocket.manager;

import nghhng.notificationservice.domain.websocket.handlers.BaseClientRequestHandler;
import nghhng.notificationservice.domain.websocket.network.IClientConnection;
import nghhng.notificationservice.domain.websocket.network.IPacket;
import nghhng.notificationservice.domain.websocket.network.UserPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import nghhng.notificationservice.config.CmdDefs;

@Service
public class SocketClientManger {

    private final Logger LOGGER = LoggerFactory.getLogger("SocketClientManger");
    private final ClientRequestHandlerManager clientRequestHandlerManager;

    public SocketClientManger(ClientRequestHandlerManager clientRequestHandlerManager) {
        this.clientRequestHandlerManager = clientRequestHandlerManager;
    }

    public void onMessage(IClientConnection connection, IPacket data) {
        int cmd = data.getCmd();
        if (cmd != CmdDefs.WEBSOCKET_TOKEN_AUTH_CMD) {
            if (connection.getUser() == null) {
                return;
            }
        }
        BaseClientRequestHandler handler = clientRequestHandlerManager.getHandler(cmd);
        if (handler != null) {
            handler.handleClientRequest(connection, data);
        } else {
            LOGGER.info("*** cmd: {} not found handler ***", data.getCmd());
        }
    }

    public void onDisconnect(IClientConnection connection, int reason, String message) {
        IPacket packet = new UserPacket(CmdDefs.CONNECTION_CLOSED_CMD);
        packet.addField("reason", reason);
        packet.addField("message", message);
        BaseClientRequestHandler handler = clientRequestHandlerManager.getHandler(CmdDefs.CONNECTION_CLOSED_CMD);
        handler.handleClientRequest(connection, packet);
    }

}
