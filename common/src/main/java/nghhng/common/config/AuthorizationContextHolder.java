package nghhng.common.config;

public class AuthorizationContextHolder {

    private static final ThreadLocal<String> authorizationHolder = new ThreadLocal<>();

    public static String getAuthorization() {
        return authorizationHolder.get();
    }

    public static void setAuthorization(String authorization) {
        authorizationHolder.set(authorization);
    }

    public static void clear() {
        authorizationHolder.remove();
    }
}