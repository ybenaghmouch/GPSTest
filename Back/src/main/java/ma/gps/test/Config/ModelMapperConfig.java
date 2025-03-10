package ma.gps.test.Config;

import lombok.AllArgsConstructor;
import lombok.Data;
import ma.gps.test.DTO.DeviceInfoResponseDTO;
import ma.gps.test.Services.Exeptions.BusinessException;
import ma.gps.test.Services.Models.DeviceData;
import ma.gps.test.common.CommonTools;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Configuration
@Data
@AllArgsConstructor
public class ModelMapperConfig {
    private CommonTools tools;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE) // Use STRICT matching
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                //.setAmbiguityIgnored(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        modelMapper.emptyTypeMap(DeviceData.class, DeviceInfoResponseDTO.class)
                .addMapping(src -> src.getId().getDate(), DeviceInfoResponseDTO::setDate) // Map nested date field
                .addMapping(DeviceData::getLatitude, DeviceInfoResponseDTO::setLatitude)
                .addMapping(DeviceData::getLongitude, DeviceInfoResponseDTO::setLongitude)
                .addMapping(DeviceData::getSpeed, DeviceInfoResponseDTO::setSpeed)
                .addMapping(DeviceData::getFuel, DeviceInfoResponseDTO::setFuel)
                .addMapping(DeviceData::getTemp, DeviceInfoResponseDTO::setTemp)
                .addMapping(DeviceData::getX, DeviceInfoResponseDTO::setX)
                .addMapping(DeviceData::getY, DeviceInfoResponseDTO::setY)
                .addMapping(DeviceData::getZ, DeviceInfoResponseDTO::setZ)
                .addMapping(DeviceData::isIgnition, DeviceInfoResponseDTO::setIgnition)
                .addMapping(DeviceData::getRpm, DeviceInfoResponseDTO::setRpm)
                .addMapping(DeviceData::getFuel_rate, DeviceInfoResponseDTO::setFuel_rate)
                .addMapping(DeviceData::getTfu, DeviceInfoResponseDTO::setTfu)
                .addMapping(DeviceData::getOdo, DeviceInfoResponseDTO::setOdo)
                .addMapping(DeviceData::getSatInView, DeviceInfoResponseDTO::setSatInView)
                .addMapping(DeviceData::getSignal, DeviceInfoResponseDTO::setSignal)
                .addMapping(DeviceData::getHeading, DeviceInfoResponseDTO::setHeading)
                .addMapping(DeviceData::isCharger, DeviceInfoResponseDTO::setCharger)
                .addMapping(DeviceData::getState, DeviceInfoResponseDTO::setState)
                .addMapping(DeviceData::getTram_id, DeviceInfoResponseDTO::setTram_id)
                .addMapping(DeviceData::getValidity, DeviceInfoResponseDTO::setValidity)
                .addMapping(DeviceData::getTemp_engine, DeviceInfoResponseDTO::setTemp_engine)
                .addMapping(DeviceData::getAccum_odo, DeviceInfoResponseDTO::setAccum_odo)
                .addMapping(DeviceData::getDo1, DeviceInfoResponseDTO::setDo1)
                .addMapping(DeviceData::getDo2, DeviceInfoResponseDTO::setDo2)
                .addMapping(DeviceData::getDo3, DeviceInfoResponseDTO::setDo3)
                .addMapping(DeviceData::getDo4, DeviceInfoResponseDTO::setDo4)
                .addMapping(DeviceData::getDi1, DeviceInfoResponseDTO::setDi1)
                .addMapping(DeviceData::getDi2, DeviceInfoResponseDTO::setDi2)
                .addMapping(DeviceData::getDi3, DeviceInfoResponseDTO::setDi3)
                .addMapping(DeviceData::getDi4, DeviceInfoResponseDTO::setDi4)
                .addMapping(DeviceData::getAn1, DeviceInfoResponseDTO::setAn1)
                .addMapping(DeviceData::getAn2, DeviceInfoResponseDTO::setAn2)
                .addMapping(DeviceData::getAn3, DeviceInfoResponseDTO::setAn3)
                .addMapping(DeviceData::getAn4, DeviceInfoResponseDTO::setAn4);

        // Converter for Date to String
        Converter<Date, String> dateToStringConverter = new AbstractConverter<>() {
            @Override
            public String convert(Date date) {
                return tools.dateToString(date);
            }
        };

        // Converter for String to Date
        Converter<String, Date> stringToDateConverter = new AbstractConverter<>() {
            @Override
            public Date convert(String s) {
                try {
                    return tools.stringToDate(s);
                } catch (ParseException e) {
                    throw new BusinessException(String.format("The date %s doesn't respect the format %s", s, tools.getDateFormat()));
                }
            }
        };

        // Converter for String to LocalDateTime
        Converter<String, LocalDateTime> stringToLocalDateTimeConverter = new AbstractConverter<>() {
            @Override
            protected LocalDateTime convert(String source) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(tools.getDateFormat());
                return LocalDateTime.parse(source, formatter);
            }
        };

        // Converter for LocalDateTime to String
        Converter<LocalDateTime, String> localDateTimeToStringConverter = new AbstractConverter<>() {
            @Override
            protected String convert(LocalDateTime source) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(tools.getDateFormat());
                return source.format(formatter);
            }
        };

        // Converter for Date to LocalDateTime
        Converter<Date, LocalDateTime> dateToLocalDateTimeConverter = new AbstractConverter<>() {
            @Override
            protected LocalDateTime convert(Date source) {
                return source.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            }
        };

        // Converter for LocalDateTime to Date
        Converter<LocalDateTime, Date> localDateTimeToDateConverter = new AbstractConverter<>() {
            @Override
            protected Date convert(LocalDateTime source) {
                return Date.from(source.atZone(ZoneId.systemDefault()).toInstant());
            }
        };

        // Add converters to ModelMapper
        modelMapper.addConverter(dateToStringConverter);
        modelMapper.addConverter(stringToDateConverter);
        modelMapper.addConverter(stringToLocalDateTimeConverter);
        modelMapper.addConverter(localDateTimeToStringConverter);
        modelMapper.addConverter(dateToLocalDateTimeConverter);
        modelMapper.addConverter(localDateTimeToDateConverter);

        return modelMapper;
    }
}