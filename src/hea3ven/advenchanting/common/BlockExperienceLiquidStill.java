package hea3ven.advenchanting.common;

import net.minecraft.block.BlockStationary;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.liquids.ILiquid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockExperienceLiquidStill extends BlockStationary implements
		ILiquid {

	private Icon[] icons;

	protected BlockExperienceLiquidStill(int id, Material material) {
		super(id, material);

		setHardness(100F);
		setLightOpacity(3);
		setUnlocalizedName("expLiquid");
		disableStats();
	}

	public void registerIcon(IconRegister par1IconRegister) {
		this.icons = new Icon[] {
				par1IconRegister.registerIcon("AdvEnchanting:expliquid"),
				par1IconRegister.registerIcon("AdvEnchanting:expliquid_flow") };
	}

	public Icon getBlockTextureFromSideAndMetadata(int par1, int par2) {
		return par1 != 0 && par1 != 1 ? icons[1] : icons[0];
	}

	@SideOnly(Side.CLIENT)
	public static Icon func_94424_b(String par0Str) {
		return par0Str == "water" ? AdvancedEnchantingMod.experienceLiquidStill.icons[0]
				: AdvancedEnchantingMod.experienceLiquidStill.icons[1];
	}

	// @Override
	// @SideOnly(Side.CLIENT)
	// public int colorMultiplier(IBlockAccess par1iBlockAccess, int par2,
	// int par3, int par4) {
	// return 0xFF0000;
	// }

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

	public static int getAmountFromLevels(int enchantmentLevel) {
		// Integrals hurt my head

		if (enchantmentLevel <= 16)
			return enchantmentLevel * 17;

		if (enchantmentLevel <= 31)
			return (int) (1029.5 + (3 * enchantmentLevel * enchantmentLevel / 2) - 28 * enchantmentLevel);
		// return 272 + (3 * enchantmentLevel * enchantmentLevel / 2) - 28
		// * enchantmentLevel - (3 * (15 * 15) / 2) + 28 * 15;

		return (2115 + (7 * (enchantmentLevel * enchantmentLevel) / 2) - 148 * enchantmentLevel);
		// return 825 + (7 * (enchantmentLevel * enchantmentLevel) / 2) - 148
		// * enchantmentLevel - (7 * (30 * 30) / 2) + 148 * 30;

		// (enchantmentLevel >= 30 ? 62 + (enchantmentLevel - 30) * 7 :
		// (enchantmentLevel >= 15 ? 17 + (enchantmentLevel - 15) * 3 : 17)) *
		// getExperienceMultiplier();
	}

	public static int getExperienceMultiplier() {
		return 2;
	}
}
