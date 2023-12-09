package org.example.dto;

import lombok.*;
import tunght.toby.common.enums.ENotifications;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class NotificationDto {

    private String toUserId;

    private String message;

    private ENotifications type;

    private String fromUserId;

    private String detailId;

    private Boolean isRead;

    private Instant timeStamp;
}
