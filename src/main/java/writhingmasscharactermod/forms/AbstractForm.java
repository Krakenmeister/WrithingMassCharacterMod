package writhingmasscharactermod.forms;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public abstract class AbstractForm {
    private static final Logger logger = LogManager.getLogger(AbstractForm.class.getName());
    public String name;
    public String description;
    public String ID;
    protected ArrayList<PowerTip> tips = new ArrayList();
    protected Color c;
    protected static final int W = 512;
    protected Texture img;
    protected float angle;
    protected float particleTimer;
    protected float particleTimer2;

    public AbstractForm() {
        this.c = Color.WHITE.cpy();
        this.img = null;
        this.particleTimer = 0.0F;
        this.particleTimer2 = 0.0F;
    }

    public abstract void updateDescription();

    public void atStartOfTurn() {
    }

    public void onEndOfTurn() {
    }

    public void onEnterForm() {
    }

    public void onExitForm() {
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return damage;
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        return this.atDamageGive(damage, type);
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        return damage;
    }

    public void onPlayCard(AbstractCard card) {
    }

    public void update() {
        this.updateAnimation();
    }

    public void updateAnimation() {
    }

    public void render(SpriteBatch sb) {
        if (this.img != null) {
            sb.setColor(this.c);
            sb.setBlendFunction(770, 1);
            sb.draw(this.img, AbstractDungeon.player.drawX - 256.0F + AbstractDungeon.player.animX, AbstractDungeon.player.drawY - 256.0F + AbstractDungeon.player.animY + AbstractDungeon.player.hb_h / 2.0F, 256.0F, 256.0F, 512.0F, 512.0F, Settings.scale, Settings.scale, -this.angle, 0, 0, 512, 512, false, false);
            sb.setBlendFunction(770, 771);
        }
    }

    public void stopIdleSfx() {
    }

    public static AbstractForm getFormFromName(String name) {
        if (name.equals("Low")) {
            return new LowForm();
        } else if (name.equals("Mid")) {
            return new MidForm();
        } else if (name.equals("High")) {
            return new HighForm();
        } else {
            logger.info("[ERROR] Unknown form: " + name + " called for in getFormFromName()");
            return null;
        }
    }
}
