package net.zel.test_vk.Models;

import java.util.HashMap;

/**
 * Created by Алексей on 06.07.2014.
 */
public class CountryCode {

    private static HashMap<String, String> country = new HashMap<String, String>();

    public static void builderCountryCodes(HashMap<String, String> _country) {
        country = _country;
    }

    public static HashMap<String, String> getCountry() {
        return country;
    }


}
