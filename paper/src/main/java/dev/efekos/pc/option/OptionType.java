/*
 * MIT License
 *
 * Copyright (c) 2024 efekos
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.efekos.pc.option;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public interface OptionType<T> {

    OptionType<String> STRING = noSerialization();
    OptionType<Integer> INTEGER = noSerialization();
    OptionType<Float> FLOAT = of(Float::doubleValue, Double::floatValue);
    OptionType<Double> DOUBLE = noSerialization();
    OptionType<Boolean> BOOLEAN = noSerialization();
    OptionType<ItemStack> ITEM_STACK = of(ItemStack::serialize,ItemStack::deserialize);
    OptionType<Location> LOCATION = of(Location::serialize,Location::deserialize);
    OptionType<Material> MATERIAL = of(
            material -> material.getKey().toString(),
            s -> Arrays.stream(Material.values()).filter(mt->mt.getKey().toString().equals(s)).findFirst().orElse(null)
    );
    OptionType<OfflinePlayer> OFFLINE_PLAYER = of(player -> player.getUniqueId().toString(), s -> Bukkit.getOfflinePlayer(UUID.fromString(s.toString())));
    OptionType<Player> PLAYER = of(player -> player.getUniqueId().toString(), s -> Bukkit.getPlayer(UUID.fromString(s.toString())));

    default OptionType<List<T>> array(){
        return of(ts -> ts.stream().map(this::serialize).toList(),s -> ((List<Object>)s).stream().map(this::deserialize).toList());
    }

    static <T> OptionType<T> noSerialization(){
        return new OptionType<>() {
            @Override
            public Object serialize(T v) {
                return v;
            }

            @Override
            public T deserialize(Object s) {
                return (T)s;
            }
        };
    }

    static <T,TTC> OptionType<T> of(Function<T,Object> serializer, Function<TTC,T> deserializer) {
        return new OptionType<>() {
            @Override
            public Object serialize(T v) {
                return serializer.apply(v);
            }

            @Override
            public T deserialize(Object s) {
                return deserializer.apply((TTC)s);
            }
        };
    }

    Object serialize(T v);
    T deserialize(Object s);

}