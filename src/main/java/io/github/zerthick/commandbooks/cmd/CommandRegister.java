/*
 * Copyright (C) 2017  Zerthick
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

package io.github.zerthick.commandbooks.cmd;

import io.github.zerthick.commandbooks.CommandBooks;
import io.github.zerthick.commandbooks.cmd.cmdexecutors.CommandArgs;
import io.github.zerthick.commandbooks.cmd.cmdexecutors.CommandBookCreateExecutor;
import io.github.zerthick.commandbooks.cmd.cmdexecutors.CommandBookExecutor;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class CommandRegister {

    public static void registerCmds(CommandBooks plugin) {

        CommandSpec commandBookCreate = CommandSpec.builder()
                .permission("commandbooks.command.create")
                .arguments(GenericArguments.optional(GenericArguments.integer(CommandArgs.USES)))
                .description(Text.of("Create a Command Book from the book held in your maind hand."))
                .executor(new CommandBookCreateExecutor(plugin))
                .build();

        CommandSpec commandBook = CommandSpec.builder()
                .permission("commandbooks.command.info")
                .executor(new CommandBookExecutor(plugin))
                .child(commandBookCreate, "create")
                .build();

        Sponge.getCommandManager().register(plugin, commandBook, "commandBook", "cb");
    }
}
