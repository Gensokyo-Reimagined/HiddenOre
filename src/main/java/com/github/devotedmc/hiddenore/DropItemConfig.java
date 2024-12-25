package com.github.devotedmc.hiddenore;

import java.util.ArrayList;
import java.util.List;

import com.nexomc.nexo.api.NexoBlocks;
import com.nexomc.nexo.api.NexoItems;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DropItemConfig {

	private @Nullable String nexoItem;
	private @Nullable ItemStack bukkitItem;
	private @NotNull DropItemConfig.DropItemType type;

	private boolean canTransform;

	public @Nullable String getNexoItem(){
		return nexoItem;
	}

	public @Nullable ItemStack getBukkitItem(){
		return bukkitItem;
	}

	public @NotNull Object getItem(){
		return switch(type){
			case NEXO -> nexoItem;
			case BUKKIT -> bukkitItem;
		};
	}

	public @NotNull DropItemConfig.DropItemType getType(){
		return type;
	}

	public DropItemConfig(@NotNull ItemStack bukkitItem){
		this(DropItemType.BUKKIT,bukkitItem);
	}

	public DropItemConfig(@NotNull String nexoItem){
		this(DropItemType.NEXO,nexoItem);
	}

	public DropItemConfig(@NotNull DropItemType type,@NotNull Object transform){
		nexoItem=null;
		bukkitItem=null;
		this.type=type;
		switch(type){
			case NEXO -> {
				nexoItem=(String)transform;
				canTransform = NexoBlocks.isCustomBlock(nexoItem);
			}
			case BUKKIT -> {
				bukkitItem=(ItemStack)transform;
				canTransform=bukkitItem.getType().isBlock();
			}
		}
	}

	public enum DropItemType {
		NEXO,
		BUKKIT
	}

	public boolean canTransform() {
		return canTransform;
	}

	public ItemStack render(double multiplier) {
		ItemStack is=null;
		switch(type){
			case NEXO -> {
				is = NexoItems.itemFromId(nexoItem).build();
			}
			case BUKKIT -> {
				is = bukkitItem.clone();
			}
		}

		is.setAmount((int) Math.round((double) is.getAmount() * multiplier));
		if (is.getAmount() > is.getMaxStackSize()) {
			is.setAmount(is.getMaxStackSize());
		}
		return is;
	}
	
	public static List<DropItemConfig> transform(List<ItemStack> bukkitItems, List<String> nexoItems) {
		ArrayList<DropItemConfig> drops = new ArrayList<DropItemConfig>(bukkitItems.size()+nexoItems.size());
		
		for (ItemStack item : bukkitItems) {
			if (item != null) {
				drops.add(new DropItemConfig(item));
			}
		}
		for (String item : nexoItems) {
			if (item != null&&NexoItems.exists(item)) {
				drops.add(new DropItemConfig(item));
			}
		}

		return drops;
	}
}
