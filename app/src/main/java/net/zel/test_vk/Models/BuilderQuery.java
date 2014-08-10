package net.zel.test_vk.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.vk.sdk.api.VKApiConst;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Алексей on 07.07.2014.
 */
public class BuilderQuery implements Parcelable {

    public HashMap<String, String> param = new HashMap<String, String>();

    public BuilderQuery fullName(String name) {
        if (name != null)
            param.put(VKApiConst.Q, name);
        return this;
    }

    public BuilderQuery ageFrom(String name) {
        if ((name != null) && (!name.equals("")))
            param.put(VKApiConst.AGE_FROM, name);
        return this;
    }

    public BuilderQuery ageTo(String name) {
        if (!name.equals(""))
            param.put(VKApiConst.AGE_TO, name);
        return this;
    }

    public BuilderQuery country(String name) {
        if ((name != null) && (!name.equals("")))
            param.put(VKApiConst.COUNTRY, name);
        return this;
    }

    public BuilderQuery sex(String name) {
        if ((name != null) && (!name.equals("")))
            param.put(VKApiConst.SEX, name);
        return this;
    }

    public BuilderQuery status(String name) {
        if (name != null)
            param.put(VKApiConst.STATUS, name);
        return this;
    }

    public BuilderQuery clearAllWithoutName() {
        param.remove(VKApiConst.AGE_FROM);
        param.remove(VKApiConst.AGE_TO);
        param.remove(VKApiConst.COUNTRY);
        param.remove(VKApiConst.SEX);
        param.remove(VKApiConst.STATUS);
        return this;
    }

    public BuilderQuery cleaName() {
        param.remove(VKApiConst.Q);
        return this;
    }

    public BuilderQuery() {
        fullName("");
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int parcelableFlags) {
        final int N = param.size();
        dest.writeInt(N);
        if (N > 0) {
            for (Map.Entry<String, String> entry : param.entrySet()) {
                dest.writeString(entry.getKey());
                dest.writeString(entry.getValue());
            }
        }
    }

    public static final Creator<BuilderQuery> CREATOR = new Creator<BuilderQuery>() {
        public BuilderQuery createFromParcel(Parcel source) {
            return new BuilderQuery(source);
        }

        public BuilderQuery[] newArray(int size) {
            return new BuilderQuery[size];
        }
    };

    private BuilderQuery(Parcel source) {
        final int N = source.readInt();
        for (int i = 0; i < N; i++) {
            String key = source.readString();
            String value = source.readString();
            param.put(key, value);
        }
    }
}
