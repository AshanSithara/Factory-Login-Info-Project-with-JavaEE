package com.proje.dto;

import com.proje.model.LoginPermission;
import com.proje.model.Place;

import java.util.ArrayList;
import java.util.List;

public class PlaceDto {

    private int placeId;
    private String placeName;
    private List<LoginPermission> loginPermissions = new ArrayList<LoginPermission>();

    public PlaceDto(){

    }

    public PlaceDto(Place place){
        this.placeId = place.getPlaceId();
        this.placeName = place.getPlaceName();
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public List<LoginPermission> getLoginPermissions() {
        return loginPermissions;
    }

    public void setLoginPermissions(List<LoginPermission> loginPermissions) {
        this.loginPermissions = loginPermissions;
    }
}
