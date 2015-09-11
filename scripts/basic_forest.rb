#!/usr/bin/jruby
# -*- coding: utf-8 -*-

require 'java'

$adder = './scripts/' # Make it blank for run from conlose

require $adder + 'lib/lowlevel.jar'
require $adder + 'lib/rlforj.jar'
require $adder + 'lib/json-simple.jar'
require $adder + 'lib/lifeforms.jar'
require $adder + 'lib/primitives.jar'
require $adder + 'lib/dnd.jar'

class BasicForest

  @@tree1_proto = Java::lowlevel::Tile.new 'Tree', $adder + 'res/tree1.png', true, false

  @@tree2_proto = Java::lowlevel::Tile.new 'Tree', $adder + 'res/tree2.png', true, false
  @@grass_proto = Java::lowlevel::Tile.new 'Grass', $adder + 'res/grass.png', false, true
  

  attr_accessor :num_groups, :in_group, :groups, :height, :width, :forest
  

  def tree 
    if Random.rand(1 .. 2) == 1 
      Java::lowlevel::Tile.new 'Tree', $adder + 'res/tree1.png', false, false
    else
      Java::lowlevel::Tile.new 'Tree', $adder + 'res/tree2.png', false, false
    end
  end

  def initialize h, w, groups, in_group 
    @width = w
    @height = h
    @forest = Java::lowlevel::Dungeon.new h, w
    @num_groups = groups
    self.in_group = in_group
    self.groups = Array.new
  end

  def seed_grass
    @height.times {
      |i|
      @width.times {
        |j|
        @forest.addTile(Java::lowlevel::Tile.new(@@grass_proto, i, j))
      }
    }
  end

  def seed_centers
    @num_groups.times {
      prew_h = Random.rand(2 .. @height-2) 
      prew_w = Random.rand(2 .. @width-2)

      tmp = Java::lowlevel::Tile.new(tree, prew_h, prew_w)
      @forest.removeTile prew_h, prew_w
      @forest.addTile tmp
      @groups.push tmp
      h = 0
      w = 0
      while(h <= 0 || w <= 0 || w >= @width || h >= @height || (((w - prew_w).abs < 3) && ((h - prew_h).abs < 3)))        
        h = Random.rand(2 .. @height-2)
        w = Random.rand(2 .. @width-2)
      end
      prew_w = w
      prew_h = h

    }
  end

  def seed_around
    @groups.each{
      |g|
      
      @in_group.times {
        |i|
        w = g.getY
        h = g.getX
        while(w <= 0 || h <= 0 || w >= @height || h >= @width || @forest.getTile(h, w).getName == 'Tree') 
          w = Random.rand(g.getY-3 .. g.getY+3)
          h = Random.rand(g.getX-3 .. g.getX+3)
        end
        @forest.removeTile h, w
        @forest.addTile Java::lowlevel::Tile.new(tree, h, w)
      }
    }
  end

  def generate
    seed_grass
    seed_centers
    seed_around
    @forest
  end
end

groups = Java::dnd::Dice.new 3, 8
trees = Java::dnd::Dice.new 3, 6


forests = []

# Пачка леса. Генерится очень долго. Варьируй их количество
forests_number = 30

forests_number.times {
  forests.push(BasicForest.new(30,30, groups.throwDice, trees.throwDice).generate)
}

forests.each_with_index {
  |f, i|
  common_f = f
  next_f = forests[i+1].nil? ? 0 : forests[i+1]
  if next_f != 0
    common_f.addPortal(Java::lowlevel::Portal.new(common_f, next_f, 0, 0))
    next_f.addPortal(Java::lowlevel::Portal.new(next_f, common_f, 0, 1))
  end

}
#Начальная локация
forest = forests[0]

#forest = BasicForest.new(10, 10, 1, 1).generate
#forest1 = BasicForest.new(10, 10, 1, 1).generate

#Java::lowlevel::AbstractThing::MainType.value_of("WEAPON")
#forest.addPortal(Java::lowlevel::Portal.new(forest, forest1, 1, 1))
#forest1.addPortal(Java::lowlevel::Portal.new(forest1, forest, 1, 1))


