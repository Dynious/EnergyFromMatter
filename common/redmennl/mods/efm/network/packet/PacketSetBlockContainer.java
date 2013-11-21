package redmennl.mods.efm.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.pahimar.ee3.core.helper.LogHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.tileentity.TileEntity;
import redmennl.mods.efm.network.PacketTypeHandler;
import cpw.mods.fml.common.network.Player;

public class PacketSetBlockContainer extends PacketEFM
{
    
    public int x, y, z;
    public int id, meta;
    public NBTTagCompound tag;
    
    public PacketSetBlockContainer()
    {
        
        super(PacketTypeHandler.SET_BLOCKCONTAINER, false);
    }
    
    public PacketSetBlockContainer(int x, int y, int z, int id, int meta, NBTTagCompound tag)
    {
        
        super(PacketTypeHandler.SET_BLOCKCONTAINER, false);
        this.x = x;
        this.y = y;
        this.z = z;
        this.id = id;
        this.meta = meta;
        this.tag = tag;
    }
    
    @Override
    public void writeData(DataOutputStream data) throws IOException
    {
        
        data.writeInt(x);
        data.writeInt(y);
        data.writeInt(z);
        data.writeInt(id);
        data.writeInt(meta);
        NBTTagCompound.writeNamedTag(tag, data);
        LogHelper.debug(tag.toString());
    }
    
    @Override
    public void readData(DataInputStream data) throws IOException
    {
        
        x = data.readInt();
        y = data.readInt();
        z = data.readInt();
        id = data.readInt();
        meta = data.readInt();
        tag = (NBTTagCompound)NBTTagCompound.readNamedTag(data);
        //LogHelper.debug(tag.toString());
    }
    
    @Override
    public void execute(INetworkManager manager, Player player)
    {
        ((EntityPlayer) player).worldObj.setBlock(x, y, z, id, meta, 2);
        TileEntity tile = ((EntityPlayer) player).worldObj.getBlockTileEntity(
                x, y, z);
        LogHelper.debug(tile.toString());
        if (tile != null)
        {
            tile.readFromNBT(tag);
            tile.worldObj.markBlockForUpdate(x, y, z);
            NBTTagCompound tag2 = new NBTTagCompound();
            tile.writeToNBT(tag2);
            LogHelper.debug(tag2.toString());
        }
    }
}
