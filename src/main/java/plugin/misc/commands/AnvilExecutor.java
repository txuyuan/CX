package plugin.misc.commands;

import com.earth2me.essentials.commands.EssentialsCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class AnvilExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("cx.anvil")){
            sender.sendMessage("§c(Error)§f You do not have permission to do this");
            return true;
        }
        if(!(sender instanceof Player)){
            sender.sendMessage("§c(Error)§f You must be a player to do this");
            return true;
        }

        Player player = (Player)sender;


        Inventory anvilInv = Bukkit.createInventory(null, InventoryType.ANVIL);
        player.openInventory(anvilInv);

        return true;
    }
}
