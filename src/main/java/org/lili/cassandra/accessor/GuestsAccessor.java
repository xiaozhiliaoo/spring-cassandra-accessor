package org.lili.cassandra.accessor;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;
import org.lili.cassandra.model.Guests;
import org.lili.cassandra.model.Hotel;

/**
 * 用户试卷作答
 *
 * @author lili03
 */
@Accessor
public interface GuestsAccessor {

    @Query("SELECT * FROM guests where guest_id=?")
    Result<Guests> getByGuests(String uuid);
}
