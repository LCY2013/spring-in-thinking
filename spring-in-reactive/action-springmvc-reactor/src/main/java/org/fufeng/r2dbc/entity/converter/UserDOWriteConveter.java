package org.fufeng.r2dbc.entity.converter;

import org.fufeng.r2dbc.entity.UserDO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.r2dbc.core.Parameter;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@WritingConverter
public class UserDOWriteConveter implements Converter<UserDO, OutboundRow> {

    private static AtomicLong id = new AtomicLong(0);

    @Override
    public OutboundRow convert(UserDO source) {
        OutboundRow row = new OutboundRow();
        if (Objects.isNull(source.getId())) {
            source.setId(id.incrementAndGet());
        }
        row.put("ID", Parameter.from(source.getId()));
        row.put("EMAIL", Parameter.from(source.getEmail()));
        row.put("FIRST_NAME", Parameter.from(source.getFirstName()));
        row.put("LAST_NAME", Parameter.from(source.getLastName()));
        row.put("PASSWORD", Parameter.from(source.getPassword()));
        row.put("ROLES", Parameter.from(String.join(";", source.getRoles())));
        return row;
    }
}