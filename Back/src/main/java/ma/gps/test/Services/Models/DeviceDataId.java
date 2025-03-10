package ma.gps.test.Services.Models;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Data
@NoArgsConstructor
public class DeviceDataId implements Serializable {
    private LocalDateTime date;
    private int id_device;

}
