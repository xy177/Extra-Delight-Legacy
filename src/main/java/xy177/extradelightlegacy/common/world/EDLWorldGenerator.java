package xy177.extradelightlegacy.common.world;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.block.BlockCoffeeBush;
import xy177.extradelightlegacy.common.block.BlockCornBottom;
import xy177.extradelightlegacy.common.block.BlockCornTop;
import xy177.extradelightlegacy.common.block.BlockOrchardSapling;
import xy177.extradelightlegacy.common.block.CropSoilHelper;
import xy177.extradelightlegacy.common.config.EDLConfig;
import xy177.extradelightlegacy.common.module.EDLModule;
import xy177.extradelightlegacy.common.registry.EDLBlocks;

import java.util.Random;

public class EDLWorldGenerator implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world,
                         IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.getDimension() != 0 || !EDLConfig.isModuleEnabled(EDLModule.WORLDGEN)) {
            return;
        }

        BlockPos biomePos = new BlockPos((chunkX << 4) + 8, 0, (chunkZ << 4) + 8);
        Biome biome = world.getBiome(biomePos);

        if (EDLConfig.shouldGenerateWildCrops()) {
            generateWildCrops(world, random, chunkX, chunkZ, biome);
        }
        if (EDLConfig.shouldGenerateOrchardTrees()) {
            generateOrchardTrees(world, random, chunkX, chunkZ, biome);
        }
    }

    private void generateWildCrops(World world, Random random, int chunkX, int chunkZ, Biome biome) {
        if (hasType(biome, BiomeDictionary.Type.PLAINS)) {
            generateCornPatch(world, random, chunkX, chunkZ, EDLConfig.getWildCornChance(), 120, 250, 12, 256);
        }
        if (hasType(biome, BiomeDictionary.Type.JUNGLE)) {
            generateCoffeePatch(world, random, chunkX, chunkZ, EDLConfig.getWildCoffeeChance(), 3, 7, 5, 128);
            generatePatch(world, random, chunkX, chunkZ, EDLConfig.getWildGingerChance(), 2, 5, 2, 128,
                EDLBlocks.WILD_GINGER);
            generatePatch(world, random, chunkX, chunkZ, EDLConfig.getWildPeanutChance(), 3, 7, 5, 128,
                EDLBlocks.WILD_PEANUT);
        }
        if (hasType(biome, BiomeDictionary.Type.HOT)) {
            generatePatch(world, random, chunkX, chunkZ, EDLConfig.getWildChiliChance(), 2, 5, 2, 128,
                EDLBlocks.WILD_CHILI);
        }
        if (hasType(biome, BiomeDictionary.Type.SWAMP)) {
            generatePatch(world, random, chunkX, chunkZ, EDLConfig.getWildMallowChance(), 70, 150, 10, 256,
                EDLBlocks.WILD_MALLOW_ROOT);
        }
        if (hasType(biome, BiomeDictionary.Type.COLD)) {
            generatePatch(world, random, chunkX, chunkZ, EDLConfig.getWildMintChance(), 20, 100, 10, 256,
                EDLBlocks.MINT_CROP);
        }
        if (hasType(biome, BiomeDictionary.Type.MOUNTAIN) && isTemperateLand(biome)) {
            generatePatch(world, random, chunkX, chunkZ, EDLConfig.getWildGarlicChance(), 3, 7, 5, 128,
                EDLBlocks.WILD_GARLIC);
        }
        if (hasType(biome, BiomeDictionary.Type.HILLS)) {
            generatePatch(world, random, chunkX, chunkZ, EDLConfig.getWildCucumberChance(), 2, 3, 2, 64,
                EDLBlocks.WILD_CUCUMBER);
        }
        if (isTemperateLand(biome)) {
            generatePatch(world, random, chunkX, chunkZ, EDLConfig.getWildSoybeanChance(), 2, 4, 5, 64,
                EDLBlocks.WILD_SOYBEAN);
        }
    }

    private void generateOrchardTrees(World world, Random random, int chunkX, int chunkZ, Biome biome) {
        if (hasType(biome, BiomeDictionary.Type.JUNGLE)) {
            generateTree(world, random, chunkX, chunkZ, EDLConfig.getCinnamonTreeChance(), EDLBlocks.CINNAMON_SAPLING);
            generateTree(world, random, chunkX, chunkZ, EDLConfig.getGrapefruitTreeChance(), EDLBlocks.GRAPEFRUIT_SAPLING);
        }
        if (hasType(biome, BiomeDictionary.Type.FOREST)) {
            generateTree(world, random, chunkX, chunkZ, EDLConfig.getHazelnutTreeChance(), EDLBlocks.HAZELNUT_SAPLING);
            generateTree(world, random, chunkX, chunkZ, EDLConfig.getAppleTreeChance(), EDLBlocks.APPLE_SAPLING);
        }
        if (hasType(biome, BiomeDictionary.Type.HILLS)) {
            generateTree(world, random, chunkX, chunkZ, EDLConfig.getLemonTreeChance(), EDLBlocks.LEMON_SAPLING);
        }
        if (hasType(biome, BiomeDictionary.Type.HOT)) {
            generateTree(world, random, chunkX, chunkZ, EDLConfig.getLimeTreeChance(), EDLBlocks.LIME_SAPLING);
        }
        if (hasType(biome, BiomeDictionary.Type.WET) && isLand(biome)) {
            generateTree(world, random, chunkX, chunkZ, EDLConfig.getOrangeTreeChance(), EDLBlocks.ORANGE_SAPLING);
        }
    }

    private void generatePatch(World world, Random random, int chunkX, int chunkZ, int chance,
                               int minCount, int maxCount, int spread, int tries,
                               EDLBlocks.BlockDefinition definition) {
        if (!shouldAttempt(random, chance) || !definition.isRegistered()) {
            return;
        }

        Block block = definition.getBlock();
        int targetCount = randomCount(random, minCount, maxCount);
        BlockPos origin = randomChunkOrigin(random, chunkX, chunkZ);
        int generated = 0;
        for (int attempt = 0; attempt < tries && generated < targetCount; attempt++) {
            BlockPos pos = patchSurfacePosition(world, origin, random, chunkX, chunkZ, spread);
            if (pos == null) {
                continue;
            }
            if (placePlant(world, pos, block, block.getDefaultState())) {
                generated++;
            }
        }
        logGenerated(block, generated, origin);
    }

    private void generateCoffeePatch(World world, Random random, int chunkX, int chunkZ, int chance,
                                     int minCount, int maxCount, int spread, int tries) {
        if (!shouldAttempt(random, chance) || !EDLBlocks.COFFEE_BUSH.isRegistered()) {
            return;
        }

        Block block = EDLBlocks.COFFEE_BUSH.getBlock();
        if (!(block instanceof BlockCoffeeBush)) {
            return;
        }

        IBlockState state = block.getDefaultState().withProperty(BlockCoffeeBush.AGE, 3);
        int targetCount = randomCount(random, minCount, maxCount);
        BlockPos origin = randomChunkOrigin(random, chunkX, chunkZ);
        int generated = 0;
        for (int attempt = 0; attempt < tries && generated < targetCount; attempt++) {
            BlockPos pos = patchSurfacePosition(world, origin, random, chunkX, chunkZ, spread);
            if (pos == null) {
                continue;
            }
            if (placePlant(world, pos, block, state)) {
                generated++;
            }
        }
        logGenerated(block, generated, origin);
    }

    private void generateCornPatch(World world, Random random, int chunkX, int chunkZ, int chance,
                                   int minCount, int maxCount, int spread, int tries) {
        if (!shouldAttempt(random, chance)
            || !EDLBlocks.CORN_CROP.isRegistered()
            || !EDLBlocks.CORN_TOP_CROP.isRegistered()) {
            return;
        }

        Block bottomBlock = EDLBlocks.CORN_CROP.getBlock();
        Block topBlock = EDLBlocks.CORN_TOP_CROP.getBlock();
        if (!(bottomBlock instanceof BlockCornBottom) || !(topBlock instanceof BlockCornTop)) {
            return;
        }

        BlockCornBottom bottom = (BlockCornBottom) bottomBlock;
        BlockCornTop top = (BlockCornTop) topBlock;
        IBlockState bottomState = bottom.withAge(bottom.getMaxAge());
        IBlockState topState = top.withAge(top.getMaxAge());
        int targetCount = randomCount(random, minCount, maxCount);
        BlockPos origin = randomChunkOrigin(random, chunkX, chunkZ);
        int generated = 0;
        for (int attempt = 0; attempt < tries && generated < targetCount; attempt++) {
            BlockPos pos = patchSurfacePosition(world, origin, random, chunkX, chunkZ, spread);
            if (pos == null) {
                continue;
            }
            if (!world.isAirBlock(pos) || !world.isAirBlock(pos.up())) {
                continue;
            }
            if (!CropSoilHelper.isGroundPlantSoil(world.getBlockState(pos.down()))) {
                continue;
            }

            world.setBlockState(pos, bottomState, 2);
            world.setBlockState(pos.up(), topState, 2);
            generated++;
        }
        logGenerated(bottomBlock, generated, origin);
    }

    private void generateTree(World world, Random random, int chunkX, int chunkZ, int chance,
                              EDLBlocks.BlockDefinition definition) {
        if (!shouldAttempt(random, chance) || !definition.isRegistered()) {
            return;
        }

        Block block = definition.getBlock();
        if (!(block instanceof BlockOrchardSapling)) {
            return;
        }

        BlockOrchardSapling sapling = (BlockOrchardSapling) block;
        for (int attempt = 0; attempt < 4; attempt++) {
            int x = (chunkX << 4) + 4 + random.nextInt(8);
            int z = (chunkZ << 4) + 4 + random.nextInt(8);
            BlockPos pos = surfacePosition(world, x, z);
            IBlockState state = block.getDefaultState();
            if (!world.isAirBlock(pos) || !sapling.canBlockStay(world, pos, state)) {
                continue;
            }
            if (sapling.generateTree(world, pos, random)) {
                logGenerated(block, 1, pos);
                return;
            }
        }
    }

    private boolean placePlant(World world, BlockPos pos, Block block, IBlockState state) {
        IBlockState existing = world.getBlockState(pos);
        if (!world.isAirBlock(pos) && existing.getBlock() != Blocks.SNOW_LAYER) {
            return false;
        }
        if (block instanceof BlockBush && !((BlockBush) block).canBlockStay(world, pos, state)) {
            return false;
        }
        return world.setBlockState(pos, state, 2);
    }

    private BlockPos randomChunkOrigin(Random random, int chunkX, int chunkZ) {
        int x = (chunkX << 4) + 1 + random.nextInt(14);
        int z = (chunkZ << 4) + 1 + random.nextInt(14);
        return new BlockPos(x, 0, z);
    }

    private BlockPos patchSurfacePosition(World world, BlockPos origin, Random random,
                                          int chunkX, int chunkZ, int spread) {
        int x = origin.getX() + randomOffset(random, spread);
        int z = origin.getZ() + randomOffset(random, spread);
        int minX = chunkX << 4;
        int minZ = chunkZ << 4;
        if (x < minX + 1 || x > minX + 14 || z < minZ + 1 || z > minZ + 14) {
            return null;
        }
        return surfacePosition(world, x, z);
    }

    private BlockPos surfacePosition(World world, int x, int z) {
        BlockPos pos = world.getHeight(new BlockPos(x, 0, z));
        if (world.getBlockState(pos.down()).getBlock() == Blocks.SNOW_LAYER) {
            return pos.down();
        }
        return pos;
    }

    private int randomOffset(Random random, int spread) {
        return random.nextInt(spread + 1) - random.nextInt(spread + 1);
    }

    private int randomCount(Random random, int minCount, int maxCount) {
        return minCount + random.nextInt(maxCount - minCount + 1);
    }

    private boolean shouldAttempt(Random random, int chance) {
        return chance > 0 && random.nextInt(chance) == 0;
    }

    private boolean isTemperateLand(Biome biome) {
        return isLand(biome)
            && !hasType(biome, BiomeDictionary.Type.HOT)
            && !hasType(biome, BiomeDictionary.Type.COLD);
    }

    private boolean isLand(Biome biome) {
        return !hasType(biome, BiomeDictionary.Type.OCEAN)
            && !hasType(biome, BiomeDictionary.Type.RIVER)
            && !hasType(biome, BiomeDictionary.Type.WATER)
            && !hasType(biome, BiomeDictionary.Type.MUSHROOM);
    }

    private boolean hasType(Biome biome, BiomeDictionary.Type type) {
        return BiomeDictionary.hasType(biome, type);
    }

    private void logGenerated(Block block, int count, BlockPos pos) {
        if (count > 0 && ExtraDelightLegacy.logger != null) {
            ExtraDelightLegacy.logger.debug("Generated {} x {} near {}", count, block.getRegistryName(), pos);
        }
    }
}
