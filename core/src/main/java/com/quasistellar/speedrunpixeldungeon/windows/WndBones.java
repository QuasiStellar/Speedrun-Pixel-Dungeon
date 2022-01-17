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
import com.quasistellar.speedrunpixeldungeon.Bones;
import com.quasistellar.speedrunpixeldungeon.Chrome;
import com.quasistellar.speedrunpixeldungeon.SPDSettings;
import com.quasistellar.speedrunpixeldungeon.items.Item;
import com.quasistellar.speedrunpixeldungeon.items.armor.LeatherArmor;
import com.quasistellar.speedrunpixeldungeon.items.artifacts.AlchemistsToolkit;
import com.quasistellar.speedrunpixeldungeon.items.artifacts.CapeOfThorns;
import com.quasistellar.speedrunpixeldungeon.items.artifacts.EtherealChains;
import com.quasistellar.speedrunpixeldungeon.items.artifacts.HornOfPlenty;
import com.quasistellar.speedrunpixeldungeon.items.artifacts.LloydsBeacon;
import com.quasistellar.speedrunpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.quasistellar.speedrunpixeldungeon.items.rings.RingOfEnergy;
import com.quasistellar.speedrunpixeldungeon.items.rings.RingOfHaste;
import com.quasistellar.speedrunpixeldungeon.items.wands.WandOfCorrosion;
import com.quasistellar.speedrunpixeldungeon.items.wands.WandOfDisintegration;
import com.quasistellar.speedrunpixeldungeon.items.wands.WandOfFireblast;
import com.quasistellar.speedrunpixeldungeon.items.wands.WandOfLightning;
import com.quasistellar.speedrunpixeldungeon.items.wands.WandOfMagicMissile;
import com.quasistellar.speedrunpixeldungeon.messages.Messages;
import com.quasistellar.speedrunpixeldungeon.scenes.HeroSelectScene;
import com.quasistellar.speedrunpixeldungeon.scenes.PixelScene;
import com.quasistellar.speedrunpixeldungeon.ui.Icons;
import com.quasistellar.speedrunpixeldungeon.ui.ItemSlot;
import com.quasistellar.speedrunpixeldungeon.ui.RedButton;
import com.quasistellar.speedrunpixeldungeon.ui.RenderedTextBlock;
import com.quasistellar.speedrunpixeldungeon.ui.Window;
import com.quasistellar.speedrunpixeldungeon.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.ui.Component;

import java.util.Arrays;
import java.util.LinkedList;

public class WndBones extends Window {

	private static final int BTN_SIZE	= 18;
	private static final float GAP		= 2;
	private static final float BTN_GAP	= 5;
	private static final int WIDTH		= 116;
	private static final int TTL_HEIGHT = 16;

	public WndBones() {
		
		super();

		LinkedList<Item> items = new LinkedList<>(Arrays.asList(
				new WandOfCorrosion(),
				new WandOfLightning(),
				new WandOfFireblast(),
				new WandOfMagicMissile(),
				new WandOfDisintegration(),
				new TimekeepersHourglass(),
				new HornOfPlenty(),
				new EtherealChains(),
				new CapeOfThorns(),
				new LloydsBeacon()
		));

		for (Item item : items) {
			if (item.isUpgradable()) {
				item.upgrade(3);
			}
		}

		RenderedTextBlock title = PixelScene.renderTextBlock( Messages.get(this, "title"), 12 );
		title.hardlight( TITLE_COLOR );
		title.setPos(
				(WIDTH - title.width()) / 2,
				(TTL_HEIGHT - title.height()) / 2
		);
		PixelScene.align(title);
		add( title );
		
		RenderedTextBlock message = PixelScene.renderTextBlock( Messages.get(this, "prompt"), 6 );
		message.maxWidth( WIDTH);
		message.setPos(0, title.bottom() + GAP*2);
		add( message );

		// guys it's fine, i know how to code... really...
		ItemButton btnItem1 = new ItemButton(items.get(0).upgrade(3)) {
			@Override
			protected void onClick() {
				super.onClick();
				onBackPressed();
			}
		};
		btnItem1.setRect( (WIDTH - BTN_SIZE * 5 - BTN_GAP * 4) / 2, message.top() + message.height() + BTN_GAP, BTN_SIZE, BTN_SIZE );
		add(btnItem1);

		ItemButton btnItem2 = new ItemButton(items.get(1)) {
			@Override
			protected void onClick() {
				super.onClick();
				onBackPressed();
			}
		};
		btnItem2.setRect( btnItem1.right() + BTN_GAP, btnItem1.top(), BTN_SIZE, BTN_SIZE );
		add(btnItem2);

		ItemButton btnItem3 = new ItemButton(items.get(2)) {
			@Override
			protected void onClick() {
				super.onClick();
				onBackPressed();
			}
		};
		btnItem3.setRect( btnItem2.right() + BTN_GAP, btnItem1.top(), BTN_SIZE, BTN_SIZE );
		add(btnItem3);

		ItemButton btnItem4 = new ItemButton(items.get(3)) {
			@Override
			protected void onClick() {
				super.onClick();
				onBackPressed();
			}
		};
		btnItem4.setRect( btnItem3.right() + BTN_GAP, btnItem1.top(), BTN_SIZE, BTN_SIZE );
		add(btnItem4);

		ItemButton btnItem5 = new ItemButton(items.get(4)) {
			@Override
			protected void onClick() {
				super.onClick();
				onBackPressed();
			}
		};
		btnItem5.setRect( btnItem4.right() + BTN_GAP, btnItem1.top(), BTN_SIZE, BTN_SIZE );
		add(btnItem5);

		ItemButton btnItem6 = new ItemButton(items.get(5)) {
			@Override
			protected void onClick() {
				super.onClick();
				onBackPressed();
			}
		};
		btnItem6.setRect( btnItem1.left(), btnItem1.bottom() + BTN_GAP, BTN_SIZE, BTN_SIZE );
		add(btnItem6);

		ItemButton btnItem7 = new ItemButton(items.get(6)) {
			@Override
			protected void onClick() {
				super.onClick();
				onBackPressed();
			}
		};
		btnItem7.setRect( btnItem6.right() + BTN_GAP, btnItem6.top(), BTN_SIZE, BTN_SIZE );
		add(btnItem7);

		ItemButton btnItem8 = new ItemButton(items.get(7)) {
			@Override
			protected void onClick() {
				super.onClick();
				onBackPressed();
			}
		};
		btnItem8.setRect( btnItem7.right() + BTN_GAP, btnItem6.top(), BTN_SIZE, BTN_SIZE );
		add(btnItem8);

		ItemButton btnItem9 = new ItemButton(items.get(8)) {
			@Override
			protected void onClick() {
				super.onClick();
				onBackPressed();
			}
		};
		btnItem9.setRect( btnItem8.right() + BTN_GAP, btnItem6.top(), BTN_SIZE, BTN_SIZE );
		add(btnItem9);

		ItemButton btnItem10 = new ItemButton(items.get(9)) {
			@Override
			protected void onClick() {
				super.onClick();
				onBackPressed();
			}
		};
		btnItem10.setRect( btnItem9.right() + BTN_GAP, btnItem6.top(), BTN_SIZE, BTN_SIZE );
		add(btnItem10);

		RedButton btnDisable = new RedButton(Messages.get(this, "disable")) {
			@Override
			protected void onClick() {
				SPDSettings.bones(false);
				onBackPressed();
			}
		};
		btnDisable.enable( SPDSettings.bones() );
		btnDisable.setRect( 0, btnItem6.bottom() + BTN_GAP, WIDTH, 20 );
		add(btnDisable);
		
		resize( WIDTH, (int) btnDisable.bottom() );
	}
	
	public static class ItemButton extends Component {
		
		protected NinePatch bg;
		protected ItemSlot slot;
		
		public Item item;

		ItemButton(Item bonesItem) {
			item = bonesItem;
			slot.item(item);
		}
		
		@Override
		protected void createChildren() {
			super.createChildren();
			
			bg = Chrome.get( Chrome.Type.RED_BUTTON);
			add( bg );

			slot = new ItemSlot() {
				@Override
				protected void onPointerDown() {
					bg.brightness( 1.2f );
					Sample.INSTANCE.play( Assets.Sounds.CLICK );
				}
				@Override
				protected void onPointerUp() {
					bg.resetColor();
				}
				@Override
				protected void onClick() {
					ItemButton.this.onClick();
				}
			};
			slot.enable(true);
			add( slot );
		}

		protected void onClick() {
			Bones.leave(this.item);
			SPDSettings.bones(true);
			SPDSettings.challenges(0);
			Game.scene().update();
		}
		
		@Override
		protected void layout() {
			super.layout();
			
			bg.x = x;
			bg.y = y;
			bg.size( width, height );
			
			slot.setRect( x + 2, y + 2, width - 4, height - 4 );
		}
		
		public void item( Item item ) {
			slot.item( this.item = item );
		}

		public void clear(){
			slot.clear();
		}
	}
}
