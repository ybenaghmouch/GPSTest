package ma.gps.test.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
public class DeviceInfoResponseDTO {
    private LocalDateTime date;
    private int id_device;
    private int speed;
    private int fuel;
    private String temp;
    private int X;
    private int Y;
    private int Z;
    private boolean ignition;
    private int rpm;
    private double fuel_rate;
    private double tfu;
    private double odo;
    private int SatInView;
    private int signal;
    private int heading;
    private boolean charger;
    private Double latitude;
    private Double longitude;
    private Boolean state;
    private Integer tram_id;
    private Boolean validity;
    private Integer temp_engine;
    private Float accum_odo;
    private Integer do1;
    private Integer do2;
    private Integer do3;
    private Integer do4;
    private Integer di1;
    private Integer di2;
    private Integer di3;
    private Integer di4;
    private Integer an1;
    private Integer an2;
    private Integer an3;
    private Integer an4;
}
