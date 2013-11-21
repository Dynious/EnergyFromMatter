package redmennl.mods.efm.tileentity;

import net.minecraft.client.Minecraft;
import redmennl.mods.efm.client.particle.EntityEmcCloudFX;
import redmennl.mods.efm.lib.EmcRGBValues;
import redmennl.mods.efm.lib.Toggles;
import redmennl.mods.efm.network.PacketTypeHandler;
import redmennl.mods.efm.network.packet.PacketSetEmcCloudSize;

import com.pahimar.ee3.emc.EmcType;
import com.pahimar.ee3.emc.EmcValue;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileMatterCondenser extends TileEmc
{
    public boolean createBlackHole = true;
    
    private float storedEmc = 0;
    private float neededEmc = 100000;
    
    private float emcThisSecond = 0;
    private int spawParticleTime = 0;
    
    @SideOnly(Side.CLIENT)
    private EntityEmcCloudFX emcCloud = null;
    
    @Override
    public void updateEntity()
    {
        super.updateEntity();
        
        if (createBlackHole)
        {
            if (worldObj.isRemote && emcCloud == null && Toggles.SHOW_EMC_PARTICLES)
            {
                emcCloud = new EntityEmcCloudFX(this.worldObj, this.xCoord + 0.5F, this.yCoord + 0.5F, this.zCoord + 0.5F, 0, EmcRGBValues.corporealR, EmcRGBValues.corporealG, EmcRGBValues.corporealB);
                Minecraft.getMinecraft().effectRenderer.addEffect(emcCloud);
            }
            if (!worldObj.isRemote)
            {
                TileEmcCapacitor emcCap = getEmcCapacitor();
                if (emcCap != null)
                {
                    spawParticleTime++;
                    
                    if (spawParticleTime >= 20)
                    {
                        emcCap.spawnEmcPartcle(new EmcValue(emcThisSecond, EmcType.CORPOREAL), this.xCoord, this.yCoord,
                                this.zCoord, false);
                        emcThisSecond = 0;
                        spawParticleTime = 0;
                    }
                    else if (spawParticleTime == 10)
                    {
                        PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord,
                                64D, worldObj.provider.dimensionId, PacketTypeHandler
                                        .populatePacket(new PacketSetEmcCloudSize(xCoord, yCoord, zCoord, storedEmc)));
                    }
                    
                    float usedEMC = 100;
                    if (emcCap.getStoredEmc(EmcType.CORPOREAL) < usedEMC)
                    {
                        usedEMC = emcCap.getStoredEmc(EmcType.CORPOREAL);
                    }
                    if (storedEmc + usedEMC > neededEmc)
                    {
                        usedEMC = neededEmc - storedEmc;
                    }
                    if (usedEMC == 0.0F)
                    {
                        return;
                    }
                    if (emcCap.useEmc(new EmcValue(usedEMC, EmcType.CORPOREAL)))
                    {
                        storedEmc += usedEMC;
                        emcThisSecond += usedEMC;
                    }
                    if (storedEmc >= neededEmc)
                    {
                        spawnBlackHole();
                        createBlackHole = false;
                        storedEmc = 0;
                    }
                }
            }
        }
    }
    
    private void spawnBlackHole()
    {
        
    }
    
    @SideOnly(Side.CLIENT)
    public void setCloudSize(float size)
    {
        if (emcCloud != null)
            emcCloud.setSize(size);
    }
    
}
