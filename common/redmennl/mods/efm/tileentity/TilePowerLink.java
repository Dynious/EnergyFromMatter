package redmennl.mods.efm.tileentity;

import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;

import com.pahimar.ee3.core.helper.LogHelper;
import com.pahimar.ee3.emc.EmcType;
import com.pahimar.ee3.emc.EmcValue;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.power.IPowerEmitter;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.power.PowerHandler.Type;

public class TilePowerLink extends TileEmc implements IPowerEmitter,
        IEnergyEmitter
{
    public static final int emcMjConversionSize = 250;
    public static final int emcEuConversionSize = 625;
    TileEntity[] tiles;
    
    public TilePowerLink()
    {
        super();
        tiles = new TileEntity[ForgeDirection.values().length];
    }
    
    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if (getEmcCapacitor() != null)
            ;
        {
            for (int i = 0; i < ForgeDirection.values().length; i++)
            {
                TileEntity tile = tiles[i];
                if (tile != null)
                {
                    if (tile instanceof IPowerReceptor)
                    {
                        PowerReceiver reciever = ((IPowerReceptor) tile)
                                .getPowerReceiver(ForgeDirection
                                        .getOrientation(i).getOpposite());
                        if (reciever != null && getEmcCapacitor() != null)
                        {
                            float usedMJ;
                            if (getEmcCapacitor().getStoredEmc(EmcType.KINETIC)
                                    * emcMjConversionSize < reciever
                                        .powerRequest())
                            {
                                usedMJ = getEmcCapacitor().getStoredEmc(
                                        EmcType.KINETIC)
                                        * emcMjConversionSize;
                            } else
                            {
                                usedMJ = reciever.powerRequest();
                            }
                            if (usedMJ != 0.0F)
                            {
                                if (getEmcCapacitor().useEmc(
                                        new EmcValue(usedMJ
                                                / emcMjConversionSize,
                                                EmcType.KINETIC)))
                                {
                                    reciever.receiveEnergy(Type.STORAGE,
                                            usedMJ,
                                            ForgeDirection.getOrientation(i));
                                }
                            }
                            break;
                        }
                    }
                    if (tile instanceof IEnergySink)
                    {
                        IEnergySink sink = (IEnergySink) tile;
                        if (sink != null
                                & sink.acceptsEnergyFrom(this, ForgeDirection
                                        .getOrientation(i).getOpposite())
                                && getEmcCapacitor() != null)
                        {
                            double usedEU;
                            if (getEmcCapacitor().getStoredEmc(EmcType.KINETIC)
                                    * emcEuConversionSize < sink
                                        .demandedEnergyUnits())
                            {
                                usedEU = getEmcCapacitor().getStoredEmc(
                                        EmcType.KINETIC)
                                        * emcEuConversionSize;
                            } else
                            {
                                usedEU = (float) sink.demandedEnergyUnits();
                            }
                            if (usedEU > sink.getMaxSafeInput())
                            {
                                usedEU = sink.getMaxSafeInput();
                            }
                            LogHelper.debug(usedEU);
                            if (usedEU != 0.0F)
                            {
                                if (getEmcCapacitor().useEmc(
                                        new EmcValue((float) usedEU
                                                / emcEuConversionSize,
                                                EmcType.KINETIC)))
                                {
                                    sink.injectEnergyUnits(ForgeDirection
                                            .getOrientation(i).getOpposite(),
                                            usedEU);
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
    
    public void scanNeighbors()
    {
        for (int i = 0; i < ForgeDirection.values().length; i++)
        {
            ForgeDirection direction = ForgeDirection.getOrientation(i);
            scanNeighbor(direction);
        }
    }
    
    public void scanNeighbor(ForgeDirection direction)
    {
        TileEntity tile;
        switch (direction)
        {
            case EAST:
                tile = worldObj.getBlockTileEntity(xCoord + 1, yCoord, zCoord);
                tiles[direction.ordinal()] = tile;
                break;
            case WEST:
                tile = worldObj.getBlockTileEntity(xCoord - 1, yCoord, zCoord);
                tiles[direction.ordinal()] = tile;
                break;
            case UP:
                tile = worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord);
                tiles[direction.ordinal()] = tile;
                break;
            case DOWN:
                tile = worldObj.getBlockTileEntity(xCoord, yCoord - 1, zCoord);
                tiles[direction.ordinal()] = tile;
                break;
            case SOUTH:
                tile = worldObj.getBlockTileEntity(xCoord, yCoord, zCoord + 1);
                tiles[direction.ordinal()] = tile;
                break;
            case NORTH:
                tile = worldObj.getBlockTileEntity(xCoord, yCoord, zCoord - 1);
                tiles[direction.ordinal()] = tile;
                break;
            default:
                break;
        }
    }
    
    @Override
    public boolean canEmitPowerFrom(ForgeDirection side)
    {
        return true;
    }
    
    @Override
    public boolean emitsEnergyTo(TileEntity tile, ForgeDirection direction)
    {
        return tiles[direction.ordinal()] == tile ? true : false;
    }
}
