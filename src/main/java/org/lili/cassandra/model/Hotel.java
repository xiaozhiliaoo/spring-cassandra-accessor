package org.lili.cassandra.model;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Frozen;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author lili
 * @date 2022/1/23 2:00
 */

@Table(name = "hotels",
        readConsistency = "QUORUM",
        writeConsistency = "QUORUM",
        caseSensitiveKeyspace = false,
        caseSensitiveTable = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {
    @PartitionKey
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    @Frozen
    private Address address;

    @Column(name = "pois")
    private Set<String> pointsOfInterest;

    public Hotel(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<String> getPointsOfInterest() {
        return pointsOfInterest;
    }

    public void setPointsOfInterest(Set<String> pointsOfInterest) {
        this.pointsOfInterest = pointsOfInterest;
    }

    public static Hotel hotel1() {
        return new Hotel("1", "七天假日酒店", "1502215228", Address.init1(), Sets.newHashSet("1", "2", "3"));
    }
}
