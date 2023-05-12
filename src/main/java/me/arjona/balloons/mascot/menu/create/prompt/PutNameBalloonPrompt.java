package me.arjona.balloons.mascot.menu.create.prompt;

import me.arjona.customutilities.CC;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

public class PutNameBalloonPrompt extends StringPrompt {

    @Override
    public String getPromptText(ConversationContext conversationContext) {
        return CC.GREEN + "Please enter a name for your balloon";
    }

    @Override
    public Prompt acceptInput(ConversationContext conversationContext, String s) {
        if (s == null || s.isEmpty()) {
            conversationContext.getForWhom().sendRawMessage(CC.RED + "You must enter a name for your balloon");
            return this;
        }

        return null;
    }
}
