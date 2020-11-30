package org.fufeng.es.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: luochunyun
 * @Date: 2020/2/6 13:15
 */
@Data
@ToString
@Document(indexName = "user")
@Setting(settingPath = "elasticsearch/setting.json")
@Mapping(mappingPath = "elasticsearch/mapping.json")
public class User {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String userId;

    private Basic basic;

    private List<App> apps;

    private Active active;

    private Device device;

    private Location location;

    private List<Business> businesses;

    private Date createTime;

    private Date updateTime;

    @Data
    @ToString
    public static class Basic {
        private Sub _1000;

        private Sub _1001;

        private Sub _1002;

        private Sub _1003;

        private Sub _1004;

        private Sub _1005;

        private Sub _1006;

        private Sub _1007;

        private Sub _1008;

        private Sub _1009;

        private Sub _1010;

        private Sub _1011;

        private Sub _1012;
    }


    @Data
    @ToString
    public static class Active {
        private Sub _1001;
        private Sub _1002;
        private Sub _1003;
        private Sub _1004;
    }


    @Data
    @ToString
    public static class App {
        private String key;
        private Map<String, Sub> app_detail;
    }

    @Data
    @ToString
    public static class Business {
        private String key;
        private Map<String, Sub> business_detail;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Business business = (Business) o;
            return key.equals(business.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }
    }

    @Data
    @ToString
    public static class Device {
        private Sub _1001;
        private Sub _1002;
        private Sub _1003;
        private Sub _1004;
        private Sub _1005;
        private Sub _1006;
    }


    @Data
    @ToString
    public static class Location {
        private Sub _1001;
        private Sub _1002;
        private Sub _1003;

    }

    @Data
    @ToString
    public static class Sub {
        private Object n;
        private Object l;
        private Object c;
    }

}
