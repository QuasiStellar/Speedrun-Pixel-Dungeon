package com.quasistellar.speedrunpixeldungeon.items;

import com.quasistellar.speedrunpixeldungeon.Assets;
import com.quasistellar.speedrunpixeldungeon.Dungeon;
import com.quasistellar.speedrunpixeldungeon.actors.hero.Hero;
import com.quasistellar.speedrunpixeldungeon.scenes.GameScene;
import com.quasistellar.speedrunpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class EnergyCrystal extends Item {

	private static final String TXT_VALUE	= "%+d";

	{
		image = ItemSpriteSheet.ENERGY;
		stackable = true;
	}

	public EnergyCrystal() {
		this( 1 );
	}

	public EnergyCrystal( int value ) {
		this.quantity = value;
	}

	@Override
	public ArrayList<String> actions(Hero hero ) {
		return new ArrayList<>();
	}

	@Override
	public boolean doPickUp(Hero hero, int pos) {

		Dungeon.energy += quantity;
		//TODO Statistics.goldCollected += quantity;
		//Badges.validateGoldCollected();

		GameScene.pickUp( this, pos );
		hero.sprite.showStatus( 0x44CCFF, TXT_VALUE, quantity );
		hero.spendAndNext( TIME_TO_PICK_UP );

		Sample.INSTANCE.play( Assets.Sounds.ITEM );

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

	@Override
	public Item random() {
		quantity = Random.IntRange( 4, 6 );
		return this;
	}

}
