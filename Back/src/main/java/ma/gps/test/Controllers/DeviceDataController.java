package ma.gps.test.Controllers;

import lombok.AllArgsConstructor;
import ma.gps.test.DTO.DeviceInfoRequestDTO;
import ma.gps.test.DTO.DeviceInfoResponseDTO;
import ma.gps.test.DTO.MovementRequestDTO;
import ma.gps.test.DTO.MovementResponseDTO;
import ma.gps.test.Services.Exeptions.BusinessException;
import ma.gps.test.Services.Exeptions.DeviceDataNotFoundException;
import ma.gps.test.Services.Exeptions.DeviceNotFoundException;
import ma.gps.test.Services.IDeviceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/device-data")
public class DeviceDataController {


    private final IDeviceDataService deviceDataService;

    @GetMapping("/movement")
    public ResponseEntity<?> getMovementByIdDevice(@RequestParam int id_device) {
        try {
            List<MovementResponseDTO> movementData = deviceDataService.getMovementByIdDevice(id_device);
            return ResponseEntity.ok(movementData);
        } catch (BusinessException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("An unexpected error occurred: " + ex.getMessage());
        }
    }

    @GetMapping("/info")
    public ResponseEntity<DeviceInfoResponseDTO> getDeviceDataByIdAndDate(@RequestParam int id_device,@RequestParam String Date ) {
        DeviceInfoResponseDTO deviceInfo = deviceDataService.getDeviceDataByIdAndDate(id_device, Date);
        return ResponseEntity.ok(deviceInfo);
    }
}
