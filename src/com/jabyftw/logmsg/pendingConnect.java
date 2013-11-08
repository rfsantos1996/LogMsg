package com.jabyftw.logmsg;

import org.bukkit.entity.Player;

/**
 *
 * @author Rafael
 */
class pendingConnect implements Runnable {
    
    private final Player player;
    private final LogMsg pl;
    private final int from;

    public pendingConnect(LogMsg pl, Player player, int from) {
        this.pl = pl;
        this.player = player;
        this.from = from;
    }

    @Override
    public void run() { // RAN OUT OF TIME, WHAT DO WE DO?
        if(pl.pendingCon.equalsIgnoreCase(player.getName())) {
            pl.pendingCon = null;
            if(from == 0) { // Quit
                for(Player p : pl.getServer().getOnlinePlayers()) {
                    p.sendMessage(pl.quitMsg.replaceAll("%player", player.getName()));
                }
            } else if(from == 1) { // Kick
                for(Player p : pl.getServer().getOnlinePlayers()) {
                    p.sendMessage(pl.kickMsg.replaceAll("%player", player.getName()));
                }
            }
        }
    }
}