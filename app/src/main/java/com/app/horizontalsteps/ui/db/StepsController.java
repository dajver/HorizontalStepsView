package com.app.horizontalsteps.ui.db;

import android.content.Context;

import com.app.horizontalsteps.ui.db.items.StepsDataIModel;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class StepsController {

    private Realm realm;

    public StepsController(Context context) {
        RealmConfiguration config = new RealmConfiguration.Builder(context).build();
        realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
    }

    public void addInfo(String stepID, String name) {
        realm.beginTransaction();

        StepsDataIModel realmObject = realm.createObject(StepsDataIModel.class);
        int id = getNextKey();
        realmObject.setId(id);
        realmObject.setStepID(stepID);
        realmObject.setName(name);

        realm.commitTransaction();
    }

    public RealmResults<StepsDataIModel> getInfo(String stepId) {
        return realm.where(StepsDataIModel.class).equalTo("stepID", stepId).findAll();
    }

    private int getNextKey() {
        return realm.where(StepsDataIModel.class).max("id").intValue() + 1;
    }
}