package redmennl.mods.efm.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.pahimar.ee3.emc.EmcRegistry;
import com.pahimar.ee3.emc.EmcValue;

public class TileMatterDistillery extends TileEmc implements IInventory,
        ISidedInventory
{
    public static final int INVENTORY_SIZE = 1;
    
    /**
     * Time in ticks to finish distillation
     */
    
    private int timePerDistillation;
    
    /**
     * Time worked
     */
    private int workTime;
    
    private ItemStack[] inventory;
    
    public TileMatterDistillery()
    {
        super();
        inventory = new ItemStack[INVENTORY_SIZE];
        timePerDistillation = 20;
    }
    
    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if (getEmcCapacitor() != null && getStackInSlot(0) != null)
        {
            if (workTime < timePerDistillation)
            {
                workTime++;
            } else if (workTime >= timePerDistillation)
            {
                EmcValue emcValue = EmcRegistry.getEmcValue(getStackInSlot(0),
                        false);
                if (emcValue != null && getEmcCapacitor().addEmc(emcValue))
                {
                    workTime = 0;
                    decrStackSize(0, 1);
                }
            }
        } else if (workTime != 0)
        {
            workTime = 0;
        }
    }
    
    /**
     * Gets the amount of work finished
     * 
     * @return Fraction of time to finish working
     */
    public float getWork()
    {
        return (float) workTime / (float) timePerDistillation;
    }
    
    @Override
    public int getSizeInventory()
    {
        return inventory.length;
    }
    
    @Override
    public ItemStack getStackInSlot(int slotIndex)
    {
        return inventory[slotIndex];
    }
    
    @Override
    public ItemStack decrStackSize(int slotIndex, int decrementAmount)
    {
        ItemStack itemStack = getStackInSlot(slotIndex);
        if (itemStack != null)
        {
            if (itemStack.stackSize <= decrementAmount)
            {
                setInventorySlotContents(slotIndex, null);
            } else
            {
                itemStack = itemStack.splitStack(decrementAmount);
                if (itemStack.stackSize == 0)
                {
                    setInventorySlotContents(slotIndex, null);
                }
            }
        }
        return itemStack;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(int slotIndex)
    {
        if (inventory[slotIndex] != null)
        {
            ItemStack itemStack = inventory[slotIndex];
            inventory[slotIndex] = null;
            return itemStack;
        } else
            return null;
    }
    
    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack itemStack)
    {
        inventory[slotIndex] = itemStack;
        if (itemStack != null
                && itemStack.stackSize > this.getInventoryStackLimit())
        {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
        this.onInventoryChanged();
    }
    
    @Override
    public String getInvName()
    {
        return "container.matterDistillery";
    }
    
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }
    
    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        return true;
    }
    
    @Override
    public void openChest()
    {
    }
    
    @Override
    public void closeChest()
    {
    }
    
    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        return EmcRegistry.hasEmcValue(itemstack) ? true : false;
    }
    
    @Override
    public boolean isInvNameLocalized()
    {
        return false;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        // Read in the ItemStacks in the inventory from NBT
        NBTTagList tagList = nbtTagCompound.getTagList("Items");
        inventory = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < tagList.tagCount(); ++i)
        {
            NBTTagCompound tagCompound = (NBTTagCompound) tagList.tagAt(i);
            byte slotIndex = tagCompound.getByte("Slot");
            if (slotIndex >= 0 && slotIndex < inventory.length)
            {
                inventory[slotIndex] = ItemStack
                        .loadItemStackFromNBT(tagCompound);
            }
        }
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        // Write the ItemStacks in the inventory to NBT
        NBTTagList tagList = new NBTTagList();
        for (int currentIndex = 0; currentIndex < inventory.length; ++currentIndex)
        {
            if (inventory[currentIndex] != null)
            {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) currentIndex);
                inventory[currentIndex].writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }
        nbtTagCompound.setTag("Items", tagList);
    }
    
    @Override
    public int[] getAccessibleSlotsFromSide(int var1)
    {
        int[] arr = { 0 };
        return arr;
    }
    
    @Override
    public boolean canInsertItem(int i, ItemStack itemstack, int j)
    {
        return EmcRegistry.hasEmcValue(itemstack) ? true : false;
    }
    
    @Override
    public boolean canExtractItem(int i, ItemStack itemstack, int j)
    {
        return true;
    }
}
