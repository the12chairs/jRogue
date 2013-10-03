require 'java'

adder = './scripts/' # Make it blank for run from conlose

require adder + 'lib/lowlevel.jar'
require adder + 'lib/rlforj.jar'
require adder + 'lib/json-simple.jar'
require adder + 'lib/lifeforms.jar'
require adder + 'lib/primitives.jar'
require adder + 'lib/dnd.jar'

puts "Making village"

village = Java::lowlevel::Dungeon.new 30, 30


num_houses = Java::dnd::Dice.new(2, 6).throwDice 
grass_proto = Java::lowlevel::Tile.new 'Grass', adder + 'res/grass.png', true, true
tree1_proto = Java::lowlevel::Tile.new 'Tree', adder + 'res/tree1.png', true, false
(village.getHeight).times {
  |i|
  (village.getWidth).times {
    |j|
    village.addTile(Java::lowlevel::Tile.new(grass_proto, i, j))
  }
}


num_houses.times {
  hlx = Random::rand(1 .. village.getHeight)
  hly = Random::rand(1 .. village.getWidth)
  lrx  = hlx + Java::dnd::Dice.new(1, 8).throwDice
  lry = hly + Java::dnd::Dice.new(1, 8).throwDice
  village.removeTile hlx, hly
  village.addTile(Java::lowlevel::Tile.new(tree1_proto, hlx, hly))
  village.removeTile lrx, lry
  village.addTile(Java::lowlevel::Tile.new(tree1_proto, lrx, lry))
}
