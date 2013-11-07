package redmennl.mods.efm.tileentity;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import com.pahimar.ee3.emc.EmcRegistry;
import com.pahimar.ee3.emc.EmcType;
import com.pahimar.ee3.emc.EmcValue;

public class TileFluidCondenser extends TileEmc implements IFluidHandler
{
    public int fluidID;
    
    public TileFluidCondenser()
    {
        super();
        fluidID = FluidRegistry.getFluidID("water");
    }
    
    @Override
    public void updateEntity()
    {
        super.updateEntity();
        
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        fluidID = nbtTagCompound.getInteger("fluidID");
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("fluidID", fluidID);
    }
    
    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        return 0;
    }
    
    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
    {
        if (worldObj.isRemote)
        {
            return null;
        }
        TileEmcCapacitor emcCap = getEmcCapacitor();
        if (emcCap != null
                && EmcRegistry.hasEmcValue(Block.blocksList[FluidRegistry
                        .getFluid(fluidID).getBlockID()]))
        {
            EmcValue value = EmcRegistry
                    .getEmcValue(Block.blocksList[FluidRegistry.getFluid(
                            fluidID).getBlockID()]);
            int maxAmount = maxDrain;
            for (EmcType type : EmcType.values())
            {
                if (emcCap.getEmc().components[type.ordinal()] < value.components[type
                        .ordinal()] * maxAmount / 1000)
                {
                    maxAmount = (int) emcCap.getEmc().components[type.ordinal()] * 1000;
                }
            }
            if (doDrain && maxAmount != 0)
            {
                EmcValue newValue = new EmcValue();
                for (EmcType type : EmcType.values())
                {
                    newValue.components[type.ordinal()] = value.components[type
                            .ordinal()] / 1000 * maxAmount;
                }
                emcCap.useEmc(newValue, xCoord, yCoord, zCoord);
            }
            return new FluidStack(FluidRegistry.getFluid(fluidID), maxAmount);
        } else
        {
            return null;
        }
    }
    
    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        return false;
    }
    
    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
        return true;
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
        if (worldObj.isRemote)
        {
            return null;
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
                if (emcCap.getEmc().components[type.ordinal()] < value.components[type
                        .ordinal()] * maxAmount / 1000)
                {
                    maxAmount = (int) emcCap.getEmc().components[type.ordinal()] * 1000;
                }
            }
            if (doDrain && maxAmount != 0)
            {
                EmcValue newValue = new EmcValue();
                for (EmcType type : EmcType.values())
                {
                    newValue.components[type.ordinal()] = value.components[type
                            .ordinal()] / 1000 * maxAmount;
                }
                emcCap.useEmc(newValue, xCoord, yCoord, zCoord);
            }
            return new FluidStack(resource.getFluid(), maxAmount);
        } else
        {
            return null;
        }
    }
}
