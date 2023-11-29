package tunght.toby.common.enums;

public enum ENotifications {
//    param_1 là username
    BOOKING("%s đã đặt sân."),
    REQUEST("%s đã đề nghị trở thành đối thủ của bạn."),
    ACCEPT("%s đã chấp nhận lời mời trở thành đối thủ của bạn."),

    DENY("%s đã từ chối lời mời trở thành đối thủ của bạn.");


    public final String notificationTemplate;

    ENotifications(String notificationTemplate) {
        this.notificationTemplate = notificationTemplate;
    }

    public static String getNotificationMessage(ENotifications type, String username) {
        return String.format(type.notificationTemplate, username);
    }
}
