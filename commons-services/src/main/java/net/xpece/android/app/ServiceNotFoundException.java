package net.xpece.android.app;

public class ServiceNotFoundException extends NullPointerException {
    static final long serialVersionUID = 1L;

    public ServiceNotFoundException() {
    }

    public ServiceNotFoundException(String message) {
        super(message);
    }
}
