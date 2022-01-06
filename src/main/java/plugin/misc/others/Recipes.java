package plugin.misc.others;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import plugin.CX.Main;

public class Recipes {

    public static void register() {
        ItemStack result = new ItemStack(Material.BUNDLE);
        NamespacedKey key = new NamespacedKey(Main.getInstance(), "bundle");
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("SRS", "R R", "RRR");
        recipe.setIngredient('S', Material.STRING);
        recipe.setIngredient('R', Material.RABBIT_HIDE);

        Bukkit.addRecipe(recipe);
    }

}
