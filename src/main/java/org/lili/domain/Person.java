package org.lili.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


public class Person implements Serializable {
    private String name;
    private String age;
    private String sex;
    private String status;
    private String level;
    /**
     * 自增主键
     */
    private long id;


    private Date gmtCreate;

    private Date gmtModified;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && Objects.equals(name, person.name) && Objects.equals(age, person.age) && Objects.equals(sex, person.sex) && Objects.equals(status, person.status) && Objects.equals(level, person.level) && Objects.equals(gmtCreate, person.gmtCreate) && Objects.equals(gmtModified, person.gmtModified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, sex, status, level, id, gmtCreate, gmtModified);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                ", status='" + status + '\'' +
                ", level='" + level + '\'' +
                ", id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }
}
