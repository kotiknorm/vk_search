package net.zel.test_vk.Tasks;

import android.os.AsyncTask;

import net.zel.test_vk.Models.CountryCode;
import net.zel.test_vk.Models.Parsers.CountryParser;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Алексей on 06.07.2014.
 */
public class CountryParserTask extends AsyncTask<JSONObject, HashMap<String, String>, HashMap<String, String>> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected HashMap<String, String> doInBackground(JSONObject... params) {
        return new CountryParser(params[0]).execute();
    }

    @Override
    protected void onPostExecute(HashMap<String, String> result) {
        super.onPostExecute(result);
        CountryCode.builderCountryCodes(result);

    }
}
