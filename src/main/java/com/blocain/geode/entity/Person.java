package com.blocain.geode.entity;

import org.springframework.data.gemfire.mapping.annotation.LocalRegion;

import java.io.Serializable;
import java.util.Date;

@LocalRegion
public class Person implements Serializable {
    private Long id;
    private String name;
    private Date createDate;

    public Person(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Person() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
