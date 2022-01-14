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

package com.quasistellar.speedrunpixeldungeon.ui.changelist;

import com.quasistellar.speedrunpixeldungeon.sprites.ItemSprite;
import com.quasistellar.speedrunpixeldungeon.sprites.ItemSpriteSheet;
import com.quasistellar.speedrunpixeldungeon.ui.Icons;
import com.quasistellar.speedrunpixeldungeon.ui.Window;

import java.util.ArrayList;

public class Speedrun_Changes {
	
	public static void addAllChanges( ArrayList<ChangeInfo> changeInfos ){
		
		ChangeInfo changes = new ChangeInfo( "Speedrun Pixel Dungeon", true, "");
		changes.hardlight( Window.BLUE);
		changeInfos.add(changes);
		
		add_v1_0_Changes(changeInfos);
	}
	
	public static void add_v1_0_Changes(ArrayList<ChangeInfo> changeInfos ){
		
		ChangeInfo changes = new ChangeInfo("v1.0", true, "");
		changes.hardlight(Window.BLUE);
		changeInfos.add(changes);
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.WAND_LIGHTNING, null), "Developer Commentary",
				"_-_ Released January 11th, 2022\n" +
				"\n" +
				"Inspired by SuperNewb's speedrunning contest."));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ARTIFACT_HOURGLASS, null), "Quality of Life",
				"_-_ Restart button in pause menu.\n" +
				"_-_ Descending to floor 1 is instant.\n" +
				"_-_ No story window on floor 1.\n" +
				"_-_ Long-click on play button opens hero selection.\n" +
				"_-_ Gorgeous title screen."));
		
		changes.addButton( new ChangeButton(Icons.get(Icons.CHALLENGE_ON), "Ready to Play",
				"_-_ All guide and catalog pages are collected and read.\n" +
				"_-_ All hero classes are unlocked.\n" +
				"_-_ All badges are collected.\n" +
				"_-_ No pop-up on skeleton key pick up.\n" +
				"_-_ No Intro Scene.\n" +
				"_-_ Challenges unlocked."));

		changes.addButton( new ChangeButton(Icons.get(Icons.CHANGES), "Dead Split",
		"_-_ Timer added.\n" +
				"_-_ Timer starts on the first turn and ends on amulet pick up.\n" +
				"_-_ Each dungeon floor is a split.\n" +
				"_-_ Your runs are compared to lowered-to-pb average (finish one run to notice a difference)."));

		changes.addButton( new ChangeButton(Icons.get(Icons.RANKINGS), "Rankings",
		"_-_ Rankings are split into 4 categories.\n" +
				"_-_ Rankings are sorted by time and contain information about splits.\n" +
				"_-_ Bones are customisable.\n" +
				"_-_ Only one save slot is available."));

	}
}
