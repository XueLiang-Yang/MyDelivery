package com.liang.delivery.Entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "information", schema = "delivery", catalog = "")
public class InformationEntity {
    private int id;
    private String infoBody;
    private String infoTitle;
    private int groupId;
    private String enclosure;
    private String enclosureRealname;
    private Timestamp time;

    public InformationEntity() {
    }

    public InformationEntity(String infoBody, String infoTitle, int groupId, String enclosure, String enclosureRealname, Timestamp time) {
        this.infoBody = infoBody;
        this.infoTitle = infoTitle;
        this.groupId = groupId;
        this.enclosure = enclosure;
        this.enclosureRealname = enclosureRealname;
        this.time = time;
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "info_body")
    public String getInfoBody() {
        return infoBody;
    }

    public void setInfoBody(String infoBody) {
        this.infoBody = infoBody;
    }

    @Basic
    @Column(name = "info_title")
    public String getInfoTitle() {
        return infoTitle;
    }

    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle;
    }

    @Basic
    @Column(name = "group_id")
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "enclosure")
    public String getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
    }

    @Basic
    @Column(name = "enclosure_realname")
    public String getEnclosureRealname() {
        return enclosureRealname;
    }

    public void setEnclosureRealname(String enclosureRealname) {
        this.enclosureRealname = enclosureRealname;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InformationEntity that = (InformationEntity) o;
        return id == that.id &&
                groupId == that.groupId &&
                Objects.equals(infoBody, that.infoBody) &&
                Objects.equals(infoTitle, that.infoTitle) &&
                Objects.equals(enclosure, that.enclosure) &&
                Objects.equals(enclosureRealname, that.enclosureRealname) &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, infoBody, infoTitle, groupId, enclosure, enclosureRealname, time);
    }
}
