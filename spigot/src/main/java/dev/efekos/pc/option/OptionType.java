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

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public interface OptionType<T> {

    OptionType<String> STRING = noSerialization();
    OptionType<Integer> INTEGER = noSerialization();
    OptionType<Float> FLOAT = of(Float::doubleValue, Double::floatValue);
    OptionType<Double> DOUBLE = noSerialization();
    OptionType<Boolean> BOOLEAN = noSerialization();
    OptionType<ItemStack> ITEM_STACK = of(ItemStack::serialize, ItemStack::deserialize);
    OptionType<Location> LOCATION = of(Location::serialize, Location::deserialize);
    OptionType<Material> MATERIAL = ofEnum(Material.class);
    OptionType<Instrument> INSTRUMENT = ofEnum(Instrument.class);
    OptionType<Axis> AXIS = ofEnum(Axis.class);
    OptionType<ChatColor> CHAT_COLOR = ofEnum(ChatColor.class);
    OptionType<DyeColor> DYE_COLOR = ofEnum(DyeColor.class);
    OptionType<GameMode> GAME_MODE = ofEnum(GameMode.class);
    OptionType<Effect> EFFECT = ofEnum(Effect.class);
    OptionType<Art> ART = ofEnum(Art.class);
    OptionType<Sound> SOUND = ofEnum(Sound.class);
    OptionType<SoundCategory> SOUND_CATEGORY = ofEnum(SoundCategory.class);
    OptionType<WeatherType> WEATHER_TYPE = ofEnum(WeatherType.class);
    OptionType<WorldType> WORLD_TYPE = ofEnum(WorldType.class);
    OptionType<Statistic> STATISTIC = ofEnum(Statistic.class);
    OptionType<Particle> PARTICLE = ofEnum(Particle.class);
    OptionType<Fluid> FLUID = ofEnum(Fluid.class);
    OptionType<Difficulty> DIFFICULTY = ofEnum(Difficulty.class);
    OptionType<Attribute> ATTRIBUTE = ofEnum(Attribute.class);
    OptionType<BarColor> BAR_COLOR = ofEnum(BarColor.class);
    OptionType<BarFlag> BAR_FLAG = ofEnum(BarFlag.class);
    OptionType<BarStyle> BAR_STYLE = ofEnum(BarStyle.class);
    OptionType<EntityType> ENTITY_TYPE = ofEnum(EntityType.class);
    OptionType<Pose> POSE = ofEnum(Pose.class);
    OptionType<SpawnCategory> SPAWN_CATEGORY = ofEnum(SpawnCategory.class);
    OptionType<EquipmentSlot> EQUIPMENT_SLOT = ofEnum(EquipmentSlot.class);
    OptionType<ItemRarity> ITEM_RARITY = ofEnum(ItemRarity.class);
    OptionType<ItemFlag> ITEM_FLAG = ofEnum(ItemFlag.class);
    OptionType<BookMeta.Generation> BOOK_GENERATION = ofEnum(BookMeta.Generation.class);
    OptionType<OfflinePlayer> OFFLINE_PLAYER = of(player -> player.getUniqueId().toString(), s -> Bukkit.getOfflinePlayer(UUID.fromString(s.toString())));
    OptionType<Player> PLAYER = of(player -> player.getUniqueId().toString(), s -> Bukkit.getPlayer(UUID.fromString(s.toString())));

    private static <T extends Enum<T>> OptionType<T> ofEnum(Class<T> clazz) {
        return of(T::name, o -> Enum.valueOf(clazz, o.toString()));
    }

    static <T> OptionType<T> noSerialization() {
        return new OptionType<>() {
            @Override
            public Object serialize(T v) {
                return v;
            }

            @Override
            public T deserialize(Object s) {
                return (T) s;
            }
        };
    }

    static <T, TTC> OptionType<T> of(Function<T, Object> serializer, Function<TTC, T> deserializer) {
        return new OptionType<>() {
            @Override
            public Object serialize(T v) {
                return serializer.apply(v);
            }

            @Override
            public T deserialize(Object s) {
                return deserializer.apply((TTC) s);
            }
        };
    }

    default OptionType<List<T>> array() {
        return of(ts -> ts.stream().map(this::serialize).toList(), s -> ((List<Object>) s).stream().map(this::deserialize).toList());
    }

    Object serialize(T v);

    T deserialize(Object s);

}
