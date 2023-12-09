package nghhng.notificationservice.service;

import lombok.RequiredArgsConstructor;
import nghhng.notificationservice.entity.NotificationEntity;
import nghhng.notificationservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    @Autowired
    MongoTemplate mongoTemplate;

    private final NotificationRepository notificationRepository;

    public List<NotificationEntity> getNotificationsByUserId(String userId) {
        return notificationRepository.findByToUserIdOrderByTimeStamp(userId);
    }

    public Long countUnreadNotifications(String userId) {
        return notificationRepository.countByToUserIdAndIsRead(userId, false);
    }

    public void readNotification(String id){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("isRead", true);
        mongoTemplate.updateMulti(query, update, NotificationEntity.class);
    }

    public void readAllNotificationsByUserId(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("toUserId").is(userId));
        Update update = new Update();
        update.set("isRead", true);
        mongoTemplate.updateMulti(query, update, NotificationEntity.class);
    }
}
