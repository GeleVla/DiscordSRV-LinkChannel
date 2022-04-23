package com.gelevla.discordsrvlinkchannel;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.api.events.DiscordGuildMessageReceivedEvent;
import github.scarsz.discordsrv.dependencies.jda.api.events.guild.GuildUnavailableEvent;
import github.scarsz.discordsrv.dependencies.jda.api.events.message.guild.GuildMessageReceivedEvent;
import github.scarsz.discordsrv.dependencies.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;


import java.util.concurrent.TimeUnit;

public class JDAListener extends ListenerAdapter {
    private final DiscordSRVLinkChannel plugin;

    public JDAListener(DiscordSRVLinkChannel plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onGuildUnavailable(@NotNull GuildUnavailableEvent event) {
        plugin.getLogger().severe(event.getGuild().getName() + " Discord server went unavailable.");
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        //If the message was sent in the discord linking channel
        String LinkingChannel = plugin.getConfig().getString("LinkingDiscordChannel");
        if (event.getChannel().getId() == LinkingChannel) {
            //don't process messages sent by ANY bot
            if (!event.getAuthor().isBot()) {
                DiscordSRV.api.callEvent(new DiscordGuildMessageReceivedEvent(event));

                String reply = DiscordSRV.getPlugin().getAccountLinkManager().process(event.getMessage().getContentRaw(), event.getAuthor().getId());
                if (reply != null) event.getChannel().sendMessage(reply).queue();
            }
            event.getChannel().deleteMessageById(event.getMessage().getId()).queueAfter(10, TimeUnit.SECONDS);
        }
    }
}
