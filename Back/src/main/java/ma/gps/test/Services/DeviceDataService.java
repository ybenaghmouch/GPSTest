package ma.gps.test.Services;

import lombok.AllArgsConstructor;
import ma.gps.test.DAO.DeviceDataRepository;
import ma.gps.test.DTO.DeviceInfoRequestDTO;
import ma.gps.test.DTO.DeviceInfoResponseDTO;
import ma.gps.test.DTO.MovementRequestDTO;
import ma.gps.test.DTO.MovementResponseDTO;
import ma.gps.test.Services.Exeptions.BusinessException;
import ma.gps.test.Services.Exeptions.DeviceDataNotFoundException;
import ma.gps.test.Services.Exeptions.ServiceException;
import ma.gps.test.Services.Models.DeviceData;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DeviceDataService implements IDeviceDataService {
    @Autowired
    private final DeviceDataRepository deviceDataRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<MovementResponseDTO> getMovementByIdDevice(int id_device) {
        MovementRequestDTO movementRequestDTO =new MovementRequestDTO();
        movementRequestDTO.setId_device(id_device);
        if (movementRequestDTO == null || movementRequestDTO.getId_device() <= 0) {
            throw new BusinessException("Invalid request: Device ID is missing or invalid.");
        }

        try {
            List<DeviceData> deviceDataList = deviceDataRepository.findById_Id_deviceOrderById_DateAsc(movementRequestDTO.getId_device());

            if (deviceDataList.isEmpty()) {
                throw new DeviceDataNotFoundException("Device with ID " + movementRequestDTO.getId_device() + " not found.");
            }
            return deviceDataList.stream()
                    .map(data -> modelMapper.map(data, MovementResponseDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("An error occurred while fetching device movement data. ", e);
        }
    }

    @Override
    public DeviceInfoResponseDTO getDeviceDataByIdAndDate(int id_device,String Date) {
        DeviceInfoRequestDTO deviceInfoRequestDTO=new DeviceInfoRequestDTO();
        deviceInfoRequestDTO.setDate(modelMapper.map(Date, LocalDateTime.class));deviceInfoRequestDTO.setId_device(id_device);
        if (deviceInfoRequestDTO == null || deviceInfoRequestDTO.getId_device() <= 0 || deviceInfoRequestDTO.getDate() == null) {
            throw new BusinessException("Invalid request: Device ID or date is missing or invalid.");
        }

        try {
            DeviceData deviceData = deviceDataRepository.findById_Id_deviceAndId_Date(
                    deviceInfoRequestDTO.getId_device(),
                    deviceInfoRequestDTO.getDate()
            );

            if (deviceData == null) {
                throw new BusinessException("No data found for device ID " + deviceInfoRequestDTO.getId_device() + " on date " + deviceInfoRequestDTO.getDate());
            }

            return modelMapper.map(deviceData, DeviceInfoResponseDTO.class);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while fetching device data.", e);
        }
    }
}
