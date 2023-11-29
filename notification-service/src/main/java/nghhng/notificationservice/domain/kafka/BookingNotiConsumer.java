package nghhng.notificationservice.domain.kafka;

import lombok.RequiredArgsConstructor;
import nghhng.notificationservice.config.CmdDefs;
import nghhng.notificationservice.domain.websocket.manager.UserManager;
import nghhng.notificationservice.domain.websocket.network.IPacket;
import nghhng.notificationservice.domain.websocket.network.IUser;
import nghhng.notificationservice.domain.websocket.network.UserPacket;
import nghhng.notificationservice.dto.NotificationDto;
import nghhng.notificationservice.entity.NotificationEntity;
import nghhng.notificationservice.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import tunght.toby.common.utils.JsonConverter;

@Component
@RequiredArgsConstructor
public class BookingNotiConsumer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final NotificationRepository notificationRepository;

    @KafkaListener(topics = "${kafka.booking-noti-topic}", groupId = "${kafka.group-noti-id}", containerFactory = "notiListenerContainerFactory")
    public void receiveMessage(@Payload String notification) {
        logger.info("receive payload: {}", notification);
        NotificationDto notificationDto = JsonConverter.deserializeObject(notification, NotificationDto.class);

        NotificationEntity notificationEntity = NotificationEntity.builder()
                .type(notificationDto.getType())
                .bookingId(notificationDto.getBookingId())
                .fromUserId(notificationDto.getFromUserId())
                .toUserId(notificationDto.getToUserId())
                .message(notificationDto.getMessage())
                .isRead(false)
                .build();

//        IUser user = UserManager.getUser(notificationDto.getToUserId());
//        if (user == null) {
//            logger.info("user {} not online", notificationDto.getToUserId());
//        } else {
////            IPacket packet = new UserPacket(CmdDefs.COMMENT_NOTI_CMD);
////            NotificationPackageSender.sendDataPackage(notificationEntity, user, packet);
//            System.out.println(notificationEntity.toString());
//        }
        System.out.println(notificationEntity.toString());

        notificationRepository.save(notificationEntity);
    }
}
