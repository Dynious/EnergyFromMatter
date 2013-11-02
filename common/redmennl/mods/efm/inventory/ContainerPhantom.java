package redmennl.mods.efm.inventory;

import redmennl.mods.efm.inventory.slot.IPhantomSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class ContainerPhantom extends Container
{
    @Override
    public ItemStack slotClick(int slotNum, int mouseButton, int modifier,
            EntityPlayer player)
    {
        Slot slot = slotNum < 0 ? null : (Slot) inventorySlots.get(slotNum);
        if (slot instanceof IPhantomSlot)
            return slotClickPhantom(slot, mouseButton, modifier, player);
        return super.slotClick(slotNum, mouseButton, modifier, player);
    }
    
    private ItemStack slotClickPhantom(Slot slot, int mouseButton,
            int modifier, EntityPlayer player)
    {
        ItemStack stack = null;
        if (mouseButton == 2)
        {
            if (((IPhantomSlot) slot).canAdjust())
            {
                slot.putStack(null);
            }
        } else if (mouseButton == 0 || mouseButton == 1)
        {
            InventoryPlayer playerInv = player.inventory;
            slot.onSlotChanged();
            ItemStack stackSlot = slot.getStack();
            ItemStack stackHeld = playerInv.getItemStack();
            if (stackSlot != null)
            {
                stack = stackSlot.copy();
            }
            if (stackSlot == null)
            {
                if (stackHeld != null && slot.isItemValid(stackHeld))
                {
                    fillPhantomSlot(slot, stackHeld);
                }
            } else if (stackHeld == null)
            {
                emptyPhantomSlot(slot);
            } else if (slot.isItemValid(stackHeld))
            {
                if (stackSlot.itemID == stackHeld.itemID
                        && stackSlot.getItemDamage() == stackHeld
                                .getItemDamage())
                {
                    emptyPhantomSlot(slot);
                } else
                {
                    fillPhantomSlot(slot, stackHeld);
                }
            }
        }
        return stack;
    }
    
    protected void fillPhantomSlot(Slot slot, ItemStack stackHeld)
    {
        if (!((IPhantomSlot) slot).canAdjust())
            return;
        int stackSize = 1;
        if (stackSize > slot.getSlotStackLimit())
        {
            stackSize = slot.getSlotStackLimit();
        }
        ItemStack phantomStack = stackHeld.copy();
        phantomStack.stackSize = stackSize;
        slot.putStack(phantomStack);
    }
    
    protected void emptyPhantomSlot(Slot slot)
    {
        if (!((IPhantomSlot) slot).canAdjust())
            return;
        slot.putStack(null);
    }
}
