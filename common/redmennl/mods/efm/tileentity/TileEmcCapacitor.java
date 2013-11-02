package redmennl.mods.efm.tileentity;

import redmennl.mods.efm.network.PacketTypeHandler;
import redmennl.mods.efm.network.packet.PacketEmcValue;

import com.pahimar.ee3.emc.EmcRegistry;
import com.pahimar.ee3.emc.EmcType;
import com.pahimar.ee3.emc.EmcValue;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEmcCapacitor extends TileEntity implements IInventory
{
    public static final int INVENTORY_SIZE = 2;
    
    /**
     * Stored EMC
     */
    public float[] storedEmc;
    
    /**
     * Max possible storage per EmcType
     */
    public float maxStoredEmc = 81920F;
    
    private ItemStack[] inventory;
    
    public TileEmcCapacitor()
    {
        super();
        storedEmc = new float[EmcType.values().length];
        inventory = new ItemStack[INVENTORY_SIZE];
    }
    
    public boolean addEmc(EmcValue emcValue)
    {
        for (int i = 0; i < EmcType.values().length; i++)
        {
            if (storedEmc[i] + emcValue.components[i] > maxStoredEmc)
            {
                return false;
            }
        }
        for (int i = 0; i < EmcType.values().length; i++)
        {
            storedEmc[i] += emcValue.components[i];
        }
        PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 15D,
                worldObj.provider.dimensionId, PacketTypeHandler
                        .populatePacket(new PacketEmcValue(storedEmc, xCoord,
                                yCoord, zCoord)));
        return true;
    }
    
    public boolean useEmc(EmcValue emcValue)
    {
        if (!hasEmc(emcValue))
        {
            return false;
        }
        for (int i = 0; i < EmcType.values().length; i++)
        {
            storedEmc[i] -= emcValue.components[i];
        }
        PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 15D,
                worldObj.provider.dimensionId, PacketTypeHandler
                        .populatePacket(new PacketEmcValue(storedEmc, xCoord,
                                yCoord, zCoord)));
        return true;
    }
    
    public boolean hasEmc(EmcValue emcValue)
    {
        for (int i = 0; i < EmcType.values().length; i++)
        {
            if (storedEmc[i] - emcValue.components[i] < 0)
            {
                return false;
            }
        }
        return true;
    }
    
    public float getStoredEmc(EmcType type)
    {
        return storedEmc[type.ordinal()];
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
        for (int i = 0; i < EmcType.values().length; i++)
        {
            storedEmc[i] = nbtTagCompound.getFloat("EMC" + i);
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
        for (int i = 0; i < EmcType.values().length; i++)
        {
            nbtTagCompound.setFloat("EMC" + i, storedEmc[i]);
        }
    }
}
