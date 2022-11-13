package org.lili.contoller;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.MappingManager;
import com.youdao.ke.courseop.common.cassandra.CassandraProperties;
import lombok.extern.slf4j.Slf4j;
import org.lili.cassandra.accessor.GuestsAccessor;
import org.lili.cassandra.accessor.HotelAccessor;
import org.lili.cassandra.model.Guests;
import org.lili.domain.Person;
import org.lili.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author lili
 * @date 2022/11/11 11:23
 */
@Slf4j
@RestController
public class HotelController {

    private static final UUID uuid = UUID.randomUUID();

    @Autowired
    private MappingManager mappingManager;

    @Autowired
    private CassandraProperties cp;


    @GetMapping("getPerson")
    public Person getById(@RequestParam Integer id) {
        return mappingManager.createAccessor(PersonMapper.class).selectById(id);
    }

    @GetMapping("getHotel")
    public String getHotel(@RequestParam String id) {
        String txt = "查找";
        Guests g = mappingManager.createAccessor(GuestsAccessor.class).getByGuests(uuid).one();
        log.info("guest id:{}", g);
        if (Objects.isNull(g)) {
            txt = "未找到Guest";
        } else {
            txt = "找到Guest";
        }
        return mappingManager.createAccessor(HotelAccessor.class).getByHotel(id).one().getName() + txt;
    }

    @GetMapping("saveGuest")
    public boolean saveGuest() {
        String txt = "查找";
        ResultSet resultSet = mappingManager.createAccessor(GuestsAccessor.class).insertIfNotExists(uuid);
        return resultSet.wasApplied();
    }

    @GetMapping("cp")
    public List<String> cp() {
        return cp.getContactPoints();
    }
}
