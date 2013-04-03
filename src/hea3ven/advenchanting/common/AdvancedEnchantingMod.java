package hea3ven.advenchanting.common;

import hea3ven.advenchanting.client.GuiHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
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

	public static int expLiquidModel;

	public final static BlockAdvancedEnchantmentTable advancedEnchantmentTableBlock = new BlockAdvancedEnchantmentTable(
			2000);

	/** Stationary lava source block */
	public final static BlockExperienceLiquidStill experienceLiquidStill = new BlockExperienceLiquidStill(
			2002, Material.water);
	public final static BlockExperienceLiquidFlowing experienceLiquidFlowing = new BlockExperienceLiquidFlowing(
			2001, Material.water);
	
	public final static BlockExperienceCollector experienceCollector = new BlockExperienceCollector(2003, Material.rock);

	public static LiquidStack experienceLiquid;

	public static Item bucketExpLiquid = (new ItemBucket(500,
			experienceLiquidFlowing.blockID)).setUnlocalizedName("bucketExp")
			.setContainerItem(Item.bucketEmpty);

	// public final static ItemExpLiquidBucket expLiquidBucket = new
	// ItemExpLiquidBucket(2003);

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		// Stub Method
	}

	@Init
	public void load(FMLInitializationEvent event) {
		proxy.registerRenderers();

		ItemStack advancedEnchantmentTableStack = new ItemStack(
				advancedEnchantmentTableBlock);
		ItemStack experienceCollectorStack = new ItemStack(experienceCollector);

		GameRegistry.registerBlock(advancedEnchantmentTableBlock,
				"advEnchantmentTable");
		LanguageRegistry.addName(advancedEnchantmentTableBlock,
				"Adv. Enchantment Table");
		GameRegistry.registerTileEntity(
				TileEntityAdvancedEnchantmentTable.class,
				"containerAdvancedEnchantmentTable");
		
		GameRegistry.registerBlock(experienceCollector);
		LanguageRegistry.addName(experienceCollector, "Experience Collector");
		GameRegistry.registerTileEntity(TileEntityExperienceCollector.class, "containerExperienceCollector");

		LanguageRegistry.addName(bucketExpLiquid, "Experience Bucket");

		experienceLiquidStill.setUnlocalizedName("expLiquidStill");
		experienceLiquidFlowing.setUnlocalizedName("expLiquidFlowing");
		GameRegistry.registerBlock(experienceLiquidStill,
				"experienceLiquidStill");
		LanguageRegistry.addName(experienceLiquidStill,
				"Experience Liquid Still");
		GameRegistry.registerBlock(experienceLiquidFlowing,
				"experienceLiquidMoving");
		LanguageRegistry.addName(experienceLiquidFlowing,
				"Experience Liquid Flowing");
		experienceLiquid = LiquidDictionary.getOrCreateLiquid(
				"ExperienceLiquid", new LiquidStack(experienceLiquidStill, 1));
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(
				LiquidDictionary.getLiquid("ExperienceLiquid",
						LiquidContainerRegistry.BUCKET_VOLUME), new ItemStack(
						bucketExpLiquid), new ItemStack(Item.bucketEmpty)));
		// GameRegistry.registerItem(expLiquidBucket, "experienceLiquidBucket");

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
