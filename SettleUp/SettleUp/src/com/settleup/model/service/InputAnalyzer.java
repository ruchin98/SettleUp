package com.settleup.model.service;

import com.settleup.model.User;

import java.util.Map;

public class InputAnalyzer {

    private final SettleUpService settleUpService;
    private final UserService userService;

    public InputAnalyzer(SettleUpService settleUpService,
                         UserService userService) {
        this.settleUpService = settleUpService;
        this.userService = userService;
    }
    public void analyzeInput(String inputString) {

        Map<String, User> idUserMap = userService.idUserMap;
        System.out.println(inputString);
        if(inputString.equals("SHOW")) {
            System.out.println("---------1----------");
            settleUpService.getSettleAmountOfAll(idUserMap);
        } else if(inputString.contains("SHOW")) {
            System.out.println("----------2---------");
            String[] splited = inputString.split("\\s+");
            String userId = splited[1];
            settleUpService.getSettleAmountOfUser(userId, idUserMap);

        } else if(inputString.contains("EXPENSE")) {
            System.out.println("----------3---------");
            String[] splited = inputString.split("\\s+");
            userService.updateAmountToTake(idUserMap.get(splited[1]), Integer.valueOf(splited[2]));
            getUserAmountToGiveMap(splited, idUserMap);
        }
    }

    private void getUserAmountToGiveMap(String[] splited, Map<String, User> idUserMap) {
        Integer totalAmount = Integer.valueOf(splited[2]);
        Integer usersToGiveAmount = Integer.valueOf(splited[3]);
        for(int i=0;i<usersToGiveAmount;i++) {
            String userId = splited[i+4];
            String distributionType = splited[4+usersToGiveAmount];
            Integer amount=0;
            if(distributionType.equals("EQUAL")) {
                amount = totalAmount/usersToGiveAmount;
            } else if(distributionType.equals("EXACT")) {
                amount = Integer.valueOf(splited[usersToGiveAmount+5+i]);
            } else {
                amount = Integer.valueOf(splited[usersToGiveAmount+5+i])*totalAmount;
                amount = amount/100;
            }
            userService.updateAmountToGive(idUserMap.get(userId), amount);
        }
    }

}
