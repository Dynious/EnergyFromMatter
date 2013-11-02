package redmennl.mods.efm.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import redmennl.mods.efm.inventory.slot.SlotRestricted;
import redmennl.mods.efm.tileentity.TileEmcCapacitor;

public class ContainerEmcCapacitor extends Container
{
    TileEmcCapacitor tile;
    private final int PLAYER_INVENTORY_ROWS = 3;
    private final int PLAYER_INVENTORY_COLUMNS = 9;
    
    public ContainerEmcCapacitor(InventoryPlayer inventoryPlayer,
            TileEmcCapacitor tile)
    {
        this.tile = tile;
        // Add the EMC Capacitor slots to the container
        this.addSlotToContainer(new SlotRestricted(tile, 0, 152, 14));
        this.addSlotToContainer(new SlotRestricted(tile, 1, 152, 54));
        // Add the player's inventory slots to the container
        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex)
        {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer,
                        inventoryColumnIndex + inventoryRowIndex * 9 + 9,
                        8 + inventoryColumnIndex * 18,
                        84 + inventoryRowIndex * 18));
            }
        }
        // Add the player's action bar slots to the container
        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex)
        {
            this.addSlotToContainer(new Slot(inventoryPlayer,
                    actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 142));
        }
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return true;
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer,
            int slotIndex)
    {
        ItemStack newItemStack = null;
        Slot slot = (Slot) inventorySlots.get(slotIndex);
        if (slot != null && slot.getHasStack())
        {
            ItemStack itemStack = slot.getStack();
            newItemStack = itemStack.copy();
            if (slotIndex < TileEmcCapacitor.INVENTORY_SIZE)
            {
                if (!this.mergeItemStack(itemStack,
                        TileEmcCapacitor.INVENTORY_SIZE, inventorySlots.size(),
                        false))
                    return null;
            } else if (!this.mergeItemStack(itemStack, 0,
                    TileEmcCapacitor.INVENTORY_SIZE, false))
                return null;
            if (itemStack.stackSize == 0)
            {
                slot.putStack((ItemStack) null);
            } else
            {
                slot.onSlotChanged();
            }
        }
        return newItemStack;
    }
}
