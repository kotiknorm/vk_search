package net.zel.test_vk.Tasks;

import android.os.AsyncTask;

import net.zel.test_vk.Controllers.PeopleListAdapter;
import net.zel.test_vk.Models.People;
import net.zel.test_vk.Models.Parsers.FriendsParser;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by alexey on 03.07.14.
 */
public class UpdateListTask extends AsyncTask<JSONObject, List<People>, List<People>> {

    private PeopleListAdapter peopleListAdapter;

    public UpdateListTask(PeopleListAdapter _peopleListAdapter) {
        peopleListAdapter = _peopleListAdapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<People> doInBackground(JSONObject... params) {
        return new FriendsParser(params[0]).execute();
    }

    @Override
    protected void onPostExecute(List<People> result) {
        super.onPostExecute(result);
        peopleListAdapter.updateList(result);

    }
}
