package ma.gps.test.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Data
public class CommonTools {

    @Value("${rest.date.format}")
    private String dateFormat;

    public String dateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(date);
    }
    public String getDateFormat() {
        return dateFormat;
    }
    public Date stringToDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.parse(date);
    }
}

