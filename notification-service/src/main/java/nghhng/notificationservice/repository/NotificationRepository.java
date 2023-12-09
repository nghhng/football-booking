package nghhng.notificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import nghhng.notificationservice.entity.NotificationEntity;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<NotificationEntity, Long> {
//    List<NotificationEntity> findAllByToUserIdOrderByCreatedAtDesc(String userId);
//
//    Long countByToUserIdAndIsRead(String userId, Boolean isRead);
//
//    @Modifying
////    @Query(value = "UPDATE NotificationEntity n " +
////            "SET n.isRead = true " +
////            "WHERE n.toUserId LIKE :userId")
//    void readAllByToUserId(String userId);
//
//    @Modifying
////    @Query(value = "UPDATE NotificationEntity n " +
////            "SET n.isRead = true " +
////            "WHERE n.id = :notificationId")
//    void readByNotificationId(Long notificationId);

    List<NotificationEntity> findByToUserIdOrderByTimeStamp(String userId);

    Long countByToUserIdAndIsRead(String userId, Boolean isRead);

//    @Modifying
//    @Query("{'toUserId': :userId}")
//    void readAllByToUserId(@Param("userId") String userId);


}
