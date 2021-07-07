package main.utils;

import main.main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Utils {

    public static String getPath() throws URISyntaxException {
        return new File(main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
    }

    public static String getDirPath() throws URISyntaxException {
        if(getOperatingSystem().toLowerCase().contains("windows 10")){
            System.out.println("\033[0;31m" + "Starting on Operating System Mode: "+getOperatingSystem());
            System.out.print("\033[0m");
            String[] pathSections = getPath().split(Pattern.quote("\\"));
            String DirPath = new String();
            for(String section : pathSections){
                if(section.contains(".jar")){
                    break;
                }
                DirPath+="/"+section;
            }
            return DirPath.replace("//","/");
        }else if(getOperatingSystem().toLowerCase().contains("mac os x")){
            System.out.println("\033[0;32m" + "Starting on Operating System Mode: "+getOperatingSystem());
            System.out.print("\033[0m");
            String[] pathSections = getPath().split("/");
            String DirPath = new String();
            for(String section : pathSections){
                if(section.contains(".jar")){
                    break;
                }
                DirPath+="/"+section;
            }
            return DirPath.replace("//","/");
        }else{
            System.out.println("\033[0;31m" + "ERROR: Incompatible Operating System: "+getOperatingSystem());
            System.out.print("\033[0m");
            System.out.println("Stopped Bot Execution");
            System.exit(1);
        }
        return null;

    }
    public static String getDirPathNoLog() throws URISyntaxException {
        if(getOperatingSystem().toLowerCase().contains("windows 10")){
            String[] pathSections = getPath().split(Pattern.quote("\\"));
            String DirPath = new String();
            for(String section : pathSections){
                if(section.contains(".jar")){
                    break;
                }
                DirPath+="/"+section;
            }
            return DirPath.replace("//","/");
        }else if(getOperatingSystem().toLowerCase().contains("mac os x")){
            String[] pathSections = getPath().split("/");
            String DirPath = new String();
            for(String section : pathSections){
                if(section.contains(".jar")){
                    break;
                }
                DirPath+="/"+section;
            }
            return DirPath.replace("//","/");
        }else{
            System.out.println("Stopped Bot Execution because Something Crashed Horribly...report this please");
            System.exit(1);
        }
        return null;

    }

    public static String getOperatingSystem() {
        return System.getProperty("os.name");
    }

    public static MessageEmbed createEmbed(HashMap<String,String> content){

        Guild guild = main.jda.getGuilds().get(0);
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Moderation Bot");
        embedBuilder.setDescription("This is in BETA - DM Yoursole1#7254 with issues");
        embedBuilder.setThumbnail(guild.getIconUrl());

        for(String field : content.keySet()){
            embedBuilder.addField(field,content.get(field),false);
        }

        embedBuilder.setFooter("Created with \u2665 by Yoursole1#7254");
        embedBuilder.setColor(new Color(2871056));
        return embedBuilder.build();
    }

}
