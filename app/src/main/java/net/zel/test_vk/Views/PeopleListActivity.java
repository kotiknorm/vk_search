package net.zel.test_vk.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKSdk;
import com.vk.sdk.VKUIHelper;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiUsers;
import com.vk.sdk.api.model.VKApiUser;

import net.zel.test_vk.Controllers.BuilderTextViewSearch;
import net.zel.test_vk.Controllers.PeopleListAdapter;
import net.zel.test_vk.Models.BuilderQuery;
import net.zel.test_vk.Models.People;
import net.zel.test_vk.Models.Requests.CountryRequest;
import net.zel.test_vk.Models.Requests.PeopleSearchRequest;
import net.zel.test_vk.R;
import net.zel.test_vk.Tasks.CountResultParamsTask;
import net.zel.test_vk.Utils.Constants;

import java.util.ArrayList;


public class PeopleListActivity extends SherlockActivity implements SearchView.OnQueryTextListener {


    private LinearLayout layoutParams;
    private TextView countPeople;
    private ListView listView;
    private SearchView searchView;
    private String nameSearch = "";
    private LinearLayout clearParam;
    private LinearLayout viewParam;
    private TextView textParam;
    private ImageButton btnClear;


    private PeopleSearchRequest request;
    private BuilderQuery bd;
    private PeopleListAdapter adapter;

    public ListView getListPeople(){
        return listView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setTitle(getResources().getString(R.string.app_name));
        getActionBar().setIcon(R.drawable.ic_ab_app);
        setContentView(R.layout.activity_my);

        clearParam = (LinearLayout) findViewById(R.id.layout_clear);
        viewParam = (LinearLayout) findViewById(R.id.layout_par);
        btnClear = (ImageButton) findViewById(R.id.btn_clear);
        textParam = (TextView) findViewById(R.id.text_param);
        layoutParams = (LinearLayout) findViewById(R.id.layout_param);
        countPeople = (TextView) findViewById(R.id.count_people);
        listView = (ListView) findViewById(R.id.listView);

        clearParam.setVisibility(View.VISIBLE);
        viewParam.setVisibility(View.GONE);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearParam.setVisibility(View.VISIBLE);
                viewParam.setVisibility(View.GONE);
                bd.clearAllWithoutName();
                startNewSearch(bd);
            }
        });

        adapter = new PeopleListAdapter(new ArrayList<People>(), PeopleListActivity.this);
        bd = new BuilderQuery();
        request = new PeopleSearchRequest(countPeople, adapter, bd);

        listView.setAdapter(adapter);
        listView.setOnScrollListener(request.getPag());

        layoutParams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PeopleListActivity.this, SearchParametersActivity.class);
                i.putExtra(Constants.PARAM_EXTRA_INTENT, bd.fullName(nameSearch));
                startActivityForResult(i, 1);
                new CountryRequest().getRequest();
            }
        });

        VKUIHelper.onCreate(this);
        VKSdk.initialize(request.getVkInitListener(), Constants.API_KEY, VKAccessToken.tokenFromSharedPreferences(this, Constants.PREFERENCES_NAME));

        VKSdk.authorize(new String[]{});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        searchView = new SearchView(this);
        searchView.setQueryHint(Constants.PEOPLE_SEARCH);
        searchView.setOnQueryTextListener(this);

        AutoCompleteTextView searchText = (AutoCompleteTextView) searchView.findViewById(R.id.abs__search_src_text);
        searchText.setHintTextColor(getResources().getColor(R.color.vk_white));
        searchText.setTextColor(getResources().getColor(R.color.vk_white));

        menu.add("Search")
                .setIcon(R.drawable.abs__ic_search)
                .setActionView(searchView)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        VKUIHelper.onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VKUIHelper.onDestroy(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constants.RESULT_CODE_ACTIVITY_CALLBACK)
            try {
                bd = data.getParcelableExtra(Constants.NAME_EXTRA_INTENT);
                startNewSearch(bd);
                clearParam.setVisibility(View.GONE);
                viewParam.setVisibility(View.VISIBLE);

                new BuilderTextViewSearch(bd, textParam).updateTextView();
            } catch (NullPointerException e) {
            }
        VKUIHelper.onActivityResult(this, requestCode, resultCode, data);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        nameSearch = query;
        bd.fullName(query);
        startNewSearch(bd);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.length() < 1) {
            nameSearch = "";
            bd.cleaName();
            startNewSearch(bd);
        }
        return true;
    }


    private void startNewSearch(BuilderQuery bd) {
        adapter.clear();
        request = new PeopleSearchRequest(countPeople, adapter, bd);
        listView.setOnScrollListener(request.getPag());
        request.getRequest();
        countPeople.setText("");
    }
}
