import com.settleup.model.SettleUp;
import com.settleup.model.service.InputAnalyzer;
import com.settleup.model.service.SettleUpService;
import com.settleup.model.service.UserService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        UserService userService = new UserService();
        SettleUpService settleUpService = new SettleUpService();
        InputAnalyzer inputAnalyzer = new InputAnalyzer(settleUpService, userService);

        Scanner sc = new Scanner(System.in);
        userService.initialyzeUserWithId("u1");
        userService.initialyzeUserWithId("u2");
        userService.initialyzeUserWithId("u3");
        userService.initialyzeUserWithId("u4");
        while(true) {
            String input = sc.nextLine();
            if(input.equals("exit")) {
                break;
            }
            inputAnalyzer.analyzeInput(input);
        }
    }
}
