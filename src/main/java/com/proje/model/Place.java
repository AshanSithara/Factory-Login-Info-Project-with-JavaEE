package com.proje.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "place")
@NamedQueries({
        @NamedQuery(name = "Place.findAll", query = "SELECT p FROM Place p")
})
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLACE_ID")
    private int placeId;

    @Column(name = "PLACE_NAME",nullable = false,unique = true)
    private String placeName;

    @ManyToMany(mappedBy = "places",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<LoginPermission> loginPermissions = new ArrayList<LoginPermission>();

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

    public void setLoginPermissions(List<LoginPermission> loginPermissions) {
        this.loginPermissions = loginPermissions;
    }


}
