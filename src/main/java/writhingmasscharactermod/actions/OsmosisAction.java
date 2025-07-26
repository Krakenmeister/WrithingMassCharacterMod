package writhingmasscharactermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class OsmosisAction extends AbstractGameAction {
    public OsmosisAction(AbstractCreature owner) {
        this.target = owner;
    }

    public void update() {
        int blockToConvert = target.currentBlock;
        AbstractDungeon.actionManager.addToBottom(new RemoveAllBlockAction(target, target));
        AbstractDungeon.actionManager.addToBottom(new AddRealTemporaryHPAction(target, target, blockToConvert));

        isDone = true;
    }
}
