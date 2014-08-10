package net.zel.test_vk.Models.Parsers;

import net.zel.test_vk.Utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Алексей on 06.07.2014.
 */

public class CountryParser {

    private JSONObject jsonObject;

    public CountryParser(JSONObject _jsonObject) {
        jsonObject = _jsonObject;
    }

    public HashMap<String, String> execute() {

        HashMap<String, String> country = new HashMap<String, String>();

        try {
            JSONArray countryJson = jsonObject.getJSONObject(Constants.RESPONSE_ITEM).getJSONArray("items");
            for (int i = 0; i < countryJson.length(); i++) {
                String idCountry = countryJson.getJSONObject(i).getString(Constants.FIELD_ID);
                String nameCountry = countryJson.getJSONObject(i).getString("title");
                country.put(nameCountry, idCountry);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return country;
    }


}
