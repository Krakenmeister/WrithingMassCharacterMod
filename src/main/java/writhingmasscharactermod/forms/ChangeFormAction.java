package writhingmasscharactermod.forms;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import writhingmasscharactermod.patches.FormFieldPatch;
import writhingmasscharactermod.util.FormUtils;

public class ChangeFormAction extends AbstractGameAction {
    private String id;
    private AbstractForm newForm;

    public ChangeFormAction(String formId) {
        newForm = null;
        duration = Settings.ACTION_DUR_FAST;
        id = formId;
    }

    public ChangeFormAction(AbstractForm newForm) {
        this(newForm.ID);
        this.newForm = newForm;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.hasPower("CannotChangeStancePower")) {
                this.isDone = true;
                return;
            }

            AbstractForm oldForm = FormFieldPatch.form.get(AbstractDungeon.player);
            if (!oldForm.ID.equals(this.id)) {
                if (newForm == null) {
                    newForm = AbstractForm.getFormFromName(id);
                }

                for(AbstractPower p : AbstractDungeon.player.powers) {
                    //p.onChangeForm(oldForm, newForm);
                }

                for(AbstractRelic r : AbstractDungeon.player.relics) {
                    //r.onChangeForm(oldForm, newForm);
                }

                oldForm.onExitForm();
                FormFieldPatch.form.set(AbstractDungeon.player, newForm);
                newForm.onEnterForm();
//                if (AbstractDungeon.actionManager.uniqueFormsThisCombat.containsKey(newForm.ID)) {
//                    int currentCount = (Integer)AbstractDungeon.actionManager.uniqueFormsThisCombat.get(newForm.ID);
//                    AbstractDungeon.actionManager.uniqueFormsThisCombat.put(newForm.ID, currentCount + 1);
//                } else {
//                    AbstractDungeon.actionManager.uniqueFormsThisCombat.put(newForm.ID, 1);
//                }

                FormUtils.playerSwitchedForm(AbstractDungeon.player);

                for(AbstractCard c : AbstractDungeon.player.discardPile.group) {
                    FormUtils.triggerExhaustedCardsOnFormChange(c, newForm);
                }

                FormUtils.onFormChange(AbstractDungeon.player, newForm);
            }

            AbstractDungeon.onModifyPower();
            if (Settings.FAST_MODE) {
                this.isDone = true;
                return;
            }
        }

        this.tickDuration();
    }
}
