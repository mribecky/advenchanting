package hea3ven.advenchanting.common;

import java.util.List;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;

public class TileEntityExperienceCollector extends TileEntity implements
		IInventory, ITankContainer {

	private LiquidTank experienceTank;
	private ItemStack[] inv;

	// private EntityPlayerExperienceCollector entityPlayer;

	public TileEntityExperienceCollector(World world) {
		inv = new ItemStack[1];
		experienceTank = new LiquidTank(
				770 * 3 * BlockExperienceLiquidStill.getExperienceMultiplier());

		// entityPlayer = new EntityPlayerExperienceCollector(world, this);
		// world.spawnEntityInWorld(entityPlayer);
	}

	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill) {
		return experienceTank.fill(resource, doFill);
	}

	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return experienceTank.drain(maxDrain, doDrain);
	}

	@Override
	public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILiquidTank[] getTanks(ForgeDirection direction) {
		return new ILiquidTank[] { experienceTank };
	}

	@Override
	public ILiquidTank getTank(ForgeDirection direction, LiquidStack type) {
		return experienceTank;
	}

	@Override
	public int getSizeInventory() {
		return inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inv[i];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amt) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			if (stack.stackSize <= amt) {
				setInventorySlotContents(slot, null);
			} else {
				stack = stack.splitStack(amt);
				if (stack.stackSize == 0) {
					setInventorySlotContents(slot, null);
				}
			}
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			setInventorySlotContents(slot, null);
		}
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inv[slot] = stack;
		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName() {
		return "tco.tileentityexperiencecollector";
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this
				&& player.getDistanceSq(xCoord + 0.5, yCoord + 0.5,
						zCoord + 0.5) < 64;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void updateEntity() {
		if (!this.worldObj.isRemote) {
			if (experienceTank.getLiquid() != null
					&& experienceTank.getLiquid().amount < experienceTank
							.getCapacity()) {
				@SuppressWarnings("rawtypes")
				List entities = this.worldObj.getLoadedEntityList();
				for (int i = 0; i < entities.size(); i++) {
					if (entities.get(i) instanceof EntityXPOrb) {
						EntityXPOrb orb = (EntityXPOrb) entities.get(i);
						if (orb.getDistance(xCoord, yCoord, zCoord) < 5.0D)
							experienceTank
									.fill(new LiquidStack(
											AdvancedEnchantingMod.experienceLiquidStill.blockID,
											orb.getXpValue()
													* BlockExperienceLiquidStill
															.getExperienceMultiplier()),
											true);
						this.worldObj.removeEntity(orb);
						if (experienceTank.getLiquid() != null
								&& experienceTank.getLiquid().amount < experienceTank
										.getCapacity()) {
							break;

						}
					}
				}
			}
		}
	}
}
