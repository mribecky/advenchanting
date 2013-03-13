package hea3ven.advenchanting.common;

import net.minecraft.block.BlockStationary;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.liquids.ILiquid;

public class BlockExperienceLiquidStill extends BlockStationary implements
		ILiquid {

	protected BlockExperienceLiquidStill(int id, Material material) {
		super(id, material);

		blockIndexInTexture = 1;
		setHardness(100F);
		setLightOpacity(3);
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.BLOCK_PNG;
	}

	// @Override
	// @SideOnly(Side.CLIENT)
	// public int colorMultiplier(IBlockAccess par1iBlockAccess, int par2,
	// int par3, int par4) {
	// return 0xFF0000;
	// }

	@Override
	public int getRenderType() {
		return AdvancedEnchantingMod.expLiquidModel;
		// return 4;
	}

	@Override
	public int stillLiquidId() {
		return AdvancedEnchantingMod.experienceLiquidStill.blockID;
	}

	@Override
	public boolean isMetaSensitive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int stillLiquidMeta() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isBlockReplaceable(World world, int i, int j, int k) {
		return true;
	}
}
