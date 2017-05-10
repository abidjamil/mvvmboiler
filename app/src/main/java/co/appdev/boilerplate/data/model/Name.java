package co.appdev.boilerplate.data.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass
public  class Name  extends RealmObject {

    @SerializedName("first")
    private String first;

    @SerializedName("last")
    private String last;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

}
