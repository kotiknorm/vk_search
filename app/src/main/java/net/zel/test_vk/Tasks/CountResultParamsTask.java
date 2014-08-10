package net.zel.test_vk.Tasks;

import android.os.AsyncTask;
import android.widget.TextView;

import net.zel.test_vk.R;
import net.zel.test_vk.Utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Алексей on 07.07.2014.
 */

public class CountResultParamsTask extends AsyncTask<JSONObject, Integer, Integer> {

    private TextView countPeople;


    public CountResultParamsTask(TextView _countPeople) {
        countPeople = _countPeople;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(JSONObject... params) {
        int count = 0;
        try {
            count = params[0].getJSONObject(Constants.RESPONSE_ITEM).getInt("count");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        countPeople.setText(countPeople.getContext().getResources().getQuantityString(R.plurals.peoples, result, result));

    }
}

