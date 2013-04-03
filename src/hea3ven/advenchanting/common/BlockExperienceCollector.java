package hea3ven.advenchanting.common;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockExperienceCollector extends BlockContainer {

	private Icon blockIconTop;
	private Icon blockIconBottom;

	protected BlockExperienceCollector(int blockID, Material material) {
		super(blockID, material);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityExperienceCollector(world);
	}

    public Icon getBlockTextureFromSideAndMetadata(int side, int metadata)
    {
    	 return side == 0 ? this.blockIconBottom : (side == 1 ? this.blockIconTop : this.blockIcon);
    }

    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("enchantment_side");
        this.blockIconTop = par1IconRegister.registerIcon("enchantment_top");
        this.blockIconBottom = par1IconRegister.registerIcon("enchantment_bottom");
    }
}
