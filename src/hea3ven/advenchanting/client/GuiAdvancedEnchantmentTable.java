package hea3ven.advenchanting.client;

import hea3ven.advenchanting.common.ContainerAdvancedEnchantmentTable;
import hea3ven.advenchanting.common.PacketHandler;
import hea3ven.advenchanting.common.TileEntityAdvancedEnchantmentTable;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import net.minecraftforge.liquids.LiquidStack;

import org.lwjgl.opengl.GL11;

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

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		addLevelButton = new GuiButton(1, guiLeft + 30, guiTop + 60, 20, 20,
				"-");
		buttonList.add(addLevelButton);
		substractLevelButton = new GuiButton(2, guiLeft + 80, guiTop + 60, 20,
				20, "+");
		buttonList.add(substractLevelButton);
		addExperienceButton = new GuiButton(3, guiLeft + 114, guiTop + 60, 50,
				20, "Add Exp.");
		buttonList.add(addExperienceButton);
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
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine
				.bindTexture("/mods/AdvEnchanting/textures/gui/advenchant.png");
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		int progress = tileEntityAdvEnchTable.getProgressScaled(23);
		// progress = 10;
		this.drawTexturedModalRect(x + 55, y + 25, 200, 14, progress, 16);

		if (tileEntityAdvEnchTable.getExperienceLiquid() != null) {
			displayGauge(x, y, 19, 167,
					tileEntityAdvEnchTable.getExperienceScaled(58),
					tileEntityAdvEnchTable.getExperienceLiquid());
		}
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
			LiquidStack liquid) {
		if (liquid == null) {
			return;
		}
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

//			drawTexturedModelRectFromIcon(j + col, k + line + 58 - x - start,
//					liquid.getRenderingIcon(), 16, 16 - (16 - x));
			start = start + 16;

			if (x == 0 || squaled == 0) {
				break;
			}
		}

		mc.renderEngine
				.bindTexture("/mods/AdvEnchanting/textures/gui/advenchant.png");
		drawTexturedModalRect(j + col, k + line, 176, 0, 16, 60);
	}

}
