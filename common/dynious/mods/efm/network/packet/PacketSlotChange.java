package dynious.mods.efm.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.network.INetworkManager;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.Player;
import dynious.mods.efm.network.PacketTypeHandler;

public class PacketSlotChange extends PacketEFM
{
    
    public int x, y, z;
    public int slot;
    public ItemStack stack;
    
    public PacketSlotChange()
    {
        
        super(PacketTypeHandler.SLOT_CHANGE, false);
    }
    
    public PacketSlotChange(int x, int y, int z, int slot, ItemStack stack)
    {
        
        super(PacketTypeHandler.SLOT_CHANGE, false);
        this.x = x;
        this.y = y;
        this.z = z;
        this.slot = slot;
        this.stack = stack;
    }
    
    @Override
    public void writeData(DataOutputStream data) throws IOException
    {
        
        data.writeInt(x);
        data.writeInt(y);
        data.writeInt(z);
        data.writeInt(slot);
        if (stack != null)
        {
            data.writeInt(stack.itemID);
            data.writeInt(stack.stackSize);
            data.writeInt(stack.getItemDamage());
            
            if (stack.hasTagCompound())
            {
                byte[] compressed = CompressedStreamTools.compress(stack
                        .getTagCompound());
                data.writeShort(compressed.length);
                data.write(compressed);
            } else
            {
                data.writeShort(0);
            }
        } else
        {
            data.writeInt(0);
        }
    }
    
    @Override
    public void readData(DataInputStream data) throws IOException
    {
        
        x = data.readInt();
        y = data.readInt();
        z = data.readInt();
        this.slot = data.readInt();
        int id = data.readInt();
        
        if (id != 0)
        {
            stack = new ItemStack(id, data.readInt(), data.readInt());
            
            short length = data.readShort();
            
            if (length > 0)
            {
                byte[] compressed = new byte[length];
                data.readFully(compressed);
                stack.setTagCompound(CompressedStreamTools
                        .decompress(compressed));
            }
            
        } else
        {
            stack = null;
        }
    }
    
    @Override
    public void execute(INetworkManager manager, Player player)
    {
        TileEntity tile = ((EntityPlayer) player).worldObj.getBlockTileEntity(
                x, y, z);
        if (tile != null && tile instanceof IInventory)
        {
            ((IInventory) tile).setInventorySlotContents(slot, stack);
        }
    }
}
