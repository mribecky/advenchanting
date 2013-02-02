package hea3ven.advenchanting.client;

import hea3ven.advenchanting.common.ContainerAdvancedEnchantmentTable;
import hea3ven.advenchanting.common.PacketHandler;
import hea3ven.advenchanting.common.TileEntityAdvancedEnchantmentTable;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.network.PacketDispatcher;

public class GuiAdvancedEnchantmentTable extends GuiContainer {

	private GuiButton addLevelButton;
	private GuiButton substractLevelButton;
	private TileEntityAdvancedEnchantmentTable tileEntityAdvEnchTable;
	private GuiButton addExperienceButton;

	public GuiAdvancedEnchantmentTable(InventoryPlayer inventoryPlayer,
			TileEntityAdvancedEnchantmentTable tileEntity) {
		// the container is instanciated and passed to the superclass for
		// handling
		super(
				new ContainerAdvancedEnchantmentTable(inventoryPlayer,
						tileEntity));
		tileEntityAdvEnchTable = tileEntity;
	}

	@Override
	public void initGui() {
		super.initGui();
		addLevelButton = new GuiButton(1, guiLeft + 30, guiTop + 50, 20, 20,
				"-");
		controlList.add(addLevelButton);
		substractLevelButton = new GuiButton(2, guiLeft + 80, guiTop + 50, 20,
				20, "+");
		controlList.add(substractLevelButton);
		addExperienceButton = new GuiButton(3, guiLeft + 114, guiTop + 60, 50,
				20, "Add Exp.");
		controlList.add(addExperienceButton);
	};

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		// draw text and stuff here
		// the parameters for drawString are: string, x, y, color
		fontRenderer.drawString("Adv. Enchantment Table", 8, 6, 4210752);
		fontRenderer.drawString("Enchantment Level:", 8, 40, 4210752);
		if(tileEntityAdvEnchTable.getEnchantmentLevel() >= 10)
			fontRenderer.drawString(Integer.toString(tileEntityAdvEnchTable.getEnchantmentLevel()), 60, 55, 4210752);
		else
			fontRenderer.drawString(Integer.toString(tileEntityAdvEnchTable.getEnchantmentLevel()), 64, 55, 4210752);
		fontRenderer.drawString(Integer.toString(tileEntityAdvEnchTable.getExperience()), 130, 50, 4210752);
		// draws "Inventory" or your regional equivalent
		fontRenderer.drawString(
				StatCollector.translateToLocal("container.inventory"), 8,
				ySize - 96 + 2, 4210752);

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2,
			int par3) {
	// draw your Gui here, only thing you need to change is the path
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
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		super.actionPerformed(par1GuiButton);
		switch (par1GuiButton.id) {
		case 1:
			PacketHandler.sendUpdateEnchantmentLevel(tileEntityAdvEnchTable, tileEntityAdvEnchTable.getEnchantmentLevel() - 1);
			break;
		case 2:
			PacketHandler.sendUpdateEnchantmentLevel(tileEntityAdvEnchTable, tileEntityAdvEnchTable.getEnchantmentLevel() + 1);
			break;
		case 3:
			PacketHandler.sendAddExperience(tileEntityAdvEnchTable);
			break;
		}
		
	}
}
