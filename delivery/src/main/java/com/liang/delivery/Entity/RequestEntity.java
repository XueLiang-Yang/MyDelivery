package com.liang.delivery.Entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "request", schema = "delivery", catalog = "")
public class RequestEntity {
    private int id;
    private int userId;
    private int groupId;
    private String groupName;
    private int operation;
    private int result;
    private Timestamp time;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
    @Column(name = "group_name")
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Basic
    @Column(name = "operation")
    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    @Basic
    @Column(name = "result")
    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
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
        RequestEntity that = (RequestEntity) o;
        return id == that.id &&
                userId == that.userId &&
                groupId == that.groupId &&
                operation == that.operation &&
                result == that.result &&
                Objects.equals(groupName, that.groupName) &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, groupId, groupName, operation, result, time);
    }

    @Override
    public String toString() {
        return "RequestEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", operation=" + operation +
                ", result=" + result +
                ", time=" + time +
                '}';
    }
}
