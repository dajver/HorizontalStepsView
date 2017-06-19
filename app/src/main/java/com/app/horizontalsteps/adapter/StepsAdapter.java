package com.app.horizontalsteps.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.app.horizontalsteps.R;
import com.app.horizontalsteps.db.items.StepsDataIModel;

import io.realm.RealmResults;

public class StepsAdapter extends BaseAdapter {

    private Context context;
    private int layoutResourceId;
    private RealmResults<StepsDataIModel> data;
    private WeatherHolder holder = null;

    public StepsAdapter(Context context, int layoutResourceId, RealmResults<StepsDataIModel> data) {
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new WeatherHolder();
            holder.name = (TextView) row.findViewById(R.id.nameText);
            holder.menu = (Button) row.findViewById(R.id.menu);

            row.setTag(holder);
        } else {
            holder = (WeatherHolder)row.getTag();
        }

        final StepsDataIModel rec = data.get(position);
        holder.name.setText(rec.getName());

        return row;
    }

    static class WeatherHolder {
        TextView name;
        Button menu;
    }
}