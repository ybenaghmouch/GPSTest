package ma.gps.test.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DeviceInfoRequestDTO {
    private int id_device;
    private LocalDateTime date;
}
