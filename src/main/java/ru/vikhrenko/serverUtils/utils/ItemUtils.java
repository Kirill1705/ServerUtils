package ru.vikhrenko.serverUtils.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.LinkedList;
import java.util.List;

public final class ItemUtils {
    public static void setCustomModelData(ItemMeta meta, String value) {
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        List<String> strings = new LinkedList<>();
        strings.add(value);
        component.setStrings(strings);
        meta.setCustomModelDataComponent(component);
    }
    public static void setCustomModelData(ItemStack itemStack, String value) {
        if (!itemStack.hasItemMeta()) {
            throw new IllegalArgumentException("ItemStack must have ItemMeta");
        }
        if (value==null|| value.isEmpty()) {
            throw new IllegalArgumentException("value can't be null or empty");
        }
        setCustomModelData(itemStack.getItemMeta(), value);
    }
    public static String getCustomModelData(ItemMeta meta) {
        if (meta.hasCustomModelDataComponent()) {
            CustomModelDataComponent component = meta.getCustomModelDataComponent();
            List<String> strings = component.getStrings();
            return strings.get(0);
        }
        return null;
    }
    public static String getCustomModelData(ItemStack itemStack) {
        if (!itemStack.hasItemMeta()||!itemStack.getItemMeta().hasCustomModelDataComponent()) {
            throw new IllegalArgumentException("itemStack must have ItemMeta and it must have CustomModelData");
        }
        return getCustomModelData(itemStack.getItemMeta());
    }
    public static int getCustomModelDataAsInt(ItemMeta meta) {
        String data = getCustomModelData(meta);
        if (data==null) {
            return 0;
        }
        return Integer.parseInt(data);
    }
    public static int getCustomModelDataAsInt(ItemStack itemStack) {
        if (!itemStack.hasItemMeta()||!itemStack.getItemMeta().hasCustomModelDataComponent()) {
            throw new IllegalArgumentException("itemStack must have ItemMeta and it must have CustomModelData");
        }
        return getCustomModelDataAsInt(itemStack.getItemMeta());
    }
}
