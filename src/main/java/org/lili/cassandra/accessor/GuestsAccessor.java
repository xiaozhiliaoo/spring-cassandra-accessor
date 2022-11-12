package org.lili.cassandra.accessor;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;
import org.lili.cassandra.model.Guests;

import java.util.UUID;

/**
 * 用户试卷作答
 *
 * @author lili03
 */
@Accessor
//@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public interface GuestsAccessor {

    @Query("SELECT * FROM guests where guest_id=?")
    Result<Guests> getByGuests(UUID guest_id);

    @Query("insert into guests (guest_id) values (:guestId)")
    ResultSet insert(@Param("guestId") UUID guestId);

    @Query("insert into guests (guest_id) values (:guestId) IF NOT EXISTS")
    ResultSet insertIfNotExists(@Param("guestId") UUID guestId);
}
