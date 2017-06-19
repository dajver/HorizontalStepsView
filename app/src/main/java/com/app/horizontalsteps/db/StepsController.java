package com.app.horizontalsteps.db;

import android.content.Context;

import com.app.horizontalsteps.db.items.StepsDataIModel;

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

    public void addInfo(String tourID, String stepID, String path, String name) {
        realm.beginTransaction();

        StepsDataIModel realmObject = realm.createObject(StepsDataIModel.class);
        int id = getNextKey();
        realmObject.setId(id);
        realmObject.setTourID(tourID);
        realmObject.setStepID(stepID);
        realmObject.setPath(path);
        realmObject.setName(name);

        realm.commitTransaction();
    }

    public RealmResults<StepsDataIModel> getInfo(String stepId, String tourId) {
        return realm.where(StepsDataIModel.class).equalTo("stepID", stepId).equalTo("tourID", tourId).findAll();
    }

    private int getNextKey() {
        return realm.where(StepsDataIModel.class).max("id").intValue() + 1;
    }
}