package com.app.horizontalsteps.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.horizontalsteps.R;
import com.app.horizontalsteps.ui.db.items.StepsDataIModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

public class StepsAdapter extends BaseAdapter {

    private Context context;
    private RealmResults<StepsDataIModel> data = null;

    public StepsAdapter(Context context, RealmResults<StepsDataIModel> data) {
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
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.item_steps, parent, false);

        Holder holder = new Holder(convertView);

        StepsDataIModel rec = data.get(position);
        holder.name.setText(rec.getName());

        return convertView;
    }

    class Holder {
        @BindView(R.id.nameText)
        TextView name;

        public Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}