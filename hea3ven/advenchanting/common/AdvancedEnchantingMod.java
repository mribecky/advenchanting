package hea3ven.advenchanting.common;

import hea3ven.advenchanting.client.GuiHandler;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "AdvancedEnchanting", name = "Advanced Enchanting", version = "0.0.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = { "AdvEnchanting" }, packetHandler = PacketHandler.class)
public class AdvancedEnchantingMod {
	@Instance("AdvancedEnchanting")
	public static AdvancedEnchantingMod instance;

	@SidedProxy(clientSide = "hea3ven.advenchanting.client.ClientProxy", serverSide = "hea3ven.advenchanting.CommonProxy")
	public static CommonProxy proxy;

	public final static BlockAdvancedEnchantmentTable advancedEnchantmentTableBlock = new BlockAdvancedEnchantmentTable(
			2000);
	public final static BlockExperienceLiquidStill experienceLiquidStill = new BlockExperienceLiquidStill(2001, Material.water);
	public final static BlockExperienceLiquidFlowing experienceLiquidFlowing = new BlockExperienceLiquidFlowing(2002, Material.water);

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		// Stub Method
	}

	@Init
	public void load(FMLInitializationEvent event) {
		proxy.registerRenderers();

		ItemStack advancedEnchantmentTableStack = new ItemStack(
				advancedEnchantmentTableBlock);

		GameRegistry.registerBlock(advancedEnchantmentTableBlock,
				"advEnchantmentTable");
		LanguageRegistry.addName(advancedEnchantmentTableBlock, "Adv. Enchantment Table");
		GameRegistry.registerTileEntity(
				TileEntityAdvancedEnchantmentTable.class,
				"containerAdvancedEnchantmentTable");
		
		GameRegistry.registerBlock(experienceLiquidStill, "experienceLiquidStill");
		GameRegistry.registerBlock(experienceLiquidFlowing, "experienceLiquidMoving");
		
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
		GameRegistry.addRecipe(advancedEnchantmentTableStack, "xxx", "xyx",
				"zrz", 'x', new ItemStack(Item.book, 1), 'y', new ItemStack(
						Block.enchantmentTable, 1), 'z', new ItemStack(
						Item.emerald, 1), 'r', new ItemStack(Item.redstone, 1));

		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
		// MinecraftForge.EVENT_BUS.
		// TickRegistry.registerTickHandler(new TimeControlTickHandler(),
		// Side.SERVER);
		// FMLCommonHandler.instance().getMinecraftServerInstance().get
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
		// Stub Method
	}

}
