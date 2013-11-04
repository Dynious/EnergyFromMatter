package redmennl.mods.efm.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import redmennl.mods.efm.EnergyFromMatter;
import redmennl.mods.efm.client.gui.inventory.GuiPortableHouse;
import redmennl.mods.efm.tileentity.TilePortableHouse;
import redmennl.mods.efm.tileentity.TilePortableHouseDeployer;
import cpw.mods.fml.common.FMLCommonHandler;

public class BlockPortableHouse extends BlockContainer
{
    
    public BlockPortableHouse(int id)
    {
        super(id, Material.iron);
        this.setUnlocalizedName("portableHouse");
        this.setCreativeTab(EnergyFromMatter.tabEFM);
        this.setHardness(5.0F);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return null;
    }
    
    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
        switch (metadata)
        {
            case 0:
                return new TilePortableHouse();
            case 1:
                return new TilePortableHouseDeployer();
        }
        return null;
    }
    
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    @Override
    public int getRenderType()
    {
        return -1;
    }
    
    @Override
    public int quantityDropped(int meta, int fortune, Random random)
    {
        return 0;
    }
    
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs,
            List par3List)
    {
        for (int j = 0; j < 2; ++j)
        {
            par3List.add(new ItemStack(par1, 1, j));
        }
    }
    
    @Override
    public void onBlockPreDestroy(World world, int x, int y, int z, int meta)
    {
        super.onBlockPreDestroy(world, x, y, z, meta);
        
        TileEntity TE = world.getBlockTileEntity(x, y, z);
        
        ItemStack stack = new ItemStack(this, 1, meta);
        if (TE instanceof TilePortableHouse)
        {
            TilePortableHouse tile = (TilePortableHouse) world
                    .getBlockTileEntity(x, y, z);
            if (tile.noDrop)
            {
                return;
            }
            if (!stack.hasTagCompound() && (tile.name != ""))
            {
                stack.setTagCompound(new NBTTagCompound());
            }
            if (tile.name != "")
            {
                stack.getTagCompound().setString("name", tile.name);
            }
        } else if (TE instanceof TilePortableHouseDeployer)
        {
            TilePortableHouseDeployer tile = (TilePortableHouseDeployer) world
                    .getBlockTileEntity(x, y, z);
            if (tile.noDrop)
            {
                return;
            }
            if (!stack.hasTagCompound())
            {
                stack.setTagCompound(new NBTTagCompound());
            }
            
            stack.getTagCompound().setIntArray("idArr", tile.idArr);
            stack.getTagCompound().setIntArray("metaArr", tile.metaArr);
            stack.getTagCompound().setIntArray("xArr", tile.xArr);
            stack.getTagCompound().setIntArray("yArr", tile.yArr);
            stack.getTagCompound().setIntArray("zArr", tile.zArr);
            stack.getTagCompound().setInteger("size", tile.size);
            stack.getTagCompound().setInteger("height", tile.height);
            if (tile.name != null)
            {
                stack.getTagCompound().setString("name", tile.name);
            }
            stack.getTagCompound().setIntArray("hasTag", tile.hasTag);
            stack.setTagCompound(tile.tag);
        }
        
        EntityItem entityItem = new EntityItem(world, x, y + 1, z, stack);
        entityItem.motionX = 0;
        entityItem.motionY = 0;
        entityItem.motionZ = 0;
        world.spawnEntityInWorld(entityItem);
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z,
            EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        
        if (player.isSneaking())
            return false;
        else
        {
            TileEntity tile = world.getBlockTileEntity(x, y, z);
            if (tile != null)
            {
                GuiPortableHouse gui = new GuiPortableHouse(tile);
                FMLCommonHandler.instance().showGuiScreen(gui);
            }
        }
        return true;
    }
    
    @Override
    public void onBlockPlacedBy(World world, int i, int j, int k,
            EntityLivingBase entity, ItemStack item)
    {
        if (item.hasTagCompound()
                && world.getBlockTileEntity(i, j, k) instanceof TilePortableHouse)
        {
            TilePortableHouse tile = (TilePortableHouse) world
                    .getBlockTileEntity(i, j, k);
            
            if (tile != null)
            {
                if (item.getTagCompound().getString("name") != null)
                {
                    tile.name = item.getTagCompound().getString("name");
                }
            }
        } else if (item.hasTagCompound()
                && world.getBlockTileEntity(i, j, k) instanceof TilePortableHouseDeployer)
        {
            TilePortableHouseDeployer tile = (TilePortableHouseDeployer) world
                    .getBlockTileEntity(i, j, k);
            
            if (tile != null)
            {
                tile.idArr = item.getTagCompound().getIntArray("idArr");
                tile.metaArr = item.getTagCompound().getIntArray("metaArr");
                tile.xArr = item.getTagCompound().getIntArray("xArr");
                tile.yArr = item.getTagCompound().getIntArray("yArr");
                tile.zArr = item.getTagCompound().getIntArray("zArr");
                tile.size = item.getTagCompound().getInteger("size");
                tile.height = item.getTagCompound().getInteger("height");
                if (item.getTagCompound().getString("name") != null)
                {
                    tile.name = item.getTagCompound().getString("name");
                }
                tile.hasTag = item.getTagCompound().getIntArray("hasTag");
                tile.tag = item.getTagCompound();
            }
        }
    }
}