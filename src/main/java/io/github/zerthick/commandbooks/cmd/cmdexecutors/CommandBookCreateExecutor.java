/*
 * Copyright (C) 2016  Zerthick
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
import io.github.zerthick.commandbooks.cmddata.CommandBookData;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommandBookCreateExecutor extends AbstractCommandExecutor {

    private static Pattern textPattern =Pattern.compile("\\{\"text\":\"(.*?)\"}");

    public CommandBookCreateExecutor(CommandBooks plugin) {
        super(plugin);
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        Optional<Integer> usesOptional = args.getOne(CommandArgs.USES);

        if (src instanceof Player) {
            Player player = (Player)src;

            int uses = usesOptional.orElse(-1);

            //Get the item the player is holding
            ItemStack item = player.getItemInHand(HandTypes.MAIN_HAND).orElse(null);

            if(item != null && item.getItem() == ItemTypes.WRITTEN_BOOK) {

                boolean isCommandBook = item.get(CommandBookData.class).isPresent();

                //Attach the command data
                List<String> commands = new ArrayList<>();
                for(Text page : item.get(Keys.BOOK_PAGES).get()) {
                    Matcher matcher = textPattern.matcher(page.toPlain());
                    if(matcher.find()) {
                        commands.addAll(Arrays.asList(matcher.group(1).split("\\\\n")));
                    }
                }
                item.offer(new CommandBookData(commands, uses));

                //If the item was already a command book we don't need to update it's title.
                if(!isCommandBook) {
                    Text name = item.get(Keys.DISPLAY_NAME).orElse(Text.of(""));
                    item.offer(Keys.DISPLAY_NAME, Text.of(TextStyles.ITALIC, TextColors.AQUA, '[', name, ']'));
                }

                if(uses != -1) {
                    item.offer(Keys.ITEM_LORE, ImmutableList.of(Text.of("Uses: ", uses)));
                } else {
                    item.offer(Keys.ITEM_LORE, ImmutableList.of(Text.of("Uses: ", "\u221E")));
                }

                player.setItemInHand(HandTypes.MAIN_HAND, item);
            }
        }

        return CommandResult.success();
    }
}
