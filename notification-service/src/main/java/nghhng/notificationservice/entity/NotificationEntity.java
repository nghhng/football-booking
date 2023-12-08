package nghhng.notificationservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tunght.toby.common.entity.BaseEntity;
import tunght.toby.common.enums.ENotifications;

import javax.persistence.*;
import java.time.ZonedDateTime;
@Document(collection = "notification")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationEntity{

    @Id
    private String id;

    private String toUserId;

    private String message;

    private ENotifications type;

    private String fromUserId;

    private String detailId;

    private Boolean isRead;

}
