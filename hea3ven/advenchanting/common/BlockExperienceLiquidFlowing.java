package hea3ven.advenchanting.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.liquids.ILiquid;

public class BlockExperienceLiquidFlowing extends BlockFluid implements ILiquid {

	protected BlockExperienceLiquidFlowing(int id, Material material) {
		super(id, material);
		blockIndexInTexture = 1;
		setHardness(100F);
		setLightOpacity(7);
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.BLOCK_PNG;
	}
	
//	@Override
//	@SideOnly(Side.CLIENT)
//	public int colorMultiplier(IBlockAccess par1iBlockAccess, int par2,
//			int par3, int par4) {
//		return 0xFF0000;
//	}

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

}
