package redmennl.mods.efm.tileentity;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import com.pahimar.ee3.emc.EmcRegistry;
import com.pahimar.ee3.emc.EmcType;
import com.pahimar.ee3.emc.EmcValue;

public class TileFluidDistillery extends TileEmc implements IFluidHandler
{
    private EmcValue distilledEmc = new EmcValue();
    private int spawParticleTime = 0;
    
    public TileFluidDistillery()
    {
        super();
    }
    
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
            emcCap.spawnEmcPartcle(distilledEmc, this.xCoord, this.yCoord,
                    this.zCoord, true);
            distilledEmc = new EmcValue();
            spawParticleTime = 0;
        }
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
    }
    
    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        if (worldObj.isRemote || resource.getFluid().getBlockID() <= 0)
        {
            return 0;
        }
        
        TileEmcCapacitor emcCap = getEmcCapacitor();
        if (emcCap != null
                && EmcRegistry.hasEmcValue(Block.blocksList[resource.getFluid()
                        .getBlockID()]))
        {
            EmcValue value = EmcRegistry.getEmcValue(Block.blocksList[resource
                    .getFluid().getBlockID()]);
            int maxAmount = resource.amount;
            for (EmcType type : EmcType.values())
            {
                if (emcCap.neededEmc(type) < value.components[type.ordinal()]
                        * maxAmount / 1000)
                {
                    maxAmount = (int) emcCap.neededEmc(type) * 1000;
                }
            }
            if (doFill)
            {
                EmcValue newValue = new EmcValue();
                for (EmcType type : EmcType.values())
                {
                    float emc = value.components[type.ordinal()] / 1000
                            * maxAmount;
                    newValue.components[type.ordinal()] = emc;
                    distilledEmc.components[type.ordinal()] += emc;
                }
                emcCap.addEmc(newValue);
            }
            return maxAmount;
        } else
        {
            return 0;
        }
    }
    
    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
    {
        return null;
    }
    
    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        return true;
    }
    
    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
        return false;
    }
    
    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        return new FluidTankInfo[] { new FluidTankInfo(null, Integer.MAX_VALUE) };
    }
    
    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource,
            boolean doDrain)
    {
        return null;
    }
}
