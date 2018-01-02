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

package io.github.zerthick.commandbooks.data.commandbook;

import com.google.common.reflect.TypeToken;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.ListValue;
import org.spongepowered.api.data.value.mutable.MutableBoundedValue;

public class CommandBookKeys {

    public static Key<ListValue<String>> COMMAND_BOOK_COMMANDS;
    public static Key<MutableBoundedValue<Integer>> COMMAND_BOOK_USES;

    public static void init() {
        COMMAND_BOOK_COMMANDS = Key.builder()
                .type(new TypeToken<ListValue<String>>() {
                })
                .query(DataQuery.of("CommandBookCommands"))
                .id("commandbooks:command_book_commands")
                .name("Command Book Commands")
                .build();

        COMMAND_BOOK_USES = Key.builder()
                .type(new TypeToken<MutableBoundedValue<Integer>>() {
                })
                .query(DataQuery.of("CommandBookUses"))
                .id("commandbooks:command_book_uses")
                .name("Command Book Uses")
                .build();
    }

}
