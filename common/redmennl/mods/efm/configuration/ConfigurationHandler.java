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
            ItemIds.LINKER = configuration.getBlock("linker",
                    ItemIds.LINKER_DEFAULT).getInt(ItemIds.LINKER_DEFAULT);
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
