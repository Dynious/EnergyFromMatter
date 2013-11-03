package redmennl.mods.efm;

import net.minecraft.creativetab.CreativeTabs;
import redmennl.mods.efm.block.ModBlocks;
import redmennl.mods.efm.configuration.ConfigurationHandler;
import redmennl.mods.efm.core.handler.IMCHandler;
import redmennl.mods.efm.core.helper.LogHelper;
import redmennl.mods.efm.core.proxy.CommonProxy;
import redmennl.mods.efm.creativetab.CreativeTabEFM;
import redmennl.mods.efm.item.ModItems;
import redmennl.mods.efm.lib.Reference;
import redmennl.mods.efm.lib.Strings;
import redmennl.mods.efm.network.PacketHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLFingerprintViolationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION_NUMBER, dependencies = Reference.DEPENDENCIES, certificateFingerprint = Reference.FINGERPRINT)
@NetworkMod(channels = { Reference.CHANNEL_NAME }, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class EnergyFromMatter
{
    @Instance(Reference.MOD_ID)
    public static EnergyFromMatter instance;
    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;
    public static CreativeTabs tabEFM = new CreativeTabEFM(
            CreativeTabs.getNextID(), Reference.MOD_ID);
    
    public static boolean hasBC;
    public static boolean hasIC2;
    
    @EventHandler
    public void invalidFingerprint(FMLFingerprintViolationEvent event)
    {
        // Report (log) to the user that the version of Energy From Matter
        // they are using has been changed/tampered with
        if (Reference.FINGERPRINT.equals("@FINGERPRINT@"))
        {
            LogHelper.warning(Strings.NO_FINGERPRINT_MESSAGE);
        } else
        {
            LogHelper.severe(Strings.INVALID_FINGERPRINT_MESSAGE);
        }
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        // Checks if IC2 and Buildcraft are present
        hasBC = Loader.isModLoaded("BuildCraft|Energy");
        hasIC2 = Loader.isModLoaded("IC2");
        
        // Initialize the log helper
        LogHelper.init();
        
        // Initialize the configuration
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        
        // Register the Sound Handler (Client only)
        proxy.registerSoundHandler();
        
        // Initialize mod blocks
        ModBlocks.init();
        
        // Initialize mod items
        ModItems.init();
    }
    
    @EventHandler
    public void load(FMLInitializationEvent event)
    {
        // Register the GUI Handler
        NetworkRegistry.instance().registerGuiHandler(instance, proxy);
        
        // Initialize mod tile entities
        proxy.initTileEntities();
        
        // Initialize custom rendering and pre-load textures (Client only)
        proxy.initRenderingAndTextures();
        
        // Add recipes to Equivalent Exchange 3
        IMCHandler.init();
        
        // Register mod dependant recipes
        ModBlocks.registerModRecipes();
    }
}
