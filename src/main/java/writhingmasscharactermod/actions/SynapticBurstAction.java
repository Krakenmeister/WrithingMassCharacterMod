package writhingmasscharactermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import writhingmasscharactermod.patches.infest.InfestCardFieldPatch;
import writhingmasscharactermod.util.WrithingCard;

import java.util.List;

public class SynapticBurstAction extends AbstractGameAction {
    private AbstractMonster infestedMonster;

    public SynapticBurstAction(AbstractMonster target) {
        this.infestedMonster = target;
    }

    public void update() {
        List<WrithingCard> infestedCards = InfestCardFieldPatch.infestedCards.get(infestedMonster);
        for (WrithingCard infestedCard : infestedCards) {
            AbstractDungeon.actionManager.addToBottom(new InfestedCardUseAction(infestedMonster, infestedCard));
        }

        isDone = true;
    }
}
