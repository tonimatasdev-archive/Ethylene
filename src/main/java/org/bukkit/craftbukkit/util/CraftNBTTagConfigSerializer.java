package org.bukkit.craftbukkit.util;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class CraftNBTTagConfigSerializer {

    private static final Pattern ARRAY = Pattern.compile("^\\[.*]");
    private static final Pattern INTEGER = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)?i", Pattern.CASE_INSENSITIVE);
    private static final Pattern DOUBLE = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?d", Pattern.CASE_INSENSITIVE);
    private static final TagParser MOJANGSON_PARSER = new TagParser(new StringReader(""));

    public static String serialize(@NotNull final Tag base) {
        final SnbtPrinterTagVisitor snbtVisitor = new SnbtPrinterTagVisitor();
        return snbtVisitor.visit(base);
    }

    public static Tag deserialize(final Object object) {
        // The new logic expects the top level object to be a single string, holding the entire nbt tag as SNBT.
        if (object instanceof final String snbtString) {
            try {
                return TagParser.parseTag(snbtString);
            } catch (final CommandSyntaxException e) {
                throw new RuntimeException("Failed to deserialise nbt", e);
            }
        } else { // Legacy logic is passed to the internal legacy deserialization that attempts to read the old format that *unsuccessfully* attempted to read/write nbt to a full yml tree.
            return internalLegacyDeserialization(object);
        }
    }

    private static Tag internalLegacyDeserialization(@NotNull final Object object) {
        if (object instanceof Map) {
            CompoundTag compound = new CompoundTag();
            for (Map.Entry<String, Object> entry : ((Map<String, Object>) object).entrySet()) {
                compound.put(entry.getKey(), internalLegacyDeserialization(entry.getValue()));
            }

            return compound;
        } else if (object instanceof List) {
            List<Object> list = (List<Object>) object;
            if (list.isEmpty()) {
                return new ListTag(); // Default
            }

            ListTag tagList = new ListTag();
            for (Object tag : list) {
                tagList.add(internalLegacyDeserialization(tag));
            }

            return tagList;
        } else if (object instanceof String) {
            String string = (String) object;

            if (ARRAY.matcher(string).matches()) {
                try {
                    return new TagParser(new StringReader(string)).readArrayTag();
                } catch (CommandSyntaxException e) {
                    throw new RuntimeException("Could not deserialize found list ", e);
                }
            } else if (INTEGER.matcher(string).matches()) { //Read integers on our own
                return IntTag.valueOf(Integer.parseInt(string.substring(0, string.length() - 1)));
            } else if (DOUBLE.matcher(string).matches()) {
                return DoubleTag.valueOf(Double.parseDouble(string.substring(0, string.length() - 1)));
            } else {
                Tag Tag = MOJANGSON_PARSER.type(string);

                if (Tag instanceof IntTag) { // If this returns an integer, it did not use our method from above
                    return StringTag.valueOf(Tag.getAsString()); // It then is a string that was falsely read as an int
                } else if (Tag instanceof DoubleTag) {
                    return StringTag.valueOf(String.valueOf(((DoubleTag) Tag).getAsDouble())); // Doubles add "d" at the end
                } else {
                    return Tag;
                }
            }
        }

        throw new RuntimeException("Could not deserialize Tag");
    }
}
