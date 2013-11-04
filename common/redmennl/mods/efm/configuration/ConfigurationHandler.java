package redmennl.mods.efm.configuration;

import java.io.File;

import net.minecraftforge.common.Configuration;
import redmennl.mods.efm.lib.BlockIds;
import redmennl.mods.efm.lib.ItemIds;
import redmennl.mods.efm.lib.Reference;

import com.pahimar.ee3.core.helper.LogHelper;

public class ConfigurationHandler
{
    public static Configuration configuration;
    
    public static void init(File configFile)
    {
        configuration = new Configuration(configFile);
        try
        {
            BlockIds.ENERGY_CONDENSER = configuration.getBlock(
                    "energyCondenser", BlockIds.ENERGY_CONDENSER_DEFAULT)
                    .getInt(BlockIds.ENERGY_CONDENSER_DEFAULT);
            BlockIds.MATTER_DISTILLERY = configuration.getBlock(
                    "matterDistillery", BlockIds.MATTER_DISTILLERY_DEFAULT)
                    .getInt(BlockIds.MATTER_DISTILLERY_DEFAULT);
            BlockIds.EMC_CAPACITOR = configuration.getBlock("emcCapacitor",
                    BlockIds.EMC_CAPACITOR_DEFAULT).getInt(
                    BlockIds.EMC_CAPACITOR_DEFAULT);
            BlockIds.CROP_RIPENER = configuration.getBlock("cropRipener",
                    BlockIds.CROP_RIPENER_DEFAULT).getInt(
                    BlockIds.CROP_RIPENER_DEFAULT);
            BlockIds.POWER_LINK = configuration.getBlock("powerLink",
                    BlockIds.POWER_LINK_DEFAULT).getInt(
                    BlockIds.POWER_LINK_DEFAULT);
            BlockIds.CONDENSER = configuration.getBlock("condenser",
                    BlockIds.CONDENSER_DEFAULT).getInt(
                    BlockIds.CONDENSER_DEFAULT);
            BlockIds.PORTABLE_HOUSE = configuration.getBlock("portableHouse",
                    BlockIds.PORTABLE_HOUSE_DEFAULT).getInt(
                    BlockIds.PORTABLE_HOUSE_DEFAULT);
            BlockIds.FLUID_DISTILLERY = configuration.getBlock(
                    "fluidDistillery", BlockIds.FLUID_DISTILLERY_DEFAULT)
                    .getInt(BlockIds.FLUID_DISTILLERY_DEFAULT);
            
            ItemIds.LINKER = configuration.getItem("linker",
                    ItemIds.LINKER_DEFAULT).getInt(ItemIds.LINKER_DEFAULT);
            ItemIds.PORTABLE_EMC_CAPACITOR = configuration.getItem(
                    "portableEmcCapacitor",
                    ItemIds.PORTABLE_EMC_CAPACITOR_DEFAULT).getInt(
                    ItemIds.PORTABLE_EMC_CAPACITOR_DEFAULT);
            ItemIds.GRAVITY_DEFYER = configuration.getItem("gravityDefyer",
                    ItemIds.GRAVITY_DEFYER_DEFAULT).getInt(
                    ItemIds.GRAVITY_DEFYER_DEFAULT);
            ItemIds.HEALER = configuration.getItem("healer",
                    ItemIds.HEALER_DEFAULT).getInt(ItemIds.HEALER_DEFAULT);
        } catch (Exception e)
        {
            LogHelper.severe(Reference.MOD_NAME
                    + " has had a problem loading its configuration");
        } finally
        {
            configuration.save();
        }
    }
}
