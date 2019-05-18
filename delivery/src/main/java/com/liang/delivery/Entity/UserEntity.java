package com.liang.delivery.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "delivery", catalog = "")
public class UserEntity implements Serializable {
    private int id;
    private String nickname;
    private String password;
    private Integer stuNum;
    private String stuName;
    private String stuSex;
    private String className;
    private String eMail;
    private List<GroupsEntity> groups;

    public UserEntity(int id, String nickname,Integer stuNum, String stuName, String stuSex, String className, String eMail) {
        this.id = id;
        this.nickname = nickname;
        this.stuNum = stuNum;
        this.stuName = stuName;
        this.stuSex = stuSex;
        this.className = className;
        this.eMail = eMail;
    }

    public UserEntity(String nickname, Integer stuNum, String stuName, String stuSex, String className, String eMail ,String password) {
        this.nickname = nickname;
        this.password = password;
        this.stuNum = stuNum;
        this.stuName = stuName;
        this.stuSex = stuSex;
        this.className = className;
        this.eMail = eMail;
    }

    public UserEntity(int id, String nickname, Integer stuNum, String stuName, String stuSex, String className, String eMail, String password) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.stuNum = stuNum;
        this.stuName = stuName;
        this.stuSex = stuSex;
        this.className = className;
        this.eMail = eMail;
    }
    public UserEntity(int id, String password) {
        this.id = id;
        this.password = password;
    }

    public UserEntity() {
    }

    @ManyToMany(mappedBy = "groupUsers",fetch = FetchType.LAZY)
    @JsonManagedReference
    public List<GroupsEntity> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupsEntity> groups) {
        this.groups = groups;
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
    @Column(name = "nickname")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Basic
    @Column(name = "password")
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "stu_num")
    public Integer getStuNum() {
        return stuNum;
    }

    public void setStuNum(Integer stuNum) {
        this.stuNum = stuNum;
    }

    @Basic
    @Column(name = "stu_name")
    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    @Basic
    @Column(name = "stu_sex")
    public String getStuSex() {
        return stuSex;
    }

    public void setStuSex(String stuSex) {
        this.stuSex = stuSex;
    }

    @Basic
    @Column(name = "class_name")
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Basic
    @Column(name = "e_mail")
    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id &&
                Objects.equals(nickname, that.nickname) &&
                Objects.equals(password, that.password) &&
                Objects.equals(stuNum, that.stuNum) &&
                Objects.equals(stuName, that.stuName) &&
                Objects.equals(stuSex, that.stuSex) &&
                Objects.equals(className, that.className) &&
                Objects.equals(eMail, that.eMail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickname, password, stuNum, stuName, stuSex, className, eMail);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", stuNum=" + stuNum +
                ", stuName='" + stuName + '\'' +
                ", stuSex='" + stuSex + '\'' +
                ", className='" + className + '\'' +
                ", eMail='" + eMail + '\'' +
                ", groups=" + groups +
                '}';
    }
}
