package fr.jnathBaba.effectStick;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.jnath.stickAPI.Stick;

public class StickCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player player = (Player)arg0;
		System.out.println(Stick.getNameById(Integer.valueOf(arg3[0])));
		player.getInventory().addItem(Stick.createStick(Stick.getNameById(Integer.valueOf(arg3[0]))));
		player.updateInventory();
		return true;
	}

}
