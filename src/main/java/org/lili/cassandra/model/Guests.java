package org.lili.cassandra.model;

import com.datastax.driver.mapping.annotations.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author lili
 * @date 2022/11/5 17:51
 */
@Table(name = "guests",
        readConsistency = "QUORUM",
        writeConsistency = "QUORUM",
        caseSensitiveKeyspace = false,
        caseSensitiveTable = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Guests {

    @PartitionKey
    @Column(name = "guest_id")
    private UUID guestId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "title")
    private String title;
    @Column(name = "emails")
    private Set<String> emails;
    @Column(name = "phone_numbers")
    private List<String> phoneNumbers;
    @Column(name = "addresses")
    @FrozenValue
    private Map<String, Address> addresses;

}
