package writhingmasscharactermod.cards.uncommon;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import writhingmasscharactermod.cards.BaseCard;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.powers.SoulBondPower;
import writhingmasscharactermod.util.CardStats;

public class Osmosis extends BaseCard {
    public static final String ID = makeID("Osmosis");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC_NUMBER = 0;
    private static final int UPG_MAGIC_NUMBER = 3;

    public Osmosis() {
        super(ID, info);

        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (magicNumber > 0) {
            addToBot(new GainBlockAction(p, magicNumber));
        }
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                int blockToConvert = p.currentBlock;
                AbstractDungeon.actionManager.addToBottom(new RemoveAllBlockAction(p, p));
                AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(p, p, blockToConvert));
                isDone = true;
            }
        });
    }
}