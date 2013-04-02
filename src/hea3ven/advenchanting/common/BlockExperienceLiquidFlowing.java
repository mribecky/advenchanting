package hea3ven.advenchanting.common;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlowing;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.liquids.ILiquid;

public class BlockExperienceLiquidFlowing extends BlockFlowing implements ILiquid {

	int numAdjacentSources = 0;
	boolean isOptimalFlowDirection[] = new boolean[4];
	int flowCost[] = new int[4];
	private Icon[] icons;

	protected BlockExperienceLiquidFlowing(int id, Material material) {
		super(id, material);

		setHardness(100F);
		setLightOpacity(3);
	}

	public void func_94332_a(IconRegister par1IconRegister) {
		this.icons = new Icon[] {
				par1IconRegister.func_94245_a("AdvEnchanting:expliquid"),
				par1IconRegister.func_94245_a("AdvEnchanting:expliquid_flow") };
	}

    @SideOnly(Side.CLIENT)
    public static Icon func_94424_b(String par0Str)
    {
        return par0Str == "water" ? AdvancedEnchantingMod.experienceLiquidFlowing.icons[0] : AdvancedEnchantingMod.experienceLiquidFlowing.icons[1];
    }
    
    public Icon getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        return par1 != 0 && par1 != 1 ? icons[1] : icons[0];
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

}
