math.randomseed(os.clock()^5)

function randomString(length)
   local res = ""
   for i = 1, length do
      res = res .. string.char(math.random(97, 122))
   end
   return res
end

function request()
   return wrk.format("POST", "/content", {}, randomString(10))
end
