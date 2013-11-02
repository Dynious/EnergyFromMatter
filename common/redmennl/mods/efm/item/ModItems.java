package redmennl.mods.efm.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import redmennl.mods.efm.lib.ItemIds;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ModItems
{
    public static Item linker;
    
    public static void init()
    {
        linker = new ItemLinker(ItemIds.LINKER);
        LanguageRegistry.addName(linker, "Linker");
        initItemRecipes();
    }
    
    private static void initItemRecipes()
    {
        GameRegistry.addShapelessRecipe(new ItemStack(linker), Item.paper,
                new ItemStack(Item.dyePowder));
    }
}
