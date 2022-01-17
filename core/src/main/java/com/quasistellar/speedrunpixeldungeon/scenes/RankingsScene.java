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

package com.quasistellar.speedrunpixeldungeon.scenes;

import com.quasistellar.speedrunpixeldungeon.Assets;
import com.quasistellar.speedrunpixeldungeon.Rankings;
import com.quasistellar.speedrunpixeldungeon.SPDSettings;
import com.quasistellar.speedrunpixeldungeon.SpeedrunPixelDungeon;
import com.quasistellar.speedrunpixeldungeon.actors.hero.HeroClass;
import com.quasistellar.speedrunpixeldungeon.effects.Flare;
import com.quasistellar.speedrunpixeldungeon.messages.Messages;
import com.quasistellar.speedrunpixeldungeon.sprites.ItemSprite;
import com.quasistellar.speedrunpixeldungeon.sprites.ItemSpriteSheet;
import com.quasistellar.speedrunpixeldungeon.ui.Archs;
import com.quasistellar.speedrunpixeldungeon.ui.ExitButton;
import com.quasistellar.speedrunpixeldungeon.ui.IconButton;
import com.quasistellar.speedrunpixeldungeon.ui.Icons;
import com.quasistellar.speedrunpixeldungeon.ui.RenderedTextBlock;
import com.quasistellar.speedrunpixeldungeon.ui.Timer;
import com.quasistellar.speedrunpixeldungeon.ui.Window;
import com.quasistellar.speedrunpixeldungeon.windows.WndError;
import com.quasistellar.speedrunpixeldungeon.windows.WndRanking;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.ui.Button;
import com.watabou.utils.GameMath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class RankingsScene extends PixelScene {
	
	private static final float ROW_HEIGHT_MAX	= 20;
	private static final float ROW_HEIGHT_MIN	= 12;

	private static final float MAX_ROW_WIDTH    = 160;

	private static final float GAP	= 4;

	public static int category = 0;
	public static final LinkedList<String> categories = new LinkedList<>(Arrays.asList(
			"Any%",
			"Bones%",
			"All Challenges"
	));

	private void nextCategory() {
		category = (category + 1) % categories.size();
		this.create();
	}

	private void previousCategory() {
		category = (category + (categories.size() - 1)) % categories.size();
		this.create();
	}

	@Override
	public void create() {
		
		super.create();

		Music.INSTANCE.playTracks(
				new String[]{Assets.Music.SPEED},
				new float[]{1},
				false);

		uiCamera.visible = false;
		
		int w = Camera.main.width;
		int h = Camera.main.height;

		Archs archs = new Archs();
		archs.setSize( w, h );
		add(archs);
		
		Rankings.INSTANCE.load();

		RenderedTextBlock title = PixelScene.renderTextBlock( categories.get(category), 9);
		title.hardlight(Window.TITLE_COLOR);
		title.setPos(
				(w - title.width()) / 2f,
				(20 - title.height()) / 2f
		);
		align(title);
		add(title);

		ArrayList<Rankings.Record> categoryRecords = new ArrayList<>();
		for (Rankings.Record record: Rankings.INSTANCE.records) {
			if (record.category.equals(categories.get(category))) categoryRecords.add(record);
		}

		if (Rankings.INSTANCE.records.size() > 0) {
			Rankings.Record last = Rankings.INSTANCE.records.get(Rankings.INSTANCE.lastRecord);
			if (last.category.equals(categories.get(category))) {
				if (categoryRecords.size() > Rankings.TABLE_SIZE) {
					categoryRecords = new ArrayList<>(categoryRecords.subList(0, Rankings.TABLE_SIZE - 1));
					boolean inTop = false;
					for (Rankings.Record r: categoryRecords) {
						if (r.score == last.score) {
							inTop = true;
							break;
						}
					}
					if (!inTop) categoryRecords.add(last);
				}
			} else {
				if (categoryRecords.size() > Rankings.TABLE_SIZE - 1) {
					categoryRecords = new ArrayList<>(categoryRecords.subList(0, Rankings.TABLE_SIZE - 1));
				}
			}
		}

		if (categoryRecords.size() > 0) {

			//attempts to give each record as much space as possible, ideally as much space as portrait mode
			float rowHeight = GameMath.gate(ROW_HEIGHT_MIN, (uiCamera.height - 26)/categoryRecords.size(), ROW_HEIGHT_MAX);

			float left = (w - Math.min( MAX_ROW_WIDTH, w )) / 2 + GAP;
			float top = (h - rowHeight  * categoryRecords.size()) / 2;
			
			int pos = 0;
			
			for (Rankings.Record rec : categoryRecords) {
				Record row = new Record( pos, rec.score == Rankings.INSTANCE.records.get(Rankings.INSTANCE.lastRecord).score, rec );
				float offset = 0;
				if (rowHeight <= 14){
					offset = (pos % 2 == 1) ? 5 : -5;
				}
				row.setRect( left+offset, top + pos * rowHeight, w - left * 2, rowHeight );
				add(row);
				
				pos++;
			}

			RenderedTextBlock label = PixelScene.renderTextBlock( 8 );
			label.hardlight( 0xCCCCCC );
			label.setHightlighting(true, Window.SHPX_COLOR);
			label.text( Messages.get(this, "total") + " " + SPDSettings.numberOfAttempts(categories.get(category)));
			add( label );

			label.setPos(
					(w - label.width()) / 2,
					h - label.height() - 2*GAP
			);
			align(label);

		} else {

			RenderedTextBlock noRec = PixelScene.renderTextBlock(Messages.get(this, "no_wins"), 8);
			noRec.hardlight( 0xCCCCCC );
			noRec.setPos(
					(w - noRec.width()) / 2,
					(h - noRec.height()) / 2
			);
			align(noRec);
			add(noRec);
			
		}

		IconButton previousCategoryButton = new IconButton(Icons.get(Icons.ARROW_LEFT)) {
			@Override
			protected void onClick() {
				previousCategory();
			}
		};

		previousCategoryButton.setRect(0, 0, 21, 21);
		previousCategoryButton.setPos(0, 0);
		add(previousCategoryButton);

		IconButton nextCategoryButton = new IconButton(Icons.get(Icons.ARROW_RIGHT)) {
			@Override
			protected void onClick() {
				nextCategory();
			}
		};

		nextCategoryButton.setRect(0, 0, 21, 21);
		nextCategoryButton.setPos(previousCategoryButton.right(), 0);
		add(nextCategoryButton);

		ExitButton btnExit = new ExitButton();
		btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
		add( btnExit );

		fadeIn();
	}
	
	@Override
	protected void onBackPressed() {
		SpeedrunPixelDungeon.switchNoFade(TitleScene.class);
	}
	
	public static class Record extends Button {
		
		private static final float GAP	= 4;
		
		private static final int[] TEXT_WIN	= {0xFFFF88, 0xB2B25F};
		private static final int[] TEXT_LOSE= {0xDDDDDD, 0x888888};
		private static final int FLARE_WIN	= 0x888866;
		private static final int FLARE_LOSE	= 0x666666;
		
		private Rankings.Record rec;
		
		protected ItemSprite shield;
		private Flare flare;
		private BitmapText position;
		private RenderedTextBlock desc;
		private Image steps;
		private BitmapText depth;
		private Image classIcon;
		private BitmapText level;
		
		public Record( int pos, boolean latest, Rankings.Record rec ) {
			super();
			
			this.rec = rec;
			
			if (latest) {
				flare = new Flare( 6, 24 );
				flare.angularSpeed = 90;
				flare.color( rec.win ? FLARE_WIN : FLARE_LOSE );
				addToBack( flare );
			}

			if (pos != Rankings.TABLE_SIZE-1) {
				position.text(Integer.toString(pos + 1));
			} else
				position.text(" ");
			position.measure();
			
			desc.text(Timer.timeToString(rec.score));

			int odd = pos % 2;
			
			if (rec.win) {
				shield.view( ItemSpriteSheet.AMULET, null );
				position.hardlight( TEXT_WIN[odd] );
				desc.hardlight( TEXT_WIN[odd] );
				depth.hardlight( TEXT_WIN[odd] );
				level.hardlight( TEXT_WIN[odd] );
			} else {
				position.hardlight( TEXT_LOSE[odd] );
				desc.hardlight( TEXT_LOSE[odd] );
				depth.hardlight( TEXT_LOSE[odd] );
				level.hardlight( TEXT_LOSE[odd] );

				if (rec.depth != 0){
					depth.text( Integer.toString(rec.depth) );
					depth.measure();
					steps.copy(Icons.DEPTH.get());

					add(steps);
					add(depth);
				}

			}

			if (rec.herolevel != 0){
				level.text( Integer.toString(rec.herolevel) );
				level.measure();
				add(level);
			}
			
			classIcon.copy( Icons.get( rec.heroClass ) );
			if (rec.heroClass == HeroClass.ROGUE){
				//cloak of shadows needs to be brightened a bit
				classIcon.brightness(2f);
			}
		}
		
		@Override
		protected void createChildren() {
			
			super.createChildren();
			
			shield = new ItemSprite( ItemSpriteSheet.TOMB, null );
			add( shield );
			
			position = new BitmapText( PixelScene.pixelFont);
			add( position );
			
			desc = renderTextBlock( 7 );
			add( desc );

			depth = new BitmapText( PixelScene.pixelFont);

			steps = new Image();
			
			classIcon = new Image();
			add( classIcon );

			level = new BitmapText( PixelScene.pixelFont);
		}
		
		@Override
		protected void layout() {
			
			super.layout();
			
			shield.x = x;
			shield.y = y + (height - shield.height) / 2f;
			align(shield);
			
			position.x = shield.x + (shield.width - position.width()) / 2f;
			position.y = shield.y + (shield.height - position.height()) / 2f + 1;
			align(position);
			
			if (flare != null) {
				flare.point( shield.center() );
			}

			classIcon.x = x + width - 16 + (16 - classIcon.width())/2f;
			classIcon.y = shield.y + (16 - classIcon.height())/2f;
			align(classIcon);

			level.x = classIcon.x + (classIcon.width - level.width()) / 2f;
			level.y = classIcon.y + (classIcon.height - level.height()) / 2f + 1;
			align(level);

			steps.x = x + width - 32 + (16 - steps.width())/2f;
			steps.y = shield.y + (16 - steps.height())/2f;
			align(steps);

			depth.x = steps.x + (steps.width - depth.width()) / 2f;
			depth.y = steps.y + (steps.height - depth.height()) / 2f + 1;
			align(depth);

			desc.maxWidth((int)(steps.x - (shield.x + shield.width + GAP)));
			desc.setPos(shield.x + shield.width + GAP, shield.y + (shield.height - desc.height()) / 2f + 1);
			align(desc);
		}
		
		@Override
		protected void onClick() {
			if (rec.gameData != null) {
				parent.add( new WndRanking( rec ) );
			} else {
				parent.add( new WndError( Messages.get(RankingsScene.class, "no_info") ) );
			}
		}
	}
}
