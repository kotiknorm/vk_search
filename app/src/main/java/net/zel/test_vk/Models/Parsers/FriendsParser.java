package net.zel.test_vk.Models.Parsers;

import android.util.Log;

import net.zel.test_vk.Models.People;
import net.zel.test_vk.Utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexey on 03.07.14.
 */

public class FriendsParser {

    private JSONObject jsonObject;

    public FriendsParser(JSONObject _jsonObject) {
        jsonObject = _jsonObject;
    }

    public List<People> execute() {
        List<People> peopleList = new ArrayList<People>();
        JSONArray jsonFriends = null;
        try {
            jsonFriends = jsonObject.getJSONObject(Constants.RESPONSE_ITEM).getJSONArray("items");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < jsonFriends.length(); i++) {

            try {
                Log.d("vk", jsonFriends.getJSONObject(i).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }


            String lastName = "";
            try {
                lastName = jsonFriends.getJSONObject(i).getString(Constants.FIELD_LAST_NAME);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String firstName = "";
            try {
                firstName = jsonFriends.getJSONObject(i).getString(Constants.FIELD_FIRST_NAME);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String photoUrl = "";
            try {
                photoUrl = jsonFriends.getJSONObject(i).getString(Constants.FIELD_PHOTO_NAME);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String city = "";
            try {
                city = jsonFriends.getJSONObject(i).getJSONObject(Constants.FIELD_CITY_NAME).getString("title");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String online = "";
            try {
                online = jsonFriends.getJSONObject(i).getString(Constants.FIELD_ONLINE);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String id = "";
            try {
                id = jsonFriends.getJSONObject(i).getString(Constants.UID);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            People people = new People.BuilderFriends().firstName(firstName).lastName(lastName).photoUrl(photoUrl).city(city).online(online).build(id);
            peopleList.add(people);


        }
        return peopleList;
    }

    public int getAllPeopleSeacrh() {
        try {
            return jsonObject.getJSONObject(Constants.RESPONSE_ITEM).getInt("count");
        } catch (JSONException e) {
            return 0;
        }
    }
}
