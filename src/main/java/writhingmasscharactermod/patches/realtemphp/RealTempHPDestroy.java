package writhingmasscharactermod.patches.realtemphp;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

@SpirePatch(
        clz = AbstractRoom.class,
        method = "endBattle"
)
public class RealTempHPDestroy {
    public static void Prefix(AbstractRoom __instance) {
        RealTempHPField.realTempHp.set(AbstractDungeon.player, 0);
    }
}