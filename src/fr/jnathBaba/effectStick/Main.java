package fr.jnathBaba.effectStick;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.jnath.stickAPI.Stick;


public class Main extends JavaPlugin {
	public static Main main;
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		main=this;
		saveDefaultConfig();
		ConfigurationSection cS=getConfig().getConfigurationSection("effectStick");
		this.getCommand("sticks").setExecutor(new StickCmd());
		for(String str :cS.getKeys(false)) {
			ConfigurationSection tmpcS=cS.getConfigurationSection(str);
			List<PotionEffect> potions = new ArrayList<PotionEffect>();
			for(String effectStr : tmpcS.getConfigurationSection("effect").getKeys(false)) {
				ConfigurationSection tmpcSEf=tmpcS.getConfigurationSection("effect."+effectStr);
				potions.add(new PotionEffect(PotionEffectType.getById(tmpcSEf.getInt("id")), tmpcSEf.getInt("duration")*20, tmpcSEf.getInt("level")-1));
			}
			new Stick(tmpcS.getString("name").replace("&", "§"), 
					tmpcS.getString("description").replace("&", "§"), 
					tmpcS.getInt("durability"), 
					potions, tmpcS.getInt("id"));
		}
		new Stick(getConfig().getString("chestexplorer.name").replace("&", "§"), 
				getConfig().getString("chestexplorer.message").replace("&", "§"), 
				getConfig().getInt("chestexplorer.dura"), (player, pair) -> Main.stick1(player, pair), 200);
		
		new Stick(getConfig().getString("cavestick.name").replace("&", "§"), 
				getConfig().getString("cavestick.message").replace("&", "§"), 
				getConfig().getInt("cavestick.dura"), (player, pair) -> Main.stick2(player, pair), 201);
		new Stick(getConfig().getString("unclaim.name").replace("&", "§"), 
				getConfig().getString("unclaim.message").replace("&", "§"), 
				getConfig().getInt("unclaim.dura"), (player, pair) -> Main.stick3(player, pair), 202);
	}
	
	public static void stick1(Player player, ImmutablePair<Location, Block> pair) {
		if(pair.right==null)return;
		if(pair.right.getType()==Material.CHEST) {
			int i=0;
			Inventory inv= Bukkit.createInventory(null, 54, "§cView Chest Explorer");
			for (ItemStack item : ((Chest) pair.right.getState()).getInventory().getContents()) {
				if(item!=null)inv.setItem(i, item);
				i++;
			}
			player.openInventory(inv);
		}	
	}
	public static void stick2(Player player, ImmutablePair<Location, Block> pair) {
		if(pair.right==null)return;
		GTiveCave cave = new GTiveCave(player, pair.right.getLocation());
		cave.runTaskTimer(main, 0, 20);
	}
	public static void stick3(Player player, ImmutablePair<Location, Block> pair) {
		int i = 0;
		for(BlockState b : pair.left.getChunk().getTileEntities()) {
			if(b.getType()==Material.CHEST)i++;
		}
		player.sendMessage(String.valueOf(i));
	}
}
