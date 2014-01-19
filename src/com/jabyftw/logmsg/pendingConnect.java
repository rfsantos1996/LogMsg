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
    public void run() {
        if (pl.pendingRecon.contains(player)) {
            pl.pendingRecon.remove(player);
            if (from == 0) { // Quit
                for (Player p : pl.getServer().getOnlinePlayers()) {
                    p.sendMessage(pl.getLang("onQuit").replaceAll("%player", player.getName()));
                }
            } else if (from == 1) { // Kick
                for (Player p : pl.getServer().getOnlinePlayers()) {
                    p.sendMessage(pl.getLang("onKick").replaceAll("%player", player.getName()));
                }
            }
        } // else already logged in
    }
}
