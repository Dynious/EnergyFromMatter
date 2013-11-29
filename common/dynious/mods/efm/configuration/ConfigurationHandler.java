package dynious.mods.efm.configuration;

import java.io.File;

import net.minecraftforge.common.Configuration;

import com.pahimar.ee3.core.helper.LogHelper;

import dynious.mods.efm.lib.BlockIds;
import dynious.mods.efm.lib.ItemIds;
import dynious.mods.efm.lib.Reference;
import dynious.mods.efm.lib.Strings;
import dynious.mods.efm.lib.Toggles;

public class ConfigurationHandler
{
    public static Configuration configuration;
    
    public static void init(File configFile)
    {
        configuration = new Configuration(configFile);
        try
        {
            BlockIds.MATTER_DISTILLERY = configuration.getBlock(
                    Strings.MATTER_DISTILLERY_NAME,
                    BlockIds.MATTER_DISTILLERY_DEFAULT).getInt(
                    BlockIds.MATTER_DISTILLERY_DEFAULT);
            BlockIds.EMC_CAPACITOR = configuration.getBlock(
                    Strings.EMC_CAPACITOR_NAME, BlockIds.EMC_CAPACITOR_DEFAULT)
                    .getInt(BlockIds.EMC_CAPACITOR_DEFAULT);
            BlockIds.CROP_RIPENER = configuration.getBlock(
                    Strings.CROP_RIPENER_NAME, BlockIds.CROP_RIPENER_DEFAULT)
                    .getInt(BlockIds.CROP_RIPENER_DEFAULT);
            BlockIds.ENERGY_CREATOR = configuration.getBlock(
                    Strings.ENERGY_CREATOR_NAME,
                    BlockIds.ENERGY_CREATOR_DEFAULT).getInt(
                    BlockIds.ENERGY_CREATOR_DEFAULT);
            BlockIds.MATTER_CREATOR = configuration.getBlock(
                    Strings.MATTER_CREATOR_NAME,
                    BlockIds.MATTER_CREATOR_DEFAULT).getInt(
                    BlockIds.MATTER_CREATOR_DEFAULT);
            BlockIds.PORTABLE_HOUSE = configuration.getBlock(
                    Strings.PORTABLE_HOUSE_NAME,
                    BlockIds.PORTABLE_HOUSE_DEFAULT).getInt(
                    BlockIds.PORTABLE_HOUSE_DEFAULT);
            BlockIds.FLUID_DISTILLERY = configuration.getBlock(
                    "fluidDistillery", BlockIds.FLUID_DISTILLERY_DEFAULT)
                    .getInt(BlockIds.FLUID_DISTILLERY_DEFAULT);
            BlockIds.FLUID_CREATOR = configuration.getBlock(
                    Strings.FLUID_CREATOR_NAME, BlockIds.FLUID_CREATOR_DEFAULT)
                    .getInt(BlockIds.FLUID_CREATOR_DEFAULT);
            BlockIds.MATTER_SUPERHEATER = configuration.getBlock(
                    Strings.MATTER_SUPERHEATER_NAME,
                    BlockIds.MATTER_SUPERHEATER_DEFAULT).getInt(
                    BlockIds.MATTER_SUPERHEATER_DEFAULT);
            BlockIds.ENERGY_DISTILLERY = configuration.getBlock(
                    Strings.ENERGY_DISTILLERY_NAME,
                    BlockIds.ENERGY_DISTILLERY_DEFAULT).getInt(
                    BlockIds.ENERGY_DISTILLERY_DEFAULT);
            BlockIds.CREATOR_INTERFACE = configuration.getBlock(
                    Strings.CREATOR_INTERFACE_NAME,
                    BlockIds.CREATOR_INTERFACE_DEFAULT).getInt(
                    BlockIds.CREATOR_INTERFACE_DEFAULT);
            BlockIds.MATTER_CONDENSER = configuration.getBlock(
                    Strings.MATTER_CONDENSER_NAME,
                    BlockIds.MATTER_CONDENSER_DEFAULT).getInt(
                    BlockIds.MATTER_CONDENSER_DEFAULT);
            
            ItemIds.LINKER = configuration.getItem(Strings.LINKER_NAME,
                    ItemIds.LINKER_DEFAULT).getInt(ItemIds.LINKER_DEFAULT);
            ItemIds.PORTABLE_EMC_CAPACITOR = configuration.getItem(
                    Strings.PORTABLE_EMC_CAPACITOR_NAME,
                    ItemIds.PORTABLE_EMC_CAPACITOR_DEFAULT).getInt(
                    ItemIds.PORTABLE_EMC_CAPACITOR_DEFAULT);
            ItemIds.GRAVITY_DEFYER = configuration
                    .getItem(Strings.GRAVITY_DEFYER_NAME,
                            ItemIds.GRAVITY_DEFYER_DEFAULT).getInt(
                            ItemIds.GRAVITY_DEFYER_DEFAULT);
            ItemIds.HEALER = configuration.getItem(Strings.HEALER_NAME,
                    ItemIds.HEALER_DEFAULT).getInt(ItemIds.HEALER_DEFAULT);
            ItemIds.CREATIVE_PORTABLE_EMC_CAPACITOR = configuration.getItem(
                    Strings.CREATIVE_PORTABLE_EMC_CAPACITOR_NAME,
                    ItemIds.CREATIVE_PORTABLE_EMC_CAPACITOR_DEFAULT).getInt(
                    ItemIds.CREATIVE_PORTABLE_EMC_CAPACITOR_DEFAULT);
            
            Toggles.SHOW_EMC_PARTICLES = configuration.get("Client options",
                    "Show EMC Particles", Toggles.SHOW_EMC_PARTICLES_DEFAULT)
                    .getBoolean(Toggles.SHOW_EMC_PARTICLES_DEFAULT);
            
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
