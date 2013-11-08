package com.jabyftw.logmsg;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class LogMsg extends JavaPlugin {
    public List<Player> hasSilent = new ArrayList<Player>();
    public FileConfiguration config;
    public boolean antiSpam, useJoin, useQuit, useKick;
    public String pendingCon;
    public String joinMsg, quitMsg, kickMsg, recMsg;
    public int recDelay;
    
    @Override
    public void onEnable() {
        config = getConfig();
        genConfig();
        if(useJoin)
            getServer().getPluginManager().registerEvents(new LoginListener(this), this);
        if(useQuit)
            getServer().getPluginManager().registerEvents(new QuitListener(this), this);
        if(useKick)
            getServer().getPluginManager().registerEvents(new KickListener(this), this);
        if (!useJoin && !useQuit && !useKick) {
            getLogger().log(Level.WARNING, "You disabled all messages! Plugin disabled.");
            return;
        }
        getLogger().info("Registred events!");
    }

    @Override
    public void onDisable() {}
    
    private void genConfig() {
        config.addDefault("config.useJoinMsg", true);
        config.addDefault("config.useQuitMsg", true);
        config.addDefault("config.useKickMsg", true);
        config.addDefault("config.antiSpam", false);
        config.addDefault("config.reconnectDelayInSec", 7);
        config.addDefault("messages.onJoin", "&3+ &b%player");
        config.addDefault("messages.onQuit", "&4- &c%player");
        config.addDefault("messages.onKick", "&4- &c%player");
        config.addDefault("messages.onReconnect", "&3+&4- &c%player");
        config.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
        useJoin = config.getBoolean("config.useJoinMsg");
        useQuit = config.getBoolean("config.useQuitMsg");
        useKick = config.getBoolean("config.useKickMsg");
        antiSpam = config.getBoolean("config.antiSpam");
        recDelay = config.getInt("config.reconnectDelayInSec");
        joinMsg = config.getString("messages.onJoin").replaceAll("&", "§");
        quitMsg = config.getString("messages.onQuit").replaceAll("&", "§");
        kickMsg = config.getString("messages.onKick").replaceAll("&", "§");
        recMsg = config.getString("messages.onReconnect").replaceAll("&", "§");
    }
    
}