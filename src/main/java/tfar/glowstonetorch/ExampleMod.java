package tfar.glowstonetorch;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.function.ToIntFunction;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ExampleMod.MODID)
public class ExampleMod
{
    // Directly reference a log4j logger.

    public static final String MODID = "glowstonetorch";

    public static Block glowstone_torch;
    public static Block glowstone_wall_torch;
    public static final Material waterproof_torch = new Material(MaterialColor.AIR,false,false,false,false,false,false, PushReaction.DESTROY);

    public ExampleMod() {
        // Register the setup method for modloading
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        bus.addGenericListener(Block.class,this::block);
        bus.addGenericListener(Item.class,this::item);
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    private static ToIntFunction<BlockState> func_235420_a_(int i) {
        return state -> i;
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(glowstone_torch, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(glowstone_wall_torch, RenderType.getCutout());
    }

    private void block(RegistryEvent.Register<Block> e) {
        glowstone_torch = register(new GlowstoneTorchBlock(AbstractBlock.Properties.create(waterproof_torch).doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel(func_235420_a_(15)),null),"glowstone_torch",e.getRegistry());
        glowstone_wall_torch = register(new GlowstoneWallTorchBlock(AbstractBlock.Properties.create(waterproof_torch).doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel(func_235420_a_(15)),null),"glowstone_wall_torch",e.getRegistry());
    }

    private void item(RegistryEvent.Register<Item> e) {
        register(new WallFloorOrCeilingItem(glowstone_torch,glowstone_wall_torch,new Item.Properties().group(ItemGroup.DECORATIONS)),"glowstone_torch",e.getRegistry());
    }

    private static <T extends IForgeRegistryEntry<T>> T register(T obj, String name, IForgeRegistry<T> registry) {
        registry.register(obj.setRegistryName(new ResourceLocation(MODID, name)));
        return obj;
    }
}
