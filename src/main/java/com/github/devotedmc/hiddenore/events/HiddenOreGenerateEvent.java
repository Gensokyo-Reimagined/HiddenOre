package com.github.devotedmc.hiddenore.events;

import com.github.devotedmc.hiddenore.DropItemConfig;
import com.nexomc.nexo.api.NexoBlocks;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HiddenOreGenerateEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();

	private final Player player;
	private final Block transformBlock;
	private DropItemConfig transform;

	public HiddenOreGenerateEvent(final Player player, final Block transformBlock, DropItemConfig.DropItemType type, Object transform) {
		super(false);
		this.player = player;
		this.transformBlock = transformBlock;
		setTransform(type,transform);
	}

	private boolean cancel = false;

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancel = cancelled;
	}

	@Override
	public HandlerList getHandlers() {
		return HiddenOreGenerateEvent.handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public Player getPlayer() {
		return player;
	}

	public Block getBlock() {
		return transformBlock;
	}

	private @Nullable String nexoBlock;
	private @Nullable Material bukkitMaterial;
	private @NotNull DropItemConfig.DropItemType type;

	public @Nullable String getNexoBlock(){
		return nexoBlock;
	}

	public @Nullable Material getBukkitMaterial(){
		return bukkitMaterial;
	}

	public @NotNull Object getTransform(){
		return switch(type){
			case NEXO -> nexoBlock;
			case BUKKIT -> bukkitMaterial;
		};
	}

	public @NotNull DropItemConfig.DropItemType getType(){
		return type;
	}

	public void setTransform(Material bukkitMaterial){
		setTransform(DropItemConfig.DropItemType.BUKKIT,bukkitMaterial);
	}
	public void setTransform(String nexoBlock){
		setTransform(DropItemConfig.DropItemType.NEXO,nexoBlock);
	}


	public void setTransform(DropItemConfig.DropItemType type, Object transform){
		nexoBlock=null;
		bukkitMaterial=null;
		this.type=type;
		switch(type){
			case NEXO -> {
				nexoBlock=(String)transform;
				if(!NexoBlocks.isCustomBlock(nexoBlock)){
					throw new RuntimeException("ID "+nexoBlock+" is not a registered NexoBlock");
				}
			}
			case BUKKIT -> {
				bukkitMaterial=(Material)transform;
			}
		}
	}
}
