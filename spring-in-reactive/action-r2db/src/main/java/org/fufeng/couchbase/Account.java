package org.fufeng.couchbase;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

@Document
public class Account {
    @Id
    private String id;
    private String firstName;
    private String lastName;
}
