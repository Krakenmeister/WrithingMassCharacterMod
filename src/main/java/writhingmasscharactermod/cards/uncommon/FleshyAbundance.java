package writhingmasscharactermod.cards.uncommon;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.defect.ReinforcedBodyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import writhingmasscharactermod.actions.FleshyAbundanceAction;
import writhingmasscharactermod.cards.BaseCard;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.util.CardStats;

public class FleshyAbundance extends BaseCard {
    public static final String ID = makeID("FleshyAbundance");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            -1
    );

    private static final int MAGIC_NUMBER = 5;
    private static final int UPG_MAGIC_NUMBER = 3;

    public FleshyAbundance() {
        super(ID, info);

        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new FleshyAbundanceAction(p, magicNumber, freeToPlayOnce, energyOnUse));
    }
}