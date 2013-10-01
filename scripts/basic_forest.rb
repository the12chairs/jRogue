#!/usr/bin/jruby

require 'java'

adder = './scripts/' # Make it blank for run from conlose

require adder + 'lib/lowlevel.jar'
require adder + 'lib/rlforj.jar'
require adder + 'lib/json-simple.jar'
require adder + 'lib/lifeforms.jar'
require adder + 'lib/primitives.jar'


puts "Making dungeon..."

forest = Java::lowlevel::Dungeon.new 30, 30

tree_proto = Java::lowlevel::Tile.new 'Tree', adder + 'res/tree.png', false, false

grass_proto = Java::lowlevel::Tile.new 'Grass', adder + 'res/grass.png', false, true

num_trees = 50

puts "Making grass..."


(forest.getHeight).times {
  |i|
  (forest.getWidth).times {
    |j|
    forest.addTile(Java::lowlevel::Tile.new(grass_proto, i, j))
  }
}
puts "Grass done!"


puts "Seeds trees"

num_trees.times {
  
  forest.addTile(Java::lowlevel::Tile.new(tree_proto, Random.rand(1 .. forest.getHeight-1), Random.rand(1 .. forest.getWidth-1)))
}

puts "Trees done!"
puts "Done!"
