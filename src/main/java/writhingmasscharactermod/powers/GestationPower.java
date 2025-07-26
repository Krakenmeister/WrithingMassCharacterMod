package writhingmasscharactermod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import writhingmasscharactermod.util.OnInfestCardPower;
import writhingmasscharactermod.util.WrithingCard;

import static writhingmasscharactermod.WrithingMassCharacterMod.makeID;

public class GestationPower extends BasePower implements OnInfestCardPower {
    public static final String POWER_ID = makeID("Gestation");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public GestationPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);

        amount = -1;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void OnInfestCard(WrithingCard card) {
        card.upgrade();
    }
}
