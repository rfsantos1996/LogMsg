package com.jabyftw.logmsg;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {
    private final LogMsg pl;
    
    QuitListener(LogMsg plugin) {
        this.pl = plugin;
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent e) { // You can't .hasPermission(perm) when the player is offline - so dumb
        Player p = e.getPlayer();
        
        if (pl.hasSilent.contains(p)) {
            e.setQuitMessage("");
        } else {
            if (pl.antiSpam) {
                if(!pl.pendingRecon.contains(p)) { // fixed bug where kick + quit messages were appearing
                    pl.pendingRecon.add(p);
                    pl.getServer().getScheduler().scheduleSyncDelayedTask(pl, new pendingConnect(pl, p, 0), pl.recDelay * 20);
                }
                e.setQuitMessage("");
            } else {
                e.setQuitMessage(pl.quitMsg.replaceAll("%player", p.getName()));
            }
        }
    }
}