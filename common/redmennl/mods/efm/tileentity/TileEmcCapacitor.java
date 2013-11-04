package redmennl.mods.efm.tileentity;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import redmennl.mods.efm.emc.IEmcHolder;
import redmennl.mods.efm.emc.IPortableEmcHolder;
import redmennl.mods.efm.network.PacketTypeHandler;
import redmennl.mods.efm.network.packet.PacketEmcValue;

import com.pahimar.ee3.emc.EmcType;
import com.pahimar.ee3.emc.EmcValue;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class TileEmcCapacitor extends TileEntity implements IInventory,
        IEmcHolder
{
    public static final int INVENTORY_SIZE = 2;
    
    /**
     * The EmcValue currently stored in the Capacitor
     */
    private EmcValue storedEmc;
    
    /**
     * Max possible storage per EmcType
     */
    private float maxStoredEmc = 81920F;
    
    private ArrayList<EntityPlayer> playersUsingInv;
    
    private int ticksSinceUpdate;
    
    private boolean wantUpdate = false;
    
    private ItemStack[] inventory;
    
    public TileEmcCapacitor()
    {
        super();
        inventory = new ItemStack[INVENTORY_SIZE];
        playersUsingInv = new ArrayList<EntityPlayer>();
        storedEmc = new EmcValue();
    }
    
    @Override
    public boolean addEmc(EmcValue emcValue)
    {
        for (int i = 0; i < EmcType.values().length; i++)
        {
            if (storedEmc.components[i] + emcValue.components[i] > maxStoredEmc)
            {
                return false;
            }
        }
        for (int i = 0; i < EmcType.values().length; i++)
        {
            storedEmc.components[i] += emcValue.components[i];
        }
        wantUpdate = true;
        return true;
    }
    
    @Override
    public float neededEmc(EmcType type)
    {
        return maxStoredEmc - storedEmc.components[type.ordinal()];
    }
    
    @Override
    public boolean useEmc(EmcValue emcValue)
    {
        if (!hasEmc(emcValue))
        {
            return false;
        }
        for (int i = 0; i < EmcType.values().length; i++)
        {
            storedEmc.components[i] -= emcValue.components[i];
        }
        wantUpdate = true;
        return true;
    }
    
    @Override
    public boolean hasEmc(EmcValue emcValue)
    {
        for (int i = 0; i < EmcType.values().length; i++)
        {
            if (storedEmc.components[i] - emcValue.components[i] < 0)
            {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean storesType(EmcType emcType)
    {
        return true;
    }
    
    @Override
    public EmcValue getEmc()
    {
        return storedEmc;
    }
    
    @Override
    public void setEmc(EmcValue emcValue)
    {
        storedEmc = emcValue;
    }
    
    @Override
    public float getMaxStoredEmc()
    {
        return maxStoredEmc;
    }
    
    public float getStoredEmc(EmcType type)
    {
        return storedEmc.components[type.ordinal()];
    }
    
    public void addPlayerUsingInv(EntityPlayer player)
    {
        playersUsingInv.add(player);
        PacketDispatcher.sendPacketToPlayer(PacketTypeHandler
                .populatePacket(new PacketEmcValue(storedEmc.components,
                        xCoord, yCoord, zCoord)), (Player) player);
    }
    
    public void removePlayerUsingInv(EntityPlayer player)
    {
        playersUsingInv.remove(player);
    }
    
    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if (!worldObj.isRemote)
        {
            ticksSinceUpdate++;
            if (wantUpdate && ticksSinceUpdate >= 10)
            {
                for (EntityPlayer player : playersUsingInv)
                {
                    PacketDispatcher.sendPacketToPlayer(PacketTypeHandler
                            .populatePacket(new PacketEmcValue(
                                    storedEmc.components, xCoord, yCoord,
                                    zCoord)), (Player) player);
                }
                wantUpdate = false;
                ticksSinceUpdate = 0;
            }
        }
        if (getStackInSlot(0) != null
                && getStackInSlot(0).getItem() instanceof IPortableEmcHolder)
        {
            IPortableEmcHolder addingStack = (IPortableEmcHolder) getStackInSlot(
                    0).getItem();
            for (EmcType type : EmcType.values())
            {
                if (addingStack.storesType(type))
                {
                    float addedEmc;
                    if (addingStack.neededEmc(type, getStackInSlot(0)) > 10.0F)
                    {
                        addedEmc = 10.0F;
                    } else
                    {
                        addedEmc = addingStack.neededEmc(type,
                                getStackInSlot(0));
                    }
                    if (addedEmc > getStoredEmc(type))
                    {
                        addedEmc = getStoredEmc(type);
                    }
                    EmcValue emcAdded = new EmcValue(addedEmc, type);
                    if (useEmc(emcAdded))
                    {
                        addingStack.addEmc(emcAdded, getStackInSlot(0));
                    }
                }
            }
        }
        if (getStackInSlot(1) != null
                && getStackInSlot(1).getItem() instanceof IPortableEmcHolder)
        {
            IPortableEmcHolder gettingStack = (IPortableEmcHolder) getStackInSlot(
                    1).getItem();
            for (EmcType type : EmcType.values())
            {
                float addedEmc;
                if (neededEmc(type) > 10.0F)
                {
                    addedEmc = 10.0F;
                } else
                {
                    addedEmc = neededEmc(type);
                }
                if (addedEmc > gettingStack.getStoredEmc(type,
                        getStackInSlot(1)))
                {
                    addedEmc = gettingStack.getStoredEmc(type,
                            getStackInSlot(1));
                }
                EmcValue emcAdded = new EmcValue(addedEmc, type);
                if (gettingStack.useEmc(emcAdded, getStackInSlot(1)))
                {
                    addEmc(emcAdded);
                }
            }
        }
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
    public boolean isItemValidForSlot(int i, ItemStack stack)
    {
        return stack.getItem() instanceof IPortableEmcHolder;
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
            storedEmc.components[i] = nbtTagCompound.getFloat("EMC" + i);
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
            nbtTagCompound.setFloat("EMC" + i, storedEmc.components[i]);
        }
    }
}
