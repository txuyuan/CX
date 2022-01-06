package plugin.misc.managers;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class InvToString {

    public static String toString(Inventory inv) {
        List<ItemStack> targetEC = Arrays.asList(inv.getStorageContents());
        String reply = "";
        targetEC.forEach(item -> {
            reply.concat("\n" + item.toString());
        });
        return reply;
    }

}
