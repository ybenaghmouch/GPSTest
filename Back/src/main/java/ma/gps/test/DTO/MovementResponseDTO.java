package ma.gps.test.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MovementResponseDTO {

    private double latitude;
    private double longitude;
    private LocalDateTime date;

}
