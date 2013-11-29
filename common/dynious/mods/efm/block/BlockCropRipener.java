package dynious.mods.efm.block;

import dynious.mods.efm.EnergyFromMatter;
import dynious.mods.efm.lib.Strings;
import dynious.mods.efm.tileentity.TileCropRipener;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class BlockCropRipener extends BlockEmc
{
    public BlockCropRipener(int id)
    {
        super(id, Material.water);
        this.setUnlocalizedName(Strings.RESOURCE_PREFIX
                + Strings.CROP_RIPENER_NAME);
        this.setCreativeTab(EnergyFromMatter.tabEFM);
        this.setHardness(5.0F);
        this.setStepSound(soundMetalFootstep);
        MinecraftForge.setBlockHarvestLevel(this, "pickaxe", 2);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileCropRipener();
    }
    
    @Override
    public boolean openGui(EntityPlayer player, World world, int x, int y, int z)
    {
        return false;
    }
}
