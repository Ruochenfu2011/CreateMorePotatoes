package net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCannon;

import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.Create;
import com.simibubi.create.CreateClient;
import com.simibubi.create.content.contraptions.base.DirectionalKineticBlock;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.curiosities.armor.BackTankUtil;
import com.simibubi.create.content.curiosities.weapons.*;
import com.simibubi.create.content.curiosities.zapper.ShootableGadgetItemMethods;
import com.simibubi.create.foundation.utility.VecHelper;
import net.forsteri.createmorepotatoes.CreateMorePotatoes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

public class StationaryPotatoCannonTileEntity extends KineticTileEntity{

    protected int timeOut;

    public ItemStack stack = ItemStack.EMPTY;

    public StationaryPotatoCannonTileEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    public void tick() {
        super.tick();
        if (Objects.requireNonNull(getLevel()).hasNeighborSignal(getBlockPos()) && (this.timeOut <= 0) && (this.getSpeed() != 0))
        {
            this.shoot();
        }
        timeOut--;
    }

    public void shoot() {
        CreateMorePotatoes.LOGGER.info("SHOOTING");
        PotatoProjectileEntity projectile = AllEntityTypes.POTATO_PROJECTILE.create(Objects.requireNonNull(getLevel()));
        assert projectile != null;
        projectile.setPos(getBlockPos().getX(), getBlockPos().getX(), getBlockPos().getX());
        projectile.setItem(stack);
        getLevel().addFreshEntity(projectile);
        stack.shrink(1);
        timeOut = stack == ItemStack.EMPTY? 0 : PotatoProjectileTypeManager.getTypeForStack(stack).get().getReloadTicks() - 1;
    }
}
