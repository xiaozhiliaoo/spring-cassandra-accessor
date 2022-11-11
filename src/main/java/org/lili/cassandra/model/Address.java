package org.lili.cassandra.model;

import com.datastax.driver.mapping.annotations.Field;
import com.datastax.driver.mapping.annotations.UDT;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lili
 * @date 2022/1/23 2:07
 */
@UDT(name = "address")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Address {

    @Field(name = "street")
    private String street;

    @Field(name = "city")
    private String city;

    @Field(name = "state_or_province")
    private String stateOrProvince;

    @Field(name = "postal_code")
    private String postalCode;

    @Field(name = "country")
    private String country;

    public static Address init1() {
        return new Address("马连洼", "北京", "北京直辖市", "10000", "中国");
    }

}
