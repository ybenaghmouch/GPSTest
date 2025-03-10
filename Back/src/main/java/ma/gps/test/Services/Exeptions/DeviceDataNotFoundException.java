package ma.gps.test.Services.Exeptions;

public class DeviceDataNotFoundException extends RuntimeException {
    public DeviceDataNotFoundException(String message) {
        super(message);
    }
}
