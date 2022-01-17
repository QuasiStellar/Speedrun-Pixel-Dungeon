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

import com.quasistellar.speedrunpixeldungeon.Badges;
import com.quasistellar.speedrunpixeldungeon.Chrome;
import com.quasistellar.speedrunpixeldungeon.Dungeon;
import com.quasistellar.speedrunpixeldungeon.GamesInProgress;
import com.quasistellar.speedrunpixeldungeon.SPDSettings;
import com.quasistellar.speedrunpixeldungeon.SpeedrunPixelDungeon;
import com.quasistellar.speedrunpixeldungeon.actors.hero.HeroClass;
import com.quasistellar.speedrunpixeldungeon.journal.Journal;
import com.quasistellar.speedrunpixeldungeon.messages.Messages;
import com.quasistellar.speedrunpixeldungeon.ui.ActionIndicator;
import com.quasistellar.speedrunpixeldungeon.ui.ExitButton;
import com.quasistellar.speedrunpixeldungeon.ui.IconButton;
import com.quasistellar.speedrunpixeldungeon.ui.Icons;
import com.quasistellar.speedrunpixeldungeon.ui.RenderedTextBlock;
import com.quasistellar.speedrunpixeldungeon.ui.StyledButton;
import com.quasistellar.speedrunpixeldungeon.ui.Window;
import com.quasistellar.speedrunpixeldungeon.windows.WndBones;
import com.quasistellar.speedrunpixeldungeon.windows.WndChallenges;
import com.quasistellar.speedrunpixeldungeon.windows.WndHeroInfo;
import com.quasistellar.speedrunpixeldungeon.windows.WndMessage;
import com.watabou.gltextures.TextureCache;
import com.watabou.input.PointerEvent;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.PointerArea;
import com.watabou.utils.DeviceCompat;
import com.watabou.utils.GameMath;

import java.util.ArrayList;

public class HeroSelectScene extends PixelScene {

	private Image background;
	private RenderedTextBlock prompt;

	//fading UI elements
	private ArrayList<StyledButton> heroBtns = new ArrayList<>();
	private StyledButton startBtn;
	private IconButton infoButton;
	private IconButton challengeButton;
	private IconButton bonesButton;
	private IconButton btnExit;

	@Override
	public void create() {
		super.create();

		Dungeon.hero = null;

		Badges.loadGlobal();
		Journal.loadGlobal();

		background = new Image(HeroClass.WARRIOR.splashArt()){
			@Override
			public void update() {
				if (rm > 1f){
					rm -= Game.elapsed;
					gm = bm = rm;
				} else {
					rm = gm = bm = 1;
				}
			}
		};
		background.scale.set(Camera.main.height/background.height);

		background.x = (Camera.main.width - background.width())/2f;
		background.y = (Camera.main.height - background.height())/2f;
		background.visible = false;
		PixelScene.align(background);
		add(background);

		if (background.x > 0){
			Image fadeLeft = new Image(TextureCache.createGradient(0xFF000000, 0x00000000));
			fadeLeft.x = background.x-2;
			fadeLeft.scale.set(4, background.height());
			add(fadeLeft);

			Image fadeRight = new Image(fadeLeft);
			fadeRight.x = background.x + background.width() + 2;
			fadeRight.y = background.y + background.height();
			fadeRight.angle = 180;
			add(fadeRight);
		}

		prompt = PixelScene.renderTextBlock(Messages.get(this, "title"), 12);
		prompt.hardlight(Window.TITLE_COLOR);
		prompt.setPos( (Camera.main.width - prompt.width())/2f, (Camera.main.height - HeroBtn.HEIGHT - prompt.height() - 4));
		PixelScene.align(prompt);
		add(prompt);

		startBtn = new StyledButton(Chrome.Type.GREY_BUTTON_TR, ""){
			@Override
			protected void onClick() {
				super.onClick();

				if (GamesInProgress.selectedClass == null) return;

				Dungeon.hero = null;
				ActionIndicator.action = null;
				InterlevelScene.mode = InterlevelScene.Mode.DESCEND;

				Game.switchScene( InterlevelScene.class );
			}
		};
		startBtn.icon(Icons.get(Icons.ENTER));
		startBtn.setSize(80, 21);
		startBtn.setPos((Camera.main.width - startBtn.width())/2f, (Camera.main.height - HeroBtn.HEIGHT + 2 - startBtn.height()));
		add(startBtn);
		startBtn.visible = false;

		infoButton = new IconButton(Icons.get(Icons.INFO)){
			@Override
			protected void onClick() {
				super.onClick();
				SpeedrunPixelDungeon.scene().addToFront(new WndHeroInfo(GamesInProgress.selectedClass));
			}
		};
		infoButton.visible = false;
		infoButton.setSize(21, 21);
		add(infoButton);

		HeroClass[] classes = HeroClass.values();

		int btnWidth = HeroBtn.MIN_WIDTH;
		int curX = (Camera.main.width - btnWidth * classes.length)/2;
		if (curX > 0){
			btnWidth += Math.min(curX/(classes.length/2), 15);
			curX = (Camera.main.width - btnWidth * classes.length)/2;
		}

		int heroBtnleft = curX;
		for (HeroClass cl : classes){
			HeroBtn button = new HeroBtn(cl);
			button.setRect(curX, Camera.main.height-HeroBtn.HEIGHT+3, btnWidth, HeroBtn.HEIGHT);
			curX += btnWidth;
			add(button);
			heroBtns.add(button);
		}

		challengeButton = new IconButton(
				Icons.get( SPDSettings.challenges() > 0 ? Icons.CHALLENGE_ON :Icons.CHALLENGE_OFF)){
			@Override
			protected void onClick() {
				SpeedrunPixelDungeon.scene().addToFront(new WndChallenges(SPDSettings.challenges(), true) {
					public void onBackPressed() {
						super.onBackPressed();
						icon(Icons.get(SPDSettings.challenges() > 0 ? Icons.CHALLENGE_ON : Icons.CHALLENGE_OFF));
					}
				} );
			}

			@Override
			public void update() {
				if( !visible && GamesInProgress.selectedClass != null){
					visible = true;
				}
				if (visible) {
					icon(Icons.get(SPDSettings.challenges() > 0 ? Icons.CHALLENGE_ON : Icons.CHALLENGE_OFF));
				}
				super.update();
			}
		};

		bonesButton = new IconButton(
				Icons.get( SPDSettings.bones() ? Icons.BONES_ON :Icons.BONES_OFF)){
			@Override
			protected void onClick() {
				SpeedrunPixelDungeon.scene().addToFront(new WndBones() {
					public void onBackPressed() {
						super.onBackPressed();
						icon(Icons.get(SPDSettings.bones() ? Icons.BONES_ON : Icons.BONES_OFF));
					}
				} );
			}

			@Override
			public void update() {
				if( !visible && GamesInProgress.selectedClass != null){
					visible = true;
				}
				if (visible) {
					icon(Icons.get(SPDSettings.bones() ? Icons.BONES_ON : Icons.BONES_OFF));
				}
				super.update();
			}
		};

		challengeButton.setRect(heroBtnleft + 16, Camera.main.height-HeroBtn.HEIGHT-16, 21, 21);
		challengeButton.visible = false;
		add(challengeButton);

		bonesButton.setRect(challengeButton.left() - 16, Camera.main.height-HeroBtn.HEIGHT-16, 21, 21);
		bonesButton.visible = false;
		add(bonesButton);

		btnExit = new ExitButton();
		btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
		add(btnExit);

		PointerArea fadeResetter = new PointerArea(0, 0, Camera.main.width, Camera.main.height){
			@Override
			public boolean onSignal(PointerEvent event) {
				resetFade();
				return false;
			}
		};
		add(fadeResetter);
		resetFade();

		if (GamesInProgress.selectedClass != null){
			setSelectedHero(GamesInProgress.selectedClass);
		}

		fadeIn();

	}

	private void setSelectedHero(HeroClass cl){
		GamesInProgress.selectedClass = cl;

		background.texture( cl.splashArt() );
		background.visible = true;
		background.hardlight(1.5f,1.5f,1.5f);

		prompt.visible = false;
		startBtn.visible = true;
		startBtn.text(Messages.titleCase(cl.title()));
		startBtn.textColor(Window.TITLE_COLOR);
		startBtn.setSize(startBtn.reqWidth() + 8, 21);
		startBtn.setPos((Camera.main.width - startBtn.width())/2f, startBtn.top());
		PixelScene.align(startBtn);

		infoButton.visible = true;
		infoButton.setPos(startBtn.right(), startBtn.top());

		challengeButton.visible = true;
		challengeButton.setPos(startBtn.left()-challengeButton.width(), startBtn.top());

		bonesButton.visible = true;
		bonesButton.setPos(challengeButton.left()-bonesButton.width(), challengeButton.top());
	}

	private float uiAlpha;

	@Override
	public void update() {
		super.update();
		challengeButton.update();
		bonesButton.update();
		//do not fade when a window is open
		for (Object v : members){
			if (v instanceof Window) resetFade();
		}
		if (GamesInProgress.selectedClass != null) {
			if (uiAlpha > 0f){
				uiAlpha -= Game.elapsed/4f;
			}
			float alpha = GameMath.gate(0f, uiAlpha, 1f);
			for (StyledButton b : heroBtns){
				b.alpha(alpha);
			}
			startBtn.alpha(alpha);
			btnExit.icon().alpha(alpha);
			challengeButton.icon().alpha(alpha);
			bonesButton.icon().alpha(alpha);
			infoButton.icon().alpha(alpha);
		}
	}

	private void resetFade(){
		//starts fading after 4 seconds, fades over 4 seconds.
		uiAlpha = 2f;
	}

	@Override
	protected void onBackPressed() {
		if (btnExit.visible){
			SpeedrunPixelDungeon.switchScene(TitleScene.class);
		} else {
			super.onBackPressed();
		}
	}

	private class HeroBtn extends StyledButton {

		private HeroClass cl;

		private static final int MIN_WIDTH = 20;
		private static final int HEIGHT = 24;

		HeroBtn ( HeroClass cl ){
			super(Chrome.Type.GREY_BUTTON_TR, "");

			this.cl = cl;

			icon(new Image(cl.spritesheet(), 0, 90, 12, 15));

		}

		@Override
		public void update() {
			super.update();
			if (cl != GamesInProgress.selectedClass){
				if (!cl.isUnlocked()){
					icon.brightness(0.1f);
				} else {
					icon.brightness(0.6f);
				}
			} else {
				icon.brightness(1f);
			}
		}

		@Override
		protected void onClick() {
			super.onClick();

			if( !cl.isUnlocked() ){
				SpeedrunPixelDungeon.scene().addToFront( new WndMessage(cl.unlockMsg()));
			} else if (GamesInProgress.selectedClass == cl) {
				SpeedrunPixelDungeon.scene().add(new WndHeroInfo(cl));
			} else {
				setSelectedHero(cl);
			}
		}
	}

}
