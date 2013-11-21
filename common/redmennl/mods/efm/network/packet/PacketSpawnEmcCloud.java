package redmennl.mods.efm.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import redmennl.mods.efm.client.particle.EntityEmcCloudFX;
import redmennl.mods.efm.lib.Toggles;
import redmennl.mods.efm.network.PacketTypeHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PacketSpawnEmcCloud extends PacketEFM
{
    private float startPosX, startPosY, startPosZ;
    private float scale;
    private int colorR, colorG, colorB;
    
    public PacketSpawnEmcCloud()
    {
        super(PacketTypeHandler.SPAWN_EMC_PARTICLE, false);
    }
    
    public PacketSpawnEmcCloud(float startPosX, float startPosY,
            float startPosZ, float scale, int colorR, int colorG,
            int colorB)
    {
        super(PacketTypeHandler.SPAWN_EMC_PARTICLE, false);
        this.startPosX = startPosX;
        this.startPosY = startPosY;
        this.startPosZ = startPosZ;
        
        this.scale = scale;
        
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }
    
    @Override
    public void writeData(DataOutputStream data) throws IOException
    {
        data.writeFloat(startPosX);
        data.writeFloat(startPosY);
        data.writeFloat(startPosZ);
        
        data.writeFloat(scale);
        
        data.write(colorR);
        data.write(colorG);
        data.write(colorB);
    }
    
    @Override
    public void readData(DataInputStream data) throws IOException
    {
        this.startPosX = data.readFloat();
        this.startPosY = data.readFloat();
        this.startPosZ = data.readFloat();
        
        this.scale = data.readFloat();
        
        this.colorR = data.read();
        this.colorG = data.read();
        this.colorB = data.read();
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void execute(INetworkManager manager, Player player)
    {
        if (Toggles.SHOW_EMC_PARTICLES)
        {
            EntityEmcCloudFX beam = new EntityEmcCloudFX(((EntityPlayer) player).worldObj, startPosX, startPosY, startPosZ, scale, colorR, colorG, colorB);
            Minecraft.getMinecraft().effectRenderer.addEffect(beam);
        }
    }
}
