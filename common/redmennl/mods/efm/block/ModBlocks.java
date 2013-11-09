package redmennl.mods.efm.block;

import ic2.core.Ic2Items;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import redmennl.mods.efm.EnergyFromMatter;
import redmennl.mods.efm.block.item.ItemPortableHouse;
import redmennl.mods.efm.lib.BlockIds;
import redmennl.mods.efm.lib.Strings;
import buildcraft.BuildCraftTransport;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks
{
    public static Block matterDistillery;
    public static Block emcCapacitor;
    public static Block cropRipener;
    public static Block powerLink;
    public static Block condenser;
    public static Block portableHouse;
    public static Block fluidDistillery;
    public static Block fluidCondenser;
    public static Block matterSuperheater;
    
    public static void init()
    {
        matterDistillery = new BlockMatterDistillery(BlockIds.MATTER_DISTILLERY);
        emcCapacitor = new BlockEmcCapacitor(BlockIds.EMC_CAPACITOR);
        cropRipener = new BlockCropRipener(BlockIds.CROP_RIPENER);
        condenser = new BlockMatterCreator(BlockIds.MATTER_CREATOR);
        portableHouse = new BlockPortableHouse(BlockIds.PORTABLE_HOUSE);
        fluidDistillery = new BlockFluidDistillery(BlockIds.FLUID_DISTILLERY);
        fluidCondenser = new BlockFluidCondenser(BlockIds.FLUID_CONDENSER);
        matterSuperheater = new BlockMatterSuperheater(
                BlockIds.MATTER_SUPERHEATER);
        
        if (EnergyFromMatter.hasBC || EnergyFromMatter.hasIC2)
        {
            powerLink = new BlockPowerLink(BlockIds.POWER_LINK);
            
            GameRegistry.registerBlock(powerLink, Strings.POWER_LINK_NAME);
        }
        
        GameRegistry.registerBlock(matterDistillery,
                Strings.MATTER_DISTILLERY_NAME);
        GameRegistry.registerBlock(emcCapacitor, Strings.EMC_CAPACITOR_NAME);
        GameRegistry.registerBlock(cropRipener, Strings.CROP_RIPENER_NAME);
        GameRegistry.registerBlock(condenser, Strings.MATTER_CREATOR_NAME);
        GameRegistry.registerBlock(portableHouse, ItemPortableHouse.class,
                Strings.PORTABLE_HOUSE_NAME);
        GameRegistry.registerBlock(fluidDistillery,
                Strings.FLUID_DISTILLERY_NAME);
        GameRegistry
                .registerBlock(fluidCondenser, Strings.FLUID_CONDENSER_NAME);
        GameRegistry.registerBlock(matterSuperheater,
                Strings.MATTER_SUPERHEATER_NAME);
        
        initBlockRecipes();
    }
    
    private static void initBlockRecipes()
    {
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
        GameRegistry.addRecipe(new ItemStack(fluidCondenser), "beb", "ede",
                "beb", 'b', Item.bucketEmpty, 'e', Item.enderPearl, 'd',
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
