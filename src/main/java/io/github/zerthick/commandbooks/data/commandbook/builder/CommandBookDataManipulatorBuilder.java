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

package io.github.zerthick.commandbooks.data.commandbook.builder;

import io.github.zerthick.commandbooks.data.commandbook.CommandBookKeys;
import io.github.zerthick.commandbooks.data.commandbook.immutable.ImmutableCommandBookData;
import io.github.zerthick.commandbooks.data.commandbook.mutable.CommandBookData;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import java.util.List;
import java.util.Optional;

public class CommandBookDataManipulatorBuilder implements DataManipulatorBuilder<CommandBookData, ImmutableCommandBookData>{

    @Override
    public CommandBookData create() {
        return new CommandBookData();
    }

    @Override
    public Optional<CommandBookData> createFrom(DataHolder dataHolder) {
        return Optional.of(dataHolder.get(CommandBookData.class).orElse(new CommandBookData()));
    }

    @Override
    public Optional<CommandBookData> build(DataView container) throws InvalidDataException {
        if(container.contains(CommandBookKeys.COMMAND_BOOK_COMMANDS.getQuery(), CommandBookKeys.COMMAND_BOOK_USES.getQuery())) {
            final List<String> commands = container.getStringList(CommandBookKeys.COMMAND_BOOK_COMMANDS.getQuery()).get();
            final int uses = container.getInt(CommandBookKeys.COMMAND_BOOK_USES.getQuery()).get();

            return Optional.of(new CommandBookData(commands, uses));
        }
        return Optional.empty();
    }
}
