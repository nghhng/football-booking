package nghhng.notificationservice.domain.websocket.manager;

import nghhng.notificationservice.domain.websocket.handlers.BaseClientRequestHandler;
import nghhng.notificationservice.domain.websocket.handlers.TokenAuthenticationHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import nghhng.notificationservice.config.CmdDefs;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ClientRequestHandlerManager implements CommandLineRunner {

    private final Map<Integer, BaseClientRequestHandler> HANDLERS = new ConcurrentHashMap<>();

    public ClientRequestHandlerManager(TokenAuthenticationHandler tokenAuthenticationHandler) {
        HANDLERS.put(CmdDefs.WEBSOCKET_TOKEN_AUTH_CMD, tokenAuthenticationHandler);
    }

    public BaseClientRequestHandler getHandler(int cmd) {
        return HANDLERS.get(cmd);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
