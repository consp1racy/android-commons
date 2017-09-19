package net.xpece.android.app;

public class SystemServiceNotFoundException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public SystemServiceNotFoundException() {
    }

    public SystemServiceNotFoundException(String message) {
        super(message);
    }
}
