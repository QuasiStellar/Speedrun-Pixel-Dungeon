/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.quasistellar.speedrunpixeldungeon.levels.traps;

import com.quasistellar.speedrunpixeldungeon.Assets;
import com.quasistellar.speedrunpixeldungeon.Dungeon;
import com.quasistellar.speedrunpixeldungeon.actors.blobs.Blob;
import com.quasistellar.speedrunpixeldungeon.actors.blobs.ToxicGas;
import com.quasistellar.speedrunpixeldungeon.scenes.GameScene;
import com.watabou.noosa.audio.Sample;

public class ToxicTrap extends Trap{

	{
		color = GREEN;
		shape = GRILL;
	}

	@Override
	public void activate() {

		GameScene.add( Blob.seed( pos, 300 + 20 * Dungeon.depth, ToxicGas.class ) );
		Sample.INSTANCE.play(Assets.Sounds.GAS);

	}
}
