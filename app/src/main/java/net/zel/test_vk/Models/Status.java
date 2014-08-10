package net.zel.test_vk.Models;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Алексей on 08.07.2014.
 */
public class Status {

    private static Map<String, String> country = new LinkedHashMap<String, String>();

    public static Map<String, String> getStatus() {

        if (country.size() < 1) {
            country.put("Не выбран", "");
            country.put("Не женат", "1");
            country.put("Встречается", "2");
            country.put("Помолвлен", "3");
            country.put("Женат", "4");
            country.put("Всё сложно", "5");
            country.put("В активном поиске", "6");
            country.put("Влюблён", "7");
        }
        return country;
    }

}
