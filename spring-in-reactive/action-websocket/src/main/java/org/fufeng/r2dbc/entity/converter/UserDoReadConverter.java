package org.fufeng.r2dbc.entity.converter;

import org.fufeng.r2dbc.entity.UserDO;
import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.Arrays;
import java.util.stream.Collectors;

@ReadingConverter
public class UserDoReadConverter implements Converter<Row, UserDO> {

    @Override
    public UserDO convert(Row source) {
        UserDO userDO = new UserDO();
        userDO.setId(source.get("ID", Long.class));
        userDO.setEmail(source.get("EMAIL", String.class));
        userDO.setFirstName(source.get("FIRST_NAME", String.class));
        userDO.setLastName(source.get("LAST_NAME", String.class));
        userDO.setPassword(source.get("PASSWORD", String.class));
        userDO.setRoles(Arrays.stream(source.get("ROLES", String.class).split(";"))
                .collect(Collectors.toList()));
        return userDO;
    }
}