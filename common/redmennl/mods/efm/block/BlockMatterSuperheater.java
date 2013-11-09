package redmennl.mods.efm.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import redmennl.mods.efm.EnergyFromMatter;
import redmennl.mods.efm.lib.Resources;
import redmennl.mods.efm.lib.Strings;
import redmennl.mods.efm.tileentity.TileMatterSuperheater;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMatterSuperheater extends BlockEmc
{
    public BlockMatterSuperheater(int id)
    {
        super(id, Material.iron);
        this.setUnlocalizedName(Strings.RESOURCE_PREFIX
                + Strings.MATTER_SUPERHEATER_NAME);
        this.setCreativeTab(EnergyFromMatter.tabEFM);
        this.setHardness(5.0F);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileMatterSuperheater();
    }
    
    @Override
    public boolean openGui(EntityPlayer player, World world, int x, int y, int z)
    {
        return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        icons = new Icon[6];
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
            default:
                switch (par2)
                {
                    case 0:
                        return icons[2];
                    default:
                        return icons[3];
                }
        }
    }
}
