package writhingmasscharactermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;
import com.megacrit.cardcrawl.vfx.combat.ScrapeEffect;
import com.megacrit.cardcrawl.vfx.combat.SwirlyBloodEffect;
import writhingmasscharactermod.cards.BaseCard;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.patches.realtemphp.RealTempHPField;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.WrithingCard;

public class Scarhide extends WrithingCard {
    private static final CardStrings cardStrings;
    public static final String ID = makeID("Scarhide");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public Scarhide() {
        this(true);
    }

    public Scarhide(boolean isBenign) {
        super(ID, info, isBenign);

        setBenign(isBenign);

        baseBlock = 0;
        setExhaust(true, false);

        this.rawDescription = updateCardText(isBenign);
        initializeDescription();
    }

    @Override
    public String updateCardText(boolean isBenign) {
        //baseBlock = owner.maxHealth - owner.currentHealth;
        String description = "";
        description = cardStrings.DESCRIPTION;
        if (!upgraded) {
            description += cardStrings.EXTENDED_DESCRIPTION[0];
        }
        return description;
    }

    @Override
    public void upgrade() {
        super.upgrade();

        this.rawDescription = updateCardText(isBenign);
        this.initializeDescription();
    }

    @Override
    public void benignUse(AbstractCreature source, AbstractCreature target) {
        baseBlock = owner.maxHealth - owner.currentHealth;

        if (owner != null) {
            addToBot(new VFXAction(new ScrapeEffect(owner.hb.cX, owner.hb.cY)));
        }

        applyPowersToBlock();

        addToBot(new GainBlockAction(owner, owner, block));

        rawDescription = cardStrings.DESCRIPTION;
        if (!upgraded) {
            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
        }
        initializeDescription();
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        benignUse(source, target);
    }

    public void applyPowers() {
        baseBlock = owner.maxHealth - owner.currentHealth;
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[1];
        if (!upgraded) {
            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
        }
        this.initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        if (!upgraded) {
            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
        }
        this.initializeDescription();
    }

    public AbstractCard makeCopy() {
        return new Scarhide();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

//    public void externalSetBlock(int block) {
//        setBlock(block);
//    }
}
