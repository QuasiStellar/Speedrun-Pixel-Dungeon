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

package com.quasistellar.speedrunpixeldungeon.items;

import com.quasistellar.speedrunpixeldungeon.Badges;
import com.quasistellar.speedrunpixeldungeon.Challenges;
import com.quasistellar.speedrunpixeldungeon.Dungeon;
import com.quasistellar.speedrunpixeldungeon.SPDSettings;
import com.quasistellar.speedrunpixeldungeon.SpeedrunPixelDungeon;
import com.quasistellar.speedrunpixeldungeon.Statistics;
import com.quasistellar.speedrunpixeldungeon.actors.Actor;
import com.quasistellar.speedrunpixeldungeon.actors.hero.Hero;
import com.quasistellar.speedrunpixeldungeon.scenes.AmuletScene;
import com.quasistellar.speedrunpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.Game;

import java.io.IOException;
import java.util.ArrayList;

public class Amulet extends Item {
	
	private static final String AC_END = "END";
	
	{
		image = ItemSpriteSheet.AMULET;
		
		unique = true;
	}
	
	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( AC_END );
		return actions;
	}
	
	@Override
	public void execute( Hero hero, String action ) {

		super.execute( hero, action );

		if (action.equals(AC_END)) {
			showAmuletScene( false );
		}
	}
	
	@Override
	public boolean doPickUp(Hero hero, int pos) {

		if (Dungeon.finishTime == -1) {
			Dungeon.finishTime = Dungeon.currentTime;
			Dungeon.splits[25] = Dungeon.finishTime - Dungeon.lastSplitTime;
			int i = 1;
			long averageRunTime = 0;
			for (long split : Dungeon.splits) {
				SPDSettings.averageSplitTime(Dungeon.category, i, (SPDSettings.averageSplitTime(Dungeon.category, i) * SPDSettings.numberOfRuns(Dungeon.category) + split) / (SPDSettings.numberOfRuns(Dungeon.category) + 1));
				averageRunTime += SPDSettings.averageSplitTime(Dungeon.category, i);
				i++;
			}
			SPDSettings.numberOfRuns(Dungeon.category, SPDSettings.numberOfRuns(Dungeon.category) + 1);
			SPDSettings.averageRunTime(Dungeon.category, averageRunTime);
			if (Dungeon.finishTime < SPDSettings.personalBest(Dungeon.category)) {
				SPDSettings.personalBest(Dungeon.category, Dungeon.finishTime);
			}
		}

		if (super.doPickUp( hero, pos )) {
			
			if (!Statistics.amuletObtained) {
				Statistics.amuletObtained = true;
				hero.spend(-TIME_TO_PICK_UP);

				//add a delayed actor here so pickup behaviour can fully process.
				Actor.addDelayed(new Actor(){
					@Override
					protected boolean act() {
						Actor.remove(this);
						showAmuletScene( true );
						return false;
					}
				}, -5);
			}
			
			return true;
		} else {
			return false;
		}
	}
	
	private void showAmuletScene( boolean showText ) {
		try {
			Dungeon.saveAll();
			AmuletScene.noText = !showText;
			Game.switchScene( AmuletScene.class, new Game.SceneChangeCallback() {
				@Override
				public void beforeCreate() {

				}

				@Override
				public void afterCreate() {
					Badges.validateVictory();
					Badges.validateChampion(Challenges.activeChallenges());
					Badges.saveGlobal();
				}
			});
		} catch (IOException e) {
			SpeedrunPixelDungeon.reportException(e);
		}
	}
	
	@Override
	public boolean isIdentified() {
		return true;
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}

}
