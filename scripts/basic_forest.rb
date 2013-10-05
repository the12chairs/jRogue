#!/usr/bin/jruby

require 'java'

$adder = './scripts/' # Make it blank for run from conlose

require $adder + 'lib/lowlevel.jar'
require $adder + 'lib/rlforj.jar'
require $adder + 'lib/json-simple.jar'
require $adder + 'lib/lifeforms.jar'
require $adder + 'lib/primitives.jar'
require $adder + 'lib/dnd.jar'

puts "Making forest..."

forest = Java::lowlevel::Dungeon.new 30, 30
forest1 = Java::lowlevel::Dungeon.new 30, 30
tree1_proto = Java::lowlevel::Tile.new 'Tree', $adder + 'res/tree1.png', false, false

tree2_proto = Java::lowlevel::Tile.new 'Tree', $adder + 'res/tree2.png', false, false
grass_proto = Java::lowlevel::Tile.new 'Grass', $adder + 'res/grass.png', false, true

#num_trees = Java::dnd::Dice.new(forest.getHeight, forest.getWidth).throwDice
num_groups_trees = Java::dnd::Dice.new 6, 6
$num_trees_in_group = Java::dnd::Dice.new 1, 12 


def tree 
  if Random.rand(1 .. 2) == 1 
    return Java::lowlevel::Tile.new 'Tree', $adder + 'res/tree1.png', false, false
  else
    return Java::lowlevel::Tile.new 'Tree', $adder + 'res/tree2.png', false, false
  end
end


def seed_groups f
  h = Random.rand(4 .. f.getHeight-4)
  w = Random.rand(4 .. f.getWidth-4)

  f.removeTile h, w
  f.addTile(Java::lowlevel::Tile.new(tree, h, w))
end



def seed_full f
  trees_places = Array.new
  f.dungeon.each{
    |t|
    if t.getName == 'Tree'
      num = $num_trees_in_group.throwDice
      num.times {
        |i|
        h_parent = h = t.getX
        w_parent = w = t.getY

        #$tile = f.getTile(h, w).getName

        while ((f.getTile(h, w)).getName == 'Tree')
          h = Random.rand h_parent-3 .. h_parent+3
          w = Random.rand w_parent-3 .. w_parent+3
          puts "is tree? #{h}:#{w}"
        end
        puts "#{num} trees in group, its #{i} -- #{h}:#{w} \n"
        
        
        #f.removeTile h, w
        #f.addTile tree, h, w
        
        trees_places.push( f.getTile( h, w))
      }
    end
  }
  
  puts "size"
  puts trees_places.size
  trees_places.each {
    |t|
    f.removeTile t.getY, t.getX
    f.addTile tree, t.getY, t.getX
  }
end
puts "Making grass..."


(forest.getHeight).times {
  |i|
  (forest.getWidth).times {
    |j|
    forest.addTile(Java::lowlevel::Tile.new(grass_proto, i, j))
    forest1.addTile(Java::lowlevel::Tile.new(grass_proto, i, j))
  }
}
puts "Grass done!"


puts "Seeding trees"
=begin
num_trees.times {
  if Random.rand(1 .. 2) == 1 
    tree_proto = tree1_proto
  else
    tree_proto = tree2_proto
  end
  h = Random.rand(1 .. forest.getHeight-1)
  w = Random.rand(1 .. forest.getWidth-1)
  forest.removeTile h, w
  forest.addTile(Java::lowlevel::Tile.new(tree_proto, h, w))
  h1 = Random.rand(1 .. forest1.getHeight-1)
  w1 = Random.rand(1 .. forest1.getWidth-1)
  forest1.removeTile h1, w1
  forest1.addTile(Java::lowlevel::Tile.new(tree_proto, h1, w1))
}
=end

(num_groups_trees.throwDice()).times {
  seed_groups forest
  seed_full forest
}

(num_groups_trees.throwDice()).times {
  seed_groups forest1
  seed_full forest1
}
Java::lowlevel::AbstractThing::MainType.value_of("WEAPON")
forest.addPortal(Java::lowlevel::Portal.new(forest, forest1, 1, 1))
forest1.addPortal(Java::lowlevel::Portal.new(forest1, forest, 1, 1))


#sword = Java::items::Weapon.new("Test", adder+"res/star.png", Java::items.Weapon::Type.value_of("MACE"), "Sword", Java::dnd::Dice.new(1,6), 100, 10, 3, 3)
#forest.addThing sword
puts "Trees done!"
puts "Done!"
