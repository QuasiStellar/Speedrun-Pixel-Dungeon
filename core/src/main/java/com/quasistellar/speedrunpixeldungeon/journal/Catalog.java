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

package com.quasistellar.speedrunpixeldungeon.journal;

import com.quasistellar.speedrunpixeldungeon.Badges;
import com.quasistellar.speedrunpixeldungeon.items.Item;
import com.quasistellar.speedrunpixeldungeon.items.armor.ClothArmor;
import com.quasistellar.speedrunpixeldungeon.items.armor.HuntressArmor;
import com.quasistellar.speedrunpixeldungeon.items.armor.LeatherArmor;
import com.quasistellar.speedrunpixeldungeon.items.armor.MageArmor;
import com.quasistellar.speedrunpixeldungeon.items.armor.MailArmor;
import com.quasistellar.speedrunpixeldungeon.items.armor.PlateArmor;
import com.quasistellar.speedrunpixeldungeon.items.armor.RogueArmor;
import com.quasistellar.speedrunpixeldungeon.items.armor.ScaleArmor;
import com.quasistellar.speedrunpixeldungeon.items.armor.WarriorArmor;
import com.quasistellar.speedrunpixeldungeon.items.artifacts.AlchemistsToolkit;
import com.quasistellar.speedrunpixeldungeon.items.artifacts.ChaliceOfBlood;
import com.quasistellar.speedrunpixeldungeon.items.artifacts.CloakOfShadows;
import com.quasistellar.speedrunpixeldungeon.items.artifacts.DriedRose;
import com.quasistellar.speedrunpixeldungeon.items.artifacts.EtherealChains;
import com.quasistellar.speedrunpixeldungeon.items.artifacts.HornOfPlenty;
import com.quasistellar.speedrunpixeldungeon.items.artifacts.MasterThievesArmband;
import com.quasistellar.speedrunpixeldungeon.items.artifacts.SandalsOfNature;
import com.quasistellar.speedrunpixeldungeon.items.artifacts.TalismanOfForesight;
import com.quasistellar.speedrunpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.quasistellar.speedrunpixeldungeon.items.artifacts.UnstableSpellbook;
import com.quasistellar.speedrunpixeldungeon.items.potions.PotionOfExperience;
import com.quasistellar.speedrunpixeldungeon.items.potions.PotionOfFrost;
import com.quasistellar.speedrunpixeldungeon.items.potions.PotionOfHaste;
import com.quasistellar.speedrunpixeldungeon.items.potions.PotionOfHealing;
import com.quasistellar.speedrunpixeldungeon.items.potions.PotionOfInvisibility;
import com.quasistellar.speedrunpixeldungeon.items.potions.PotionOfLevitation;
import com.quasistellar.speedrunpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.quasistellar.speedrunpixeldungeon.items.potions.PotionOfMindVision;
import com.quasistellar.speedrunpixeldungeon.items.potions.PotionOfParalyticGas;
import com.quasistellar.speedrunpixeldungeon.items.potions.PotionOfPurity;
import com.quasistellar.speedrunpixeldungeon.items.potions.PotionOfStrength;
import com.quasistellar.speedrunpixeldungeon.items.potions.PotionOfToxicGas;
import com.quasistellar.speedrunpixeldungeon.items.rings.RingOfAccuracy;
import com.quasistellar.speedrunpixeldungeon.items.rings.RingOfElements;
import com.quasistellar.speedrunpixeldungeon.items.rings.RingOfEnergy;
import com.quasistellar.speedrunpixeldungeon.items.rings.RingOfEvasion;
import com.quasistellar.speedrunpixeldungeon.items.rings.RingOfForce;
import com.quasistellar.speedrunpixeldungeon.items.rings.RingOfFuror;
import com.quasistellar.speedrunpixeldungeon.items.rings.RingOfHaste;
import com.quasistellar.speedrunpixeldungeon.items.rings.RingOfMight;
import com.quasistellar.speedrunpixeldungeon.items.rings.RingOfSharpshooting;
import com.quasistellar.speedrunpixeldungeon.items.rings.RingOfTenacity;
import com.quasistellar.speedrunpixeldungeon.items.rings.RingOfWealth;
import com.quasistellar.speedrunpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.quasistellar.speedrunpixeldungeon.items.scrolls.ScrollOfLullaby;
import com.quasistellar.speedrunpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.quasistellar.speedrunpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.quasistellar.speedrunpixeldungeon.items.scrolls.ScrollOfRage;
import com.quasistellar.speedrunpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.quasistellar.speedrunpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.quasistellar.speedrunpixeldungeon.items.scrolls.ScrollOfRetribution;
import com.quasistellar.speedrunpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.quasistellar.speedrunpixeldungeon.items.scrolls.ScrollOfTerror;
import com.quasistellar.speedrunpixeldungeon.items.scrolls.ScrollOfTransmutation;
import com.quasistellar.speedrunpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.quasistellar.speedrunpixeldungeon.items.wands.WandOfBlastWave;
import com.quasistellar.speedrunpixeldungeon.items.wands.WandOfCorrosion;
import com.quasistellar.speedrunpixeldungeon.items.wands.WandOfCorruption;
import com.quasistellar.speedrunpixeldungeon.items.wands.WandOfDisintegration;
import com.quasistellar.speedrunpixeldungeon.items.wands.WandOfFireblast;
import com.quasistellar.speedrunpixeldungeon.items.wands.WandOfFrost;
import com.quasistellar.speedrunpixeldungeon.items.wands.WandOfLightning;
import com.quasistellar.speedrunpixeldungeon.items.wands.WandOfLivingEarth;
import com.quasistellar.speedrunpixeldungeon.items.wands.WandOfMagicMissile;
import com.quasistellar.speedrunpixeldungeon.items.wands.WandOfPrismaticLight;
import com.quasistellar.speedrunpixeldungeon.items.wands.WandOfRegrowth;
import com.quasistellar.speedrunpixeldungeon.items.wands.WandOfTransfusion;
import com.quasistellar.speedrunpixeldungeon.items.wands.WandOfWarding;
import com.quasistellar.speedrunpixeldungeon.items.weapon.melee.AssassinsBlade;
import com.quasistellar.speedrunpixeldungeon.items.weapon.melee.BattleAxe;
import com.quasistellar.speedrunpixeldungeon.items.weapon.melee.Crossbow;
import com.quasistellar.speedrunpixeldungeon.items.weapon.melee.Dagger;
import com.quasistellar.speedrunpixeldungeon.items.weapon.melee.Dirk;
import com.quasistellar.speedrunpixeldungeon.items.weapon.melee.Flail;
import com.quasistellar.speedrunpixeldungeon.items.weapon.melee.Gauntlet;
import com.quasistellar.speedrunpixeldungeon.items.weapon.melee.Glaive;
import com.quasistellar.speedrunpixeldungeon.items.weapon.melee.Gloves;
import com.quasistellar.speedrunpixeldungeon.items.weapon.melee.Greataxe;
import com.quasistellar.speedrunpixeldungeon.items.weapon.melee.Greatshield;
import com.quasistellar.speedrunpixeldungeon.items.weapon.melee.Greatsword;
import com.quasistellar.speedrunpixeldungeon.items.weapon.melee.HandAxe;
import com.quasistellar.speedrunpixeldungeon.items.weapon.melee.Longsword;
import com.quasistellar.speedrunpixeldungeon.items.weapon.melee.Mace;
import com.quasistellar.speedrunpixeldungeon.items.weapon.melee.MagesStaff;
import com.quasistellar.speedrunpixeldungeon.items.weapon.melee.Quarterstaff;
import com.quasistellar.speedrunpixeldungeon.items.weapon.melee.RoundShield;
import com.quasistellar.speedrunpixeldungeon.items.weapon.melee.RunicBlade;
import com.quasistellar.speedrunpixeldungeon.items.weapon.melee.Sai;
import com.quasistellar.speedrunpixeldungeon.items.weapon.melee.Scimitar;
import com.quasistellar.speedrunpixeldungeon.items.weapon.melee.Shortsword;
import com.quasistellar.speedrunpixeldungeon.items.weapon.melee.Spear;
import com.quasistellar.speedrunpixeldungeon.items.weapon.melee.Sword;
import com.quasistellar.speedrunpixeldungeon.items.weapon.melee.WarHammer;
import com.quasistellar.speedrunpixeldungeon.items.weapon.melee.Whip;
import com.quasistellar.speedrunpixeldungeon.items.weapon.melee.WornShortsword;
import com.watabou.utils.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

public enum Catalog {
	
	WEAPONS,
	ARMOR,
	WANDS,
	RINGS,
	ARTIFACTS,
	POTIONS,
	SCROLLS;
	
	private LinkedHashMap<Class<? extends Item>, Boolean> seen = new LinkedHashMap<>();
	
	public Collection<Class<? extends Item>> items(){
		return seen.keySet();
	}
	
	public boolean allSeen(){
		for (Class<?extends Item> item : items()){
			if (!seen.get(item)){
				return false;
			}
		}
		return true;
	}
	
	static {
		WEAPONS.seen.put( WornShortsword.class,             false);
		WEAPONS.seen.put( Gloves.class,                     false);
		WEAPONS.seen.put( Dagger.class,                     false);
		WEAPONS.seen.put( MagesStaff.class,                 false);
		WEAPONS.seen.put( Shortsword.class,                 false);
		WEAPONS.seen.put( HandAxe.class,                    false);
		WEAPONS.seen.put( Spear.class,                      false);
		WEAPONS.seen.put( Quarterstaff.class,               false);
		WEAPONS.seen.put( Dirk.class,                       false);
		WEAPONS.seen.put( Sword.class,                      false);
		WEAPONS.seen.put( Mace.class,                       false);
		WEAPONS.seen.put( Scimitar.class,                   false);
		WEAPONS.seen.put( RoundShield.class,                false);
		WEAPONS.seen.put( Sai.class,                        false);
		WEAPONS.seen.put( Whip.class,                       false);
		WEAPONS.seen.put( Longsword.class,                  false);
		WEAPONS.seen.put( BattleAxe.class,                  false);
		WEAPONS.seen.put( Flail.class,                      false);
		WEAPONS.seen.put( RunicBlade.class,                 false);
		WEAPONS.seen.put( AssassinsBlade.class,             false);
		WEAPONS.seen.put( Crossbow.class,                   false);
		WEAPONS.seen.put( Greatsword.class,                 false);
		WEAPONS.seen.put( WarHammer.class,                  false);
		WEAPONS.seen.put( Glaive.class,                     false);
		WEAPONS.seen.put( Greataxe.class,                   false);
		WEAPONS.seen.put( Greatshield.class,                false);
		WEAPONS.seen.put( Gauntlet.class,                   false);
	
		ARMOR.seen.put( ClothArmor.class,                   false);
		ARMOR.seen.put( LeatherArmor.class,                 false);
		ARMOR.seen.put( MailArmor.class,                    false);
		ARMOR.seen.put( ScaleArmor.class,                   false);
		ARMOR.seen.put( PlateArmor.class,                   false);
		ARMOR.seen.put( WarriorArmor.class,                 false);
		ARMOR.seen.put( MageArmor.class,                    false);
		ARMOR.seen.put( RogueArmor.class,                   false);
		ARMOR.seen.put( HuntressArmor.class,                false);
	
		WANDS.seen.put( WandOfMagicMissile.class,           false);
		WANDS.seen.put( WandOfLightning.class,              false);
		WANDS.seen.put( WandOfDisintegration.class,         false);
		WANDS.seen.put( WandOfFireblast.class,              false);
		WANDS.seen.put( WandOfCorrosion.class,              false);
		WANDS.seen.put( WandOfBlastWave.class,              false);
		WANDS.seen.put( WandOfLivingEarth.class,            false);
		WANDS.seen.put( WandOfFrost.class,                  false);
		WANDS.seen.put( WandOfPrismaticLight.class,         false);
		WANDS.seen.put( WandOfWarding.class,                false);
		WANDS.seen.put( WandOfTransfusion.class,            false);
		WANDS.seen.put( WandOfCorruption.class,             false);
		WANDS.seen.put( WandOfRegrowth.class,               false);
	
		RINGS.seen.put( RingOfAccuracy.class,               false);
		RINGS.seen.put( RingOfEnergy.class,                 false);
		RINGS.seen.put( RingOfElements.class,               false);
		RINGS.seen.put( RingOfEvasion.class,                false);
		RINGS.seen.put( RingOfForce.class,                  false);
		RINGS.seen.put( RingOfFuror.class,                  false);
		RINGS.seen.put( RingOfHaste.class,                  false);
		RINGS.seen.put( RingOfMight.class,                  false);
		RINGS.seen.put( RingOfSharpshooting.class,          false);
		RINGS.seen.put( RingOfTenacity.class,               false);
		RINGS.seen.put( RingOfWealth.class,                 false);
	
		ARTIFACTS.seen.put( AlchemistsToolkit.class,        false);
		//ARTIFACTS.seen.put( CapeOfThorns.class,             false);
		ARTIFACTS.seen.put( ChaliceOfBlood.class,           false);
		ARTIFACTS.seen.put( CloakOfShadows.class,           false);
		ARTIFACTS.seen.put( DriedRose.class,                false);
		ARTIFACTS.seen.put( EtherealChains.class,           false);
		ARTIFACTS.seen.put( HornOfPlenty.class,             false);
		//ARTIFACTS.seen.put( LloydsBeacon.class,             false);
		ARTIFACTS.seen.put( MasterThievesArmband.class,     false);
		ARTIFACTS.seen.put( SandalsOfNature.class,          false);
		ARTIFACTS.seen.put( TalismanOfForesight.class,      false);
		ARTIFACTS.seen.put( TimekeepersHourglass.class,     false);
		ARTIFACTS.seen.put( UnstableSpellbook.class,        false);
	
		POTIONS.seen.put( PotionOfHealing.class,            false);
		POTIONS.seen.put( PotionOfStrength.class,           false);
		POTIONS.seen.put( PotionOfLiquidFlame.class,        false);
		POTIONS.seen.put( PotionOfFrost.class,              false);
		POTIONS.seen.put( PotionOfToxicGas.class,           false);
		POTIONS.seen.put( PotionOfParalyticGas.class,       false);
		POTIONS.seen.put( PotionOfPurity.class,             false);
		POTIONS.seen.put( PotionOfLevitation.class,         false);
		POTIONS.seen.put( PotionOfMindVision.class,         false);
		POTIONS.seen.put( PotionOfInvisibility.class,       false);
		POTIONS.seen.put( PotionOfExperience.class,         false);
		POTIONS.seen.put( PotionOfHaste.class,              false);
	
		SCROLLS.seen.put( ScrollOfIdentify.class,           false);
		SCROLLS.seen.put( ScrollOfUpgrade.class,            false);
		SCROLLS.seen.put( ScrollOfRemoveCurse.class,        false);
		SCROLLS.seen.put( ScrollOfMagicMapping.class,       false);
		SCROLLS.seen.put( ScrollOfTeleportation.class,      false);
		SCROLLS.seen.put( ScrollOfRecharging.class,         false);
		SCROLLS.seen.put( ScrollOfMirrorImage.class,        false);
		SCROLLS.seen.put( ScrollOfTerror.class,             false);
		SCROLLS.seen.put( ScrollOfLullaby.class,            false);
		SCROLLS.seen.put( ScrollOfRage.class,               false);
		SCROLLS.seen.put( ScrollOfRetribution.class,        false);
		SCROLLS.seen.put( ScrollOfTransmutation.class,      false);
	}
	
	public static LinkedHashMap<Catalog, Badges.Badge> catalogBadges = new LinkedHashMap<>();
	static {
		catalogBadges.put(WEAPONS, Badges.Badge.ALL_WEAPONS_IDENTIFIED);
		catalogBadges.put(ARMOR, Badges.Badge.ALL_ARMOR_IDENTIFIED);
		catalogBadges.put(WANDS, Badges.Badge.ALL_WANDS_IDENTIFIED);
		catalogBadges.put(RINGS, Badges.Badge.ALL_RINGS_IDENTIFIED);
		catalogBadges.put(ARTIFACTS, Badges.Badge.ALL_ARTIFACTS_IDENTIFIED);
		catalogBadges.put(POTIONS, Badges.Badge.ALL_POTIONS_IDENTIFIED);
		catalogBadges.put(SCROLLS, Badges.Badge.ALL_SCROLLS_IDENTIFIED);
	}
	
	public static boolean isSeen(Class<? extends Item> itemClass){
		for (Catalog cat : values()) {
			if (cat.seen.containsKey(itemClass)) {
				return cat.seen.get(itemClass);
			}
		}
		return false;
	}
	
	public static void setSeen(Class<? extends Item> itemClass){
		for (Catalog cat : values()) {
			if (cat.seen.containsKey(itemClass) && !cat.seen.get(itemClass)) {
				cat.seen.put(itemClass, true);
				Journal.saveNeeded = true;
			}
		}
		Badges.validateItemsIdentified();
	}
	
	private static final String CATALOG_ITEMS = "catalog_items";
	
	public static void store( Bundle bundle ){
		
		Badges.loadGlobal();
		
		ArrayList<Class> seen = new ArrayList<>();
		
		//if we have identified all items of a set, we use the badge to keep track instead.
		if (!Badges.isUnlocked(Badges.Badge.ALL_ITEMS_IDENTIFIED)) {
			for (Catalog cat : values()) {
				if (!Badges.isUnlocked(catalogBadges.get(cat))) {
					for (Class<? extends Item> item : cat.items()) {
						if (cat.seen.get(item)) seen.add(item);
					}
				}
			}
		}
		
		bundle.put( CATALOG_ITEMS, seen.toArray(new Class[0]) );
		
	}
	
	public static void restore( Bundle bundle ){
		
		Badges.loadGlobal();
		
		//logic for if we have all badges
		if (Badges.isUnlocked(Badges.Badge.ALL_ITEMS_IDENTIFIED)){
			for ( Catalog cat : values()){
				for (Class<? extends Item> item : cat.items()){
					cat.seen.put(item, true);
				}
			}
			return;
		}
		
		//catalog-specific badge logic
		for (Catalog cat : values()){
			if (Badges.isUnlocked(catalogBadges.get(cat))){
				for (Class<? extends Item> item : cat.items()){
					cat.seen.put(item, true);
				}
			}
		}
		
		//general save/load
		if (bundle.contains(CATALOG_ITEMS)) {
			List<Class> seenClasses = new ArrayList<>();
			if (bundle.contains(CATALOG_ITEMS)) {
				seenClasses = Arrays.asList(bundle.getClassArray(CATALOG_ITEMS));
			}
			
			for (Catalog cat : values()) {
				for (Class<? extends Item> item : cat.items()) {
					if (seenClasses.contains(item)) {
						cat.seen.put(item, true);
					}
				}
			}
		}
	}
	
}
