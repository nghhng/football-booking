package nghhng.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import tunght.toby.common.enums.ENotifications;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class NotificationDto {

    private String toUserId;

    private String message;

    private ENotifications type;

    private String fromUserId;

    private String bookingId;

    private Boolean isRead;
}
