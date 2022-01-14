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
import com.quasistellar.speedrunpixeldungeon.actors.Actor;
import com.quasistellar.speedrunpixeldungeon.actors.Char;
import com.quasistellar.speedrunpixeldungeon.actors.buffs.Bleeding;
import com.quasistellar.speedrunpixeldungeon.actors.buffs.Blindness;
import com.quasistellar.speedrunpixeldungeon.actors.buffs.Buff;
import com.quasistellar.speedrunpixeldungeon.actors.buffs.Cripple;
import com.quasistellar.speedrunpixeldungeon.actors.mobs.Mob;
import com.quasistellar.speedrunpixeldungeon.scenes.GameScene;
import com.watabou.noosa.audio.Sample;

public class FlashingTrap extends Trap {

	{
		color = GREY;
		shape = STARS;

		disarmedByActivation = false;
		avoidsHallways = true;
	}

	@Override
	public void activate() {
		
		Char c = Actor.findChar( pos );
		
		if (c != null) {
			int damage = Math.max( 0,  (4 + Dungeon.depth/2) - c.drRoll()/2 );
			Buff.affect( c, Bleeding.class ).set( damage );
			Buff.prolong( c, Blindness.class, Blindness.DURATION );
			Buff.prolong( c, Cripple.class, Cripple.DURATION*2f );
			
			if (c instanceof Mob) {
				if (((Mob)c).state == ((Mob)c).HUNTING) ((Mob)c).state = ((Mob)c).WANDERING;
				((Mob)c).beckon( Dungeon.level.randomDestination( c ) );
			}
		}
		
		if (Dungeon.level.heroFOV[pos]) {
			GameScene.flash(0x80FFFFFF);
			Sample.INSTANCE.play( Assets.Sounds.BLAST );
		}
		
	}

}
