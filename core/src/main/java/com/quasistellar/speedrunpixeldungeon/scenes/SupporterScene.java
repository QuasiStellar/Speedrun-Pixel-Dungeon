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

import com.quasistellar.speedrunpixeldungeon.Chrome;
import com.quasistellar.speedrunpixeldungeon.SpeedrunPixelDungeon;
import com.quasistellar.speedrunpixeldungeon.messages.Languages;
import com.quasistellar.speedrunpixeldungeon.messages.Messages;
import com.quasistellar.speedrunpixeldungeon.ui.Archs;
import com.quasistellar.speedrunpixeldungeon.ui.ExitButton;
import com.quasistellar.speedrunpixeldungeon.ui.Icons;
import com.quasistellar.speedrunpixeldungeon.ui.RenderedTextBlock;
import com.quasistellar.speedrunpixeldungeon.ui.StyledButton;
import com.quasistellar.speedrunpixeldungeon.ui.Window;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Image;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.DeviceCompat;

public class SupporterScene extends PixelScene {

	private static final int BTN_HEIGHT = 22;
	private static final int GAP = 2;

	@Override
	public void create() {
		super.create();

		uiCamera.visible = false;

		int w = Camera.main.width;
		int h = Camera.main.height;

		int elementWidth = PixelScene.landscape() ? 202 : 120;

		Archs archs = new Archs();
		archs.setSize(w, h);
		add(archs);

		ExitButton btnExit = new ExitButton();
		btnExit.setPos(w - btnExit.width(), 0);
		add(btnExit);

		RenderedTextBlock title = PixelScene.renderTextBlock(Messages.get(this, "title"), 9);
		title.hardlight(Window.TITLE_COLOR);
		title.setPos(
				(w - title.width()) / 2f,
				(20 - title.height()) / 2f
		);
		align(title);
		add(title);

		SupporterMessage msg = new SupporterMessage();
		msg.setSize(elementWidth, 0);
		add(msg);

		StyledButton link1 = new StyledButton(Chrome.Type.GREY_BUTTON_TR, Messages.get(this, "patreon_link")){
			@Override
			protected void onClick() {
				super.onClick();
				String link = "https://www.patreon.com/ShatteredPixel";
				//tracking codes, so that the website knows where this pageview came from
				link += "?utm_source=speedrunpd";
				link += "&utm_medium=supporter_page";
				link += "&utm_campaign=ingame_link";
				DeviceCompat.openURI(link);
			}
		};
		link1.icon(Icons.get(Icons.GOLD));
		link1.textColor(Window.TITLE_COLOR);
		link1.setSize(elementWidth, BTN_HEIGHT);
		add(link1);

		StyledButton link2 = new StyledButton(Chrome.Type.GREY_BUTTON_TR, Messages.get(this, "itchio_link")){
			@Override
			protected void onClick() {
				super.onClick();
				String link = "https://quasistellar.itch.io/hollow-dungeon";
				DeviceCompat.openURI(link);
			}
		};
		link2.icon(Icons.get(Icons.ENERGY));
		link2.textColor(Window.BLUE);
		link2.setSize(elementWidth, BTN_HEIGHT);
		add(link2);

		float elementHeight = msg.height() + BTN_HEIGHT + GAP;

		float top = 16 + (h - 16 - elementHeight)/2f;
		float left = (w-elementWidth)/2f;

		msg.setPos(left, top);
		align(msg);

		link1.setPos(left, msg.bottom()+GAP);
		align(link1);

		link2.setPos(left, link1.bottom()+GAP);
		align(link2);

	}

	@Override
	protected void onBackPressed() {
		SpeedrunPixelDungeon.switchNoFade( TitleScene.class );
	}

	private static class SupporterMessage extends Component {

		NinePatch bg;
		RenderedTextBlock text;

		@Override
		protected void createChildren() {
			bg = Chrome.get(Chrome.Type.GREY_BUTTON_TR);
			add(bg);

			String message = Messages.get(SupporterScene.class, "intro");
			message += "\n\n" + Messages.get(SupporterScene.class, "patreon_msg");
			message += "\n\n" + Messages.get(SupporterScene.class, "itchio_msg");
			message += "\n\n- QuasiStellar";

			text = PixelScene.renderTextBlock(message, 6);
			add(text);
		}

		@Override
		protected void layout() {
			bg.x = x;
			bg.y = y;

			text.maxWidth((int)width - bg.marginHor());
			text.setPos(x + bg.marginLeft(), y + bg.marginTop() + 1);

			height = (text.bottom() + 3) - y;

			height += bg.marginBottom();

			bg.size(width, height);

		}

	}

}
