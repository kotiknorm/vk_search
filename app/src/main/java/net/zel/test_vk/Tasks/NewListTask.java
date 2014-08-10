package net.zel.test_vk.Tasks;

import android.os.AsyncTask;
import android.widget.TextView;

import net.zel.test_vk.Controllers.PeopleListAdapter;
import net.zel.test_vk.Models.People;
import net.zel.test_vk.Models.Parsers.FriendsParser;
import net.zel.test_vk.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexey on 04.07.14.
 */


public class NewListTask extends AsyncTask<JSONObject, Void, Void> {

    private PeopleListAdapter peopleListAdapter;
    private List<People> peopleList = new ArrayList<People>();
    private TextView countPeople;
    private int count;
    private FriendsParser friendsParser;


    public NewListTask(TextView _countPeople, PeopleListAdapter _peopleListAdapter) {
        peopleListAdapter = _peopleListAdapter;
        countPeople = _countPeople;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(JSONObject... params) {
        friendsParser = new FriendsParser(params[0]);
        peopleList = friendsParser.execute();
        count = friendsParser.getAllPeopleSeacrh();
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        countPeople.setText(countPeople.getContext().getResources().getQuantityString(R.plurals.peoples, count, count));

        peopleListAdapter.newList(peopleList);

    }
}
