class Race

  
  attr_accessor :name, :str, :dex, :intel, :hp
  
  def initialize name, str, dex, intel, hp
    self.name = name
    self.str = str
    self.dex = dex
    self.intel = intel
    self.hp = hp
  end

end


elf = Race.new "Elf", -2, 4, 2, -2

puts elf.name
