package com.app.horizontalsteps.ui.db.items;

import io.realm.RealmObject;

public class StepsDataIModel extends RealmObject {

    private int id;
    private String stepID;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStepID() {
        return stepID;
    }

    public void setStepID(String stepID) {
        this.stepID = stepID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
