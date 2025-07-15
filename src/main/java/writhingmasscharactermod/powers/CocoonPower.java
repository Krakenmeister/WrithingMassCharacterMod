package writhingmasscharactermod.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import writhingmasscharactermod.forms.HighForm;
import writhingmasscharactermod.patches.FormFieldPatch;

import static writhingmasscharactermod.WrithingMassCharacterMod.makeID;

public class CocoonPower extends BasePower {
    public static final String POWER_ID = makeID("Cocoon");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public CocoonPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 1);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer && FormFieldPatch.form.get(AbstractDungeon.player).ID.equals(HighForm.FORM_ID)) {
            for(AbstractCard c : AbstractDungeon.player.hand.group) {
                if (!c.isEthereal) {
                    c.retain = true;
                }
            }
        }

        super.atEndOfTurn(isPlayer);


    }

    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}
