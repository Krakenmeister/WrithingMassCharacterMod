package writhingmasscharactermod.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static writhingmasscharactermod.WrithingMassCharacterMod.makeID;

public class RealMalleablePower extends BasePower {
    public static final String POWER_ID = makeID("RealMalleable");;
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    private Color color = new Color(1.0F, 1.0F, 1.0F, 0.0F);
    private Color redColor = new Color(1.0F, 0.0F, 0.0F, 1.0F);
    private Color greenColor = new Color(0.0F, 1.0F, 1.0F, 1.0F);

    public RealMalleablePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.amount2 = amount;
        this.updateDescription();
        this.loadRegion("malleable");
    }

    public void updateDescription() {
        if (!owner.hasPower(WrigglewallPower.POWER_ID)) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + NAME + DESCRIPTIONS[2] + this.amount2 + DESCRIPTIONS[3];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + NAME + DESCRIPTIONS[4];
        }
    }

    public void updateDescription(boolean hasWriggleWall) {
        if (!hasWriggleWall) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + NAME + DESCRIPTIONS[2] + this.amount2 + DESCRIPTIONS[3];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + NAME + DESCRIPTIONS[4];
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (!this.owner.isPlayer) {
            this.amount = this.amount2;
            this.updateDescription();
        }
    }

    public void atEndOfRound() {
        if (this.owner.isPlayer) {
            if (!owner.hasPower(WrigglewallPower.POWER_ID)) {
                this.amount = this.amount2;
            } else {
                this.amount2 = 0;
            }
            this.updateDescription();
        }
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount < this.owner.currentHealth && damageAmount > 0 && info.owner != null && info.type == DamageInfo.DamageType.NORMAL && info.type != DamageInfo.DamageType.HP_LOSS) {
            this.flash();
            if (this.owner.isPlayer) {
                this.addToTop(new GainBlockAction(this.owner, this.owner, this.amount));
            } else {
                this.addToBot(new GainBlockAction(this.owner, this.owner, this.amount));
            }

            ++this.amount;
            if (owner.hasPower(ResiliencePower.POWER_ID)) {
                amount += owner.getPower(ResiliencePower.POWER_ID).amount;
            }

            this.updateDescription();
        }

        return damageAmount;
    }

    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        this.amount2 += stackAmount;
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        if (this.amount > 0) {
            if (!this.isTurnBased) {
                this.greenColor.a = c.a;
                c = this.greenColor;
            }

            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.amount), x + 5.0F * Settings.scale, y - 7.0F * Settings.scale, this.fontScale, c);
        } else if (this.amount < 0 && this.canGoNegative) {
            this.redColor.a = c.a;
            c = this.redColor;
            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.amount), x + 5.0F * Settings.scale, y - 7.0F * Settings.scale, this.fontScale, c);
        }

        if (this.amount2 != 0 && !owner.hasPower(WrigglewallPower.POWER_ID)) {
            if (!this.isTurnBased) {
                float alpha = c.a;
                c = this.amount2 > 0 ? this.greenColor : this.redColor2;
                c.a = alpha;
            }

            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.amount2), x - 30.0F * Settings.scale, y - 7.0F * Settings.scale, this.fontScale, c);
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = "Malleable";
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
