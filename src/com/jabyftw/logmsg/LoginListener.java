package com.jabyftw.logmsg;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LoginListener implements Listener {

    private final LogMsg pl;

    LoginListener(LogMsg plugin) {
        this.pl = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (p.hasPermission("logmsg.silent")) {
            e.setJoinMessage("");
            pl.hasSilent.add(p);
        } else {
            if (pl.antiSpam) {
                if (pl.pendingRecon.contains(p)) {
                    pl.pendingRecon.remove(p);
                    e.setJoinMessage(pl.getLang("onReconnect").replaceAll("%player", p.getName()));
                } else {
                    e.setJoinMessage(pl.getLang("onJoin").replaceAll("%player", p.getName()));
                }
            } else {
                e.setJoinMessage(pl.getLang("onJoin").replaceAll("%player", p.getName()));
            }
        }
    }
}
