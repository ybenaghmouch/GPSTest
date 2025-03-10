package ma.gps.test.DAO;

import ma.gps.test.Services.Models.DeviceData;
import ma.gps.test.Services.Models.DeviceDataId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DeviceDataRepository extends JpaRepository<DeviceData, DeviceDataId> {
    // Corrected method
    @Query("SELECT d FROM DeviceData d WHERE d.id.id_device = :id_device ORDER BY d.id.date ASC")
    List<DeviceData> findById_Id_deviceOrderById_DateAsc(@Param("id_device") int id_device);

    // Corrected method
    @Query("SELECT d FROM DeviceData d WHERE d.id.id_device = :id_device AND d.id.date = :date")
    DeviceData findById_Id_deviceAndId_Date(@Param("id_device") int id_device, @Param("date") LocalDateTime date);
}
