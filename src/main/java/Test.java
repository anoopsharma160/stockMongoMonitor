import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;

import static java.util.Locale.*;

public class Test {
    public static void main(String[] args) throws ParseException {

        String decimalString="-1,234.12";
        String decimalString1="1,043.20";

        DecimalFormat decimalFormat= (DecimalFormat) DecimalFormat.getInstance();

        System.out.println(decimalFormat.parse(decimalString1));


    }
}
