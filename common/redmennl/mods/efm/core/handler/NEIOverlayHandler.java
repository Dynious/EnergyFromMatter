package redmennl.mods.efm.core.handler;

import com.pahimar.ee3.emc.EmcRegistry;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import redmennl.mods.efm.client.gui.inventory.GuiMatterCreator;
import codechicken.nei.api.IOverlayHandler;
import codechicken.nei.recipe.IRecipeHandler;

public class NEIOverlayHandler implements IOverlayHandler
{

    @Override
    public void overlayRecipe(GuiContainer gui, IRecipeHandler recipe, int recipeIndex,
            boolean shift)
    {
        if (gui instanceof GuiMatterCreator)
        {
            ItemStack stack = recipe.getResultStack(recipeIndex).items[0];
            stack.stackSize = 1;
            if (EmcRegistry.hasEmcValue(stack))
            {
                gui.inventorySlots.putStackInSlot(0, stack);
            }
        }
    }
}
