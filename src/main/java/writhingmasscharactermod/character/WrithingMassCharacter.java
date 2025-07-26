package writhingmasscharactermod.character;

import basemod.BaseMod;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpineAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.esotericsoftware.spine.AnimationState;
import writhingmasscharactermod.cards.basic.*;
import writhingmasscharactermod.cards.uncommon.Flagella;
import writhingmasscharactermod.forms.ChangeFormAction;
import writhingmasscharactermod.forms.HighForm;
import writhingmasscharactermod.forms.LowForm;
import writhingmasscharactermod.forms.MidForm;
import writhingmasscharactermod.relics.starter.Tumor;
import writhingmasscharactermod.util.WrithingCard;
import writhingmasscharactermod.util.WrithingMassOrb;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static writhingmasscharactermod.WrithingMassCharacterMod.characterPath;
import static writhingmasscharactermod.WrithingMassCharacterMod.makeID;

public class WrithingMassCharacter extends CustomPlayer {
    //Stats
    public static final int ENERGY_PER_TURN = 3;
    public static final int MAX_HP = 30;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;

    public static final String WRITHING_MASS_SKELETON_ATLAS = characterPath("animation/writhingMass.atlas"); // spine animation atlas
    public static final String WRITHING_MASS_SKELETON_JSON = characterPath("animation/writhingMass.json");;

    //Strings
    private static final String ID = makeID("WrithingMassCharacter"); //This should match whatever you have in the CharacterStrings.json file
    private static String[] getNames() { return CardCrawlGame.languagePack.getCharacterString(ID).NAMES; }
    private static String[] getText() { return CardCrawlGame.languagePack.getCharacterString(ID).TEXT; }

    //This static class is necessary to avoid certain quirks of Java classloading when registering the character.
    public static class Meta {
        //These are used to identify your character, as well as your character's card color.
        //Library color is basically the same as card color, but you need both because that's how the game was made.
        @SpireEnum
        public static PlayerClass WRITHING_MASS_CHARACTER;
        @SpireEnum(name = "CHARACTER_WRITHING_COLOR") // These two MUST match. Change it to something unique for your character.
        public static AbstractCard.CardColor CARD_COLOR;
        @SpireEnum(name = "CHARACTER_WRITHING_COLOR") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;

        //Character select images
        private static final String CHAR_SELECT_BUTTON = characterPath("select/button.png");
        private static final String CHAR_SELECT_PORTRAIT = characterPath("select/portrait.png");

        //Character card images
        private static final String BG_ATTACK = characterPath("cardback/bg_attack.png");
        private static final String BG_ATTACK_P = characterPath("cardback/bg_attack_p.png");
        private static final String BG_SKILL = characterPath("cardback/bg_skill.png");
        private static final String BG_SKILL_P = characterPath("cardback/bg_skill_p.png");
        private static final String BG_POWER = characterPath("cardback/bg_power.png");
        private static final String BG_POWER_P = characterPath("cardback/bg_power_p.png");
        private static final String ENERGY_ORB = characterPath("cardback/energy_orb.png");
        private static final String ENERGY_ORB_P = characterPath("cardback/energy_orb_p.png");
        private static final String SMALL_ORB = characterPath("cardback/small_orb.png");

        //This is used to color *some* images, but NOT the actual cards. For that, edit the images in the cardback folder!
        private static final Color cardColor = new Color(128f/255f, 128f/255f, 128f/255f, 1f);

        //Methods that will be used in the main mod file
        public static void registerColor() {
            BaseMod.addColor(CARD_COLOR, cardColor,
                    BG_ATTACK, BG_SKILL, BG_POWER, ENERGY_ORB,
                    BG_ATTACK_P, BG_SKILL_P, BG_POWER_P, ENERGY_ORB_P,
                    SMALL_ORB);
        }

        public static void registerCharacter() {
            BaseMod.addCharacter(new WrithingMassCharacter(), CHAR_SELECT_BUTTON, CHAR_SELECT_PORTRAIT);
        }
    }


    //In-game images
    private static final String SHOULDER_1 = characterPath("shoulder.png"); //Shoulder 1 and 2 are used at rest sites.
    private static final String SHOULDER_2 = characterPath("shoulder2.png");
    private static final String CORPSE = characterPath("corpse.png"); //Corpse is when you die.

    private Texture healthBarIndicator = ImageMaster.loadImage(characterPath("indicator.png"));


    private void setCharacterScale() {
        float scale = maxHealth / 40F;
        if (scale > 1F) {
            scale = (float) Math.sqrt(scale);
        }
        skeleton.getData().getBones().get(0).setScaleX(-1 * scale);
        skeleton.getData().getBones().get(0).setScaleY(scale);
        skeleton.getBones().get(0).setScaleX(-1 * scale);
        skeleton.getBones().get(0).setScaleY(scale);
    }


    //Actual character class code below this point

    public WrithingMassCharacter() {
        super(getNames()[0], Meta.WRITHING_MASS_CHARACTER,
                new WrithingMassOrb(),
                new SpineAnimation(WRITHING_MASS_SKELETON_ATLAS, WRITHING_MASS_SKELETON_JSON, 1.0F)); //Animation

        initializeClass(null,
                SHOULDER_2,
                SHOULDER_1,
                CORPSE,
                getLoadout(),
                20.0F, -20.0F, 200.0F, 250.0F, //Character hitbox. x y position, then width and height.
                new EnergyManager(ENERGY_PER_TURN));

        //Location for text bubbles. You can adjust it as necessary later. For most characters, these values are fine.
        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 220.0F * Settings.scale);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        e.setTimeScale(0.6F);

        setCharacterScale();
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        //List of IDs of cards for your starting deck.
        //If you want multiple of the same card, you have to add it multiple times.
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);

        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);

        retVal.add(Morph.ID);
        retVal.add(Implant.ID);

        //retVal.add(Flagella.ID);
        //retVal.add(Spank.ID);

        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        //IDs of starting relics. You can have multiple, but one is recommended.
        retVal.add(Tumor.ID);

        return retVal;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        //This card is used for the Gremlin card matching game.
        //It should be a non-strike non-defend starter card, but it doesn't have to be.
        return new Flagella();
    }

    /*- Below this is methods that you should *probably* adjust, but don't have to. -*/

    @Override
    public int getAscensionMaxHPLoss() {
        return 4; //Max hp reduction at ascension 14+
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        //These attack effects will be used when you attack the heart.
        return new AbstractGameAction.AttackEffect[] {
                AbstractGameAction.AttackEffect.SLASH_VERTICAL,
                AbstractGameAction.AttackEffect.SLASH_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY
        };
    }

    private final Color cardRenderColor = Color.LIGHT_GRAY.cpy(); //Used for some vfx on moving cards (sometimes) (maybe)
    private final Color cardTrailColor = Color.LIGHT_GRAY.cpy(); //Used for card trail vfx during gameplay.
    private final Color slashAttackColor = Color.LIGHT_GRAY.cpy(); //Used for a screen tint effect when you attack the heart.
    @Override
    public Color getCardRenderColor() {
        return cardRenderColor;
    }

    @Override
    public Color getCardTrailColor() {
        return cardTrailColor;
    }

    @Override
    public Color getSlashAttackColor() {
        return slashAttackColor;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        //Font used to display your current energy.
        //energyNumFontRed, Blue, Green, and Purple are used by the basegame characters.
        //It is possible to make your own, but not convenient.
        return FontHelper.energyNumFontRed;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        //This occurs when you click the character's button in the character select screen.
        //See SoundMaster for a full list of existing sound effects, or look at BaseMod's wiki for adding custom audio.
        CardCrawlGame.sound.playA("ATTACK_DAGGER_2", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        //Similar to doCharSelectScreenSelectEffect, but used for the Custom mode screen. No shaking.
        return "ATTACK_DAGGER_2";
    }

    //Don't adjust these four directly, adjust the contents of the CharacterStrings.json file.
    @Override
    public String getLocalizedCharacterName() {
        return getNames()[0];
    }
    @Override
    public String getTitle(PlayerClass playerClass) {
        return getNames()[1];
    }
    @Override
    public String getSpireHeartText() {
        return getText()[1];
    }
    @Override
    public String getVampireText() {
        return getText()[2]; //Generally, the only difference in this text is how the vampires refer to the player.
    }

    /*- You shouldn't need to edit any of the following methods. -*/

    //This is used to display the character's information on the character selection screen.
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(getNames()[0], getText()[0],
                MAX_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this,
                getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Meta.CARD_COLOR;
    }

    @Override
    public AbstractPlayer newInstance() {
        //Makes a new instance of your character class.
        return new WrithingMassCharacter();
    }

    public void damage(DamageInfo info) {
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output - this.currentBlock > 0) {
            AnimationState.TrackEntry e = this.state.setAnimation(0, "Hit", false);
            this.state.addAnimation(0, "Idle", true, 0.0F);
            e.setTimeScale(0.6F);
        }

        super.damage(info);
    }

    @Override
    public void decreaseMaxHealth(int amount) {
        super.decreaseMaxHealth(amount);

        setCharacterScale();
    }

    @Override
    public void increaseMaxHp(int amount, boolean showEffect) {
        super.increaseMaxHp(amount, showEffect);

        setCharacterScale();
    }

    @Override
    public void applyStartOfCombatLogic() {
        super.applyStartOfCombatLogic();

        List<AbstractCard> allCards = new ArrayList<>();
        allCards.addAll(this.hand.group);
        allCards.addAll(this.discardPile.group);
        allCards.addAll(this.drawPile.group);
        allCards.addAll(this.exhaustPile.group);
        allCards = allCards.stream()
                .filter(card -> card instanceof WrithingCard)
                .collect(Collectors.toList());
        List<WrithingCard> mutableCards = new ArrayList<>();
        for (AbstractCard card : allCards) {
            if (((WrithingCard)card).isMutable) {
                mutableCards.add((WrithingCard) card);
            }
        }

        for (int i = 0; i < mutableCards.size(); i++) {
            if (AbstractDungeon.cardRandomRng.random() < 0.5F) {
                mutableCards.get(i).setBenign(false);
            }
        }

        /*Collections.shuffle(mutableCards, AbstractDungeon.cardRandomRng.random);
        int halfSize = mutableCards.size() / 2;
        for (int i = 0; i < halfSize; i++) {
            mutableCards.get(i).setBenign(false);
        }*/
    }

    @Override
    public void useCard(AbstractCard c, AbstractMonster monster, int energyOnUse) {
        super.useCard(c, monster, energyOnUse);

        if (c instanceof WrithingCard) {
            if (((WrithingCard)c).isMutable) {
                for (AbstractCard card : this.hand.group) {
                    if (card instanceof WrithingCard) {
                        if (((WrithingCard)card).isMutable) {
                            ((WrithingCard)card).toggleBenign();
                        }
                    }
                }
                for (AbstractCard card : this.discardPile.group) {
                    if (card instanceof WrithingCard) {
                        if (((WrithingCard)card).isMutable) {
                            ((WrithingCard)card).toggleBenign();
                        }
                    }
                }
                for (AbstractCard card : this.drawPile.group) {
                    if (card instanceof WrithingCard) {
                        if (((WrithingCard)card).isMutable) {
                            ((WrithingCard)card).toggleBenign();
                        }
                    }
                }
                for (AbstractCard card : this.exhaustPile.group) {
                    if (card instanceof WrithingCard) {
                        if (((WrithingCard)card).isMutable) {
                            ((WrithingCard)card).toggleBenign();
                        }
                    }
                }
            }
        }


    }

    @Override
    public void healthBarUpdatedEvent() {
        super.healthBarUpdatedEvent();

//        try {
//            if ((float)currentHealth / (float)maxHealth < 0.3333F) {
//                AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(new LowStance()));
//            } else if ((float)currentHealth / (float)maxHealth < 0.6666667F) {
//                AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(new MidStance()));
//            } else {
//                AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(new HighStance()));
//            }
//        } catch (Exception e) {
//
//        }

        try {

            if ((float)currentHealth / (float)maxHealth < 0.3333F) {
                AbstractDungeon.actionManager.addToBottom(new ChangeFormAction(new LowForm()));
            } else if ((float)currentHealth / (float)maxHealth < 0.6666667F) {
                AbstractDungeon.actionManager.addToBottom(new ChangeFormAction(new MidForm()));
            } else {
                AbstractDungeon.actionManager.addToBottom(new ChangeFormAction(new HighForm()));
            }
        } catch (Exception e) {

        }

    }

    @Override
    public void renderHealth(SpriteBatch sb) {
        float HEALTH_BAR_HEIGHT = 20.0F * Settings.scale;
        float HEALTH_BG_OFFSET_X = 31.0F * Settings.scale;

        if (!Settings.hideCombatElements) {
            float x = hb.cX - hb.width / 2.0F;
            float y = hb.cY - hb.height / 2.0F;

            float leftX = x - HEALTH_BAR_HEIGHT + 10.0F * Settings.scale;
            float rightX = x + hb.width - 10.0F * Settings.scale;

            float iconHeight = HEALTH_BAR_HEIGHT * 1.8F;
            float iconY = y - HEALTH_BG_OFFSET_X + 3.0F * Settings.scale - (HEALTH_BAR_HEIGHT * 0.4F);

            sb.setColor(Color.MAGENTA);
            sb.draw(healthBarIndicator, leftX + (rightX - leftX) / 3, iconY, HEALTH_BAR_HEIGHT, iconHeight);
            sb.setColor(Color.GREEN);
            sb.draw(healthBarIndicator, leftX + 2 * (rightX - leftX) / 3, iconY, HEALTH_BAR_HEIGHT, iconHeight);
        }

        super.renderHealth(sb);
    }

    //    @Override
//    public void useFastAttackAnimation() {
//        AnimationState.TrackEntry e = this.state.setAnimation(0, "Attack", false);
//        AnimationState.TrackEntry f = this.state.addAnimation(0, "Idle", true, 0.0F);
//        e.setTimeScale(3.0F);
//        f.setTimeScale(0.6F);
//    }
}
