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

package io.github.zerthick.commandbooks.data;

import io.github.zerthick.commandbooks.data.commandbook.builder.CommandBookDataManipulatorBuilder;
import io.github.zerthick.commandbooks.data.commandbook.immutable.ImmutableCommandBookData;
import io.github.zerthick.commandbooks.data.commandbook.mutable.CommandBookData;
import org.spongepowered.api.Sponge;

public class CommandBooksData {
    public static void registerData() {
        Sponge.getDataManager().register(CommandBookData.class, ImmutableCommandBookData.class, new CommandBookDataManipulatorBuilder());
    }
}