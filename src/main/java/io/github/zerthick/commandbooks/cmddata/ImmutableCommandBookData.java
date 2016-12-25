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

package io.github.zerthick.commandbooks.cmddata;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableBoundedValue;
import org.spongepowered.api.data.value.immutable.ImmutableListValue;

import java.util.ArrayList;
import java.util.List;

public class ImmutableCommandBookData extends AbstractImmutableData<ImmutableCommandBookData, CommandBookData> {

    private final List<String> commands;
    private final int uses;

    public ImmutableCommandBookData() {
        this(new ArrayList<>(), 0);
    }

    public ImmutableCommandBookData(List<String> commands, int uses) {
        this.commands = commands;
        this.uses = uses;
    }

    public ImmutableListValue<String> commands() {
        return Sponge.getRegistry().getValueFactory().createListValue(CommandBookKeys.COMMAND_BOOK_COMMANDS, this.commands, null).asImmutable();
    }

    public ImmutableBoundedValue<Integer> uses() {
        return Sponge.getRegistry().getValueFactory().createBoundedValueBuilder(CommandBookKeys.COMMAND_BOOK_USES)
                .defaultValue(0)
                .minimum(-1)
                .maximum(Integer.MAX_VALUE)
                .actualValue(this.uses)
                .build()
                .asImmutable();
    }

    private List<String> getCommands() {
        return commands;
    }

    private int getUses() {
        return uses;
    }

    @Override
    protected void registerGetters() {
        registerFieldGetter(CommandBookKeys.COMMAND_BOOK_COMMANDS, this::getCommands);
        registerKeyValue(CommandBookKeys.COMMAND_BOOK_COMMANDS, this::commands);

        registerFieldGetter(CommandBookKeys.COMMAND_BOOK_USES, this::getUses);
        registerKeyValue(CommandBookKeys.COMMAND_BOOK_USES, this::uses);
    }

    @Override
    public CommandBookData asMutable() {
        return new CommandBookData(this.getCommands(), this.getUses());
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer()
                .set(CommandBookKeys.COMMAND_BOOK_COMMANDS, this.getCommands())
                .set(CommandBookKeys.COMMAND_BOOK_USES, this.getUses());
    }
}
