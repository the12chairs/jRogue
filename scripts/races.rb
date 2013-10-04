#!/usr/bin/jruby
# -*- coding: utf-8 -*-

# List of game races
#
#
#
require "java"
require "./scripts/lib/properties.jar"
require "./scripts/lib/dnd.jar"

# Формат
# Имя, сила, ловкость, интеллект, мудрость, харизма, выносливость - именно в этом порядке
# Внимание! В описании расы указываются дайсы, из которых раскидываются параметры, а не само значение параметров

# Format

# 1 Name race
# 2 str
# 3 dex
# 4 int
# 5 wis
# 6 cha
# 7 sta
# Attention! Race description contains dices, which makes character's params, not digital parameters!



# Here make dices you need
# First arg are numbers, second are edges  

dice3d6 = Java::dnd::Dice.new 3, 6 # Here 3d6 dice

# The hash list of races
races_list = {}



# Humans DnD
# STR 3d6 
# DEX 3d6
# INT 3d6
# WIS 3d6
# CHA 3d6
# STA 3d6

races_list[:human] = Java::properties::Race.new("Human", dice3d6, dice3d6, dice3d6, dice3d6, dice3d6, dice3d6)

# Elves
# STR
# DEX
# INT
# WIS
# CHA
# STA

#races_list[:elf] = Java::properties::Race.new



race = races_list[:human]

