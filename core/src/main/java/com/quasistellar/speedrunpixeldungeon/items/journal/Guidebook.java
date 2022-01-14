package com.quasistellar.speedrunpixeldungeon.items.journal;

import com.quasistellar.speedrunpixeldungeon.Assets;
import com.quasistellar.speedrunpixeldungeon.actors.hero.Hero;
import com.quasistellar.speedrunpixeldungeon.items.Item;
import com.quasistellar.speedrunpixeldungeon.journal.Document;
import com.quasistellar.speedrunpixeldungeon.scenes.GameScene;
import com.quasistellar.speedrunpixeldungeon.sprites.ItemSpriteSheet;
import com.quasistellar.speedrunpixeldungeon.windows.WndJournal;
import com.quasistellar.speedrunpixeldungeon.windows.WndStory;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class Guidebook extends Item {

	{
		image = ItemSpriteSheet.MASTERY;
	}

	@Override
	public final boolean doPickUp(Hero hero, int pos) {
		GameScene.pickUpJournal(this, pos);
		String page = Document.GUIDE_INTRO;
		Game.runOnRenderThread(new Callback() {
			@Override
			public void call() {
				GameScene.show(new WndStory(WndJournal.GuideTab.iconForPage(page),
						Document.ADVENTURERS_GUIDE.pageTitle(page),
						Document.ADVENTURERS_GUIDE.pageBody(page)){

					float elapsed = 0;

					@Override
					public void update() {
						elapsed += Game.elapsed;
						super.update();
					}

					@Override
					public void hide() {
						//prevents accidentally closing
						if (elapsed >= 1) {
							super.hide();
						}
					}
				});
			}
		});
		Document.ADVENTURERS_GUIDE.readPage(Document.GUIDE_INTRO);
		Sample.INSTANCE.play( Assets.Sounds.ITEM );
		hero.spendAndNext( TIME_TO_PICK_UP );
		return true;
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

}
