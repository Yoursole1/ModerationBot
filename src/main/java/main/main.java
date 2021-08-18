package main;

import main.model.Data;
import main.Events.GuildMessageEvent;
import main.utils.Utils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.*;

public class main {
    public static JDABuilder builder;
    public static JDA jda = null;
    public static int modLine = -1;
    public static int adminLine = -1;
    public static String moderatorText = new String();
    public static String adminText = new String();


    public static void main(String[] args) throws LoginException, URISyntaxException, InterruptedException {
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
            System.out.println("\033[0;31m" + "ERROR: Please put a Config.txt file in the jar's directory");
            System.out.print("\033[0m");
            System.out.println("Stopped Bot Execution");
            System.exit(1);
        }

        while (Objects.requireNonNull(sc).hasNextLine()){
            Data.bannedWords.add(sc.nextLine());
        }

        for(String string : Data.bannedWords){
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

        builder = JDABuilder.createDefault(token);
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        builder.setChunkingFilter(ChunkingFilter.ALL);
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setCompression(Compression.NONE);
        builder.setActivity(Activity.playing("Your messages"));
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        builder.addEventListeners(new GuildMessageEvent());
        jda = builder.build();
        jda.awaitReady();

        setupStaff();



    }
    public static void setupStaff(){
        int i = 0;
        for(String string : Data.bannedWords){
            if(string.toLowerCase().contains("moderators")){
                modLine = i;
                moderatorText = string;
                if(string.split(":").length>1&&string.split(":")[1].length()>4){
                    Arrays.asList(string.split(":")[1].replace(" ", "").split(",")).forEach((k)-> {
                        Guild guild = jda.getGuilds().get(0);
                        Data.moderators.add(guild.getMemberByTag(k));
                    });
                }
                break;
            }
            i++;
        }
        i = 0;
        for(String string : Data.bannedWords){
            if(string.split(":").length>1&&string.toLowerCase().contains("admins")){
                adminLine = i;
                adminText = string;
                if(string.split(":")[1].length()>4){
                    Arrays.asList(string.split(":")[1].replace(" ", "").split(",")).forEach((k)-> {
                        Guild guild = jda.getGuilds().get(0);
                        Data.admins.add(guild.getMemberByTag(k));
                    });
                }

                break;
            }
            i++;
        }
        for(String string : Data.bannedWords){
            Data.bannedWords.set(Data.bannedWords.indexOf(string),string.toLowerCase());
        }
    }
}
