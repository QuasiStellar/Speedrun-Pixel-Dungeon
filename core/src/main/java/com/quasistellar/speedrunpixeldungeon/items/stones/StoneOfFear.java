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

package com.quasistellar.speedrunpixeldungeon.items.stones;

import com.quasistellar.speedrunpixeldungeon.Assets;
import com.quasistellar.speedrunpixeldungeon.Dungeon;
import com.quasistellar.speedrunpixeldungeon.actors.Actor;
import com.quasistellar.speedrunpixeldungeon.actors.Char;
import com.quasistellar.speedrunpixeldungeon.actors.buffs.Buff;
import com.quasistellar.speedrunpixeldungeon.actors.buffs.Terror;
import com.quasistellar.speedrunpixeldungeon.effects.Flare;
import com.quasistellar.speedrunpixeldungeon.sprites.ItemSpriteSheet;
import com.quasistellar.speedrunpixeldungeon.tiles.DungeonTilemap;
import com.watabou.noosa.audio.Sample;

public class StoneOfFear extends Runestone {
	
	{
		image = ItemSpriteSheet.STONE_FEAR;
	}
	
	@Override
	protected void activate(int cell) {

		Char ch = Actor.findChar( cell );

		if (ch != null){
			Buff.affect( ch, Terror.class, Terror.DURATION ).object = curUser.id();
		}

		new Flare( 5, 16 ).color( 0xFF0000, true ).show(Dungeon.hero.sprite.parent, DungeonTilemap.tileCenterToWorld(cell), 2f );
		Sample.INSTANCE.play( Assets.Sounds.READ );
		
	}
	
}
