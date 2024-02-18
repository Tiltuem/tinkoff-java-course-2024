package edu.java.bot.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkValidator {
    private LinkValidator() {
    }

    @SuppressWarnings("MagicNumber")
    public static boolean linkExists(String link) {
        String regex = "^(https?:\\/\\/)?([\\w-]{1,32}\\.[\\w-]{1,32})[^\\s@]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(link);

        if (matcher.matches()) {
            try {
                URL url = URI.create(link).toURL();
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                huc.setRequestMethod("HEAD");
                huc.connect();
                int responseCode = huc.getResponseCode();

                return responseCode >= 200 && responseCode < 400;
            } catch (IOException e) {
                return false;
            }
        }
       return false;
    }

    public static boolean siteIsSupported(String link) {
        String site = parse(link);

        for (SupportedWebsites websites : SupportedWebsites.values()) {
            if (websites.getUrl().equalsIgnoreCase(site)) {
                return true;
            }
        }

        return false;
    }

    private static String parse(String link) {
        try {
            URL url = URI.create(link).toURL();
            return url.getHost();
        } catch (IOException e) {
            return "";
        }
    }
}
