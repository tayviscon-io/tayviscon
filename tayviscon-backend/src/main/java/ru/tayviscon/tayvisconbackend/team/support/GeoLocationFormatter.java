package ru.tayviscon.tayvisconbackend.team.support;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import ru.tayviscon.tayvisconbackend.team.GeoLocation;

import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GeoLocationFormatter implements Formatter<GeoLocation> {

    public static final Pattern PATTERN = Pattern.compile("(-?\\d+(?:\\.\\d+)?)\\s*,\\s*(-?\\d+(?:\\.\\d+)?)");

    @Override
    public GeoLocation parse(String text, Locale locale) throws ParseException {
        Matcher matcher = PATTERN.matcher(text);
        if(!matcher.find()) {
            throw new ParseException(text, 0);
        }
        float latitude = Float.valueOf(matcher.group(1));
        float longitude = Float.valueOf(matcher.group(2));
        return new GeoLocation(latitude, longitude);
    }

    @Override
    public String print(GeoLocation location, Locale locale) {
        return String.format(Locale.US, "%f,%f", location.getLatitude(), location.getLongitude());
    }
}
