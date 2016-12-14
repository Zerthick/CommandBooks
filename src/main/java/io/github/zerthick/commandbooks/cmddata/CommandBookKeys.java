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

import com.google.common.reflect.TypeToken;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.mutable.ListValue;
import org.spongepowered.api.data.value.mutable.MutableBoundedValue;

import javax.annotation.Nullable;
import java.util.List;

public class CommandBookKeys {

    public static final Key<ListValue<String>> COMMAND_BOOK_COMMANDS = KeyFactory.makeListKey(new TypeToken<List<String>>() {},
            new TypeToken<ListValue<String>>() {},
            DataQuery.of("CommandBookCommands"), "commandbooks:command_book_commands", "Command Book Commands");

    public static final Key<MutableBoundedValue<Integer>> COMMAND_BOOK_USES = KeyFactory.makeSingleKey(TypeToken.of(Integer.class),
            new TypeToken<MutableBoundedValue<Integer>>(){}, DataQuery.of("CommandBookUses"),"commandbooks:command_book_uses", "Command Book Uses");

}
