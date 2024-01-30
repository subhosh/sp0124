package com.sp.trms.dao;

import com.sp.trms.domain.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

@Component
public class UserDao {
    //In Memory Database
    private final Map<String, User> users = Map.ofEntries(
            Map.entry("1", new Customer() {{
                setId("1");
                setName("John Doe");
                setUserType(UserType.CUSTOMER);
                setAgreements(new ArrayList<>());
                }}),
            Map.entry("2", new StoreAssociate() {{
                setId("2");
                setName("James Bond");
                setUserType(UserType.STORE_ASSOCIATE);
            }}),
            Map.entry("3", new Customer() {{
                setId("3");
                setName("Jane Doe");
                setUserType(UserType.CUSTOMER);
                setAgreements(new ArrayList<>());
            }})
    );

    public User getUser(String customerId) {
        return users.get(customerId);
    }
}
