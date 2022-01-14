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

package com.quasistellar.speedrunpixeldungeon.items.weapon.curses;

import com.quasistellar.speedrunpixeldungeon.Dungeon;
import com.quasistellar.speedrunpixeldungeon.actors.Char;
import com.quasistellar.speedrunpixeldungeon.actors.mobs.Mob;
import com.quasistellar.speedrunpixeldungeon.effects.CellEmitter;
import com.quasistellar.speedrunpixeldungeon.effects.Speck;
import com.quasistellar.speedrunpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.quasistellar.speedrunpixeldungeon.items.weapon.Weapon;
import com.quasistellar.speedrunpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.Random;

public class Displacing extends Weapon.Enchantment {

	private static ItemSprite.Glowing BLACK = new ItemSprite.Glowing( 0x000000 );

	@Override
	public int proc(Weapon weapon, Char attacker, Char defender, int damage ) {

		float procChance = 1/12f * procChanceMultiplier(attacker);
		if (Random.Float() < procChance && !defender.properties().contains(Char.Property.IMMOVABLE)){

			int oldpos = defender.pos;
			if (ScrollOfTeleportation.teleportChar(defender)){
				if (Dungeon.level.heroFOV[oldpos]) {
					CellEmitter.get( oldpos ).start( Speck.factory( Speck.LIGHT ), 0.2f, 3 );
				}

				if (defender instanceof Mob && ((Mob) defender).state == ((Mob) defender).HUNTING){
					((Mob) defender).state = ((Mob) defender).WANDERING;
				}
			}
		}

		return damage;
	}

	@Override
	public boolean curse() {
		return true;
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return BLACK;
	}

}
