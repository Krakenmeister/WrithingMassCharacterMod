package writhingmasscharactermod.powers;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import writhingmasscharactermod.util.OnUseInfestedCardPower;
import writhingmasscharactermod.util.WrithingCard;

import static writhingmasscharactermod.WrithingMassCharacterMod.makeID;

public class FunishmentPower extends BasePower implements OnUseInfestedCardPower {
    public static final String POWER_ID = makeID("Funishment");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public FunishmentPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);

        this.source = source;

        if (this.amount >= 9999) {
            this.amount = 9999;
        }
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        addToBot(new RemoveSpecificPowerAction(owner, owner, FunishmentPower.POWER_ID));
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!(card instanceof WrithingCard)) return;
        if (((WrithingCard) card).isBenign) return;

        if (source instanceof AbstractPlayer) {
            addToBot(new HealAction(owner, source, amount));
        }
    }

    @Override
    public void OnUseInfestedCard(WrithingCard card) {
        if (card.isBenign) return;

        if (source instanceof AbstractMonster) {
            addToBot(new HealAction(owner, source, amount));
        }
    }
}
