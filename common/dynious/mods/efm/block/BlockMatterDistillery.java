package dynious.mods.efm.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dynious.mods.efm.EnergyFromMatter;
import dynious.mods.efm.lib.GuiIds;
import dynious.mods.efm.lib.Resources;
import dynious.mods.efm.lib.Strings;
import dynious.mods.efm.tileentity.TileMatterDistillery;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockMatterDistillery extends BlockEmc
{
    public BlockMatterDistillery(int id)
    {
        super(id, Material.iron);
        this.setUnlocalizedName(Strings.RESOURCE_PREFIX
                + Strings.MATTER_DISTILLERY_NAME);
        this.setCreativeTab(EnergyFromMatter.tabEFM);
        this.setHardness(5.0F);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileMatterDistillery();
    }
    
    @Override
    public boolean openGui(EntityPlayer player, World world, int x, int y, int z)
    {
        player.openGui(EnergyFromMatter.instance, GuiIds.MATTER_DISTILLERY,
                world, x, y, z);
        return true;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        icons = new Icon[8];
        for (int i = 0; i < icons.length; i++)
        {
            icons[i] = par1IconRegister.registerIcon(Resources.MOD_ID + ":"
                    + this.getUnlocalizedName().substring(9) + i);
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int par1, int par2)
    {
        switch (par1)
        {
            case 0:
                switch (par2)
                {
                    case 0:
                        return icons[4];
                    default:
                        return icons[5];
                }
            case 1:
                switch (par2)
                {
                    case 0:
                        return icons[0];
                    default:
                        return icons[1];
                }
            case 2:
            {
                switch (par2)
                {
                    case 0:
                        return icons[2];
                    default:
                        return icons[3];
                }
            }
            case 3:
            {
                switch (par2)
                {
                    case 0:
                        return icons[2];
                    default:
                        return icons[3];
                }
            }
            case 4:
            {
                switch (par2)
                {
                    case 0:
                        return icons[6];
                    default:
                        return icons[7];
                }
            }
            case 5:
            {
                switch (par2)
                {
                    case 0:
                        return icons[6];
                    default:
                        return icons[7];
                }
            }
            default:
                switch (par2)
                {
                    case 0:
                        return icons[6];
                    default:
                        return icons[7];
                }
        }
    }
    
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int id2)
    {
        int id = world.getBlockId(x, y - 1, z);
        if (id == 10 || id == 11 || id == 51)
        {
            ((TileMatterDistillery) world.getBlockTileEntity(x, y, z)).hasHeater = true;
        } else
        {
            ((TileMatterDistillery) world.getBlockTileEntity(x, y, z)).hasHeater = false;
        }
        super.onNeighborBlockChange(world, x, y, z, id2);
        // TODO fix
    }
    
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z,
            EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
    {
        int id = world.getBlockId(x, y - 1, z);
        if (id == 10 || id == 11 || id == 51)
        {
            ((TileMatterDistillery) world.getBlockTileEntity(x, y, z)).hasHeater = true;
        }
        super.onBlockPlacedBy(world, x, y, z, par5EntityLivingBase,
                par6ItemStack);
    }
    
}
