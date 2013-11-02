package redmennl.mods.efm.inventory.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotRestricted extends Slot
{
    IInventory inventory;
    
    public SlotRestricted(IInventory par1iInventory, int par2, int par3,
            int par4)
    {
        super(par1iInventory, par2, par3, par4);
        inventory = par1iInventory;
    }
    
    @Override
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return inventory.isItemValidForSlot(slotNumber, par1ItemStack) ? true
                : false;
    }
}
