package redmennl.mods.efm.block;

import ic2.core.Ic2Items;
import buildcraft.BuildCraftTransport;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import redmennl.mods.efm.EnergyFromMatter;
import redmennl.mods.efm.lib.BlockIds;
import redmennl.mods.efm.lib.Reference;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ModBlocks
{
    public static Block energyCondenser;
    public static Block matterDistillery;
    public static Block emcCapacitor;
    public static Block cropRipener;
    public static Block powerLink;
    public static Block condenser;
    
    public static void init()
    {
        energyCondenser = new BlockEnergyCondenser(BlockIds.ENERGY_CONDENSER);
        matterDistillery = new BlockMatterDistillery(BlockIds.MATTER_DISTILLERY);
        emcCapacitor = new BlockEmcCapacitor(BlockIds.EMC_CAPACITOR);
        cropRipener = new BlockCropRipener(BlockIds.CROP_RIPENER);
        condenser = new BlockCondenser(BlockIds.CONDENSER);
        
        if (EnergyFromMatter.hasBC || EnergyFromMatter.hasIC2)
        {
            powerLink = new BlockPowerLink(BlockIds.POWER_LINK);
            
            GameRegistry.registerBlock(powerLink, Reference.MOD_ID
                    + powerLink.getUnlocalizedName().substring(5));
            
            LanguageRegistry.addName(powerLink, "Power Link");
        }
        
        GameRegistry.registerBlock(energyCondenser, Reference.MOD_ID
                + energyCondenser.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(matterDistillery, Reference.MOD_ID
                + matterDistillery.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(emcCapacitor, Reference.MOD_ID
                + emcCapacitor.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(cropRipener, Reference.MOD_ID
                + cropRipener.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(condenser, Reference.MOD_ID
                + condenser.getUnlocalizedName().substring(5));
        
        LanguageRegistry.addName(energyCondenser, "Energy Condenser");
        LanguageRegistry.addName(matterDistillery, "Matter Distillery");
        LanguageRegistry.addName(emcCapacitor, "EMC Capacitor");
        LanguageRegistry.addName(cropRipener, "Crop Ripener");
        LanguageRegistry.addName(condenser, "Condenser");
        
        initBlockRecipes();
    }
    
    private static void initBlockRecipes()
    {
        GameRegistry
                .addRecipe(new ItemStack(energyCondenser), "odo", "dcd", "odo",
                        'o', Block.obsidian, 'd', Item.diamond, 'c',
                        Block.chest);
        GameRegistry.addRecipe(new ItemStack(emcCapacitor), "ded", "ege",
                "ded", 'd', Item.diamond, 'e', Item.enderPearl, 'g',
                com.pahimar.ee3.block.ModBlocks.glassBell);
        GameRegistry.addRecipe(new ItemStack(cropRipener), "sew", "ebe", "pec",
                's', Block.sapling, 'e', Item.enderPearl, 'w', Item.seeds, 'b',
                new ItemStack(Item.dyePowder.itemID, 1, 15), 'p', Item.potato,
                'c', Item.carrot);
        GameRegistry.addRecipe(new ItemStack(condenser), "oeo", "ede", "oeo",
                'o', Block.obsidian, 'e', Item.enderPearl, 'd',
                Block.blockDiamond);
    }
    
    public static void registerModRecipes()
    {
        GameRegistry.addRecipe(new ItemStack(matterDistillery), "dod", "ogo",
                "dod", 'd', Item.diamond, 'o', Block.obsidian, 'g',
                com.pahimar.ee3.item.ModItems.miniumStone);
        if (EnergyFromMatter.hasBC && EnergyFromMatter.hasIC2)
        {
            GameRegistry.addRecipe(new ItemStack(powerLink), "gpg", "fef",
                    "gpg", 'g', Block.glass, 'p',
                    BuildCraftTransport.pipePowerDiamond, 'f',
                    Ic2Items.glassFiberCableItem, 'e', Item.enderPearl);
        }
    }
}
