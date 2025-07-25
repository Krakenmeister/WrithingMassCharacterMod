package writhingmasscharactermod.relics.starter;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.powers.RealMalleablePower;
import writhingmasscharactermod.relics.BaseRelic;

import static writhingmasscharactermod.WrithingMassCharacterMod.makeID;

public class Tumor extends BaseRelic {
    private static final String NAME = "Tumor";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.STARTER;
    private static final LandingSound SOUND = LandingSound.HEAVY;
    private static final int STARTING_MALLEABLE = 1;

    public Tumor() {
        super(ID, NAME, WrithingMassCharacter.Meta.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + STARTING_MALLEABLE + DESCRIPTIONS[1];
    }

    public void atBattleStart() {
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RealMalleablePower(AbstractDungeon.player, STARTING_MALLEABLE), STARTING_MALLEABLE));
        this.grayscale = true;
    }

    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }

    public AbstractRelic makeCopy() {
        return new Tumor();
    }
}
