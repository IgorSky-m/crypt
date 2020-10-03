package it.academy.miner;

import it.academy.miner.service.WalletService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

@Component
@Getter
@Setter
public class CommandExecutor {
    private static final Logger logger = Logger.getLogger(CommandExecutor.class.getName());
    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors() / 2;
    private final ExecutorService executorService;
    boolean isExit;

    @Autowired
    private MiningManager miningService;

    @Autowired
    private WalletService walletService;

    private final Map<String, String> commands;

    CommandExecutor(){
        executorService = Executors.newFixedThreadPool(AVAILABLE_PROCESSORS);
        this.isExit = false;
        commands = new HashMap<>(
                Map.of(
                        "start", "this command start mining process",
                        "stop", "this command stopped mining process",
                        "wallet set", "allows set your wallet id for send coins reward",
                        "wallet get", "allows see you wallet id",
                        "app exit","exit program",
                        "help","shows all available commands"
                )
        );
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\r\nWelcome to IGS-Crypto miner!\r\nEnter \"help\" if you want to see all available commands");
        while (!isExit) {

            System.out.print("enter command: ");
            String command = scanner.nextLine();

            execute(command);

        }
        System.out.println("Exit program");
        System.exit(0);
    }

    public void execute(String command) {
        Scanner scanner = new Scanner(System.in);
        try {
            switch (command.toLowerCase()) {
                case "1":
                case "start": {
                        miningService.startMining();
                        return;
                }
                case "2":
                case "set":
                case "wallet set": {
                    logger.info("enter wallet id:");
                    String wallet = walletService.setWalletId(scanner.nextLine());
                    logger.info("you wallet id is : " + wallet);
                    return;
                }
                case "3":
                case "get":
                case "wallet get": {
                    String wallet = walletService.getWalletId();
                    System.out.println("you wallet id is : " + wallet);
                    return;
                }
                case "4":
                case "app exit": {
                    this.isExit = true;
                    return;
                }
                case "help": {
                    for (Map.Entry<String, String> currentCommand : commands.entrySet()) {
                        System.out.println("\""+currentCommand.getKey()+ "\": "+currentCommand.getValue()+"\r\n");
                    }
                    return;
                }
                default:
                    throw new IllegalStateException("Unexpected value: " + command.toLowerCase());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
