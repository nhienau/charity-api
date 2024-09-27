package com.test.charity_api.util;

import java.text.ParseException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

public class DateTimeUtil {

    public static Date stringToDate(String s) throws ParseException {
        TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse(s);
        Instant i = Instant.from(ta);
        Date d = Date.from(i);
        return d;
    }
}
