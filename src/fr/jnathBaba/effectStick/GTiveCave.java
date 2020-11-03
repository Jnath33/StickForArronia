package fr.jnathBaba.effectStick;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GTiveCave extends BukkitRunnable{
	Player player;
	Location loc1;
	Location loc2;
	Integer time=5;
	public GTiveCave(Player player, Location loc1) {
		this.player=player;
		this.loc1=new Location(loc1.getWorld(), loc1.getX(), loc1.getY()-1, loc1.getZ());
		this.loc2=player.getLocation();
		player.setGameMode(GameMode.SPECTATOR);
		player.teleport(loc1);
	}
	public void run() {
		player.teleport(new Location(loc1.getWorld(), loc1.getX(), loc1.getY(), loc1.getZ(), player.getLocation().getYaw(), player.getLocation().getPitch()));
		if(time==0) {
			player.setGameMode(GameMode.SURVIVAL);
			player.teleport(loc2);
			cancel();
		}
		time --; 
	}
}
