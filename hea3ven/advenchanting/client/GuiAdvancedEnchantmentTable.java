package hea3ven.advenchanting.client;

import hea3ven.advenchanting.common.AdvancedEnchantingMod;
import hea3ven.advenchanting.common.ContainerAdvancedEnchantmentTable;
import hea3ven.advenchanting.common.PacketHandler;
import hea3ven.advenchanting.common.TileEntityAdvancedEnchantmentTable;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.ForgeHooksClient;

import org.lwjgl.opengl.GL11;

import buildcraft.core.DefaultProps;

import cpw.mods.fml.common.network.PacketDispatcher;

public class GuiAdvancedEnchantmentTable extends GuiContainer {

	private GuiButton addLevelButton;
	private GuiButton substractLevelButton;
	private TileEntityAdvancedEnchantmentTable tileEntityAdvEnchTable;
	private GuiButton addExperienceButton;

	public GuiAdvancedEnchantmentTable(InventoryPlayer inventoryPlayer,
			TileEntityAdvancedEnchantmentTable tileEntity) {
		super(
				new ContainerAdvancedEnchantmentTable(inventoryPlayer,
						tileEntity));
		tileEntityAdvEnchTable = tileEntity;
		xSize = 200;
		ySize = 177;
	}

	@Override
	public void initGui() {
		super.initGui();
		addLevelButton = new GuiButton(1, guiLeft + 30, guiTop + 60, 20, 20,
				"-");
		controlList.add(addLevelButton);
		substractLevelButton = new GuiButton(2, guiLeft + 80, guiTop + 60, 20,
				20, "+");
		controlList.add(substractLevelButton);
		addExperienceButton = new GuiButton(3, guiLeft + 114, guiTop + 60, 50,
				20, "Add Exp.");
		controlList.add(addExperienceButton);
	};

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		fontRenderer.drawString("Adv. Enchantment Table", 8, 6, 4210752);
		fontRenderer.drawString("Enchantment Level:", 8, 50, 4210752);
		int pad = 0;
		if (tileEntityAdvEnchTable.getEnchantmentLevel() < 10)
			pad = 4;
		fontRenderer.drawString(
				Integer.toString(tileEntityAdvEnchTable.getEnchantmentLevel()),
				60 + pad, 65, 4210752);
		fontRenderer.drawString(
				StatCollector.translateToLocal("container.inventory"), 8,
				ySize - 96 + 2, 4210752);

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2,
			int par3) {
		int texture = mc.renderEngine
				.getTexture("/hea3ven/advenchanting/advenchant.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		int progress = tileEntityAdvEnchTable.getProgressScaled(23);
		// progress = 10;
		this.drawTexturedModalRect(x + 79, y + 22, 176, 14, progress, 16);

		displayGauge(x, y, 19, 167,
				tileEntityAdvEnchTable.getExperienceScaled(58),
				AdvancedEnchantingMod.experienceLiquidStill.blockID, 0);
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		super.actionPerformed(par1GuiButton);
		switch (par1GuiButton.id) {
		case 1:
			PacketHandler.sendUpdateEnchantmentLevel(tileEntityAdvEnchTable,
					tileEntityAdvEnchTable.getEnchantmentLevel() - 1);
			break;
		case 2:
			PacketHandler.sendUpdateEnchantmentLevel(tileEntityAdvEnchTable,
					tileEntityAdvEnchTable.getEnchantmentLevel() + 1);
			break;
		case 3:
			PacketHandler.sendAddExperience(tileEntityAdvEnchTable);
			break;
		}

	}

	private void displayGauge(int j, int k, int line, int col, int squaled,
			int liquidId, int liquidMeta) {
		int liquidImgIndex = 0;

		if (liquidId <= 0)
			return;
		if (liquidId < Block.blocksList.length
				&& Block.blocksList[liquidId] != null) {
			ForgeHooksClient.bindTexture(
					Block.blocksList[liquidId].getTextureFile(), 0);
			liquidImgIndex = Block.blocksList[liquidId].blockIndexInTexture;
		} else if (Item.itemsList[liquidId] != null) {
			ForgeHooksClient.bindTexture(
					Item.itemsList[liquidId].getTextureFile(), 0);
			liquidImgIndex = Item.itemsList[liquidId]
					.getIconFromDamage(liquidMeta);
		} else
			return;

		int imgLine = liquidImgIndex / 16;
		int imgColumn = liquidImgIndex - imgLine * 16;

		int start = 0;

		while (true) {
			int x = 0;

			if (squaled > 16) {
				x = 16;
				squaled -= 16;
			} else {
				x = squaled;
				squaled = 0;
			}

			drawTexturedModalRect(j + col, k + line + 58 - x - start,
					imgColumn * 16, imgLine * 16 + (16 - x), 16, 16 - (16 - x));
			start = start + 16;

			if (x == 0 || squaled == 0) {
				break;
			}
		}

		int i = mc.renderEngine
				.getTexture("/hea3ven/advenchanting/advenchant.png");

		mc.renderEngine.bindTexture(i);
		drawTexturedModalRect(j + col, k + line, 200, 34, 16, 60);
	}
}
