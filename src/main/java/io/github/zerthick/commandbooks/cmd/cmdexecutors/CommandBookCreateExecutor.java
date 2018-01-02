/*
 * Copyright (C) 2018  Zerthick
 *
 * This file is part of CommandBooks.
 *
 * CommandBooks is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * CommandBooks is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CommandBooks.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.zerthick.commandbooks.cmd.cmdexecutors;

import com.google.common.collect.ImmutableList;
import io.github.zerthick.commandbooks.CommandBooks;
import io.github.zerthick.commandbooks.data.commandbook.mutable.CommandBookData;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandBookCreateExecutor extends AbstractCommandExecutor {

    private static Pattern textPattern =Pattern.compile("\\{\"text\":\"(.*?)\"}");

    public CommandBookCreateExecutor(CommandBooks plugin) {
        super(plugin);
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {

        Optional<Integer> usesOptional = args.getOne(CommandArgs.USES);

        if (src instanceof Player) {
            Player player = (Player)src;

            int uses = usesOptional.orElse(-1);

            //Get the item the player is holding
            ItemStack item = player.getItemInHand(HandTypes.MAIN_HAND).orElse(null);

            if (item != null && item.getType() == ItemTypes.WRITTEN_BOOK) {

                boolean isCommandBook = item.get(CommandBookData.class).isPresent();

                ItemStack commandBookItem = ItemStack.of(ItemTypes.ENCHANTED_BOOK, 1);

                //Attach the command data
                List<String> commands = new ArrayList<>();
                for(Text page : item.get(Keys.BOOK_PAGES).get()) {
                    Matcher matcher = textPattern.matcher(page.toPlain());
                    if(matcher.find()) {
                        commands.addAll(Arrays.asList(matcher.group(1).split("\\\\n")));
                    }
                }
                commandBookItem.offer(new CommandBookData(commands, uses));

                //Set Name
                Text name = item.get(Keys.DISPLAY_NAME).orElse(Text.of(""));
                commandBookItem.offer(Keys.DISPLAY_NAME, Text.of(TextStyles.ITALIC, TextColors.AQUA, '[', name, ']'));

                //Display Uses in Item Lore
                if(uses != -1) {
                    commandBookItem.offer(Keys.ITEM_LORE, ImmutableList.of(Text.of("Uses: ", uses)));
                } else {
                    commandBookItem.offer(Keys.ITEM_LORE, ImmutableList.of(Text.of("Uses: ", "\u221E")));
                }

                player.setItemInHand(HandTypes.MAIN_HAND, commandBookItem);
            } else {
                player.sendMessage(ChatTypes.CHAT, Text.of(TextColors.RED, "You can't make a Command Book out of that item!"));
            }
            return CommandResult.success();
        }
        src.sendMessage(Text.of("You can't create Command Books from the console!"));
        return CommandResult.success();
    }
}
