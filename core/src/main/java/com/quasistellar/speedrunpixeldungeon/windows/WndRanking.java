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

package com.quasistellar.speedrunpixeldungeon.windows;

import com.quasistellar.speedrunpixeldungeon.Assets;
import com.quasistellar.speedrunpixeldungeon.Badges;
import com.quasistellar.speedrunpixeldungeon.Dungeon;
import com.quasistellar.speedrunpixeldungeon.Rankings;
import com.quasistellar.speedrunpixeldungeon.Statistics;
import com.quasistellar.speedrunpixeldungeon.actors.hero.Belongings;
import com.quasistellar.speedrunpixeldungeon.actors.hero.HeroSubClass;
import com.quasistellar.speedrunpixeldungeon.items.Item;
import com.quasistellar.speedrunpixeldungeon.messages.Messages;
import com.quasistellar.speedrunpixeldungeon.scenes.PixelScene;
import com.quasistellar.speedrunpixeldungeon.sprites.HeroSprite;
import com.quasistellar.speedrunpixeldungeon.ui.BadgesGrid;
import com.quasistellar.speedrunpixeldungeon.ui.BadgesList;
import com.quasistellar.speedrunpixeldungeon.ui.Icons;
import com.quasistellar.speedrunpixeldungeon.ui.ItemSlot;
import com.quasistellar.speedrunpixeldungeon.ui.RedButton;
import com.quasistellar.speedrunpixeldungeon.ui.RenderedTextBlock;
import com.quasistellar.speedrunpixeldungeon.ui.TalentButton;
import com.quasistellar.speedrunpixeldungeon.ui.TalentsPane;
import com.quasistellar.speedrunpixeldungeon.ui.Timer;
import com.quasistellar.speedrunpixeldungeon.ui.Window;
import com.quasistellar.speedrunpixeldungeon.utils.GLog;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.ui.Button;
import com.watabou.noosa.ui.Component;

import java.util.Locale;

public class WndRanking extends WndTabbed {
	
	private static final int WIDTH			= 115;
	private static final int HEIGHT			= 144;
	private static final int TTL_HEIGHT		= 16;
	
	private static Thread thread;
	private String error = null;
	
	private Image busy;
	
	public WndRanking( final Rankings.Record rec ) {
		
		super();
		resize( WIDTH, HEIGHT );
		
		if (thread != null){
			hide();
			return;
		}
		
		thread = new Thread() {
			@Override
			public void run() {
				try {
					Badges.loadGlobal();
					Rankings.INSTANCE.loadGameData( rec );
				} catch ( Exception e ) {
					error = Messages.get(WndRanking.class, "error");
				}
			}
		};

		busy = Icons.BUSY.get();
		busy.origin.set( busy.width / 2, busy.height / 2 );
		busy.angularSpeed = 720;
		busy.x = (WIDTH - busy.width) / 2;
		busy.y = (HEIGHT - busy.height) / 2;
		add( busy );
		
		thread.start();
	}
	
	@Override
	public void update() {
		super.update();
		
		if (thread != null && !thread.isAlive() && busy != null) {
			if (error == null) {
				remove( busy );
				busy = null;
				if (Dungeon.hero != null) {
					createControls();
				} else {
					hide();
				}
			} else {
				hide();
				Game.scene().add( new WndError( error ) );
			}
		}
	}
	
	@Override
	public void destroy() {
		super.destroy();
		thread = null;
	}
	
	private void createControls() {
		
		Icons[] icons =
			{Icons.RANKINGS, Icons.BACKPACK_LRG, Icons.CHANGES};
		Group[] pages =
			{new StatsTab(), new ItemsTab(), new SplitsTab()};
		
		for (int i=0; i < pages.length; i++) {
			
			add( pages[i] );
			
			Tab tab = new RankingTab( icons[i], pages[i] );
			add( tab );
		}

		layoutTabs();
		
		select( 0 );
	}

	private class RankingTab extends IconTab {
		
		private Group page;
		
		public RankingTab( Icons icon, Group page ) {
			super( Icons.get(icon) );
			this.page = page;
		}
		
		@Override
		protected void select( boolean value ) {
			super.select( value );
			if (page != null) {
				page.visible = page.active = selected;
			}
		}
	}
	
	private class StatsTab extends Group {

		private int GAP	= 4;
		
		public StatsTab() {
			super();
			
			String heroClass = Dungeon.hero.className();
			
			IconTitle title = new IconTitle();
			title.icon( HeroSprite.avatar( Dungeon.hero.heroClass, Dungeon.hero.tier() ) );
			title.label( Messages.get(this, "title", Dungeon.hero.lvl, heroClass ).toUpperCase( Locale.ENGLISH ) );
			title.color(Window.TITLE_COLOR);
			title.setRect( 0, 0, WIDTH, 0 );
			add( title );
			
			float pos = title.bottom() + GAP;

			RedButton btnTalents = new RedButton( Messages.get(this, "talents") ){
				@Override
				protected void onClick() {
					//removes talents from upper tiers
					int tiers = 1;
					if (Dungeon.hero.lvl >= 6) tiers++;
					if (Dungeon.hero.lvl >= 12 && Dungeon.hero.subClass != HeroSubClass.NONE) tiers++;
					if (Dungeon.hero.lvl >= 20 && Dungeon.hero.armorAbility != null) tiers++;
					while (Dungeon.hero.talents.size() > tiers){
						Dungeon.hero.talents.remove(Dungeon.hero.talents.size()-1);
					}
					Game.scene().addToFront( new Window(){
						{
							TalentsPane p = new TalentsPane(TalentButton.Mode.INFO);
							add(p);
							p.setPos(0, 0);
							p.setSize(120, p.content().height());
							resize((int)p.width(), (int)p.height());
							p.setPos(0, 0);
						}
					});
				}
			};
			btnTalents.icon(Icons.get(Icons.TALENT));
			btnTalents.setRect( (WIDTH - btnTalents.reqWidth()+2)/2, pos, btnTalents.reqWidth()+2 , 16 );
			add(btnTalents);

			pos = btnTalents.bottom();

			if (Dungeon.challenges > 0) {
				RedButton btnChallenges = new RedButton( Messages.get(this, "challenges") ) {
					@Override
					protected void onClick() {
						Game.scene().add( new WndChallenges( Dungeon.challenges, false ) );
					}
				};

				btnChallenges.icon(Icons.get(Icons.CHALLENGE_ON));
				btnChallenges.setSize( btnChallenges.reqWidth()+2, 16 );
				add( btnChallenges );

				float left = (WIDTH - btnTalents.width() - btnChallenges.width())/3f;

				btnTalents.setPos(left, btnTalents.top());
				btnChallenges.setPos(btnTalents.right() + left, btnTalents.top());
			}

			pos += GAP;

			int strBonus = Dungeon.hero.STR() - Dungeon.hero.STR;
			if (strBonus > 0)       pos = statSlot(this, Messages.get(this, "str"), Dungeon.hero.STR + " + " + strBonus, pos);
			else if (strBonus < 0)  pos = statSlot(this, Messages.get(this, "str"), Dungeon.hero.STR + " - " + -strBonus, pos );
			else                    pos = statSlot(this, Messages.get(this, "str"), Integer.toString(Dungeon.hero.STR), pos);
			pos = statSlot( this, Messages.get(this, "health"), Integer.toString( Dungeon.hero.HT ), pos );
			
			pos += GAP;
			
			pos = statSlot( this, Messages.get(this, "duration"), Integer.toString( (int)Statistics.duration ), pos );
			
			pos += GAP;
			
			pos = statSlot( this, Messages.get(this, "depth"), Integer.toString( Statistics.deepestFloor ), pos );
			pos = statSlot( this, Messages.get(this, "enemies"), Integer.toString( Statistics.enemiesSlain ), pos );
			pos = statSlot( this, Messages.get(this, "gold"), Integer.toString( Statistics.goldCollected ), pos );
			
			pos += GAP;
			
			pos = statSlot( this, Messages.get(this, "food"), Integer.toString( Statistics.foodEaten ), pos );
			pos = statSlot( this, Messages.get(this, "alchemy"), Integer.toString( Statistics.itemsCrafted ), pos );
			pos = statSlot( this, Messages.get(this, "ankhs"), Integer.toString( Statistics.ankhsUsed ), pos );
		}
		
		private float statSlot( Group parent, String label, String value, float pos ) {
			
			RenderedTextBlock txt = PixelScene.renderTextBlock( label, 7 );
			txt.setPos(0, pos);
			parent.add( txt );
			
			txt = PixelScene.renderTextBlock( value, 7 );
			txt.setPos(WIDTH * 0.7f, pos);
			PixelScene.align(txt);
			parent.add( txt );
			
			return pos + GAP + txt.height();
		}
	}
	
	private class ItemsTab extends Group {
		
		private float pos;
		
		public ItemsTab() {
			super();
			
			Belongings stuff = Dungeon.hero.belongings;
			if (stuff.weapon != null) {
				addItem( stuff.weapon );
			}
			if (stuff.armor != null) {
				addItem( stuff.armor );
			}
			if (stuff.artifact != null) {
				addItem( stuff.artifact );
			}
			if (stuff.misc != null) {
				addItem( stuff.misc );
			}
			if (stuff.ring != null) {
				addItem( stuff.ring );
			}

			pos = 0;
			for (int i = 0; i < 4; i++){
				if (Dungeon.quickslot.getItem(i) != null){
					QuickSlotButton slot = new QuickSlotButton(Dungeon.quickslot.getItem(i));

					slot.setRect( pos, 120, 28, 23 );

					add(slot);

				} else {
					ColorBlock bg = new ColorBlock( 28, 23, 0x9953564D );
					bg.x = pos;
					bg.y = 120;
					add(bg);
				}
				pos += 29;
			}
		}
		
		private void addItem( Item item ) {
			ItemButton slot = new ItemButton( item );
			slot.setRect( 0, pos, width, ItemButton.HEIGHT );
			add( slot );
			
			pos += slot.height() + 1;
		}
	}
	
	private class SplitsTab extends Group {
		
		public SplitsTab() {
			super();

			RenderedTextBlock title = PixelScene.renderTextBlock( Messages.get(this, "splits"), 12 );
			title.hardlight( TITLE_COLOR );
			title.setPos(
					(WIDTH - title.width()) / 2,
					(TTL_HEIGHT - title.height()) / 2
			);
			PixelScene.align(title);
			add( title );

			long[] splits = Dungeon.splits;

			float x = 0;
			float y = title.bottom() + 6;
			int floor = 1;
			for (Long split : splits) {
				RenderedTextBlock txt = PixelScene.renderTextBlock(String.format("_%s_: %s", floor, Timer.timeToString(split)), 6 );
				txt.setPos(x, y);
				add( txt );
				floor++;
				y += 8;
				if (y > 8 * 8 + title.bottom() + 6) {
					x += (float) WIDTH / 3;
					y = title.bottom() + 6;
				}
			}
			y = title.bottom() + 80;
			x = 0;
			RenderedTextBlock txt1 = PixelScene.renderTextBlock(String.format("_Sewers_: %s", Timer.timeToString(
					splits[0] +
					splits[1] +
					splits[2] +
					splits[3] +
					splits[4])), 8 );
			txt1.setPos(x, y);
			add( txt1 );
			y += 10;
			x += 10;
			RenderedTextBlock txt2 = PixelScene.renderTextBlock(String.format("_Prison_: %s", Timer.timeToString(
					splits[5] +
					splits[6] +
					splits[7] +
					splits[8] +
					splits[9])), 8 );
			txt2.setPos(x, y);
			add( txt2 );
			y += 10;
			x += 10;
			RenderedTextBlock txt3 = PixelScene.renderTextBlock(String.format("_Caves_: %s", Timer.timeToString(
					splits[10] +
					splits[11] +
					splits[12] +
					splits[13] +
					splits[14])), 8 );
			txt3.setPos(x, y);
			add( txt3 );
			y += 10;
			x += 10;
			RenderedTextBlock txt4 = PixelScene.renderTextBlock(String.format("_Metropolis_: %s", Timer.timeToString(
					splits[15] +
					splits[16] +
					splits[17] +
					splits[18] +
					splits[19])), 8 );
			txt4.setPos(x, y);
			add( txt4 );
			y += 10;
			x += 10;
			RenderedTextBlock txt5 = PixelScene.renderTextBlock(String.format("_Demon Halls_: %s", Timer.timeToString(
					splits[20] +
					splits[21] +
					splits[22] +
					splits[23] +
					splits[24] +
					splits[25])), 8 );
			txt5.setPos(x, y);
			add( txt5 );
			y += 10;
			x += 10;
		}
	}

	private class ItemButton extends Button {
		
		public static final int HEIGHT	= 23;
		
		private Item item;
		
		private ItemSlot slot;
		private ColorBlock bg;
		private RenderedTextBlock name;
		
		public ItemButton( Item item ) {
			
			super();

			this.item = item;
			
			slot.item( item );
			if (item.cursed && item.cursedKnown) {
				bg.ra = +0.2f;
				bg.ga = -0.1f;
			} else if (!item.isIdentified()) {
				bg.ra = 0.1f;
				bg.ba = 0.1f;
			}
		}
		
		@Override
		protected void createChildren() {
			
			bg = new ColorBlock( 28, HEIGHT, 0x9953564D );
			add( bg );
			
			slot = new ItemSlot();
			add( slot );
			
			name = PixelScene.renderTextBlock( 7 );
			add( name );
			
			super.createChildren();
		}
		
		@Override
		protected void layout() {
			bg.x = x;
			bg.y = y;
			
			slot.setRect( x, y, 28, HEIGHT );
			PixelScene.align(slot);
			
			name.maxWidth((int)(width - slot.width() - 2));
			name.text(Messages.titleCase(item.name()));
			name.setPos(
					slot.right()+2,
					y + (height - name.height()) / 2
			);
			PixelScene.align(name);
			
			super.layout();
		}
		
		@Override
		protected void onPointerDown() {
			bg.brightness( 1.5f );
			Sample.INSTANCE.play( Assets.Sounds.CLICK, 0.7f, 0.7f, 1.2f );
		}
		
		protected void onPointerUp() {
			bg.brightness( 1.0f );
		}
		
		@Override
		protected void onClick() {
			Game.scene().add( new WndInfoItem( item ) );
		}
	}

	private class QuickSlotButton extends ItemSlot{

		public static final int HEIGHT	= 23;

		private Item item;
		private ColorBlock bg;

		QuickSlotButton(Item item){
			super(item);
			this.item = item;
		}

		@Override
		protected void createChildren() {
			bg = new ColorBlock( 28, HEIGHT, 0x9953564D );
			add( bg );

			super.createChildren();
		}

		@Override
		protected void layout() {
			bg.x = x;
			bg.y = y;

			super.layout();
		}

		@Override
		protected void onPointerDown() {
			bg.brightness( 1.5f );
			Sample.INSTANCE.play( Assets.Sounds.CLICK, 0.7f, 0.7f, 1.2f );
		}

		protected void onPointerUp() {
			bg.brightness( 1.0f );
		}

		@Override
		protected void onClick() {
			Game.scene().add(new WndInfoItem(item));
		}
	}
}
