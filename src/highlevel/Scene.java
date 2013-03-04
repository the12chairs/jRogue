package highlevel;

import java.util.LinkedList;

import primitives.Quest;

import lifeforms.Mob;
import lifeforms.NPC;
import lowlevel.AbstractThing;
import lowlevel.Dungeon;

public class Scene {
	private LinkedList<AbstractThing> sceneThings;
	private LinkedList<Quest> sceneQuests;
	private LinkedList<NPC> sceneNPCs;
	private LinkedList<Mob> sceneMobs;
	private boolean heroPresence;
	private Dungeon map;
}
