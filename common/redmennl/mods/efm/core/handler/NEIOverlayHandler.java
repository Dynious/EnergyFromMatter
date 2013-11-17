package redmennl.mods.efm.core.handler;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import redmennl.mods.efm.client.gui.inventory.GuiMatterCreator;
import redmennl.mods.efm.network.PacketTypeHandler;
import redmennl.mods.efm.network.packet.PacketSlotChange;
import redmennl.mods.efm.tileentity.TileMatterCreator;
import codechicken.nei.api.IOverlayHandler;
import codechicken.nei.recipe.IRecipeHandler;

import com.pahimar.ee3.emc.EmcRegistry;

import cpw.mods.fml.common.network.PacketDispatcher;

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
