package com.liang.delivery.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "groups", schema = "delivery", catalog = "")
public class GroupsEntity implements Serializable {
    private int id;
    private String groupName;
    private String groupInfo;
    private String groupCreater;
    private List<UserEntity> groupUsers;

    public GroupsEntity() {
    }

    public GroupsEntity(int id,String groupName, String groupInfo) {
        this.id = id;
        this.groupName = groupName;
        this.groupInfo = groupInfo;
    }

    public GroupsEntity(String groupName, String groupInfo, String groupCreater) {
        this.groupName = groupName;
        this.groupInfo = groupInfo;
        this.groupCreater = groupCreater;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "group_name")
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Basic
    @Column(name = "group_info")
    public String getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(String groupInfo) {
        this.groupInfo = groupInfo;
    }

    @Basic
    @Column(name = "group_creater")
    public String getGroupCreater() {
        return groupCreater;
    }

    public void setGroupCreater(String groupCreater) {
        this.groupCreater = groupCreater;
    }

    public void setGroupUsers(List<UserEntity> groupUsers) {
        this.groupUsers = groupUsers;
    }


    @JoinTable(name = "user_group",
            joinColumns = { @JoinColumn(name = "group_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") })
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JsonBackReference
    public List<UserEntity> getGroupUsers() {
        return groupUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupsEntity that = (GroupsEntity) o;
        return id == that.id &&
                Objects.equals(groupName, that.groupName) &&
                Objects.equals(groupInfo, that.groupInfo) &&
                Objects.equals(groupCreater, that.groupCreater);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, groupName, groupInfo, groupCreater);
    }

    @Override
    public String toString() {
        return "GroupsEntity{" +
                "id=" + id +
                ", groupName='" + groupName + '\'' +
                ", groupInfo='" + groupInfo + '\'' +
                ", groupCreater='" + groupCreater + '\'' +
                '}';
    }
}
