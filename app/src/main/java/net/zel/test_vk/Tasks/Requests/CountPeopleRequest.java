package net.zel.test_vk.Tasks.Requests;

import android.util.Log;
import android.widget.Button;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import net.zel.test_vk.Models.BuilderQuery;
import net.zel.test_vk.Tasks.CountResultParamsTask;
import net.zel.test_vk.Utils.Constants;

import java.util.HashMap;

/**
 * Created by Алексей on 07.07.2014.
 */
public class CountPeopleRequest implements Request {

    private Button countPeople;
    private HashMap<String, String> param = new HashMap<String, String>();


    public CountPeopleRequest(Button _countPeople, BuilderQuery bd) {
        param = bd.param;
        countPeople = _countPeople;
    }

    private final VKRequest.VKRequestListener vkGetCountPeople = new VKRequest.VKRequestListener() {
        @Override
        public void onComplete(VKResponse response) {
            Log.d(Constants.TAG, "count list - " + response.json.toString());
            new CountResultParamsTask(countPeople).execute(response.json);
        }

        @Override
        public void onError(VKError error) {
        }

        @Override
        public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
        }

        @Override
        public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
        }
    };

    public void getRequest() {
        try {
            VKRequest request = VKApi.users().search(VKParameters.from(
                    VKApiConst.Q, param.get(VKApiConst.Q),
                    VKApiConst.COUNT, "0",
                    VKApiConst.COUNTRY, param.get(VKApiConst.COUNTRY),
                    VKApiConst.SEX, param.get(VKApiConst.SEX),
                    VKApiConst.STATUS, param.get(VKApiConst.STATUS),
                    VKApiConst.AGE_TO, param.get(VKApiConst.AGE_TO),
                    VKApiConst.AGE_FROM, param.get(VKApiConst.AGE_FROM)));

            request.executeWithListener(vkGetCountPeople);
        } catch (NullPointerException e) {

        }
    }


}
