package redmennl.mods.efm.block;

import ic2.core.Ic2Items;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import redmennl.mods.efm.EnergyFromMatter;
import redmennl.mods.efm.block.item.ItemPortableHouse;
import redmennl.mods.efm.lib.BlockIds;
import redmennl.mods.efm.lib.Reference;
import buildcraft.BuildCraftTransport;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ModBlocks
{
    //public static Block energyCondenser;
    public static Block matterDistillery;
    public static Block emcCapacitor;
    public static Block cropRipener;
    public static Block powerLink;
    public static Block condenser;
    public static Block portableHouse;
    public static Block fluidDistillery;
    public static Block fluidCondenser;
    
    public static void init()
    {
        //energyCondenser = new BlockEnergyCondenser(BlockIds.ENERGY_CONDENSER);
        matterDistillery = new BlockMatterDistillery(BlockIds.MATTER_DISTILLERY);
        emcCapacitor = new BlockEmcCapacitor(BlockIds.EMC_CAPACITOR);
        cropRipener = new BlockCropRipener(BlockIds.CROP_RIPENER);
        condenser = new BlockCondenser(BlockIds.CONDENSER);
        portableHouse = new BlockPortableHouse(BlockIds.PORTABLE_HOUSE);
        fluidDistillery = new BlockFluidDistillery(BlockIds.FLUID_DISTILLERY);
        fluidCondenser = new BlockFluidCondenser(BlockIds.FLUID_CONDENSER);
        
        if (EnergyFromMatter.hasBC || EnergyFromMatter.hasIC2)
        {
            powerLink = new BlockPowerLink(BlockIds.POWER_LINK);
            
            GameRegistry.registerBlock(powerLink, Reference.MOD_ID
                    + powerLink.getUnlocalizedName().substring(5));
            
            LanguageRegistry.addName(powerLink, "Power Link");
        }
        
        //GameRegistry.registerBlock(energyCondenser, Reference.MOD_ID
        //        + energyCondenser.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(matterDistillery, Reference.MOD_ID
                + matterDistillery.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(emcCapacitor, Reference.MOD_ID
                + emcCapacitor.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(cropRipener, Reference.MOD_ID
                + cropRipener.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(condenser, Reference.MOD_ID
                + condenser.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(portableHouse, ItemPortableHouse.class,
                Reference.MOD_ID
                        + portableHouse.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(fluidDistillery, Reference.MOD_ID
                + fluidDistillery.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(fluidCondenser, Reference.MOD_ID
                + fluidCondenser.getUnlocalizedName().substring(5));
        
        //LanguageRegistry.addName(energyCondenser, "Energy Condenser");
        LanguageRegistry.addName(matterDistillery, "Matter Distillery");
        LanguageRegistry.addName(emcCapacitor, "EMC Capacitor");
        LanguageRegistry.addName(cropRipener, "Crop Ripener");
        LanguageRegistry.addName(condenser, "Condenser");
        LanguageRegistry.addName(new ItemStack(portableHouse, 1, 0),
                "Portable House");
        LanguageRegistry.addName(new ItemStack(portableHouse, 1, 1),
                "Portable House Deployer");
        LanguageRegistry.addName(fluidDistillery, "Fluid Distillery");
        LanguageRegistry.addName(fluidCondenser, "Fluid Condenser");
        
        initBlockRecipes();
    }
    
    private static void initBlockRecipes()
    {
        //GameRegistry
        //        .addRecipe(new ItemStack(energyCondenser), "odo", "dcd", "odo",
        //                'o', Block.obsidian, 'd', Item.diamond, 'c',
        //                Block.chest);
        GameRegistry.addRecipe(new ItemStack(matterDistillery), "dod", "ogo",
                "dod", 'd', Item.diamond, 'o', Block.obsidian, 'g',
                new ItemStack(com.pahimar.ee3.item.ModItems.miniumStone, 1,
                        OreDictionary.WILDCARD_VALUE));
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
        GameRegistry.addRecipe(new ItemStack(portableHouse), "ded", "ege",
                "ded", 'd', Item.diamond, 'e', Item.enderPearl, 'g',
                Block.glass);
        GameRegistry.addRecipe(new ItemStack(fluidDistillery), "dwd", "lgl",
                "dwd", 'd', Item.diamond, 'w', Item.bucketWater, 'l',
                Item.bucketLava, 'g', new ItemStack(
                        com.pahimar.ee3.item.ModItems.miniumStone, 1,
                        OreDictionary.WILDCARD_VALUE));
        GameRegistry.addRecipe(new ItemStack(fluidCondenser), "beb", "ede", "beb",
                'b', Item.bucketEmpty, 'e', Item.enderPearl, 'd',
                Item.diamond);
    }
    
    public static void registerModRecipes()
    {
        if (EnergyFromMatter.hasBC && !EnergyFromMatter.hasIC2)
        {
            GameRegistry.addRecipe(new ItemStack(powerLink), "gpg", "pep",
                    "gpg", 'g', Block.glass, 'p',
                    BuildCraftTransport.pipePowerDiamond, 'e', Item.enderPearl);
        }
        if (!EnergyFromMatter.hasBC && EnergyFromMatter.hasIC2)
        {
            GameRegistry.addRecipe(new ItemStack(powerLink), "gfg", "fef",
                    "gfg", 'g', Block.glass, 'f', Ic2Items.glassFiberCableItem,
                    'e', Item.enderPearl);
        }
        if (EnergyFromMatter.hasBC && EnergyFromMatter.hasIC2)
        {
            GameRegistry.addRecipe(new ItemStack(powerLink), "gpg", "fef",
                    "gpg", 'g', Block.glass, 'p',
                    BuildCraftTransport.pipePowerDiamond, 'f',
                    Ic2Items.glassFiberCableItem, 'e', Item.enderPearl);
        }
    }
}
