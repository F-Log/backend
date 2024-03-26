    package com.f_log.flog.dietfood.util;

    import java.math.BigDecimal;
    import java.math.RoundingMode;

    public class roundUtil {
        public static double roundToTwoDecimals(double value) {
            return BigDecimal.valueOf(value)
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
        }
    }
