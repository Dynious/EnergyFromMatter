package redmennl.mods.efm.tileentity;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import redmennl.mods.efm.emc.IEmcHolder;
import redmennl.mods.efm.emc.IPortableEmcHolder;
import redmennl.mods.efm.lib.EmcRGBValues;
import redmennl.mods.efm.lib.RGBValue;
import redmennl.mods.efm.network.PacketTypeHandler;
import redmennl.mods.efm.network.packet.PacketEmcValue;
import redmennl.mods.efm.network.packet.PacketSpawnEmcParticle;

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
    
    /**
     * The leader of the group of EMC Capacitors
     */
    private int linkedCapX, linkedCapY, linkedCapZ;
    
    public int linkedCapacitors = 0;
    
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
            if (storedEmc.components[i] + emcValue.components[i] > getCapacitor()
                    .getMaxStoredEmc())
            {
                return false;
            }
        }
        for (int i = 0; i < EmcType.values().length; i++)
        {
            getCapacitor().storedEmc.components[i] += emcValue.components[i];
        }
        getCapacitor().wantUpdate = true;
        return true;
    }
    
    public boolean addEmc(EmcValue emcValue, int x, int y, int z)
    {
        if (addEmc(emcValue))
        {
            spawnEmcPartcle(emcValue, x, y, z, true);
            return true;
        }
        return false;
    }
    
    @Override
    public float neededEmc(EmcType type)
    {
        return getCapacitor().getMaxStoredEmc()
                - getCapacitor().getEmc().components[type.ordinal()];
    }
    
    @Override
    public boolean useEmc(EmcValue emcValue)
    {
        if (!getCapacitor().hasEmc(emcValue))
        {
            return false;
        }
        for (int i = 0; i < EmcType.values().length; i++)
        {
            getCapacitor().storedEmc.components[i] -= emcValue.components[i];
        }
        getCapacitor().wantUpdate = true;
        return true;
    }
    
    public boolean useEmc(EmcValue emcValue, int x, int y, int z)
    {
        if (useEmc(emcValue))
        {
            spawnEmcPartcle(emcValue, x, y, z, false);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean hasEmc(EmcValue emcValue)
    {
        for (int i = 0; i < EmcType.values().length; i++)
        {
            if (getCapacitor().storedEmc.components[i] - emcValue.components[i] < 0)
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
        return getCapacitor().storedEmc;
    }
    
    @Override
    public void setEmc(EmcValue emcValue)
    {
        storedEmc = emcValue;
    }
    
    @Override
    public float getMaxStoredEmc()
    {
        return getCapacitor().maxStoredEmc * getCapacitor().linkedCapacitors;
    }
    
    public float getStoredEmc(EmcType type)
    {
        return getCapacitor().storedEmc.components[type.ordinal()];
    }
    
    public void spawnEmcPartcle(EmcValue emcValue, int x, int y, int z,
            boolean add)
    {
        if (emcValue.getValue() != 0.0F)
        {
            RGBValue rgbvalue = EmcRGBValues.getRGB(emcValue);
            TileEmcCapacitor tile = getCapacitor();
            if (add)
            {
                PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord,
                        64D, worldObj.provider.dimensionId, PacketTypeHandler
                                .populatePacket(new PacketSpawnEmcParticle(
                                        x + 0.5F, y + 0.5F, z + 0.5F,
                                        tile.xCoord + 0.5F, tile.yCoord + 0.5F,
                                        tile.zCoord + 0.5F, 0.1F, emcValue
                                                .getValue(), rgbvalue.colorR,
                                        rgbvalue.colorG, rgbvalue.colorB)));
            } else
            {
                PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord,
                        64D, worldObj.provider.dimensionId, PacketTypeHandler
                                .populatePacket(new PacketSpawnEmcParticle(
                                        tile.xCoord + 0.5F, tile.yCoord + 0.5F,
                                        tile.zCoord + 0.5F, x + 0.5F, y + 0.5F,
                                        z + 0.5F, 0.1F, emcValue.getValue(),
                                        rgbvalue.colorR, rgbvalue.colorG,
                                        rgbvalue.colorB)));
            }
        }
    }
    
    public void addPlayerUsingInv(EntityPlayer player)
    {
        getCapacitor().playersUsingInv.add(player);
        PacketDispatcher.sendPacketToPlayer(PacketTypeHandler
                .populatePacket(new PacketEmcValue(
                        getCapacitor().storedEmc.components,
                        getCapacitor().linkedCapacitors, getCapacitor().xCoord,
                        getCapacitor().yCoord, getCapacitor().zCoord)),
                (Player) player);
    }
    
    public void removePlayerUsingInv(EntityPlayer player)
    {
        getCapacitor().playersUsingInv.remove(player);
    }
    
    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if (this.worldObj.isRemote || this.xCoord != linkedCapX
                || this.yCoord != linkedCapY || this.zCoord != linkedCapZ)
        {
            return;
        }
        
        getCapacitor().ticksSinceUpdate++;
        if (getCapacitor().wantUpdate && getCapacitor().ticksSinceUpdate >= 10)
        {
            for (EntityPlayer player : getCapacitor().playersUsingInv)
            {
                PacketDispatcher.sendPacketToPlayer(PacketTypeHandler
                        .populatePacket(new PacketEmcValue(
                                getCapacitor().storedEmc.components,
                                getCapacitor().linkedCapacitors,
                                getCapacitor().xCoord, getCapacitor().yCoord,
                                getCapacitor().zCoord)), (Player) player);
            }
            getCapacitor().wantUpdate = false;
            getCapacitor().ticksSinceUpdate = 0;
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
    
    public void scanNeighbors()
    {
        for (int i = 0; i < ForgeDirection.values().length; i++)
        {
            ForgeDirection direction = ForgeDirection.getOrientation(i);
            if (scanNeighbor(direction))
                break;
        }
    }
    
    public TileEmcCapacitor getCapacitor()
    {
        TileEntity tile = worldObj.getBlockTileEntity(this.linkedCapX,
                this.linkedCapY, this.linkedCapZ);
        if (tile != null && tile instanceof TileEmcCapacitor)
        {
            return (TileEmcCapacitor) tile;
        }
        return this;
    }
    
    public boolean scanNeighbor(ForgeDirection direction)
    {
        TileEntity tile;
        switch (direction)
        {
            case EAST:
                tile = worldObj.getBlockTileEntity(xCoord + 1, yCoord, zCoord);
                if (tile instanceof TileEmcCapacitor)
                {
                    setLinkedCapacitor(tile);
                    return true;
                }
                break;
            case WEST:
                tile = worldObj.getBlockTileEntity(xCoord - 1, yCoord, zCoord);
                if (tile instanceof TileEmcCapacitor)
                {
                    setLinkedCapacitor(tile);
                    return true;
                }
                break;
            case UP:
                tile = worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord);
                if (tile instanceof TileEmcCapacitor)
                {
                    setLinkedCapacitor(tile);
                    return true;
                }
                break;
            case DOWN:
                tile = worldObj.getBlockTileEntity(xCoord, yCoord - 1, zCoord);
                if (tile instanceof TileEmcCapacitor)
                {
                    setLinkedCapacitor(tile);
                    return true;
                }
                break;
            case SOUTH:
                tile = worldObj.getBlockTileEntity(xCoord, yCoord, zCoord + 1);
                if (tile instanceof TileEmcCapacitor)
                {
                    setLinkedCapacitor(tile);
                    return true;
                }
                break;
            case NORTH:
                tile = worldObj.getBlockTileEntity(xCoord, yCoord, zCoord - 1);
                if (tile instanceof TileEmcCapacitor)
                {
                    setLinkedCapacitor(tile);
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }
    
    public void setLinkedCapacitor(TileEntity tile)
    {
        TileEmcCapacitor tileCap = (TileEmcCapacitor) tile;
        tileCap = tileCap.getCapacitor();
        this.linkedCapX = tileCap.xCoord;
        this.linkedCapY = tileCap.yCoord;
        this.linkedCapZ = tileCap.zCoord;
        tileCap.linkedCapacitors++;
    }
    
    @Override
    public void invalidate()
    {
        TileEntity tile = worldObj.getBlockTileEntity(linkedCapX, linkedCapY,
                linkedCapZ);
        if (tile != null && tile instanceof TileEmcCapacitor)
        {
            ((TileEmcCapacitor) tile).linkedCapacitors--;
        }
        super.invalidate();
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
        
        linkedCapX = nbtTagCompound.getInteger("linkedCapX");
        linkedCapY = nbtTagCompound.getInteger("linkedCapY");
        linkedCapZ = nbtTagCompound.getInteger("linkedCapZ");
        linkedCapacitors = nbtTagCompound.getInteger("linkedCapacitors");
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
        
        nbtTagCompound.setInteger("linkedCapX", linkedCapX);
        nbtTagCompound.setInteger("linkedCapY", linkedCapY);
        nbtTagCompound.setInteger("linkedCapZ", linkedCapZ);
        nbtTagCompound.setInteger("linkedCapacitors", linkedCapacitors);
    }
}
