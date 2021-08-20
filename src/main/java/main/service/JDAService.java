package main.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import main.event.GuildMessageEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.Map;

@Slf4j
@Getter
public final class JDAService {

    private static JDAService instance;

    private JDA jda;

    private JDAService() {
        try {
            JDABuilder builder = JDABuilder
                    .createDefault(System.getenv("MODERATION_BOT_TOKEN"))
                    .disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .setBulkDeleteSplittingEnabled(false)
                    .setCompression(Compression.NONE)
                    .setActivity(Activity.playing("Your messages"))
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .addEventListeners(new GuildMessageEvent());
            this.jda = builder.build();
        } catch (LoginException e) {
            log.error("Invalid token!", e);
        }
    }

    public static MessageEmbed createEmbed(Map<String,String> content){
        Guild guild = instance.jda.getGuilds().get(0);
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Moderation Bot");
        embedBuilder.setDescription("This is in BETA - DM Yoursole1#7254 with issues");
        embedBuilder.setThumbnail(guild.getIconUrl());

        for(String field : content.keySet()){
            embedBuilder.addField(field, content.get(field),false);
        }

        embedBuilder.setFooter("Created with \u2665 by Yoursole1#7254");
        embedBuilder.setColor(new Color(2871056));
        return embedBuilder.build();
    }

    public static synchronized JDAService getInstance() {
        if (instance == null) {
            instance = new JDAService();
        }
        return instance;
    }


}
