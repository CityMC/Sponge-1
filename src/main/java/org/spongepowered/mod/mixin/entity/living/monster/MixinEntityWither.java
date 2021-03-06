/**
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered.org <http://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.mod.mixin.entity.living.monster;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.monster.Wither;
import org.spongepowered.api.util.annotation.NonnullByDefault;
import org.spongepowered.mod.mixin.Implements;
import org.spongepowered.mod.mixin.Interface;
import org.spongepowered.mod.mixin.Mixin;
import org.spongepowered.mod.mixin.Shadow;

import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

@NonnullByDefault
@Mixin(EntityWither.class)
@Implements(@Interface(iface = Wither.class, prefix = "wither$"))
public abstract class MixinEntityWither extends EntityMob {

    @Shadow
    public abstract int getInvulTime();

    @Shadow
    public abstract void setInvulTime(int invulnerableTicks);

    @Shadow
    public abstract int getWatchedTargetId(int targetId);

    public MixinEntityWither(World worldIn) {
        super(worldIn);
    }

    public int wither$getInvulnerableTicks() {
        return this.getInvulTime();
    }

    public void wither$setInvulnerableTicks(int invulnerableTicks) {
        this.setInvulTime(invulnerableTicks);
    }

    public List<Living> wither$getTargets() {
        List<Living> watchedTargets = new ArrayList<Living>();
        for (int i = 0; i < 2; ++i) {
            int j = this.getWatchedTargetId(i + 1);
            Entity entity = null;

            if (j > 0) {
                entity = this.worldObj.getEntityByID(j);
            }
            if (entity != null) {
                watchedTargets.add((Living)entity);
            }
        }
        return watchedTargets;
    }
}
