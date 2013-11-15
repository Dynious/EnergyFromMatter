package redmennl.mods.efm.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import redmennl.mods.efm.client.particle.EntityBeamFX;
import redmennl.mods.efm.lib.Toggles;
import redmennl.mods.efm.network.PacketTypeHandler;

public class PacketSpawnEmcParticle extends PacketEFM
{
    private float startPosX, startPosY, startPosZ;
    private float targetPosX, targetPosY, targetPosZ;
    private float speed;
    private float scale;
    private int colorR, colorG, colorB;
    
    public PacketSpawnEmcParticle()
    {
        super(PacketTypeHandler.SPAWN_EMC_PARTICLE, false);
    }
    
    public PacketSpawnEmcParticle(float startPosX, float startPosY,
            float startPosZ, float targetPosX, float targetPosY,
            float targetPosZ, float speed, float scale, int colorR, int colorG,
            int colorB)
    {
        super(PacketTypeHandler.SPAWN_EMC_PARTICLE, false);
        this.startPosX = startPosX;
        this.startPosY = startPosY;
        this.startPosZ = startPosZ;
        
        this.targetPosX = targetPosX;
        this.targetPosY = targetPosY;
        this.targetPosZ = targetPosZ;
        
        this.speed = speed;
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
        
        data.writeFloat(targetPosX);
        data.writeFloat(targetPosY);
        data.writeFloat(targetPosZ);
        
        data.writeFloat(speed);
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
        
        this.targetPosX = data.readFloat();
        this.targetPosY = data.readFloat();
        this.targetPosZ = data.readFloat();
        
        this.speed = data.readFloat();
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
            EntityBeamFX beam = new EntityBeamFX(
                    ((EntityPlayer) player).worldObj, startPosX, startPosY,
                    startPosZ, targetPosX, targetPosY, targetPosZ, speed,
                    scale, colorR, colorG, colorB);
            Minecraft.getMinecraft().effectRenderer.addEffect(beam);
        }
    }
}
