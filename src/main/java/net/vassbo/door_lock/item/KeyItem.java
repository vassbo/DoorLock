package net.vassbo.door_lock.item;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class KeyItem extends Item {
    private static String TOOLTIP_TEXT = "item_tooltip.door_lock.key";
    private static Formatting TOOLTIP_FORMAT = Formatting.GOLD;

    public KeyItem(Settings settings) {
		super(settings);
	}

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable(TOOLTIP_TEXT).formatted(TOOLTIP_FORMAT));
    }
}
