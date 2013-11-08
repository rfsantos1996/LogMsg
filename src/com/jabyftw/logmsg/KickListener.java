package com.jabyftw.logmsg;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class KickListener implements Listener {

    private final LogMsg pl;

    KickListener(LogMsg plugin) {
        this.pl = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onKick(PlayerKickEvent e) {
        Player p = e.getPlayer();

        if (p.hasPermission("logmsg.silent")) {
            e.setLeaveMessage("");
        } else {
            if (pl.antiSpam) {
                pl.pendingCon = p.getName();
                e.setLeaveMessage("");
                pl.getServer().getScheduler().scheduleSyncDelayedTask(pl, new pendingConnect(pl, p, 1), pl.recDelay * 20);
            } else {
                e.setLeaveMessage(pl.kickMsg.replaceAll("%player", p.getName()));
            }
        }
    }
}