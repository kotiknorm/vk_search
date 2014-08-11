package net.zel.test_vk.Tasks.Requests;

import android.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCaptchaDialog;
import com.vk.sdk.VKSdkListener;
import com.vk.sdk.VKUIHelper;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import net.zel.test_vk.Controllers.PaginationScrollListener;
import net.zel.test_vk.Controllers.PeopleListAdapter;
import net.zel.test_vk.Models.BuilderQuery;
import net.zel.test_vk.Tasks.NewListTask;
import net.zel.test_vk.Tasks.UpdateListTask;
import net.zel.test_vk.Utils.Constants;

import java.util.HashMap;

/**
 * Created by alexey on 03.07.14.
 */
public class PeopleSearchRequest implements Request {

    private HashMap<String, String> param = new HashMap<String, String>();
    private PeopleListAdapter adapter;
    private TextView countPeople;
    private PaginationScrollListener pag;

    public PeopleSearchRequest(TextView _countPeople, PeopleListAdapter _adapter, BuilderQuery bd) {
        param = new HashMap<String, String>();
        adapter = _adapter;
        param = bd.param;
        countPeople = _countPeople;
        pag = new PaginationScrollListener() {
            @Override
            public void startPagination() {
                Log.d("new list - ", "startPagination");
                updateGetPeople();
            }
        };
    }

    public PaginationScrollListener getPag() {
        return pag;
    }


    private final VKSdkListener vkInitListener = new VKSdkListener() {
        @Override
        public void onCaptchaError(VKError captchaError) {
            new VKCaptchaDialog(captchaError).show();
        }

        @Override
        public void onTokenExpired(VKAccessToken expiredToken) {
        }

        @Override
        public void onAccessDenied(final VKError authorizationError) {
            new AlertDialog.Builder(VKUIHelper.getTopActivity()).setMessage(authorizationError.toString()).show();
        }

        @Override
        public void onReceiveNewToken(VKAccessToken newToken) {
            newToken.saveTokenToSharedPreferences(countPeople.getContext(), Constants.PREFERENCES_NAME);
            getRequest();
        }

        @Override


        public void onAcceptUserToken(VKAccessToken token) {
            getRequest();
        }
    };


    private final VKRequest.VKRequestListener vkGetPeopleListener = new VKRequest.VKRequestListener() {
        @Override
        public void onComplete(VKResponse response) {
            new UpdateListTask(adapter).execute(response.json);
            pag.up();
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

    private final VKRequest.VKRequestListener vkGetNewPeopleListener = new VKRequest.VKRequestListener() {
        @Override
        public void onComplete(VKResponse response) {
            Log.d(Constants.TAG, response.json.toString());
            new NewListTask(countPeople, adapter).execute(response.json);
            pag.up();
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

    public VKSdkListener getVkInitListener() {
        return vkInitListener;
    }

    private void updateGetPeople() {
        try {
            getRequest1();
        } catch (NullPointerException e) {
        }
    }

    public void getRequest1() {
        try {
            VKRequest request = VKApi.users().search(VKParameters.from(
                    VKApiConst.FIELDS, Constants.VIEW_FIELD_PEOPLE,
                    VKApiConst.OFFSET, String.valueOf(pag.getOffset()),
                    VKApiConst.Q, param.get(VKApiConst.Q),
                    VKApiConst.COUNT, Constants.COUNT_PAGINATION,
                    VKApiConst.SEX, param.get(VKApiConst.SEX),
                    VKApiConst.COUNTRY, param.get(VKApiConst.COUNTRY),
                    VKApiConst.AGE_TO, param.get(VKApiConst.AGE_TO),
                    VKApiConst.STATUS, param.get(VKApiConst.STATUS),
                    VKApiConst.AGE_FROM, param.get(VKApiConst.AGE_FROM)));

            request.executeWithListener(vkGetPeopleListener);

        } catch (NullPointerException e) {
        }
    }


    public void getRequest() {
        pag.refresh();
        try {

            VKRequest request = VKApi.users().search(VKParameters.from(
                    VKApiConst.FIELDS, Constants.VIEW_FIELD_PEOPLE,
                    VKApiConst.OFFSET, String.valueOf(pag.getOffset()),
                    VKApiConst.Q, param.get(VKApiConst.Q),
                    VKApiConst.COUNT, Constants.COUNT_PAGINATION,
                    VKApiConst.SEX, param.get(VKApiConst.SEX),
                    VKApiConst.COUNTRY, param.get(VKApiConst.COUNTRY),
                    VKApiConst.AGE_TO, param.get(VKApiConst.AGE_TO),
                    VKApiConst.STATUS, param.get(VKApiConst.STATUS),
                    VKApiConst.AGE_FROM, param.get(VKApiConst.AGE_FROM)));

            request.executeWithListener(vkGetNewPeopleListener);

        } catch (NullPointerException e) {
        }
    }


}
