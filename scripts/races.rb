#!/usr/bin/jruby
# -*- coding: undecided -*-

# List of game races
# cd
#
#
require "java"
require "./scripts/lib/race.jar"


# Формат
# Имя, сила, ловкость, интеллект, мудрость, харизма
# Внимание! В описании расы описывается бонус(штраф) к статам. которые получает конкретное существо данной расы


races_list = {}

# Humans
# 
#
#
#
#
races_list[:human] = Java::properties::Race.new("Human", 1, 1, 1, 10)
#races_list[:elf] = Java::properties::Race.new("Elf",)

race = races_list[:human]

