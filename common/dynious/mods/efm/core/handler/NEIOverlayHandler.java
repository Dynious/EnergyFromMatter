package dynious.mods.efm.core.handler;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import codechicken.nei.api.IOverlayHandler;
import codechicken.nei.recipe.IRecipeHandler;

import com.pahimar.ee3.emc.EmcRegistry;

import cpw.mods.fml.common.network.PacketDispatcher;
import dynious.mods.efm.client.gui.inventory.GuiMatterCreator;
import dynious.mods.efm.network.PacketTypeHandler;
import dynious.mods.efm.network.packet.PacketSlotChange;
import dynious.mods.efm.tileentity.TileMatterCreator;

public class NEIOverlayHandler implements IOverlayHandler
{
    
    @Override
    public void overlayRecipe(GuiContainer gui, IRecipeHandler recipe,
            int recipeIndex, boolean shift)
    {
        if (gui instanceof GuiMatterCreator)
        {
            ItemStack stack = recipe.getResultStack(recipeIndex).items[0];
            stack.stackSize = 1;
            if (EmcRegistry.hasEmcValue(stack))
            {
                TileMatterCreator tile = ((GuiMatterCreator) gui).tile;
                PacketDispatcher.sendPacketToServer(PacketTypeHandler
                        .populatePacket(new PacketSlotChange(tile.xCoord,
                                tile.yCoord, tile.zCoord, 0, stack)));
            }
        }
    }
}
