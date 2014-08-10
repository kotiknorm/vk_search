package net.zel.test_vk.Views;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.vk.sdk.VKUIHelper;
import net.zel.test_vk.Models.BuilderQuery;
import net.zel.test_vk.Models.CountryCode;
import net.zel.test_vk.Models.Requests.CountPeopleRequest;
import net.zel.test_vk.Models.Requests.Request;
import net.zel.test_vk.Models.Status;
import net.zel.test_vk.R;
import net.zel.test_vk.Utils.Constants;
import java.util.Map;

/**
 * Created by Алексей on 06.07.2014.
 */

public class SearchParametersActivity extends SherlockActivity {

    private Button startSearch;
    private Request countPeopleRequest;
    private EditText country;
    private BuilderQuery bd = new BuilderQuery();
    private TextView age;
    private CheckBox allAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parameters);

        age = (TextView) findViewById(R.id.age);

        final RangeSeekBar<Integer> seekBar = new RangeSeekBar<Integer>(14, 80, this);
        seekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                bd.ageFrom(String.valueOf(minValue));
                bd.ageTo(String.valueOf(maxValue));
                countPeopleRequest = new CountPeopleRequest(startSearch, bd);
                countPeopleRequest.getRequest();
                age.setText(minValue + " - " + maxValue);
            }
        });

        LinearLayout ln = (LinearLayout) findViewById(R.id.ln);
        ln.addView(seekBar);


        allAge = (CheckBox) findViewById(R.id.all_age_checked);

        allAge.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    seekBar.setEnabled(false);
                    bd.ageFrom(Constants.MIN_AGE);
                    bd.ageTo(Constants.MAX_AGE);
                } else {
                    seekBar.setEnabled(true);
                    bd.ageFrom(String.valueOf(seekBar.getSelectedMinValue()));
                    bd.ageTo(String.valueOf(seekBar.getSelectedMaxValue()));

                }
                countPeopleRequest = new CountPeopleRequest(startSearch, bd);
                countPeopleRequest.getRequest();
            }

        });

        country = (EditText) findViewById(R.id.country);
        country.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bd.country(CountryCode.getCountry().get(s.toString()));
                countPeopleRequest = new CountPeopleRequest(startSearch, bd);
                countPeopleRequest.getRequest();
            }
        });

        View.OnClickListener radioListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton rb = (RadioButton) v;
                switch (rb.getId()) {
                    case R.id.only:
                        bd.sex(Constants.ANY_SEX);
                        break;
                    case R.id.male:
                        bd.sex(Constants.MALE);
                        break;
                    case R.id.famale:
                        bd.sex(Constants.FAMALE);
                        break;
                    default:
                        bd.sex(Constants.ANY_SEX);
                        break;
                }
                countPeopleRequest = new CountPeopleRequest(startSearch, bd);
                countPeopleRequest.getRequest();
            }
        };

        RadioButton radioBtnOnly = (RadioButton) findViewById(R.id.only);
        radioBtnOnly.setOnClickListener(radioListener);

        RadioButton radioBtnMale = (RadioButton) findViewById(R.id.male);
        radioBtnMale.setOnClickListener(radioListener);

        RadioButton radioBtnFamale = (RadioButton) findViewById(R.id.famale);
        radioBtnFamale.setOnClickListener(radioListener);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle(getResources().getString(R.string.param_search));
        getActionBar().setIcon(R.drawable.ic_ab_app);

        Map<String, String> a = Status.getStatus();
        String[] b = new String[a.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : a.entrySet()) {
            b[i] = entry.getKey();
            i++;
        }

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, b);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                bd.status(Status.getStatus().get(spinner.getSelectedItem().toString()));
                countPeopleRequest = new CountPeopleRequest(startSearch, bd);
                countPeopleRequest.getRequest();
            }
        });

        startSearch = (Button) findViewById(R.id.start_search);
        startSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(Constants.NAME_EXTRA_INTENT, bd);
                setResult(Constants.RESULT_CODE_ACTIVITY_CALLBACK, intent);
                finish();
            }
        });

        bd = getIntent().getParcelableExtra(Constants.PARAM_EXTRA_INTENT);
        bd.ageFrom(Constants.MIN_AGE);
        bd.ageTo(Constants.MAX_AGE);
        bd.sex(Constants.ANY_SEX);

        countPeopleRequest = new CountPeopleRequest(startSearch, bd);
        countPeopleRequest.getRequest();
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
        VKUIHelper.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }

}

