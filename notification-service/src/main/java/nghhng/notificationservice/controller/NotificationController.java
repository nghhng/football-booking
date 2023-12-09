package nghhng.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import nghhng.notificationservice.entity.NotificationEntity;
import nghhng.notificationservice.service.NotificationService;
import tunght.toby.common.security.AuthUserDetails;

import java.util.List;

@RestController
@RequestMapping(value = "/notifications")
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
//
//    @PutMapping("read/{id}")
//    public void readNotification(@PathVariable(name = "id") Long notificationId) {
//        notificationService.readNotification(notificationId);
//    }
//
//    @PutMapping("read-all")
//    public void readAllNotificationsByUserId(@RequestParam(name = "userId") String userId) {
//        notificationService.readAllNotificationsByUserId(userId);
//    }
}
