package nghhng.notificationservice.domain.websocket.network;

public interface IUserClient {
    void addConnection(IClientConnection connection);

    void removeConnection(IClientConnection connection);
}
