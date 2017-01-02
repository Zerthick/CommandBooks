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

package io.github.zerthick.commandbooks.data.commandbook.mutable;

import io.github.zerthick.commandbooks.data.commandbook.CommandBookKeys;
import io.github.zerthick.commandbooks.data.commandbook.immutable.ImmutableCommandBookData;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.ListValue;
import org.spongepowered.api.data.value.mutable.MutableBoundedValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandBookData extends AbstractData<CommandBookData, ImmutableCommandBookData>{

    private List<String> commands;
    private int uses;

    public CommandBookData() {
        this(new ArrayList<>(), 0);
    }

    public CommandBookData(List<String> commands, int uses) {
        this.commands = commands;
        this.uses = uses;
        registerGettersAndSetters();
    }

    protected ListValue<String> commands() {
        return Sponge.getRegistry().getValueFactory().createListValue(CommandBookKeys.COMMAND_BOOK_COMMANDS, this.commands, new ArrayList<>());
    }

    protected MutableBoundedValue<Integer> uses() {
        return Sponge.getRegistry().getValueFactory().createBoundedValueBuilder(CommandBookKeys.COMMAND_BOOK_USES)
                .defaultValue(0)
                .minimum(-1)
                .maximum(Integer.MAX_VALUE)
                .actualValue(this.uses)
                .build();
    }

    private List<String> getCommands() {
        return commands;
    }

    private void setCommands(List<String> commands) {
        this.commands = commands;
    }

    private int getUses() {
        return uses;
    }

    private void setUses(int uses) {
        this.uses = uses;
    }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(CommandBookKeys.COMMAND_BOOK_COMMANDS, this::getCommands);
        registerFieldSetter(CommandBookKeys.COMMAND_BOOK_COMMANDS, this::setCommands);
        registerKeyValue(CommandBookKeys.COMMAND_BOOK_COMMANDS, this::commands);

        registerFieldGetter(CommandBookKeys.COMMAND_BOOK_USES, this::getUses);
        registerFieldSetter(CommandBookKeys.COMMAND_BOOK_USES, this::setUses);
        registerKeyValue(CommandBookKeys.COMMAND_BOOK_USES, this::uses);
    }


    @Override
    public Optional<CommandBookData> fill(DataHolder dataHolder) {

        CommandBookData commandBookData = dataHolder.get(CommandBookData.class).orElse(null);

        return Optional.ofNullable(commandBookData);
    }

    @Override
    public Optional<CommandBookData> fill(DataHolder dataHolder, MergeFunction overlap) {

        CommandBookData commandBookData = overlap.merge(this, dataHolder.get(CommandBookData.class).orElse(null));
        setCommands(commandBookData.getCommands());
        setUses(commandBookData.getUses());

        return Optional.of(this);
    }

    @Override
    public Optional<CommandBookData> from(DataContainer container) {

        if(container.contains(CommandBookKeys.COMMAND_BOOK_COMMANDS.getQuery(), CommandBookKeys.COMMAND_BOOK_USES.getQuery())) {
            final List<String> commands = container.getStringList(CommandBookKeys.COMMAND_BOOK_COMMANDS.getQuery()).get();
            final int uses = container.getInt(CommandBookKeys.COMMAND_BOOK_USES.getQuery()).get();

            setCommands(commands);
            setUses(uses);

            return Optional.of(this);
        }
        return Optional.empty();
    }

    @Override
    public CommandBookData copy() {
        return new CommandBookData(this.getCommands(), this.getUses());
    }

    @Override
    public ImmutableCommandBookData asImmutable() {
        return new ImmutableCommandBookData(this.getCommands(), this.getUses());
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
