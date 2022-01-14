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

package com.quasistellar.speedrunpixeldungeon.items.weapon.missiles;

import com.quasistellar.speedrunpixeldungeon.Assets;
import com.quasistellar.speedrunpixeldungeon.actors.Char;
import com.quasistellar.speedrunpixeldungeon.actors.buffs.Buff;
import com.quasistellar.speedrunpixeldungeon.actors.buffs.Cripple;
import com.quasistellar.speedrunpixeldungeon.sprites.ItemSpriteSheet;

public class Bolas extends MissileWeapon {
	
	{
		image = ItemSpriteSheet.BOLAS;
		hitSound = Assets.Sounds.HIT;
		hitSoundPitch = 1f;
		
		tier = 3;
		baseUses = 5;
	}
	
	@Override
	public int max(int lvl) {
		return  3 * tier +                      //9 base, down from 15
				(tier == 1 ? 2*lvl : tier*lvl); //scaling unchanged
	}
	
	@Override
	public int proc( Char attacker, Char defender, int damage ) {
		Buff.prolong( defender, Cripple.class, Cripple.DURATION );
		return super.proc( attacker, defender, damage );
	}
}
