package net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCanon;

import com.simibubi.create.content.contraptions.base.DirectionalAxisKineticBlock;
import com.simibubi.create.content.contraptions.base.DirectionalKineticBlock;
import com.simibubi.create.content.contraptions.base.KineticBlock;
import com.simibubi.create.content.contraptions.base.RotatedPillarKineticBlock;
import com.simibubi.create.content.contraptions.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.contraptions.relays.gauge.GaugeInstance;
import com.simibubi.create.foundation.block.ITE;
import net.forsteri.createmorepotatoes.CreateMorePotatoes;
import net.forsteri.createmorepotatoes.entry.ModTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;

@SuppressWarnings("deprecation")
public class StationaryPotatoCanonBlock extends DirectionalAxisKineticBlock implements ITE<StationaryPotatoCanonTileEntity> {

    protected int timeOut;
    public StationaryPotatoCanonBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Class<StationaryPotatoCanonTileEntity> getTileEntityClass() {
        return StationaryPotatoCanonTileEntity.class;
    }

    @Override
    public BlockEntityType<? extends StationaryPotatoCanonTileEntity> getTileEntityType() {
        return ModTileEntities.STATIONARY_POTATO_CANON.get();
    }

    public void neighborChanged(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Block pBlock, @NotNull BlockPos pFromPos, boolean pIsMoving) {
        CreateMorePotatoes.LOGGER.info("timeout: " + timeOut);
        CreateMorePotatoes.LOGGER.info("signal: " + pLevel.hasNeighborSignal(pPos));
        CreateMorePotatoes.LOGGER.info("speed: " + Objects.requireNonNull(this.getTileEntity(pLevel, pPos)).getSpeed());
        CreateMorePotatoes.LOGGER.info("spinning?: " + (Objects.requireNonNull(this.getTileEntity(pLevel, pPos)).getSpeed() != 0));
        if (pLevel.hasNeighborSignal(pPos) && (this.timeOut <= 0) && (Objects.requireNonNull(this.getTileEntity(pLevel, pPos)).getSpeed() != 0))
        {
            this.shoot(pLevel, pPos);
            this.timeOut = 10;
        }
    }

    public void shoot(Level pLevel, BlockPos pPos){
        CreateMorePotatoes.LOGGER.info("SHOOTING");
    }

    @Override
    public void tick(@NotNull BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull Random pRandom) {
        super.tick(pState, pLevel, pPos, pRandom);
        CreateMorePotatoes.LOGGER.info("TICKING");
        timeOut--;
    }
}
