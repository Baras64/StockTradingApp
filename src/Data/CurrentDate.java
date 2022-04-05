package Data;

import java.text.SimpleDateFormat;
import java.util.Date;
public class CurrentDate {
    public static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }
}