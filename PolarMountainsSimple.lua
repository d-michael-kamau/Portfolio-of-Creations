local folder = game.Workspace.Mountain
local temp = game.Workspace.Ice

--Concept; A code that makes spiral pattern mountain, using polar coordinates

--Spiral formula r=a+b*theta
local a = 1
local b = 0.326

---Crash prevention
local lastyeild = tick()
local frame = 1/60

--Conversion method
local function toCartesian(r,t)
	return r*math.cos(math.rad(t)), r*math.sin(math.rad(t))
end

--Gets the parts within radius from (x,y)
local function getRadius(x,y,r)
	local circle = {}
	for dx = x-r,x+r do
		for dy = y-r,y+r do
			if math.sqrt((x-dx)^2 + (y-dy)^2) <= r then
				table.insert(circle,{dx,dy})
			end	
		end
	end
	return circle
end

--Calculating the height at a given radius
local peak = 600
local k = -0.025

local function getheight(x)
	--any function here to determine the height per radius
	--return peak*math.exp(k*x) - 400 
	return 100 -x*5
end

--Generating the spiral
for r = 0,120,0.05 do
	--A constant can be added to theta to rotate the mountain
	local theta = math.deg(math.log(r-a)/b)
	theta += 45
	
	--Trig Magic to make x and y
	local x,y = toCartesian(r,theta)

	--Get the radius of parts to set. 4 is ~pi
	local area = getRadius(math.round(x),math.round(y),b*4)

	--Spawning in the block
	for i,apart in pairs(area) do
		if folder:FindFirstChild(apart[1]..","..apart[2]) ~= nil then continue end
		local block = temp:Clone()
		block.Parent = folder
		block.Name = apart[1]..","..apart[2]
		block.Size = Vector3.new(4,100,4)
		block.CFrame = CFrame.new(math.round(apart[1])*4,getheight(r),math.round(apart[2])*4)
	end
	
	--Crash prevention
	if tick() - lastyeild >= frame then
		task.wait()
		lastyeild = tick()
	end
end