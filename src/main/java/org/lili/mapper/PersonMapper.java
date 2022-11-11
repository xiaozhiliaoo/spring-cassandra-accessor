package org.lili.mapper;

import org.lili.domain.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author lili
 * @date 2021/8/21 14:42
 */
@Mapper
public interface PersonMapper {
    @Select("SELECT * FROM person WHERE id = #{id}")
    public Person selectById(int id);
}