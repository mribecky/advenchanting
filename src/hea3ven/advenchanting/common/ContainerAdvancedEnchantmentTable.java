package hea3ven.advenchanting.common;

import java.util.Iterator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAdvancedEnchantmentTable extends Container {

	protected TileEntityAdvancedEnchantmentTable tileEntity;
	private int lastProgress;
	private int lastEnchantmentLevel;
	private int lastExperience;

	public ContainerAdvancedEnchantmentTable(InventoryPlayer inventoryPlayer,
			TileEntityAdvancedEnchantmentTable te) {
		tileEntity = te;

		lastProgress = 0;
		lastEnchantmentLevel = 1;
		lastExperience = 0;

		// the Slot constructor takes the IInventory and the slot number in that
		// it binds to
		// and the x-y coordinates it resides on-screen
		addSlotToContainer(new Slot(tileEntity, 0, 32, 23));
		addSlotToContainer(new Slot(tileEntity, 1, 92, 23));

		// commonly used vanilla code that adds the player's inventory
		bindPlayerInventory(inventoryPlayer);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tileEntity.isUseableByPlayer(player);
	}

	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
						20 + j * 18, 95 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 20 + i * 18, 153));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);

		// null checks and checks if the item can be stacked (maxStackSize > 1)
		if (slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();

			// merges the item into player inventory since its in the tileEntity
			if (slot < 9) {
				if (!this.mergeItemStack(stackInSlot, 9, 45, true)) {
					return null;
				}
			}
			// places it into the tileEntity is possible since its in the player
			// inventory
			else if (!this.mergeItemStack(stackInSlot, 0, 9, false)) {
				return null;
			}

			if (stackInSlot.stackSize == 0) {
				slotObject.putStack(null);
			} else {
				slotObject.onSlotChanged();
			}

			if (stackInSlot.stackSize == stack.stackSize) {
				return null;
			}
			slotObject.onPickupFromSlot(player, stackInSlot);
		}
		return stack;
	}

	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		Iterator var1 = this.crafters.iterator();
		while (var1.hasNext()) {
			ICrafting var2 = (ICrafting) var1.next();
			if (this.lastProgress != this.tileEntity.getProgress()) {
				var2.sendProgressBarUpdate(this, 0,
						this.tileEntity.getProgress());
			}
			if (this.lastEnchantmentLevel != this.tileEntity
					.getEnchantmentLevel()) {
				var2.sendProgressBarUpdate(this, 1,
						this.tileEntity.getEnchantmentLevel());
			}
			if (this.lastExperience != this.tileEntity.getExperience()) {
				var2.sendProgressBarUpdate(this, 2,
						this.tileEntity.getExperience());
			}
		}
		this.lastProgress = this.tileEntity.getProgress();
		this.lastEnchantmentLevel = this.tileEntity.getEnchantmentLevel();
		this.lastExperience = this.tileEntity.getExperience();
	}

	public void updateProgressBar(int par1, int par2) {
		if (par1 == 0) {
			tileEntity.setProgress(par2);
		}
		if (par1 == 1) {
			tileEntity.setEnchantmentLevel(par2);
		}
		if (par1 == 2) {
			tileEntity.setExperience(par2);
		}
	}

}
