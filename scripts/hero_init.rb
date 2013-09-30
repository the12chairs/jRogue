#!/usr/bin/jruby

# List of game races
# cd
#
#
require "java"
require "./scripts/lib/race.jar"

human = Java::properties.Race.new("Human", 1, 1, 1, 10)
dwarf = Java::properties.Race.new("Dwarf", 5, 0, -3, 4)
goblin = Java::properties.Race.new("Muttafuker", 1, 1, 1, 10)
