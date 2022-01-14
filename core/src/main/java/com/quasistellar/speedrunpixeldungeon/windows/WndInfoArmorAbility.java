package com.quasistellar.speedrunpixeldungeon.windows;

import com.quasistellar.speedrunpixeldungeon.actors.hero.HeroClass;
import com.quasistellar.speedrunpixeldungeon.actors.hero.Talent;
import com.quasistellar.speedrunpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.quasistellar.speedrunpixeldungeon.messages.Messages;
import com.quasistellar.speedrunpixeldungeon.ui.HeroIcon;
import com.quasistellar.speedrunpixeldungeon.ui.TalentButton;
import com.quasistellar.speedrunpixeldungeon.ui.TalentsPane;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class WndInfoArmorAbility extends WndTitledMessage {

	public WndInfoArmorAbility(HeroClass cls, ArmorAbility ability){
		super( new HeroIcon(ability), Messages.titleCase(ability.name()), ability.desc());

		ArrayList<LinkedHashMap<Talent, Integer>> talentList = new ArrayList<>();
		Talent.initArmorTalents(ability, talentList);

		TalentsPane.TalentTierPane talentPane = new TalentsPane.TalentTierPane(talentList.get(3), 4, TalentButton.Mode.INFO);
		talentPane.title.text( Messages.titleCase(Messages.get(WndHeroInfo.class, "talents")));
		talentPane.setRect(0, height + 5, width, talentPane.height());
		add(talentPane);
		resize(width, (int) talentPane.bottom());

	}

}
