package dynious.mods.efm.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.pahimar.ee3.core.helper.LogHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.Player;
import dynious.mods.efm.network.PacketTypeHandler;

public class PacketSetTileNBT extends PacketEFM
{
    
    public int x, y, z;
    public NBTTagCompound tag;
    
    public PacketSetTileNBT()
    {
        
        super(PacketTypeHandler.SET_TILE_NBT, false);
    }
    
    public PacketSetTileNBT(int x, int y, int z, NBTTagCompound tag)
    {
        
        super(PacketTypeHandler.SET_TILE_NBT, false);
        this.x = x;
        this.y = y;
        this.z = z;
        this.tag = tag;
    }
    
    @Override
    public void writeData(DataOutputStream data) throws IOException
    {
        
        data.writeInt(x);
        data.writeInt(y);
        data.writeInt(z);
        NBTTagCompound.writeNamedTag(tag, data);
        LogHelper.debug(tag.toString());
    }
    
    @Override
    public void readData(DataInputStream data) throws IOException
    {
        
        x = data.readInt();
        y = data.readInt();
        z = data.readInt();
        tag = (NBTTagCompound)NBTTagCompound.readNamedTag(data);
        //LogHelper.debug(tag.toString());
    }
    
    @Override
    public void execute(INetworkManager manager, Player player)
    {
        TileEntity tile = ((EntityPlayer) player).worldObj.getBlockTileEntity(
                x, y, z);
        if (tile != null)
        {
            tile.readFromNBT(tag);
            tile.worldObj.markBlockForUpdate(x, y, z);
        }
    }
}
