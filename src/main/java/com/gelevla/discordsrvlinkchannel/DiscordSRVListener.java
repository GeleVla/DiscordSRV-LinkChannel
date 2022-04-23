package com.gelevla.discordsrvlinkchannel;

import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.AccountLinkedEvent;
import github.scarsz.discordsrv.api.events.AccountUnlinkedEvent;
import github.scarsz.discordsrv.api.events.DiscordReadyEvent;
import github.scarsz.discordsrv.util.DiscordUtil;

public class DiscordSRVListener {
    private final DiscordSRVLinkChannel plugin;

    public DiscordSRVListener(DiscordSRVLinkChannel plugin) {
        this.plugin = plugin;
    }

    @Subscribe
    public void discordReadyEvent(DiscordReadyEvent event) {
        DiscordUtil.getJda().addEventListener(new JDAListener(plugin));
    }


    @Subscribe
    public void accountLinked(AccountLinkedEvent event) {

    }

    @Subscribe
    public void accountUnlinked(AccountUnlinkedEvent event) {

    }
}
