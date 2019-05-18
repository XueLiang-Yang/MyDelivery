package com.liang.delivery.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_group", schema = "delivery", catalog = "")
@IdClass(UserGroupEntityPK.class)
public class UserGroupEntity {
    private int userId;
    private int groupId;
    private Integer weight;

    public UserGroupEntity(){}
    public UserGroupEntity(int userId, int groupId, Integer weight) {
        this.userId = userId;
        this.groupId = groupId;
        this.weight = weight;
    }

    @Id
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "group_id")
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "weight")
    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGroupEntity that = (UserGroupEntity) o;
        return userId == that.userId &&
                groupId == that.groupId &&
                Objects.equals(weight, that.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, groupId, weight);
    }

    @Override
    public String toString() {
        return "UserGroupEntity{" +
                "userId=" + userId +
                ", groupId=" + groupId +
                ", weight=" + weight +
                '}';
    }
}
