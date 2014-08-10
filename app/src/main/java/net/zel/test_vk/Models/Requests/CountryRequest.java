package net.zel.test_vk.Models.Requests;

import android.util.Log;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import net.zel.test_vk.Models.CountryCode;
import net.zel.test_vk.Tasks.CountryParserTask;
import net.zel.test_vk.Utils.Constants;

/**
 * Created by Алексей on 06.07.2014.
 */
public class CountryRequest implements Request {

    private final VKRequest.VKRequestListener vkGetCountryListener = new VKRequest.VKRequestListener() {
        @Override
        public void onComplete(VKResponse response) {
            Log.d(Constants.TAG, "country - " + response.json.toString());
            new CountryParserTask().execute(response.json);
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
            if (CountryCode.getCountry().size() < 1) {
                VKRequest request = new VKRequest(Constants.REQUEST_COUNTRY, VKParameters.from(
                        "need_all", "1", "count", "1000"));
                request.executeWithListener(vkGetCountryListener);
            }
        } catch (NullPointerException e) {
        }
    }


}
