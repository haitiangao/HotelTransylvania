package com.example.hoteltransylvania.model;


import android.os.Parcel;
import android.os.Parcelable;


public class HotelRoom implements Parcelable {

    private int hotelRoomNumber;
    private String  hotelPushKey;

    private boolean available;
    private String  imageUrl;
    private int price;

    public HotelRoom(String  hotelPushKey,int hotelRoomNumber, boolean available, String imageUrl, int price) {
        this.hotelPushKey = hotelPushKey;
        this.hotelRoomNumber = hotelRoomNumber;
        this.available = available;
        this.imageUrl = imageUrl;
        this.price = price;
    }
    public HotelRoom(){}


    public String getHotelPushKey() {
        return hotelPushKey;
    }

    public void setHotelPushKey(String hotelPushKey) {
        this.hotelPushKey = hotelPushKey;
    }

    protected HotelRoom(Parcel in) {
        hotelRoomNumber = in.readInt();
        available = in.readByte() != 0;
        imageUrl = in.readString();
        price = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(hotelRoomNumber);
        dest.writeByte((byte) (available ? 1 : 0));
        dest.writeString(imageUrl);
        dest.writeInt(price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HotelRoom> CREATOR = new Creator<HotelRoom>() {
        @Override
        public HotelRoom createFromParcel(Parcel in) {
            return new HotelRoom(in);
        }

        @Override
        public HotelRoom[] newArray(int size) {
            return new HotelRoom[size];
        }
    };

    public int getHotelRoomNumber() {
        return hotelRoomNumber;
    }

    public void setHotelRoomNumber(int hotelRoomNumber) {
        this.hotelRoomNumber = hotelRoomNumber;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
