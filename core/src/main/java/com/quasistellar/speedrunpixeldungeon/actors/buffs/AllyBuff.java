package com.quasistellar.speedrunpixeldungeon.actors.buffs;

import com.quasistellar.speedrunpixeldungeon.Badges;
import com.quasistellar.speedrunpixeldungeon.Statistics;
import com.quasistellar.speedrunpixeldungeon.actors.Char;
import com.quasistellar.speedrunpixeldungeon.actors.hero.Hero;
import com.quasistellar.speedrunpixeldungeon.actors.mobs.Mob;
import com.quasistellar.speedrunpixeldungeon.messages.Messages;
import com.quasistellar.speedrunpixeldungeon.sprites.CharSprite;

//generic class for buffs which convert an enemy into an ally
// There is a decent amount of logic that ties into this, which is why it has its own abstract class
public abstract class AllyBuff extends Buff{

	@Override
	public boolean attachTo(Char target) {
		if (super.attachTo(target)){
			target.alignment = Char.Alignment.ALLY;
			if (target.buff(PinCushion.class) != null){
				target.buff(PinCushion.class).detach();
			}
			return true;
		} else {
			return false;
		}
	}

	//for when applying an ally buff should also cause that enemy to give exp/loot as if they had died
	//consider that chars with the ally alignment do not drop items or award exp on death
	public static void affectAndLoot(Mob enemy, Hero hero, Class<?extends AllyBuff> buffCls){
		boolean droppingLoot = enemy.alignment != Char.Alignment.ALLY;
		Buff.affect(enemy, buffCls);

		if (enemy.buff(buffCls) != null){
			if (droppingLoot) enemy.rollToDropLoot();
			Statistics.enemiesSlain++;
			Badges.validateMonstersSlain();
			Statistics.qualifiedForNoKilling = false;
			if (enemy.EXP > 0 && hero.lvl <= enemy.maxLvl) {
				hero.sprite.showStatus(CharSprite.POSITIVE, Messages.get(enemy, "exp", enemy.EXP));
				hero.earnExp(enemy.EXP, enemy.getClass());
			} else {
				hero.earnExp(0, enemy.getClass());
			}
		}
	}

}
