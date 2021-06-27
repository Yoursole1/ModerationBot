package main;

import main.Data.IllegalWords;
import main.Events.GuildMessageEvent;
import main.utils.Utils;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Scanner;

public class main {
    public static JDABuilder builder;
    public static void main(String[] args) throws LoginException, URISyntaxException {
        String token = "";

        File file = null;
        try {
            file = new File(Utils.getDirPath()+"/Config.txt");
        } catch (URISyntaxException uriSyntaxException) {

        }
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println(Utils.getPath());
            System.out.println("\033[0;31m" + "ERROR: Please put a Config.txt file in the jar's directory");
            System.out.print("\033[0m");
            System.out.println("Stopped Bot Execution");
            System.exit(1);
        }

        while (Objects.requireNonNull(sc).hasNextLine()){
            IllegalWords.bannedWords.add(sc.nextLine());
        }

        for(String string : IllegalWords.bannedWords){
            if(string.toLowerCase().contains("token")){
                token = string.split(":")[1].replace(" ","");
                break;
            }
        }

        if(token.equalsIgnoreCase("")){
            System.out.println("\033[0;31m" + "ERROR: Please enter a bot token");
            System.out.print("\033[0m");
            System.out.println("Stopped Bot Execution");
            System.exit(1);
        }

        for(String string : IllegalWords.bannedWords){
            IllegalWords.bannedWords.set(IllegalWords.bannedWords.indexOf(string),string.toLowerCase());
        }

        builder = JDABuilder.createDefault(token);
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setCompression(Compression.NONE);
        builder.setActivity(Activity.playing("Your messages"));
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        builder.addEventListeners(new GuildMessageEvent());
        builder.build();



    }
}
