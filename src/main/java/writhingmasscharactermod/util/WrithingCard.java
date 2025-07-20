package writhingmasscharactermod.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import writhingmasscharactermod.cards.BaseCard;
import writhingmasscharactermod.patches.FormFieldPatch;

public abstract class WrithingCard extends BaseCard {
    public boolean isBenign = true;
    public boolean isMutable = false;
    public boolean isInert = false;

    public boolean shouldFlash = true;

    public AbstractCreature owner = AbstractDungeon.player;

    public WrithingCard(String ID, CardStats info, boolean isBenign) {
        super(ID, info);
        setBenign(isBenign);
    }

    public void setBenign(boolean isBenign) {
        this.isBenign = isBenign;
        this.rawDescription = updateCardText(isBenign);
        this.initializeDescription();
        this.initializeTitle();

        if (cardsToPreview != null) {
            if (cardsToPreview instanceof WrithingCard) {
                ((WrithingCard)cardsToPreview).setBenign(!isBenign);
            }
        }
    }

    public void setOwner(AbstractCreature owner) {
        this.owner = owner;
    }

    @Override
    protected void applyPowersToBlock() {
        this.isBlockModified = false;
        float tmp = (float)this.baseBlock;

        for(AbstractPower p : owner.powers) {
            tmp = p.modifyBlock(tmp, this);
        }

        for(AbstractPower p : owner.powers) {
            tmp = p.modifyBlockLast(tmp);
        }

        if (this.baseBlock != MathUtils.floor(tmp)) {
            this.isBlockModified = true;
        }

        if (tmp < 0.0F) {
            tmp = 0.0F;
        }

        this.block = MathUtils.floor(tmp);
    }

    public void calculateCardDamage(AbstractCreature target) {
        this.isDamageModified = false;
        if (!this.isMultiDamage) {
            float tmp = (float)this.baseDamage;

            if (owner instanceof AbstractPlayer) {
                for(AbstractRelic r : AbstractDungeon.player.relics) {
                    tmp = r.atDamageModify(tmp, this);
                    if (this.baseDamage != (int)tmp) {
                        this.isDamageModified = true;
                    }
                }
            }

            for(AbstractPower p : owner.powers) {
                tmp = p.atDamageGive(tmp, this.damageTypeForTurn, this);
            }

            if (owner instanceof AbstractPlayer) {
                tmp = AbstractDungeon.player.stance.atDamageGive(tmp, this.damageTypeForTurn, this);
                if (this.baseDamage != (int)tmp) {
                    this.isDamageModified = true;
                }

                tmp = FormFieldPatch.form.get(AbstractDungeon.player).atDamageGive(tmp, this.damageTypeForTurn, this);
                if (this.baseDamage != (int)tmp) {
                    this.isDamageModified = true;
                }
            }

            for(AbstractPower p : target.powers) {
                tmp = p.atDamageReceive(tmp, this.damageTypeForTurn, this);
            }

            for(AbstractPower p : owner.powers) {
                tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn, this);
            }

            for(AbstractPower p : target.powers) {
                tmp = p.atDamageFinalReceive(tmp, this.damageTypeForTurn, this);
            }

            if (tmp < 0.0F) {
                tmp = 0.0F;
            }

            if (this.baseDamage != MathUtils.floor(tmp)) {
                this.isDamageModified = true;
            }

            this.damage = MathUtils.floor(tmp);
        }
    }

    public void setMutable(boolean isMutable) {
        this.isMutable = isMutable;
    }

    public void setInert(boolean isInert) {
        this.isInert = isInert;
    }

    public void toggleBenign() {
        setBenign(!this.isBenign);
    }

    @Override
    public boolean freeToPlay() {
        if (!isBenign) {
            return true;
        }
        return super.freeToPlay();
    }

    protected abstract String updateCardText(boolean isBenign);

    public void triggerOnGlowCheck() {
        if (isBenign) {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = Color.RED;
        }
    }

    public abstract void benignUse(AbstractCreature source, AbstractCreature target);
    public abstract void malignantUse(AbstractCreature source, AbstractCreature target);

    public void writhingUse(AbstractCreature source, AbstractCreature target) {
        if (isBenign) {
            benignUse(source, target);
        } else {
            malignantUse(source, target);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        writhingUse(p, m);
    }

    public void upgrade() {
        if (!this.upgraded && this.cardsToPreview != null) {
            this.cardsToPreview.upgrade();
        }

        super.upgrade();
    }
}