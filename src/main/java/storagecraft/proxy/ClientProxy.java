package storagecraft.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import storagecraft.StorageCraftBlocks;
import storagecraft.StorageCraftItems;
import storagecraft.block.EnumControllerType;
import storagecraft.block.EnumGridType;
import storagecraft.item.*;
import storagecraft.render.BlockCableRenderer;
import storagecraft.tile.TileCable;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent e)
	{
		super.preInit(e);

		ModelBakery.registerItemVariants(StorageCraftItems.STORAGE_CELL,
			new ResourceLocation("storagecraft:1k_storage_cell"),
			new ResourceLocation("storagecraft:4k_storage_cell"),
			new ResourceLocation("storagecraft:16k_storage_cell"),
			new ResourceLocation("storagecraft:64k_storage_cell"),
			new ResourceLocation("storagecraft:creative_storage_cell")
		);

		ModelBakery.registerItemVariants(StorageCraftItems.STORAGE_PART,
			new ResourceLocation("storagecraft:1k_storage_part"),
			new ResourceLocation("storagecraft:4k_storage_part"),
			new ResourceLocation("storagecraft:16k_storage_part"),
			new ResourceLocation("storagecraft:64k_storage_part")
		);

		ModelBakery.registerItemVariants(StorageCraftItems.PROCESSOR,
			new ResourceLocation("storagecraft:basic_printed_processor"),
			new ResourceLocation("storagecraft:improved_printed_processor"),
			new ResourceLocation("storagecraft:advanced_printed_processor"),
			new ResourceLocation("storagecraft:basic_processor"),
			new ResourceLocation("storagecraft:improved_processor"),
			new ResourceLocation("storagecraft:advanced_processor"),
			new ResourceLocation("storagecraft:printed_silicon")
		);

		ModelBakery.registerItemVariants(StorageCraftItems.CORE,
			new ResourceLocation("storagecraft:construction_core"),
			new ResourceLocation("storagecraft:destruction_core")
		);

		ModelBakery.registerItemVariants(StorageCraftItems.WIRELESS_GRID,
			new ResourceLocation("storagecraft:wireless_grid_connected"),
			new ResourceLocation("storagecraft:wireless_grid_disconnected")
		);

		ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(StorageCraftBlocks.CABLE), 0, TileCable.class);
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(StorageCraftBlocks.CABLE), 0, new ModelResourceLocation("storagecraft:cable", "inventory"));
		MinecraftForge.EVENT_BUS.register(BakeEventHandler.instance);
	}

	public static class BakeEventHandler
	{
		public static final BakeEventHandler instance = new BakeEventHandler();

		@SubscribeEvent
		public void onModelBakeEvent(ModelBakeEvent event)
		{
			event.modelManager.getBlockModelShapes().registerBuiltInBlocks(StorageCraftBlocks.CABLE);
		}
	}

	@Override
	public void init(FMLInitializationEvent e)
	{
		super.init(e);

		ClientRegistry.bindTileEntitySpecialRenderer(TileCable.class, new BlockCableRenderer());

		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

		// Items
		mesher.register(StorageCraftItems.STORAGE_CELL, ItemStorageCell.TYPE_1K, new ModelResourceLocation("storagecraft:1k_storage_cell", "inventory"));
		mesher.register(StorageCraftItems.STORAGE_CELL, ItemStorageCell.TYPE_4K, new ModelResourceLocation("storagecraft:4k_storage_cell", "inventory"));
		mesher.register(StorageCraftItems.STORAGE_CELL, ItemStorageCell.TYPE_16K, new ModelResourceLocation("storagecraft:16k_storage_cell", "inventory"));
		mesher.register(StorageCraftItems.STORAGE_CELL, ItemStorageCell.TYPE_64K, new ModelResourceLocation("storagecraft:64k_storage_cell", "inventory"));
		mesher.register(StorageCraftItems.STORAGE_CELL, ItemStorageCell.TYPE_CREATIVE, new ModelResourceLocation("storagecraft:creative_storage_cell", "inventory"));

		mesher.register(StorageCraftItems.STORAGE_PART, ItemStoragePart.TYPE_1K, new ModelResourceLocation("storagecraft:1k_storage_part", "inventory"));
		mesher.register(StorageCraftItems.STORAGE_PART, ItemStoragePart.TYPE_4K, new ModelResourceLocation("storagecraft:4k_storage_part", "inventory"));
		mesher.register(StorageCraftItems.STORAGE_PART, ItemStoragePart.TYPE_16K, new ModelResourceLocation("storagecraft:16k_storage_part", "inventory"));
		mesher.register(StorageCraftItems.STORAGE_PART, ItemStoragePart.TYPE_64K, new ModelResourceLocation("storagecraft:64k_storage_part", "inventory"));

		mesher.register(StorageCraftItems.PROCESSOR, ItemProcessor.TYPE_PRINTED_BASIC, new ModelResourceLocation("storagecraft:basic_printed_processor", "inventory"));
		mesher.register(StorageCraftItems.PROCESSOR, ItemProcessor.TYPE_PRINTED_IMPROVED, new ModelResourceLocation("storagecraft:improved_printed_processor", "inventory"));
		mesher.register(StorageCraftItems.PROCESSOR, ItemProcessor.TYPE_PRINTED_ADVANCED, new ModelResourceLocation("storagecraft:advanced_printed_processor", "inventory"));
		mesher.register(StorageCraftItems.PROCESSOR, ItemProcessor.TYPE_BASIC, new ModelResourceLocation("storagecraft:basic_processor", "inventory"));
		mesher.register(StorageCraftItems.PROCESSOR, ItemProcessor.TYPE_IMPROVED, new ModelResourceLocation("storagecraft:improved_processor", "inventory"));
		mesher.register(StorageCraftItems.PROCESSOR, ItemProcessor.TYPE_ADVANCED, new ModelResourceLocation("storagecraft:advanced_processor", "inventory"));
		mesher.register(StorageCraftItems.PROCESSOR, ItemProcessor.TYPE_PRINTED_SILICON, new ModelResourceLocation("storagecraft:printed_silicon", "inventory"));

		mesher.register(StorageCraftItems.SILICON, 0, new ModelResourceLocation("storagecraft:silicon", "inventory"));

		mesher.register(StorageCraftItems.QUARTZ_ENRICHED_IRON, 0, new ModelResourceLocation("storagecraft:quartz_enriched_iron", "inventory"));

		mesher.register(StorageCraftItems.CORE, ItemCore.TYPE_CONSTRUCTION, new ModelResourceLocation("storagecraft:construction_core", "inventory"));
		mesher.register(StorageCraftItems.CORE, ItemCore.TYPE_DESTRUCTION, new ModelResourceLocation("storagecraft:destruction_core", "inventory"));

		mesher.register(StorageCraftItems.WIRELESS_GRID_PLATE, 0, new ModelResourceLocation("storagecraft:wireless_grid_plate", "inventory"));
		mesher.register(StorageCraftItems.WIRELESS_GRID, new ItemMeshDefinition()
		{
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack)
			{
				return new ModelResourceLocation("storagecraft:wireless_grid_" + (ItemWirelessGrid.isValid(stack) ? "connected" : "disconnected"), "inventory");
			}
		});

		// Blocks
		mesher.register(Item.getItemFromBlock(StorageCraftBlocks.GRID), EnumGridType.NORMAL.getId(), new ModelResourceLocation("storagecraft:grid", "inventory"));
		mesher.register(Item.getItemFromBlock(StorageCraftBlocks.GRID), EnumGridType.CRAFTING.getId(), new ModelResourceLocation("storagecraft:grid", "inventory"));
		mesher.register(Item.getItemFromBlock(StorageCraftBlocks.MACHINE_CASING), 0, new ModelResourceLocation("storagecraft:machine_casing", "inventory"));
		mesher.register(Item.getItemFromBlock(StorageCraftBlocks.EXPORTER), 0, new ModelResourceLocation("storagecraft:exporter", "inventory"));
		mesher.register(Item.getItemFromBlock(StorageCraftBlocks.IMPORTER), 0, new ModelResourceLocation("storagecraft:importer", "inventory"));
		mesher.register(Item.getItemFromBlock(StorageCraftBlocks.EXTERNAL_STORAGE), 0, new ModelResourceLocation("storagecraft:external_storage", "inventory"));
		mesher.register(Item.getItemFromBlock(StorageCraftBlocks.DRIVE), 0, new ModelResourceLocation("storagecraft:drive", "inventory"));
		mesher.register(Item.getItemFromBlock(StorageCraftBlocks.CONTROLLER), EnumControllerType.NORMAL.getId(), new ModelResourceLocation("storagecraft:controller", "inventory"));
		mesher.register(Item.getItemFromBlock(StorageCraftBlocks.CONTROLLER), EnumControllerType.CREATIVE.getId(), new ModelResourceLocation("storagecraft:controller", "inventory"));
		mesher.register(Item.getItemFromBlock(StorageCraftBlocks.CONSTRUCTOR), 0, new ModelResourceLocation("storagecraft:constructor", "inventory"));
		mesher.register(Item.getItemFromBlock(StorageCraftBlocks.DESTRUCTOR), 0, new ModelResourceLocation("storagecraft:destructor", "inventory"));
		mesher.register(Item.getItemFromBlock(StorageCraftBlocks.SOLDERER), 0, new ModelResourceLocation("storagecraft:solderer", "inventory"));
		mesher.register(Item.getItemFromBlock(StorageCraftBlocks.WIRELESS_TRANSMITTER), 0, new ModelResourceLocation("storagecraft:wireless_transmitter", "inventory"));
		mesher.register(Item.getItemFromBlock(StorageCraftBlocks.DETECTOR), 0, new ModelResourceLocation("storagecraft:detector", "inventory"));
	}
}
