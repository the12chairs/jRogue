#!/usr/bin/jruby

require 'java'

adder = './scripts/' # Make it blank for run from conlose

require adder + 'lib/lowlevel.jar'
require adder + 'lib/rlforj.jar'
require adder + 'lib/json-simple.jar'
require adder + 'lib/lifeforms.jar'
require adder + 'lib/primitives.jar'
require adder + 'lib/dnd.jar'

puts "Making forest..."

forest = Java::lowlevel::Dungeon.new 30, 30

tree1_proto = Java::lowlevel::Tile.new 'Tree', adder + 'res/tree1.png', false, false

tree2_proto = Java::lowlevel::Tile.new 'Tree', adder + 'res/tree2.png', false, false
grass_proto = Java::lowlevel::Tile.new 'Grass', adder + 'res/grass.png', false, true

num_trees = Java::dnd::Dice.new(forest.getHeight, forest.getWidth).throwDice

puts "Making grass..."


(forest.getHeight).times {
  |i|
  (forest.getWidth).times {
    |j|
    forest.addTile(Java::lowlevel::Tile.new(grass_proto, i, j))
  }
}
puts "Grass done!"


puts "Seeding trees"

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
}

puts "Trees done!"
puts "Done!"
