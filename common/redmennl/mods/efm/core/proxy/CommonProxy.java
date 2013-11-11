package redmennl.mods.efm.core.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import redmennl.mods.efm.EnergyFromMatter;
import redmennl.mods.efm.client.gui.inventory.GuiMatterCreator;
import redmennl.mods.efm.client.gui.inventory.GuiCropRipener;
import redmennl.mods.efm.client.gui.inventory.GuiEmcCapacitor;
import redmennl.mods.efm.client.gui.inventory.GuiMatterDistillery;
import redmennl.mods.efm.client.gui.inventory.GuiPortableHouse;
import redmennl.mods.efm.inventory.ContainerMatterCreator;
import redmennl.mods.efm.inventory.ContainerCropRipener;
import redmennl.mods.efm.inventory.ContainerEmcCapacitor;
import redmennl.mods.efm.inventory.ContainerMatterDistillery;
import redmennl.mods.efm.lib.GuiIds;
import redmennl.mods.efm.lib.Strings;
import redmennl.mods.efm.tileentity.TileEnergyDistillery;
import redmennl.mods.efm.tileentity.TileMatterCreator;
import redmennl.mods.efm.tileentity.TileCropRipener;
import redmennl.mods.efm.tileentity.TileEmcCapacitor;
import redmennl.mods.efm.tileentity.TileFluidCreator;
import redmennl.mods.efm.tileentity.TileFluidDistillery;
import redmennl.mods.efm.tileentity.TileMatterDistillery;
import redmennl.mods.efm.tileentity.TileMatterSuperheater;
import redmennl.mods.efm.tileentity.TilePortableHouse;
import redmennl.mods.efm.tileentity.TilePortableHouseDeployer;
import redmennl.mods.efm.tileentity.TileEnergyCreator;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy implements IGuiHandler
{
    public void initRenderingAndTextures()
    {
    }
    
    public void initTileEntities()
    {
        GameRegistry.registerTileEntity(TileMatterDistillery.class,
                Strings.MATTER_DISTILLERY_NAME);
        GameRegistry.registerTileEntity(TileEmcCapacitor.class,
                Strings.EMC_CAPACITOR_NAME);
        GameRegistry.registerTileEntity(TileCropRipener.class,
                Strings.CROP_RIPENER_NAME);
        GameRegistry.registerTileEntity(TileMatterCreator.class,
                Strings.MATTER_CREATOR_NAME);
        GameRegistry.registerTileEntity(TilePortableHouse.class,
                Strings.PORTABLE_HOUSE_NAME);
        GameRegistry.registerTileEntity(TilePortableHouseDeployer.class,
                Strings.PORTABLE_HOUSE_NAME + "Deployer");
        GameRegistry.registerTileEntity(TileFluidDistillery.class,
                Strings.FLUID_DISTILLERY_NAME);
        GameRegistry.registerTileEntity(TileFluidCreator.class,
                Strings.FLUID_CREATOR_NAME);
        GameRegistry.registerTileEntity(TileMatterSuperheater.class,
                Strings.MATTER_SUPERHEATER_NAME);
        
        if (EnergyFromMatter.hasBC || EnergyFromMatter.hasIC2)
        {
            GameRegistry.registerTileEntity(TileEnergyCreator.class,
                    Strings.ENERGY_CREATOR_NAME);
            GameRegistry.registerTileEntity(TileEnergyDistillery.class,
                    Strings.ENERGY_DISTILLERY_NAME);
        }
    }
    
    public void initEntities()
    {
        /*
         * EntityRegistry.registerModEntity(EntityCompanion.class, "companion",
         * 0, MiscTools.instance, 80, 1, true);
         */
    }
    
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world,
            int x, int y, int z)
    {
        if (ID == GuiIds.MATTER_DISTILLERY)
        {
            TileMatterDistillery tile = (TileMatterDistillery) world
                    .getBlockTileEntity(x, y, z);
            return new ContainerMatterDistillery(player.inventory, tile);
        } else if (ID == GuiIds.EMC_CAPACITOR)
        {
            TileEmcCapacitor tile = (TileEmcCapacitor) world
                    .getBlockTileEntity(x, y, z);
            return new ContainerEmcCapacitor(player.inventory, tile);
        } else if (ID == GuiIds.CROP_RIPENER)
        {
            TileCropRipener tile = (TileCropRipener) world.getBlockTileEntity(
                    x, y, z);
            return new ContainerCropRipener(player.inventory, tile);
        } else if (ID == GuiIds.MATTER_CREATOR)
        {
            TileMatterCreator tile = (TileMatterCreator) world
                    .getBlockTileEntity(x, y, z);
            return new ContainerMatterCreator(player.inventory, tile);
        }
        return null;
    }
    
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world,
            int x, int y, int z)
    {
        if (ID == GuiIds.MATTER_DISTILLERY)
        {
            TileMatterDistillery tile = (TileMatterDistillery) world
                    .getBlockTileEntity(x, y, z);
            return new GuiMatterDistillery(player.inventory, tile);
        } else if (ID == GuiIds.EMC_CAPACITOR)
        {
            TileEmcCapacitor tile = (TileEmcCapacitor) world
                    .getBlockTileEntity(x, y, z);
            return new GuiEmcCapacitor(player.inventory, tile);
        } else if (ID == GuiIds.CROP_RIPENER)
        {
            TileCropRipener tile = (TileCropRipener) world.getBlockTileEntity(
                    x, y, z);
            return new GuiCropRipener(player.inventory, tile);
        } else if (ID == GuiIds.MATTER_CREATOR)
        {
            TileMatterCreator tile = (TileMatterCreator) world
                    .getBlockTileEntity(x, y, z);
            return new GuiMatterCreator(player.inventory, tile);
        } else if (ID == GuiIds.PORTABLE_HOUSE)
        {
            TileEntity tile = world.getBlockTileEntity(x, y, z);
            return new GuiPortableHouse(tile);
        }
        return null;
    }
    
    public void registerSoundHandler()
    {
    }
    
    public void findModels()
    {
    }
    
    public void registerKeyBindingHandler()
    {
    }
}
