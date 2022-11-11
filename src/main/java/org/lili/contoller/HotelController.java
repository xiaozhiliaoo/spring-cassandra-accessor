package org.lili.contoller;

import lombok.extern.slf4j.Slf4j;
import org.lili.cassandra.accessor.GuestsAccessor;
import org.lili.cassandra.accessor.HotelAccessor;
import org.lili.cassandra.model.Guests;
import org.lili.cassandra.model.Hotel;
import org.lili.domain.Person;
import org.lili.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lili
 * @date 2022/11/11 11:23
 */
@Slf4j
@RestController
public class HotelController {

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private HotelAccessor hotelAccessor;

    @Autowired
    private GuestsAccessor guestsAccessor;


    @GetMapping("getPerson")
    public Person getById(@RequestParam Integer id) {
        return personMapper.selectById(id);
    }

    @GetMapping("getHotel")
    public Hotel getHotel(@RequestParam String id) {
        //Guests g = guestsAccessor.getByGuests("").one();
//        log.info("guest id:{}", g);
        return hotelAccessor.getByHotel(id).one();
    }
}
