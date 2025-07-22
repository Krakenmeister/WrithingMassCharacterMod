package writhingmasscharactermod.patches.infest;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CtBehavior;
import org.lwjgl.util.vector.Vector2f;
import writhingmasscharactermod.WrithingMassCharacterMod;
import writhingmasscharactermod.util.InfestUtils;
import writhingmasscharactermod.util.WrithingCard;

import java.util.ArrayList;
import java.util.List;

@SpirePatch2(
        clz= AbstractMonster.class,
        method="render"
)
public class RenderInfestCardsPatch {
    private static final Texture cardSlotTexture = ImageMaster.loadImage(WrithingMassCharacterMod.imagePath("cardslot.png"));

    @SpireInsertPatch(
            locator=Locator.class
    )
    public static void Insert(AbstractMonster __instance, SpriteBatch sb) {
        if (!__instance.isDying && !__instance.isEscaping && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.player.isDead && !Settings.hideCombatElements) {
            // Render slots
            List<Vector2f> cardPositions = InfestUtils.GetInfestedCardPositions(__instance);
            float cardWidth = InfestUtils.GetInfestedCardWidth();
            float cardHeight = InfestUtils.GetInfestedCardHeight();

            for (Vector2f cardPosition : cardPositions) {
                sb.setColor(Color.WHITE);
                sb.draw(cardSlotTexture, cardPosition.x - (cardWidth / 2), cardPosition.y - (cardHeight / 2), cardWidth, cardHeight);
            }

            List<WrithingCard> infestedCards = InfestCardFieldPatch.infestedCards.get(__instance);
            for (int i = 0; i < infestedCards.size(); i++) {
                WrithingCard infestedCard = infestedCards.get(i);
                Vector2f pos = cardPositions.get(i);

                infestedCard.current_x = pos.x;
                infestedCard.current_y = pos.y;

                infestedCard.hb.move(pos.x, pos.y);
                infestedCard.hb.update();

                if (infestedCard.hb.hovered) {
                    infestedCard.drawScale = 1.0f;
                    infestedCard.lighten(true);
                } else {
                    infestedCard.drawScale = (cardWidth - 5.0F * Settings.scale) / AbstractCard.IMG_WIDTH;
                    infestedCard.unhover();
                }

                infestedCard.calculateCardDamage(null);
                infestedCard.render(sb);
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(Hitbox.class, "render");
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }
    }
}
