package com.proje.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "introduction_reason")
@NamedQueries({
        @NamedQuery(name = "Reason.findAll", query = "SELECT i FROM IntroductionReason i")
})
public class IntroductionReason {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REASON_ID")
    private int reasonId;

    @Column(name = "REASON_NAME",nullable = false,unique = true)
    private String reasonName;

    @ManyToMany(mappedBy = "introductionReasons",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<LoginPermission> loginPermissions = new ArrayList<LoginPermission>();

    public int getReasonId() {
        return reasonId;
    }

    public void setReasonId(int reasonId) {
        this.reasonId = reasonId;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public void setLoginPermissions(List<LoginPermission> loginPermissions) {
        this.loginPermissions = loginPermissions;
    }


}
