package nghhng.notificationservice.domain.kafka;

import nghhng.notificationservice.domain.websocket.network.IPacket;
import nghhng.notificationservice.domain.websocket.network.IUser;
import nghhng.notificationservice.entity.NotificationEntity;

public class NotificationPackageSender {
//    static void sendDataPackage(NotificationEntity notificationEntity, IUser user, IPacket packet) {
//        packet.addField("type", notificationEntity.getType().name());
//        packet.addField("postId", notificationEntity.getPostId());
//        packet.addField("commentId", notificationEntity.getCommentId());
//        packet.addField("fromUserId", notificationEntity.getFromUserId());
//        packet.addField("toUserId", notificationEntity.getToUserId());
//        packet.addField("message", notificationEntity.getMessage());
//        packet.addField("isRead", notificationEntity.getIsRead());
//        packet.addField("createdAt", notificationEntity.getCreatedAt());
//        user.sendMessageTo(packet);
//    }
}
