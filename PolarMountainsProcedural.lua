local folder = game.Workspace.Mountain
local temp = game.Workspace.Ice

--Concept; A code that makes spiral-like mountains, using polar coordinates

--Spiral Values r=a+e^(b*theta)
local a = 1
local b = 0.5

--Crash prevention things
local lastyeild = tick()
local frame = 1/60

--Table of all the (x,y) pairs already generated
local occupied = {}

--Conversion methods
local function toCartesian(r,t)
	return r*math.cos(t), r*math.sin(t)
end
local function toPolar(x,y)
	return math.deg(math.atan2(y,x)), math.sqrt(x^2 + y^2) 
	--arctan(y/x), where the quadrant is considered to find theta
end

--Gets all parts in radius r around an (x,y)
local function getRadius(x,y,r)
	local circle = {}
	for dx = x-r,x+r do
		for dy = y-r,y+r do
			if math.sqrt((x-dx)^2 + (y-dy)^2) <= r then
				if dx == -0 or dy == -0 then continue end
				table.insert(circle,{dx,dy})
			end	
		end
	end
	return circle
end

--Height formulas
local peak = 200
local k = -0.01

local function getheight(x)
	--Different height function examples
	return 25 -x*1.2 --linear
	--return peak*math.exp(k*x) --Exponential ground
	--return 200/ (1+100*math.exp(-0.0006*200*x)) --Exponential with logarithmic taper
	--return 10*math.sin(x/8)+100 --Sinusoisal
end

--Generated a spiral segment
local function generate(theta,rOrgin, color)
	--Finding the base change in theta where theta and the radius line up
	local delta = (math.log(rOrgin-a)/b)-theta
	
	--Varriation in height and length of segment
	local heightVary = math.random(-1,3)
	local len = math.random(10,50)
	
	for r = rOrgin,rOrgin+len,0.25 do
		local theta = (math.log(r-a)/b) + delta
		
		--Trig Magic to make x and y
		local x,y = toCartesian(r,theta)

		--Finding the radius (the radius should be quadratic along the length of the segment)
		local localRadius = (1 -(0.5/len) * (r-rOrgin) * (r - (rOrgin + len)))
		local area = getRadius(math.round(x),math.round(y),localRadius)
		
		--Spawning in the block
		for i,apart in pairs(area) do
			local rx = math.round(apart[1])
			local ry = math.round(apart[2])
			
			--Ignoring already computed blocks
			if occupied[rx..","..ry] == true then
				continue
			end
			occupied[rx..","..ry] = true 
			local block = temp:Clone()
			block.Parent = folder
			block.Name = rx..","..ry
			block.Color = color
			block.Size = Vector3.new(1,100,1)
			block.CFrame = CFrame.new(rx*1
							,getheight(r)+heightVary
							,ry*1)
		end
		
		--Crash prevention yeild
		if tick() - lastyeild >= frame then
			task.wait()
			lastyeild = tick()
		end
	end
end



local Mountain = getRadius(0,0,110)

--Generate segments until theres no more spaces
while #Mountain > 0 do
	--choosing random coordinates from the range, removing them, and computing them
	local index = math.random(1,#Mountain)
	local x,y = Mountain[index][1],Mountain[index][2]
	table.remove(Mountain,index)
	if folder:FindFirstChild(x..","..y) ~= nil then continue end
	
	--Generating Segment
	local color = Color3.new(math.random(0,200)/1000,math.random(0,800)/1000,1)
	local t,r = toPolar(x,y)	
	print(#folder:GetChildren())
	generate(t,r, color)
	
	--Crash Prevention
	if tick() - lastyeild >= frame then
		task.wait()
		lastyeild = tick()
	end
end

print("finished")

