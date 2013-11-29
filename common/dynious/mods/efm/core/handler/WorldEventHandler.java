package dynious.mods.efm.core.handler;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent.Load;
import codechicken.nei.api.API;
import cpw.mods.fml.common.Loader;
import dynious.mods.efm.block.ModBlocks;
import dynious.mods.efm.client.gui.inventory.GuiMatterCreator;

public class WorldEventHandler
{
    @ForgeSubscribe
    public void onWorldLoaded(Load event)
    {
        if (event.world.isRemote && Loader.isModLoaded("NotEnoughItems"))
        {
            API.registerGuiOverlayHandler(GuiMatterCreator.class,
                    new NEIOverlayHandler(), "crafting");
            API.setMaxDamageException(ModBlocks.matterDistillery.blockID, 0);
            API.setMaxDamageException(ModBlocks.cropRipener.blockID, 0);
            API.setMaxDamageException(ModBlocks.cropRipener.blockID, 0);
            API.setMaxDamageException(ModBlocks.matterCreator.blockID, 0);
            API.setMaxDamageException(ModBlocks.fluidDistillery.blockID, 0);
            API.setMaxDamageException(ModBlocks.fluidCreator.blockID, 0);
            API.setMaxDamageException(ModBlocks.matterSuperheater.blockID, 0);
            API.setMaxDamageException(ModBlocks.energyDistillery.blockID, 0);
        }
    }
}
