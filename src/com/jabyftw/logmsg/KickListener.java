package com.jabyftw.logmsg;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class KickListener implements Listener {

    private final LogMsg pl;

    KickListener(LogMsg pl) {
        this.pl = pl;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onKick(PlayerKickEvent e) {
        Player p = e.getPlayer();

        if (p.hasPermission("logmsg.silent")) {
            e.setLeaveMessage("");
        } else {
            if (pl.antiSpam) {
                pl.pendingRecon.add(p);
                e.setLeaveMessage("");
                pl.getServer().getScheduler().scheduleSyncDelayedTask(pl, new pendingConnect(pl, p, 1), pl.recDelay * 20);
            } else {
                e.setLeaveMessage(pl.getLang("onKick").replaceAll("%player", p.getName()));
            }
        }
    }
}