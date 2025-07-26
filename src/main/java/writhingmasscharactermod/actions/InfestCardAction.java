package writhingmasscharactermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import writhingmasscharactermod.util.InfestUtils;
import writhingmasscharactermod.util.WrithingCard;

public class InfestCardAction extends AbstractGameAction {
    private WrithingCard infestedCard;
    private AbstractMonster infestedMonster;
    private boolean didInfest;

    public InfestCardAction(AbstractMonster target, WrithingCard infestedCard) {
        this.infestedMonster = target;
        this.actionType = ActionType.WAIT;
        this.infestedCard = infestedCard;
        this.duration = 0.5F;
        this.didInfest = false;
    }

    public void update() {
        if (!didInfest) {
            InfestUtils.InfestMonster(infestedMonster, infestedCard);
            didInfest = true;
        }

        tickDuration();
    }
}
