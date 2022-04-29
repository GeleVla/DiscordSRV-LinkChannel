package com.gelevla.discordsrvlinkchannel;

import com.tchristofferson.configupdater.ConfigUpdater;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bstats.bukkit.Metrics;
import github.scarsz.discordsrv.DiscordSRV;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public final class DiscordSRVLinkChannel extends JavaPlugin implements Listener {

    private DiscordSRVListener discordsrvListener = new DiscordSRVListener(this);

    @Override
    public void onEnable() {
        DiscordSRV.api.subscribe(discordsrvListener);
        getLogger().info("DiscordSRV-LinkChannel addon has been enabled!");
        getServer().getPluginManager().registerEvents(this, this);

        saveDefaultConfig();
        File configFile = new File(getDataFolder(), "config.yml");

        try {
            ConfigUpdater.update(this, "config.yml", configFile, Arrays.asList("RemoveMessages"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        reloadConfig();

        int pluginId = 15021; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("lcreload")){
            if (sender instanceof ConsoleCommandSender){
                reloadConfig();
                getLogger().info("The config has been reloaded!");
            }
            if (sender instanceof Player p){
                p.sendMessage(ChatColor.RED + "This command can only be send from console.");
            }
        }
        return true;
    }

    @Override
    public void onDisable() {
        DiscordSRV.api.unsubscribe(discordsrvListener);
    }
}
