package main.Events;

import main.Data.IllegalWords;
import main.utils.Utils;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Scanner;

public class GuildMessageEvent extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent e){
        try{
            for(String string : e.getMessage().getContentRaw().split(" ")){
                if(IllegalWords.bannedWords.contains(string)){
                    e.getMessage().delete().queue();
                }
            }
        }catch(net.dv8tion.jda.api.exceptions.InsufficientPermissionException exception){
            System.out.println("\033[0;31m" + "ERROR: Please give me Admin Permissions so I can moderate properly");
            System.out.print("\033[0m");
        }

    }
}
