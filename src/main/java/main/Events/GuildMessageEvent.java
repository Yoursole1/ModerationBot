package main.Events;

import main.Data.Data;
import main.main;
import main.utils.Utils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.*;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class GuildMessageEvent extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent e){


        switch(e.getMessage().getContentRaw().toLowerCase().split(" ")[0]){
            case "?help":{
                e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                    put("Help Section","**?help**\nThis Page\n**?addMod \"example#1234\"**\nAdd a moderator\n" +
                            "**?addAdmin \"example#1234\"**\nAdd an admin user\n**?removeMod \"example#1234\"**\n" +
                            "Remove a moderator\n**?removeAdmin \"example#1234\"**\nRemove an admin");
                }})).queue();
                break;
            }
            case "?addmod":{
                if(Data.admins.contains(e.getMember())){
                    if(e.getMessage().getContentRaw().split(" ").length==2){
                        Guild guild = main.jda.getGuilds().get(0);
                        Member member = null;
                        try{
                            member = guild.getMemberByTag(e.getMessage().getContentRaw().split(" ")[1]);

                            if(member!=null){
                                if(!Data.moderators.contains(member)){
                                    //*******************************
                                    //ADD USER TO DATA ARRAY AND CONFIG FILE
                                    String tag = e.getMessage().getContentRaw().split(" ")[1];
                                    String oldText = main.moderatorText;
                                    String newText = "";
                                    if(oldText.split(":").length>1&&oldText.split(":")[1].length()>4){
                                        newText = oldText + "," + tag;
                                    }else{
                                        newText = oldText + "" + tag;
                                    }
                                    newText = newText.replace(",,",",");


                                    try {
                                        main.moderatorText=newText;
                                        Scanner sc = new Scanner(new File(Utils.getDirPathNoLog()+"/Config.txt"));
                                        StringBuffer buffer = new StringBuffer();
                                        while (sc.hasNextLine()) {
                                            buffer.append(sc.nextLine()+System.lineSeparator());
                                        }
                                        String fileContents = buffer.toString();
                                        sc.close();
                                        fileContents = fileContents.replaceAll(oldText, newText);
                                        FileWriter writer = new FileWriter(Utils.getDirPathNoLog()+"/Config.txt");
                                        writer.append(fileContents);
                                        writer.flush();

                                        Data.moderators.add(member);

                                        Member finalMember = member;

                                        e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                                            put("Moderation Bot", finalMember.getAsMention() + " is now a moderator!");
                                        }})).queue();

                                    } catch (FileNotFoundException | URISyntaxException fileNotFoundException) {


                                        System.out.println("\033[0;31m" + "ERROR: You had the config file, but deleted it...why?????");
                                        System.out.print("\033[0m");
                                        e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                                            put("Moderation Bot","You broke something, please check the bot's console");
                                        }})).queue();
                                    } catch (IOException exception) {
                                        System.out.println("\033[0;31m" + "ERROR: You had the config file, but deleted it...why?????");
                                        System.out.print("\033[0m");
                                        System.out.println("Please note that if you did not delete the config file this is the dev's fault, please contact us");

                                    }


                                    //********************************
                                }else{
                                    e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                                        put("Moderation Bot","This user is already a moderator!");
                                    }})).queue();
                                }

                            }else{
                                e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                                    put("Moderation Bot","Please specify a real user (Case Sensitive)");
                                }})).queue();
                            }
                        }catch (java.lang.IllegalArgumentException exception){
                            e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                                put("Moderation Bot","Please specify a real user (Case Sensitive)");
                            }})).queue();
                        }
                    }else{
                        e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                            put("Moderation Bot","?addMod format: ?addMod username#1234 (case sensitive)");
                        }})).queue();
                    }
                }else{
                    e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                        put("Moderation Bot","You do not have permission to execute this command!");
                    }})).queue();
                }


                break;
            }
            case "?addadmin":{
                if(Data.admins.contains(e.getMember())){
                    if(e.getMessage().getContentRaw().split(" ").length==2){
                        Guild guild = main.jda.getGuilds().get(0);
                        Member member = null;
                        try{
                            member = guild.getMemberByTag(e.getMessage().getContentRaw().split(" ")[1]);

                            if(member!=null){
                                if(!Data.admins.contains(member)){
                                    //*******************************
                                    //ADD USER TO DATA ARRAY AND CONFIG FILE
                                    String tag = e.getMessage().getContentRaw().split(" ")[1];
                                    String oldText = main.adminText;
                                    String newText = "";
                                    if(oldText.split(":").length>1&&oldText.split(":")[1].length()>4){
                                        newText = oldText + "," + tag;
                                    }else{
                                        newText = oldText + "" + tag;
                                    }

                                    newText = newText.replace(",,",",");

                                    try {
                                        main.adminText=newText;
                                        Scanner sc = new Scanner(new File(Utils.getDirPathNoLog()+"/Config.txt"));
                                        StringBuffer buffer = new StringBuffer();
                                        while (sc.hasNextLine()) {
                                            buffer.append(sc.nextLine()+System.lineSeparator());
                                        }
                                        String fileContents = buffer.toString();
                                        sc.close();
                                        fileContents = fileContents.replaceAll(oldText, newText);
                                        FileWriter writer = new FileWriter(Utils.getDirPathNoLog()+"/Config.txt");
                                        writer.append(fileContents);
                                        writer.flush();

                                        Data.admins.add(member);

                                        Member finalMember = member;

                                        e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                                            put("Moderation Bot", finalMember.getAsMention() + " is now an admin!");
                                        }})).queue();

                                    } catch (FileNotFoundException | URISyntaxException fileNotFoundException) {


                                        System.out.println("\033[0;31m" + "ERROR: You had the config file, but deleted it...why?????");
                                        System.out.print("\033[0m");
                                        e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                                            put("Moderation Bot","You broke something, please check the bot's console");
                                        }})).queue();
                                    } catch (IOException exception) {
                                        System.out.println("\033[0;31m" + "ERROR: You had the config file, but deleted it...why?????");
                                        System.out.print("\033[0m");
                                        System.out.println("Please note that if you did not delete the config file this is the dev's fault, please contact us");

                                    }


                                    //********************************
                                }else{
                                    e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                                        put("Moderation Bot","This user is already an admin!");
                                    }})).queue();
                                }

                            }else{
                                e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                                    put("Moderation Bot","Please specify a real user (Case Sensitive)");
                                }})).queue();
                            }
                        }catch (java.lang.IllegalArgumentException exception){
                            e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                                put("Moderation Bot","Please specify a real user (Case Sensitive)");
                            }})).queue();
                        }
                    }else{
                        e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                            put("Moderation Bot","?addAdmin format: ?addAdmin username#1234 (case sensitive)");
                        }})).queue();
                    }
                }else{
                    e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                        put("Moderation Bot","You do not have permission to execute this command!");
                    }})).queue();
                }
                break;
            }
            case "?removemod":{
                if(Data.admins.contains(e.getMember())){
                    if(e.getMessage().getContentRaw().split(" ").length==2){
                        Guild guild = main.jda.getGuilds().get(0);
                        Member member = null;
                        try{
                            member = guild.getMemberByTag(e.getMessage().getContentRaw().split(" ")[1]);

                            if(member!=null){
                                if(Data.moderators.contains(member)){
                                    //*******************************
                                    //ADD USER TO DATA ARRAY AND CONFIG FILE
                                    String tag = e.getMessage().getContentRaw().split(" ")[1];
                                    String oldText = main.moderatorText;
                                    String newText = oldText.replace(tag,"").replace(",,",",");
                                    if(newText.split(",").length==1){
                                        newText=newText.replace(",","");
                                    }


                                    try {
                                        main.moderatorText=newText;
                                        Scanner sc = new Scanner(new File(Utils.getDirPathNoLog()+"/Config.txt"));
                                        StringBuffer buffer = new StringBuffer();
                                        while (sc.hasNextLine()) {
                                            buffer.append(sc.nextLine()+System.lineSeparator());
                                        }
                                        String fileContents = buffer.toString();
                                        sc.close();
                                        fileContents = fileContents.replaceAll(oldText, newText);
                                        FileWriter writer = new FileWriter(Utils.getDirPathNoLog()+"/Config.txt");
                                        writer.append(fileContents);
                                        writer.flush();

                                        Data.moderators.remove(member);

                                        Member finalMember = member;

                                        e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                                            put("Moderation Bot", finalMember.getAsMention() + " is no longer a moderator D:");
                                        }})).queue();

                                    } catch (FileNotFoundException | URISyntaxException fileNotFoundException) {


                                        System.out.println("\033[0;31m" + "ERROR: You had the config file, but deleted it...why?????");
                                        System.out.print("\033[0m");
                                        e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                                            put("Moderation Bot","You broke something, please check the bot's console");
                                        }})).queue();
                                    } catch (IOException exception) {
                                        System.out.println("\033[0;31m" + "ERROR: You had the config file, but deleted it...why?????");
                                        System.out.print("\033[0m");
                                        System.out.println("Please note that if you did not delete the config file this is the dev's fault, please contact us");

                                    }


                                    //********************************
                                }else{
                                    e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                                        put("Moderation Bot","This user is not a moderator!");
                                    }})).queue();
                                }

                            }else{
                                e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                                    put("Moderation Bot","Please specify a real user (Case Sensitive)");
                                }})).queue();
                            }
                        }catch (java.lang.IllegalArgumentException exception){
                            e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                                put("Moderation Bot","Please specify a real user (Case Sensitive)");
                            }})).queue();
                        }
                    }else{
                        e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                            put("Moderation Bot","?removeMod format: ?removeMod username#1234 (case sensitive)");
                        }})).queue();
                    }
                }else{
                    e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                        put("Moderation Bot","You do not have permission to execute this command!");
                    }})).queue();
                }
                break;
            }
            case "?removeadmin":{
                if(Data.admins.contains(e.getMember())){
                    if(e.getMessage().getContentRaw().split(" ").length==2){
                        Guild guild = main.jda.getGuilds().get(0);
                        Member member = null;
                        try{
                            member = guild.getMemberByTag(e.getMessage().getContentRaw().split(" ")[1]);

                            if(member!=null){
                                if(Data.admins.contains(member)){
                                    //*******************************
                                    //ADD USER TO DATA ARRAY AND CONFIG FILE
                                    String tag = e.getMessage().getContentRaw().split(" ")[1];
                                    String oldText = main.adminText;
                                    String newText = oldText.replace(tag,"").replace(",,",",");
                                    if(newText.split(",").length==1){
                                        newText=newText.replace(",","");
                                    }


                                    try {
                                        main.adminText=newText;
                                        Scanner sc = new Scanner(new File(Utils.getDirPathNoLog()+"/Config.txt"));
                                        StringBuffer buffer = new StringBuffer();
                                        while (sc.hasNextLine()) {
                                            buffer.append(sc.nextLine()+System.lineSeparator());
                                        }
                                        String fileContents = buffer.toString();
                                        sc.close();
                                        fileContents = fileContents.replaceAll(oldText, newText);
                                        FileWriter writer = new FileWriter(Utils.getDirPathNoLog()+"/Config.txt");
                                        writer.append(fileContents);
                                        writer.flush();

                                        Data.admins.remove(member);

                                        Member finalMember = member;

                                        e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                                            put("Moderation Bot", finalMember.getAsMention() + " is no longer an admin D:");
                                        }})).queue();

                                    } catch (FileNotFoundException | URISyntaxException fileNotFoundException) {


                                        System.out.println("\033[0;31m" + "ERROR: You had the config file, but deleted it...why?????");
                                        System.out.print("\033[0m");
                                        e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                                            put("Moderation Bot","You broke something, please check the bot's console");
                                        }})).queue();
                                    } catch (IOException exception) {
                                        System.out.println("\033[0;31m" + "ERROR: You had the config file, but deleted it...why?????");
                                        System.out.print("\033[0m");
                                        System.out.println("Please note that if you did not delete the config file this is the dev's fault, please contact us");

                                    }


                                    //********************************
                                }else{
                                    e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                                        put("Moderation Bot","This user is not an admin!");
                                    }})).queue();
                                }

                            }else{
                                e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                                    put("Moderation Bot","Please specify a real user (Case Sensitive)");
                                }})).queue();
                            }
                        }catch (java.lang.IllegalArgumentException exception){
                            e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                                put("Moderation Bot","Please specify a real user (Case Sensitive)");
                            }})).queue();
                        }
                    }else{
                        e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                            put("Moderation Bot","?removeAdmin format: ?removeAdmin username#1234 (case sensitive)");
                        }})).queue();
                    }
                }else{
                    e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                        put("Moderation Bot","You do not have permission to execute this command!");
                    }})).queue();
                }

                break;
            }
            case "?ban":{
                if(Data.moderators.contains(e.getMember())||Data.admins.contains(e.getMember())){
                    if(e.getMessage().getContentRaw().split(" ").length==2){
                        Guild guild = main.jda.getGuilds().get(0);
                        Member member = null;
                        try{
                            member = guild.getMemberByTag(e.getMessage().getContentRaw().split(" ")[1]);
                            try{
                                if(member!=null){
                                    member.ban(0).queue();
                                }
                            }catch(net.dv8tion.jda.api.exceptions.HierarchyException ex){
                                e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                                    put("Moderation Bot","I can not ban that member...if they need to be banned please use discord's default banning system");
                                }})).queue();
                            }

                        }catch (java.lang.IllegalArgumentException exception){
                            e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                                put("Moderation Bot","Please specify a real user (Case Sensitive)");
                            }})).queue();
                        }
                    }else{
                        e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                            put("Moderation Bot","?ban format: ?ban username#1234 (case sensitive)");
                        }})).queue();
                    }
                }else{
                    e.getChannel().sendMessage(Utils.createEmbed(new HashMap<>(){{
                        put("Moderation Bot","You do not have permission to execute this command!");
                    }})).queue();
                }

                break;
            }
        }
        try{
            for(String string : e.getMessage().getContentRaw().split(" ")){
                if(Data.bannedWords.contains(string.toLowerCase())){
                    e.getMessage().delete().queue();
                }
            }
        }catch(net.dv8tion.jda.api.exceptions.InsufficientPermissionException exception){
            System.out.println("\033[0;31m" + "ERROR: Please give me Admin Permissions so I can moderate properly");
            System.out.print("\033[0m");
        }
    }
}
