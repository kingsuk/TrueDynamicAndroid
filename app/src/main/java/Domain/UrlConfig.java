package Domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class UrlConfig implements Parcelable {
    public int ID;
    public String Endpoint_name;
    public String url;
    public String url_param;
    public int interval_number;
    public String Report_Name;
    public boolean Status;
    public Object TimeStamp;
    public Date CreateDate;
    public Date UpdateDate;
    public int Count;

    protected UrlConfig(Parcel in) {
        ID = in.readInt();
        Endpoint_name = in.readString();
        url = in.readString();
        url_param = in.readString();
        interval_number = in.readInt();
        Report_Name = in.readString();
        Status = in.readByte() != 0;
        Count = in.readInt();
    }

    public static final Creator<UrlConfig> CREATOR = new Creator<UrlConfig>() {
        @Override
        public UrlConfig createFromParcel(Parcel in) {
            return new UrlConfig(in);
        }

        @Override
        public UrlConfig[] newArray(int size) {
            return new UrlConfig[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(ID);
        parcel.writeString(Endpoint_name);
        parcel.writeString(url);
        parcel.writeString(url_param);
        parcel.writeInt(interval_number);
        parcel.writeString(Report_Name);
        parcel.writeByte((byte) (Status ? 1 : 0));
        parcel.writeInt(Count);
    }
}
