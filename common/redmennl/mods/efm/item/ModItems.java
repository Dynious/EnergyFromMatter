package redmennl.mods.efm.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import redmennl.mods.efm.lib.ItemIds;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems
{
    public static Item linker;
    public static Item portableEmcCapacitor;
    public static Item gravityDefyer;
    public static Item healer;
    
    public static void init()
    {
        linker = new ItemLinker(ItemIds.LINKER);
        portableEmcCapacitor = new ItemProtableEmcCapacitor(
                ItemIds.PORTABLE_EMC_CAPACITOR);
        gravityDefyer = new ItemGravityDefyer(ItemIds.GRAVITY_DEFYER);
        healer = new ItemHealer(ItemIds.HEALER);
        
        /*
         * LanguageRegistry.addName(linker, "Linker"); LanguageRegistry
         * .addName(portableEmcCapacitor, "Portable EMC Capacitor");
         * LanguageRegistry.addName(gravityDefyer, "Gravity Defyer");
         * LanguageRegistry.addName(healer, "Healer");
         */
        
        initItemRecipes();
    }
    
    private static void initItemRecipes()
    {
        GameRegistry.addShapelessRecipe(new ItemStack(linker), Item.paper,
                Item.dyePowder);
        GameRegistry.addRecipe(new ItemStack(portableEmcCapacitor), "ded",
                "ege", "ded", 'd', Item.ingotGold, 'e', Item.enderPearl, 'g',
                com.pahimar.ee3.block.ModBlocks.glassBell);
        GameRegistry.addRecipe(new ItemStack(gravityDefyer), "fbf", "bpb",
                "fbf", 'f', Item.feather, 'b', Item.blazePowder, 'p',
                portableEmcCapacitor);
        GameRegistry.addRecipe(new ItemStack(healer), "cac", "cpc", "cac", 'c',
                Item.goldenCarrot, 'a', Item.appleGold, 'p',
                portableEmcCapacitor);
    }
}
