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

package io.github.zerthick.commandbooks;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import io.github.zerthick.commandbooks.cmd.CommandRegister;
import io.github.zerthick.commandbooks.data.CommandBookDataRegister;
import io.github.zerthick.commandbooks.data.commandbook.CommandBookKeys;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

import java.util.List;
import java.util.Optional;

@Plugin(
        id = "commandbooks",
        name = "CommandBooks",
        description = "A simple Command Book plugin",
        authors = {
                "Zerthick"
        }
)
public class CommandBooks {

    @Inject
    private Logger logger;

    @Inject
    private PluginContainer instance;


    public Logger getLogger() {
        return logger;
    }

    public PluginContainer getInstance() {
        return instance;
    }

    @Listener
    public void onGamePreInit(GamePreInitializationEvent event) {

        //Register Custom Data Manipulators
        CommandBookDataRegister.registerData(getInstance());

    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {

        //Register Commands
        CommandRegister.registerCmds(this);

        // Log Start Up to Console
        getLogger().info(
                instance.getName() + " version " + instance.getVersion().orElse("")
                        + " enabled!");
    }

    @Listener
    public void onItemInteract(InteractItemEvent.Primary event, @Root Player player) {

        ItemStackSnapshot item = event.getItemStack();
        if (item.getType() == ItemTypes.ENCHANTED_BOOK) {
            Optional<List<String>> commands = item.get(CommandBookKeys.COMMAND_BOOK_COMMANDS);
            Optional<Integer> uses = item.get(CommandBookKeys.COMMAND_BOOK_USES);
            if(commands.isPresent() && uses.isPresent()) {

                if(player.hasPermission("commandbooks.use")) {

                    //Process the commands
                    commands.get().forEach(c -> {
                        String cmd = c.replace("[p]", player.getName())
                                .replace("[w]", player.getWorld().getName());
                        //Check if it is a console command
                        if(cmd.startsWith("$")){
                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), cmd.substring(1).trim());
                        } else {
                            Sponge.getCommandManager().process(player, cmd.trim());
                        }
                    });

                    //Decrement number of uses and remove item if appropriate
                    if (uses.get() != -1) {
                        int numUsesLeft = uses.get() - 1;
                        if (numUsesLeft == 0) {
                            player.setItemInHand(event.getHandType(), null);
                        } else {
                            ItemStack updatedItem = item.createStack();
                            updatedItem.offer(CommandBookKeys.COMMAND_BOOK_USES, numUsesLeft);
                            updatedItem.offer(Keys.ITEM_LORE, ImmutableList.of(Text.of("Uses: ", numUsesLeft)));
                            player.setItemInHand(event.getHandType(), updatedItem);
                        }
                    }
                }

                //Cancel opening the book
                event.setCancelled(true);
            }
        }
    }
}
