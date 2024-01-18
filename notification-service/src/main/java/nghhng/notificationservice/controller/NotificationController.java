package nghhng.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import nghhng.notificationservice.entity.NotificationEntity;
import nghhng.notificationservice.service.NotificationService;
import nghhng.common.security.AuthUserDetails;

import java.util.List;

@RestController
@RequestMapping(value = "/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public List<NotificationEntity> getNotificationsByUserId(@AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return notificationService.getNotificationsByUserId(authUserDetails.getId());
    }

    @GetMapping("/count-unread")
    public Long countUnreadNotifications(@AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return notificationService.countUnreadNotifications(authUserDetails.getId());
    }

    @PutMapping("read/{id}")
    public void readNotification(@PathVariable(name = "id") String notificationId) {
        notificationService.readNotification(notificationId);
    }

    @PutMapping("read-all")
    public void readAllNotificationsByUserId(@AuthenticationPrincipal AuthUserDetails authUserDetails) {
        notificationService.readAllNotificationsByUserId(authUserDetails.getId());
    }
}
