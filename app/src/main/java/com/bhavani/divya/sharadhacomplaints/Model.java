package com.bhavani.divya.sharadhacomplaints;

public class Model {
    public String mId, mName, mComplaint, mRoom;
    public Boolean solved;

    public Model() {

    }

    public Model(String mId, String mName, String mComplaint, String mRoom,Boolean solved) {
        this.mId = mId;
        this.mName = mName;
        this.mComplaint = mComplaint;
        this.mRoom = mRoom;
        this.solved = solved;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public Boolean getSolved() {
        return solved;
    }

    public void setSolved(Boolean solved) {
        this.solved = solved;
    }

    public String getmName() { return mName; }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmComplaint() {
        return mComplaint;
    }

    public void setmComplaint(String mComplaint) {
        this.mComplaint = mComplaint;
    }

    public String getmRoom() {
        return mRoom;
    }

    public void setmRoom(String mRoom) {
        this.mRoom = mRoom;
    }

}
