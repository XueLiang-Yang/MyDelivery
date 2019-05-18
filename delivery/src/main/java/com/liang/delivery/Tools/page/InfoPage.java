package com.liang.delivery.Tools.page;

import com.liang.delivery.Entity.InformationEntity;

import java.util.List;
import java.util.Map;

public class InfoPage {
    private List<InformationEntity> infoList;
    private Map<Integer,String> groupName;
    private int nowPage;
    private int totalPage;

    public InfoPage() {
    }

    public InfoPage(List<InformationEntity> infoList, Map<Integer, String> groupName, int nowPage, int totalPage) {
        this.infoList = infoList;
        this.groupName = groupName;
        this.nowPage = nowPage;
        this.totalPage = totalPage;
    }

    public InfoPage(List<InformationEntity> infoList,int nowPage, int totalPage) {
        this.infoList = infoList;
        this.nowPage = nowPage;
        this.totalPage = totalPage;
    }

    public Map<Integer, String> getGroupName() {
        return groupName;
    }

    public void setGroupName(Map<Integer, String> groupName) {
        this.groupName = groupName;
    }

    public List<InformationEntity> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<InformationEntity> infoList) {
        this.infoList = infoList;
    }

    public int getNowPage() {
        return nowPage;
    }

    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public String toString() {
        return "InfoPage{" +
                "infoList=" + infoList +
                ", nowPage=" + nowPage +
                ", totalPage=" + totalPage +
                '}';
    }
}
