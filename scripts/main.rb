#!/usr/bin/jruby


require 'java'
require "dnd.jar"

#java_import Java::rendering.TileRenderer
dice = Java::dnd.Dice.new 1, 6

100.times {
	puts dice.throwDice
}
