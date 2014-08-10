package net.zel.test_vk.Controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.zel.test_vk.Models.People;
import net.zel.test_vk.R;
import net.zel.test_vk.Tasks.ImageLoader;

import java.util.List;

/**
 * Created by alexey on 03.07.14.
 */
public class PeopleListAdapter extends BaseAdapter {

    private LayoutInflater lInflater;
    private List<People> peoplesList;
    private Context context;
    private ImageLoader imageLoader;

    public PeopleListAdapter(List<People> _friendsList, Context _context) {
        peoplesList = _friendsList;
        context = _context;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(context);
    }

    @Override
    public int getCount() {
        return peoplesList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_list, viewGroup, false);
        }



        People people = peoplesList.get(i);
        view.setTag(people);
        TextView textView = (TextView) view.findViewById(R.id.name);
        textView.setText(people.fullName());

        TextView city = (TextView) view.findViewById(R.id.city);
        city.setText(people.getCity());

        ImageView online = (ImageView) view.findViewById(R.id.online);
        online.setImageBitmap(null);

        if (people.getOnline().equals("1")) {
            online.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_user_verified));
        }

        ImageView photo = (ImageView) view.findViewById(R.id.photo);
        photo.setImageBitmap(null);

        imageLoader.displayImage(people.getPhotoUrl(), photo);

        return view;
    }

    private void add(List<People> friendsListTemp) {
        peoplesList.addAll(friendsListTemp);
    }

    public void updateList(List<People> friendsListTemp) {
        add(friendsListTemp);
        notifyDataSetChanged();
    }

    public void newList(List<People> friendsListTemp) {
        peoplesList.clear();
        updateList(friendsListTemp);
        notifyDataSetChanged();
    }

    public void clear() {
        peoplesList.clear();
        notifyDataSetChanged();
    }
}
