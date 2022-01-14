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

package com.quasistellar.speedrunpixeldungeon.ui;

import com.quasistellar.speedrunpixeldungeon.Dungeon;
import com.quasistellar.speedrunpixeldungeon.SPDSettings;
import com.quasistellar.speedrunpixeldungeon.scenes.PixelScene;
import com.quasistellar.speedrunpixeldungeon.utils.GLog;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Game;
import com.watabou.noosa.ui.Component;

public class Timer extends Component {

	private BitmapText timerText;
	private BitmapText goalText;
	public BitmapText pbText;

	private final int GREEN = 0x8AFF8A;
	private final int RED = 0xFF8A8A;

	@Override
	protected void createChildren() {
		super.createChildren();
		timerText = new BitmapText(PixelScene.pixelFont);
		add(timerText);
		if (SPDSettings.numberOfRuns(Dungeon.category) > 0) {
			goalText = new BitmapText(PixelScene.pixelFont);
			add(goalText);
			pbText = new BitmapText(PixelScene.pixelFont);
			pbText.text(timeToString(SPDSettings.personalBest(Dungeon.category)));
			add(pbText);
		}
	}

	@Override
	protected void layout() {
		super.layout();
		timerText.x = x;
		timerText.y = y;
		if (SPDSettings.numberOfRuns(Dungeon.category) > 0) {
			goalText.x = x;
			goalText.y = y + 10;
			pbText.x = x;
			pbText.y = y + 20;
		}
	}

	@Override
	public void update() {
		super.update();
		if (Dungeon.finishTime != -1) return;
		timerText.text( timeToString(Dungeon.currentTime) );
		if (SPDSettings.numberOfRuns(Dungeon.category) > 0) {
			if (Dungeon.goalSplitTime > Dungeon.currentTime) {
				timerText.hardlight(GREEN);
			} else {
				timerText.hardlight(RED);
			}
			goalText.text( timeToString(Dungeon.goalSplitTime) );
		} else {
			timerText.hardlight(GREEN);
		}
	}

	public static String timeToString(long time) {
		int minutes = (int)(time / 60000);
		time -= minutes * 60000L;
		int seconds = (int)(time / 1000);
		time -= seconds * 1000L;
		int hundredths = (int)(time / 10);
		return String.format("%s:%s.%s",
				addLeadingZeroes(minutes, 2),
				addLeadingZeroes(seconds, 2),
				addLeadingZeroes(hundredths, 2)
		);
	}

	private static String addLeadingZeroes(long number, int length) {
		String numberString = Long.toString(number);
		int numberLength = numberString.length();
		if (numberLength >= length) return numberString;
		int leadingZeroes = length - numberLength;
		return new String(new char[leadingZeroes]).replace("\0", "0") + numberString;
	}
}
