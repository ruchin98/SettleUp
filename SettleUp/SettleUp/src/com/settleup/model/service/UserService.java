package com.settleup.model.service;



import com.settleup.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserService {

    Map<String, User> idUserMap = new HashMap<>();

    public void updateAmountToGive(User user, Integer amount) {

        user.setAmount(user.getAmount()-amount);
    }
    public void updateAmountToTake(User user, Integer amount) {

        user.setAmount(user.getAmount()+amount);
    }

    public void initialyzeUserWithId(String id) {
        User user = new User();
        user.setId(id);
        user.setName("NA");
        idUserMap.put(id, user);
    }
}

