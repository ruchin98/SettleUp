package com.settleup.model.service;

import com.settleup.model.SettleUp;
import com.settleup.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettleUpService {

    Map<String, List<SettleUp>> settleUpMap = new HashMap<>();
    public void settleAmount(Map<String, User> idUserMap) {
        settleUpMap.clear();
        List<User> userList = getSortedUserList(idUserMap);
        List<User> userListCopy = getUserListCopy(userList);
        System.out.println("userlist: "+userListCopy);
        int i=0, j=userListCopy.size()-1;
        while(i<j) {
            User user1 = userListCopy.get(i);
            User user2 = userListCopy.get(j);
            if(user1.getAmount()== user2.getAmount()) {
                break;
            }
            Integer amount=0;
            amount = getAmountOfUser1AndUser2(user1, user2, amount);
            if(Math.abs(user1.getAmount())==Math.abs(amount)) {
                i++;
            } else {
                j--;
            }
            setAmountOfUser1AndUser2(user1, user2, 0);
            addToSettleUpMap(user1, user2, amount, true);
            addToSettleUpMap(user2, user1, amount, false);
        }
    }

    private List<User> getSortedUserList(Map<String, User> idUserMap) {
        List<User> userList = getUserList(idUserMap);
        Collections.sort(userList, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                if(o1.getAmount()<o2.getAmount()) {
                    return -1;
                } return 1;
            }
        });
        return userList;
    }

    private List<User> getUserListCopy(List<User> userList) {
        List<User> userListCopy = new ArrayList<>();
        for(int i = 0; i< userList.size(); i++) {
            User user = new User();
            user.setAmount(userList.get(i).getAmount());
            user.setId(userList.get(i).getId());
            user.setName(userList.get(i).getName());
            userListCopy.add(user);
        }
        return userListCopy;
    }

    private Integer getAmountOfUser1AndUser2(User user1, User user2, Integer amount) {
        if(user1.getAmount()<0 && Math.abs(user1.getAmount()) >= user2.getAmount()) {
            amount = user2.getAmount();
        }
        else if(user1.getAmount()<0 && Math.abs(user1.getAmount()) <= user2.getAmount()) {
            amount = user1.getAmount();

        }
        return Math.abs(amount);
    }

    private void setAmountOfUser1AndUser2(User user1, User user2, Integer amount) {
        if(user1.getAmount()<0 && Math.abs(user1.getAmount()) > user2.getAmount()) {
            amount = user2.getAmount();
            user1.setAmount(user1.getAmount()+ amount);
            user2.setAmount(0);
        }
        else if(user1.getAmount()<0 && Math.abs(user1.getAmount()) < user2.getAmount()) {
            amount = user1.getAmount();
            user1.setAmount(0);
            user2.setAmount(user2.getAmount()+ amount);
        }
        else if(user1.getAmount()<0 && Math.abs(user1.getAmount()) == user2.getAmount()) {
            user1.setAmount(0);
            user2.setAmount(0);
        }
    }

    private List<User> getUserList(Map<String, User> idUserMap) {
        List<User> userList = new ArrayList<>();
        for(String userId : idUserMap.keySet()) {
            userList.add(idUserMap.get(userId));
        }
        return userList;
    }

    private void addToSettleUpMap(User user1, User user2, Integer amount, Boolean isAmountToGive) {
        List<SettleUp> settleUps = settleUpMap.containsKey(user2.getId())?settleUpMap.get(user2.getId()):new ArrayList<>();
        SettleUp settleUp = new SettleUp();
        settleUp.setUserId(user1.getId());
        settleUp.setAmount(amount);
        if(isAmountToGive) {
            settleUp.setAmountToGive(true);
            settleUp.setAmountToTake(false);
        }
        else {
            settleUp.setAmountToTake(true);
            settleUp.setAmountToGive(false);
        }
        settleUps.add(settleUp);
        settleUpMap.put(user2.getId(), settleUps);
    }

    public void getSettleAmountOfUser(String userId, Map<String, User> idUserMap) {
        settleAmount(idUserMap);
        List<SettleUp> settleUps = settleUpMap.get(userId);
        if(settleUps==null || (settleUps!=null && settleUps.size()==0)) {
            System.out.println("no balances");
            return;
        }
        for (SettleUp settleUp : settleUps) {
            if(settleUp.getAmountToGive()) {
                System.out.println(settleUp.getUserId()+" owes "+userId+": "+settleUp.getAmount());
            } else {
                System.out.println(userId+" owes "+settleUp.getUserId()+": "+settleUp.getAmount());
            }
        }

    }

    public void getSettleAmountOfAll(Map<String, User> idUserMap) {
        settleAmount(idUserMap);
        Boolean balance = false;
        for(String userId : idUserMap.keySet()) {
            List<SettleUp> settleUps = settleUpMap.get(userId);
            if(settleUps==null) continue;
            for (SettleUp settleUp : settleUps) {
                balance=true;
                if(settleUp.getAmountToGive()) {
                    System.out.println(settleUp.getUserId()+" owes "+userId+": "+settleUp.getAmount());
                }
            }
        }
        if(!balance) {
            System.out.println("no balances");
        }
    }
}
