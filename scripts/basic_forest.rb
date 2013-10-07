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

$forests = Array.new
forest = Java::lowlevel::Dungeon.new 30, 30
forest1 = Java::lowlevel::Dungeon.new 30, 30
$num_forests = Java::dnd::Dice.new 10,20

$tree1_proto = Java::lowlevel::Tile.new 'Tree', $adder + 'res/tree1.png', true, false

$tree2_proto = Java::lowlevel::Tile.new 'Tree', $adder + 'res/tree2.png', true, false
grass_proto = Java::lowlevel::Tile.new 'Grass', $adder + 'res/grass.png', true, true

#num_trees = Java::dnd::Dice.new(forest.getHeight, forest.getWidth).throwDice
$num_groups_trees = Java::dnd::Dice.new 8, 6
$num_trees_in_group = Java::dnd::Dice.new 1, 9
$groups = Array.new



def gen_forests
  $num_forests.throwDice.times{
    $forests.push(Java::lowlevel::Dungeon.new(30, 30))
  }
  i = 0
  $forests.each {
    |f|
    if i != 0 
      
    end
  } 
end

def tree 
  if Random.rand(1 .. 2) == 1 
    return Java::lowlevel::Tile.new 'Tree', $adder + 'res/tree1.png', false, false
  else
    return Java::lowlevel::Tile.new 'Tree', $adder + 'res/tree2.png', false, false
  end
end


def seed_groups f
  ($num_groups_trees.throwDice).times {
    prew_h = Random.rand(2 .. f.getHeight-2) 
    prew_w = Random.rand(2 .. f.getWidth-2)

    tmp = Java::lowlevel::Tile.new($tree1_proto, prew_h, prew_w)
    f.removeTile prew_h, prew_w
    f.addTile tmp
    $groups.push tmp
    h = 0
    w = 0
    while(h <= 0 || w <= 0 || w >= f.getWidth || h >= f.getHeight || (((w - prew_w).abs < 5) && ((h - prew_h).abs < 5)))
      
      h = Random.rand(2 .. f.getHeight-2)
      w = Random.rand(2 .. f.getWidth-2)
    end
    prew_w = w
    prew_h = h
  }
end



def seed_full f
  trees_places = Array.new
  i = 0
  $groups.each{
    |g|
    num = $num_trees_in_group.throwDice
    num.times {
      |i|
      w = g.getY
      h = g.getX
      while(w <= 0 || h <= 0 || w >= f.getHeight || h >= f.getWidth || f.getTile(h, w).getName == 'Tree') 
        w = Random.rand(g.getY-3 .. g.getY+3)
        h = Random.rand(g.getX-3 .. g.getX+3)
      end
      f.removeTile h, w
      f.addTile Java::lowlevel::Tile.new($tree2_proto, h, w)
      puts "group #{i} tree n #{i} from #{num}" 
    }
    i+=1
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

gen_forests

seed_groups forest
seed_full forest


seed_groups forest1

seed_full forest1 
Java::lowlevel::AbstractThing::MainType.value_of("WEAPON")
forest.addPortal(Java::lowlevel::Portal.new(forest, forest1, 1, 1))
forest1.addPortal(Java::lowlevel::Portal.new(forest1, forest, 1, 1))


#sword = Java::items::Weapon.new("Test", adder+"res/star.png", Java::items.Weapon::Type.value_of("MACE"), "Sword", Java::dnd::Dice.new(1,6), 100, 10, 3, 3)
#forest.addThing sword
puts "Trees done!"
puts "Done!"
