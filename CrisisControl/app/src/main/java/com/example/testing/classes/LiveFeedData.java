package com.example.testing.classes;

public class LiveFeedData {
    private int id;
    private String urltxt;
    private String url;
    private String info;

    public LiveFeedData(int id, String url, String urltxt, String info) {
        this.id = id;
        this.urltxt = urltxt;
        this.url = url;
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrltxt() {
        return urltxt;
    }

    public void setUrltxt(String urltxt) {
        this.urltxt = urltxt;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
}

    @Override
    public String toString() {
        return "LiveFeedData{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}


