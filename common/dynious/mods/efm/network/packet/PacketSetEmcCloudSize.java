package dynious.mods.efm.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dynious.mods.efm.network.PacketTypeHandler;
import dynious.mods.efm.tileentity.TileMatterCondenser;

public class PacketSetEmcCloudSize extends PacketEFM
{
    private int x, y, z;
    private float scale;
    
    public PacketSetEmcCloudSize()
    {
        super(PacketTypeHandler.SET_EMC_CLOUD_SIZE, false);
    }
    
    public PacketSetEmcCloudSize(int x, int y,
            int z, float scale)
    {
        super(PacketTypeHandler.SET_EMC_CLOUD_SIZE, false);
        this.x = x;
        this.y = y;
        this.z = z;
        
        this.scale = scale;
    }
    
    @Override
    public void writeData(DataOutputStream data) throws IOException
    {
        data.writeInt(x);
        data.writeInt(y);
        data.writeInt(z);
        
        data.writeFloat(scale);
    }
    
    @Override
    public void readData(DataInputStream data) throws IOException
    {
        this.x = data.readInt();
        this.y = data.readInt();
        this.z = data.readInt();
        
        this.scale = data.readFloat();
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void execute(INetworkManager manager, Player player)
    {
        TileMatterCondenser tile = (TileMatterCondenser)((EntityPlayer) player).worldObj.getBlockTileEntity(
                x, y, z);
        if (tile != null)
        {
            tile.setCloudSize(scale);
        }
    }
}
