package redmennl.mods.efm.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import redmennl.mods.efm.inventory.slot.SlotPhantom;
import redmennl.mods.efm.tileentity.TileCondenser;

public class ContainerCondenser extends ContainerPhantom
{
    private final int CHEST_INVENTORY_ROWS = 4;
    private final int CHEST_INVENTORY_COLUMNS = 13;
    private final int PLAYER_INVENTORY_ROWS = 3;
    private final int PLAYER_INVENTORY_COLUMNS = 9;
    
    public ContainerCondenser(InventoryPlayer inventoryPlayer,
            TileCondenser tileCondenser)
    {
        // Add the Condenser slot to the container
        this.addSlotToContainer(new SlotPhantom(tileCondenser, 0, 8, 7));
        for (int chestRowIndex = 0; chestRowIndex < CHEST_INVENTORY_ROWS; ++chestRowIndex)
        {
            for (int chestColumnIndex = 0; chestColumnIndex < CHEST_INVENTORY_COLUMNS; ++chestColumnIndex)
            {
                this.addSlotToContainer(new Slot(tileCondenser, 1
                        + chestColumnIndex + chestRowIndex * 13,
                        8 + chestColumnIndex * 18, 27 + chestRowIndex * 18));
            }
        }
        // Add the player's inventory slots to the container
        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex)
        {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer,
                        inventoryColumnIndex + inventoryRowIndex * 9 + 9,
                        44 + inventoryColumnIndex * 18,
                        113 + inventoryRowIndex * 18));
            }
        }
        // Add the player's action bar slots to the container
        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex)
        {
            this.addSlotToContainer(new Slot(inventoryPlayer,
                    actionBarSlotIndex, 44 + actionBarSlotIndex * 18, 171));
        }
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer var1)
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
            if (slotIndex < 1 + CHEST_INVENTORY_ROWS * CHEST_INVENTORY_COLUMNS)
            {
                if (!this
                        .mergeItemStack(itemStack, 1 + CHEST_INVENTORY_ROWS
                                * CHEST_INVENTORY_COLUMNS,
                                inventorySlots.size(), false))
                    return null;
            } else
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
