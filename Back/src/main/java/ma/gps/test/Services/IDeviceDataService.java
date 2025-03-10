package ma.gps.test.Services;

import ma.gps.test.DTO.DeviceInfoRequestDTO;
import ma.gps.test.DTO.DeviceInfoResponseDTO;
import ma.gps.test.DTO.MovementRequestDTO;
import ma.gps.test.DTO.MovementResponseDTO;
import java.util.List;

public interface IDeviceDataService {
    List<MovementResponseDTO> getMovementByIdDevice(int id_device);
    DeviceInfoResponseDTO getDeviceDataByIdAndDate(int id_device,String Date);
}
