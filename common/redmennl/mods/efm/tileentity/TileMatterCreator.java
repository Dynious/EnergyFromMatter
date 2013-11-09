package redmennl.mods.efm.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.pahimar.ee3.emc.EmcRegistry;

public class TileMatterCreator extends TileEmc implements IInventory,
        ISidedInventory
{
    /**
     * Time in ticks to finish one ripening
     */
    private int timePerCondensation;
    /**
     * Time worked
     */
    private int workTime;
    public static final int INVENTORY_SIZE = (13 * 4) + 1;
    /**
     * The ItemStacks that hold the items in the Condenser
     */
    private ItemStack[] inventory;
    
    public TileMatterCreator()
    {
        super();
        inventory = new ItemStack[INVENTORY_SIZE];
        timePerCondensation = 10;
    }
    
    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if (worldObj.isRemote)
        {
            return;
        }
        TileEmcCapacitor emcCap = getEmcCapacitor();
        if (emcCap != null)
        {
            if (workTime < timePerCondensation)
            {
                workTime++;
            } else if (workTime >= timePerCondensation)
            {
                if (getStackInSlot(0) != null
                        && EmcRegistry.hasEmcValue(getStackInSlot(0))
                        && EmcRegistry.getEmcValue(getStackInSlot(0))
                                .getValue() != 0F
                        // TODO delete this if Pahimar added it to list
                        && !getStackInSlot(0).getUnlocalizedName().contains(
                                "ore"))
                {
                    if (emcCap.hasEmc(EmcRegistry
                            .getEmcValue(getStackInSlot(0))))
                    {
                        addItemsFromEMC(emcCap);
                    }
                }
                workTime = 0;
            }
        }
    }
    
    private void addItemsFromEMC(TileEmcCapacitor emcCap)
    {
        for (int i = 1; i < INVENTORY_SIZE; i++)
        {
            ItemStack stack = getStackInSlot(i);
            if (stack != null && stack.getItem() == getStackInSlot(0).getItem()
                    && stack.stackSize != stack.getMaxStackSize())
            {
                if (emcCap.useEmc(EmcRegistry.getEmcValue(getStackInSlot(0)),
                        xCoord, yCoord, zCoord))
                {
                    getStackInSlot(i).stackSize++;
                    return;
                }
            }
        }
        for (int i = 1; i < INVENTORY_SIZE; i++)
        {
            ItemStack stack = getStackInSlot(i);
            if (stack == null)
            {
                if (emcCap.useEmc(EmcRegistry.getEmcValue(getStackInSlot(0)),
                        xCoord, yCoord, zCoord))
                {
                    ItemStack newStack = getStackInSlot(0).copy();
                    setInventorySlotContents(i, newStack);
                    return;
                }
            }
        }
    }
    
    /**
     * Gets the amount of work finished
     * 
     * @return Fraction of time to finish working
     */
    public float getWork()
    {
        return workTime == 0 ? 0F : (float) workTime
                / (float) timePerCondensation;
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
        return "container.condenser";
    }
    
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
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
    public boolean isInvNameLocalized()
    {
        return false;
    }
    
    @Override
    public boolean isItemValidForSlot(int slotIndex, ItemStack itemStack)
    {
        return EmcRegistry.hasEmcValue(itemStack);
    }
    
    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        return true;
    }
    
    @Override
    public int[] getAccessibleSlotsFromSide(int var1)
    {
        int[] slots;
        switch (var1)
        {
            case 1:
                slots = new int[1];
                slots[0] = 0;
            default:
                slots = new int[INVENTORY_SIZE - 1];
                for (int i = 1; i < INVENTORY_SIZE; i++)
                {
                    slots[i - 1] = i;
                }
        }
        return slots;
    }
    
    @Override
    public boolean canInsertItem(int i, ItemStack itemstack, int j)
    {
        if (i == 0)
        {
            return EmcRegistry.hasEmcValue(itemstack);
        }
        return true;
    }
    
    @Override
    public boolean canExtractItem(int i, ItemStack itemstack, int j)
    {
        return true;
    }
}
