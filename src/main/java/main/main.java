package main;

import main.service.JDAService;
import net.dv8tion.jda.api.JDA;

public class main {
    public static final JDA jda = JDAService.getInstance().getJda();

    public static void main(String[] args) throws InterruptedException {
        jda.awaitReady();
    }
}
