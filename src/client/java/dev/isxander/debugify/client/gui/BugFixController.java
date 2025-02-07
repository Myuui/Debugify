package dev.isxander.debugify.client.gui;

import dev.isxander.yacl.api.Option;
import dev.isxander.yacl.api.utils.Dimension;
import dev.isxander.yacl.gui.AbstractWidget;
import dev.isxander.yacl.gui.YACLScreen;
import dev.isxander.yacl.gui.controllers.BooleanController;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class BugFixController extends BooleanController {
    public BugFixController(Option<Boolean> option) {
        super(option, state -> {
            if (!option.available())
                return Component.translatable("debugify.fix.unavailable");
            return state
                    ? Component.translatable("debugify.fix.enabled").withStyle(ChatFormatting.GREEN)
                    : Component.translatable("debugify.fix.disabled").withStyle(ChatFormatting.RED);
        }, false);
    }

    @Override
    public AbstractWidget provideWidget(YACLScreen screen, Dimension<Integer> widgetDimension) {
        return new BugFixControllerElement(this, screen, widgetDimension);
    }

    public static class BugFixControllerElement extends BooleanControllerElement {
        private String tooltipString;

        public BugFixControllerElement(BugFixController control, YACLScreen screen, Dimension<Integer> dim) {
            super(control, screen, dim);
            control.option().addListener((opt, pending) -> recalculateTooltipString());
            recalculateTooltipString();
        }

        @Override
        public boolean matchesSearch(String query) {
            return super.matchesSearch(query) || tooltipString.contains(query.toLowerCase());
        }

        private void recalculateTooltipString() {
            this.tooltipString = control.option().tooltip().getString().toLowerCase();
        }
    }
}
