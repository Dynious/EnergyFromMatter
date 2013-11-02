package redmennl.mods.efm.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import redmennl.mods.efm.tileentity.TileCropRipener;

public class ContainerCropRipener extends Container
{
    TileCropRipener tile;
    
    public ContainerCropRipener(InventoryPlayer inventoryPlayer,
            TileCropRipener tile)
    {
        this.tile = tile;
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
        return null;
    }
}
