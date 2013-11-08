package redmennl.mods.efm.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import redmennl.mods.efm.EnergyFromMatter;
import redmennl.mods.efm.lib.GuiIds;
import redmennl.mods.efm.lib.Resources;
import redmennl.mods.efm.lib.Strings;
import redmennl.mods.efm.tileentity.TileMatterDistillery;

public class BlockMatterDistillery extends BlockEmc
{
    public BlockMatterDistillery(int id)
    {
        super(id, Material.iron);
        this.setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.MATTER_DISTILLERY_NAME);
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
