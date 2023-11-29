package nghhng.notificationservice.service;

import lombok.RequiredArgsConstructor;
import nghhng.notificationservice.entity.NotificationEntity;
import nghhng.notificationservice.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

//    public List<NotificationEntity> getNotificationsByUserId(String userId) {
//        return notificationRepository.findAllByToUserIdOrderByCreatedAtDesc(userId);
//    }
//
//    public Long countUnreadNotifications(String userId) {
//        return notificationRepository.countByToUserIdAndIsRead(userId, false);
//    }
//
//    @Transactional
//    public void readNotification(Long notificationId) {
//        notificationRepository.readByNotificationId(notificationId);
//    }
//
//    @Transactional
//    public void readAllNotificationsByUserId(String userId) {
//        notificationRepository.readAllByToUserId(userId);
//    }
}
