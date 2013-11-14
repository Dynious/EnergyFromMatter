package redmennl.mods.efm.core.handler;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent.Load;
import redmennl.mods.efm.client.gui.inventory.GuiMatterCreator;
import codechicken.nei.api.API;

import com.pahimar.ee3.core.helper.LogHelper;

public class WorldEventHandler
{
    @ForgeSubscribe
    public void onWorldLoaded(Load event)
    {
        LogHelper.debug("Loaded!");
        //API.registerGuiOverlay(GuiMatterCreator.class, "crafting");
        API.registerGuiOverlayHandler(GuiMatterCreator.class, new NEIOverlayHandler(), "crafting");
    }
}
