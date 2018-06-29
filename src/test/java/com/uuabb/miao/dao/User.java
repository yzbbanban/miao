package com.uuabb.miao.dao;

/**
 * Created by ban on 2018/6/28.
 */
public class User {
    private String id;
    private String name;
    private long created;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", created=" + created +
                '}';
    }
}
