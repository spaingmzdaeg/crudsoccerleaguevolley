package com.example.crudsoccerleaguevolley;

public class PlayersItem {

    private String mImageUrl;
    private String mIdTeam;
    private String mFirstName;
    private String mLastName;
    private String mKit;
    private String mPosition;
    private String mCountry;

    public PlayersItem(String mImageUrl, String mIdTeam, String mFirstName, String mLastName, String mKit, String mPosition, String mCountry) {
        this.mImageUrl = mImageUrl;
        this.mIdTeam = mIdTeam;
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mKit = mKit;
        this.mPosition = mPosition;
        this.mCountry = mCountry;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmIdTeam() {
        return mIdTeam;
    }

    public void setmIdTeam(String mIdTeam) {
        this.mIdTeam = mIdTeam;
    }

    public String getmFirstName() {
        return mFirstName;
    }

    public void setmFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getmLastName() {
        return mLastName;
    }

    public void setmLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getmKit() {
        return mKit;
    }

    public void setmKit(String mKit) {
        this.mKit = mKit;
    }

    public String getmPosition() {
        return mPosition;
    }

    public void setmPosition(String mPosition) {
        this.mPosition = mPosition;
    }

    public String getmCountry() {
        return mCountry;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }
}
