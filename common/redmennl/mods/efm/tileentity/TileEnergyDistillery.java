package redmennl.mods.efm.tileentity;

import redmennl.mods.efm.EnergyFromMatter;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PerditionCalculator;
import cofh.api.energy.IEnergyHandler;

import com.pahimar.ee3.emc.EmcType;
import com.pahimar.ee3.emc.EmcValue;

import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.Optional.InterfaceList;
import cpw.mods.fml.common.Optional.Method;

@InterfaceList(value = {
        @Interface(iface = "buildcraft.api.power.IPowerReceptor", modid = "BuildCraft|Energy"),
        @Interface(iface = "ic2.api.energy.tile.IEnergySink", modid = "IC2"),
        @Interface(iface = "cofh.api.energy.IEnergyHandler", modid = "CoFHCore") })
public class TileEnergyDistillery extends TileEmc implements IEnergySink,
        IPowerReceptor, IEnergyHandler
{
    public static final int emcMjConversionSize = 250;
    public static final int emcEuConversionSize = 625;
    public static final int emcRfConversionSize = 5000;
    private float distilledEmc = 0F;
    private int spawParticleTime = 0;
    
    private PowerHandler bcPowerhandler;
    
    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if (worldObj.isRemote)
        {
            return;
        }
        spawParticleTime++;
        
        TileEmcCapacitor emcCap = getEmcCapacitor();
        if (spawParticleTime >= 20 && emcCap != null)
        {
            emcCap.spawnEmcPartcle(new EmcValue(distilledEmc, EmcType.KINETIC),
                    this.xCoord, this.yCoord, this.zCoord, true);
            distilledEmc = 0F;
            spawParticleTime = 0;
        }
        
        if (emcCap != null && EnergyFromMatter.hasBC)
        {
            float MJ = getPowerProvider().getEnergyStored();
            if (MJ != 0F)
            {
                if (emcCap.neededEmc(EmcType.KINETIC) * emcMjConversionSize < MJ)
                {
                    MJ = emcCap.neededEmc(EmcType.KINETIC)
                            * emcMjConversionSize;
                }
                if (getPowerProvider().useEnergy(MJ, MJ, true) >= MJ)
                {
                    emcCap.addEmc(new EmcValue(
                            (float) MJ / emcMjConversionSize, EmcType.KINETIC));
                    distilledEmc += MJ / emcMjConversionSize;
                }
            }
            this.getPowerProvider().configure(0.0F, 320.0F, 0.0F,
                    emcCap.neededEmc(EmcType.KINETIC) / emcMjConversionSize);
        }
    }
    
    @Method(modid = "IC2")
    @Override
    public boolean acceptsEnergyFrom(TileEntity tile, ForgeDirection direction)
    {
        return true;
    }
    
    @Method(modid = "IC2")
    @Override
    public double demandedEnergyUnits()
    {
        TileEmcCapacitor emcCap = getEmcCapacitor();
        if (emcCap != null)
        {
            return emcCap.neededEmc(EmcType.KINETIC) * emcEuConversionSize;
        }
        return 0;
    }
    
    @Method(modid = "IC2")
    @Override
    public int getMaxSafeInput()
    {
        return Integer.MAX_VALUE;
    }
    
    @Method(modid = "IC2")
    @Override
    public double injectEnergyUnits(ForgeDirection direction, double eu)
    {
        double returnedEu = eu;
        TileEmcCapacitor emcCap = getEmcCapacitor();
        if (emcCap != null)
        {
            returnedEu = 0;
            if (emcCap.neededEmc(EmcType.KINETIC) * emcEuConversionSize < eu)
            {
                returnedEu = eu - emcCap.neededEmc(EmcType.KINETIC)
                        * emcEuConversionSize;
                eu = emcCap.neededEmc(EmcType.KINETIC) * emcEuConversionSize;
            }
            emcCap.addEmc(new EmcValue((float) eu / emcEuConversionSize,
                    EmcType.KINETIC));
            distilledEmc += eu / emcEuConversionSize;
        }
        
        return returnedEu;
    }
    
    @Method(modid = "BuildCraft|Energy")
    @Override
    public PowerHandler.PowerReceiver getPowerReceiver(ForgeDirection side)
    {
        return getPowerProvider().getPowerReceiver();
    }
    
    @Method(modid = "BuildCraft|Energy")
    public PowerHandler getPowerProvider()
    {
        if (this.bcPowerhandler == null)
        {
            this.bcPowerhandler = new PowerHandler(this,
                    PowerHandler.Type.STORAGE);
            if (this.bcPowerhandler != null)
            {
                this.bcPowerhandler.setPerdition(new PerditionCalculator(0F));
                TileEmcCapacitor emcCap = getEmcCapacitor();
                if (emcCap != null)
                {
                    this.bcPowerhandler.configure(0.0F, 320.0F, 0.0F,
                            getEmcCapacitor().neededEmc(EmcType.KINETIC)
                                    / emcMjConversionSize);
                } else
                {
                    this.bcPowerhandler.configure(0.0F, 320.0F, 0.0F, 0.0F);
                }
            }
        }
        return this.bcPowerhandler;
    }
    
    @Method(modid = "BuildCraft|Energy")
    @Override
    public void doWork(PowerHandler workProvider)
    {
        float MJ = workProvider.getEnergyStored();
        TileEmcCapacitor emcCap = getEmcCapacitor();
        if (emcCap != null)
        {
            if (emcCap.neededEmc(EmcType.KINETIC) * emcMjConversionSize < MJ)
            {
                MJ = emcCap.neededEmc(EmcType.KINETIC) * emcMjConversionSize;
            }
            if (getPowerProvider().useEnergy(MJ, MJ, true) >= MJ)
            {
                emcCap.addEmc(new EmcValue((float) MJ / emcMjConversionSize,
                        EmcType.KINETIC));
                distilledEmc += MJ / emcMjConversionSize;
            }
            
            this.getPowerProvider().configure(0.0F, 320.0F, 0.0F,
                    emcCap.neededEmc(EmcType.KINETIC) / emcMjConversionSize);
        }
    }
    
    @Method(modid = "BuildCraft|Energy")
    @Override
    public World getWorld()
    {
        return this.worldObj;
    }
    
    @Override
    public void validate()
    {
        super.validate();
        if (!worldObj.isRemote && EnergyFromMatter.hasIC2)
        {
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
        }
    }
    
    @Override
    public void invalidate()
    {
        if (!worldObj.isRemote && EnergyFromMatter.hasIC2)
        {
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
        }
        
        super.invalidate();
    }
    
    @Override
    public void onChunkUnload()
    {
        if (!worldObj.isRemote && EnergyFromMatter.hasIC2)
        {
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
        }
        
        super.onChunkUnload();
    }
    
    @Method(modid = "CoFHCore")
    @Override
    public boolean canInterface(ForgeDirection direction)
    {
        return true;
    }
    
    @Method(modid = "CoFHCore")
    @Override
    public int extractEnergy(ForgeDirection direction, int amount,
            boolean simulate)
    {
        return 0;
    }
    
    @Method(modid = "CoFHCore")
    @Override
    public int getEnergyStored(ForgeDirection direction)
    {
        return Integer.MAX_VALUE;
    }
    
    @Method(modid = "CoFHCore")
    @Override
    public int getMaxEnergyStored(ForgeDirection direction)
    {
        return Integer.MAX_VALUE;
    }
    
    @Method(modid = "CoFHCore")
    @Override
    public int receiveEnergy(ForgeDirection direction, int amount,
            boolean simulate)
    {
        TileEmcCapacitor emcCap = getEmcCapacitor();
        if (emcCap != null)
        {
            int recievedRF;
            if (emcCap.neededEmc(EmcType.KINETIC) * emcRfConversionSize < amount)
            {
                recievedRF = (int) emcCap.neededEmc(EmcType.KINETIC)
                        * emcRfConversionSize;
            } else
            {
                recievedRF = amount;
            }
            if (recievedRF != 0)
            {
                if (simulate)
                {
                    return recievedRF;
                }
                if (emcCap.addEmc(new EmcValue((float) recievedRF
                        / emcRfConversionSize, EmcType.KINETIC)))
                {
                    distilledEmc += (float) recievedRF / emcRfConversionSize;
                    return recievedRF;
                }
            }
        }
        return 0;
    }
}
