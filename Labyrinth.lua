local size = 25
local finish = (size .. "," .. size)
local start = "1,1"

local function Textify(x,y)
	return (x .. "," .. y)
end

local lastYield = tick()
local timeBudget = 1/60

--Recursion function
local function LabMove(x,y,Visited)
	--Script that dynamically changes the wait between function calls, minimizing buffering.lag
	if tick() - lastYield >= timeBudget then
		task.wait()
		lastYield = tick()
	end

	--Base Case: When we reach the finish
	if Textify(x,y) == finish then return "" end

	local moves = {{x+1, y},{x-1, y},{x, y+1},{x, y-1}}
	--Quick randomness shuffle, so the movment is randomized
	for i = #moves, 2, -1 do
		local j = math.random(i)
		moves[i], moves[j] = moves[j], moves[i]
	end

	--Iterate all possible 1 step moves
	for i, v in pairs(moves) do
		--Ensure the x and y are between 1 and 9 inclusive
		if v[1]<1 or v[1]>size or v[2]<1 or v[2]>size then continue end
		if table.find(Visited,Textify(v[1],v[2])) then continue end
		
		--Making new visited with updated move
		table.insert(Visited,Textify(v[1],v[2]))
		
		--Recursion Call: Move to the next space
		local path = LabMove(v[1],v[2],Visited)
		
		--If the path is valid, return it now!
		--If the path is invalid, it would go to the next possible movment choice
		if path ~= "X" then
			return Textify(v[1],v[2]).." "..path
		end
	end
	
	--If no paths were reached, return X
	--Since the calls are all random, this will prevent cases where there are dead ends
	return "X"
end

--Igniting Case: Starting Node with Starting Node only visited
local path = start .. " " .. LabMove(1,1,{Textify(1,1)})
path = string.sub(path,1,string.len(path)-1)
print(path)


--Generating the labyrinth in game
local labyrinth = game.Workspace.Lab
local pathtable = string.split(path," ")

for i,path in pairs(string.split(path," ")) do
	task.wait(0.01)
	
	--Creating block
	local x,y = string.split(path,",")[1],string.split(path,",")[2]
	local block = game.Workspace.LabTemplate:Clone()
	block.Parent = labyrinth
	block.Name = Textify(x,y)
	block:PivotTo( CFrame.new(x*16,0.5,y*16))
	
	--Clear out the walls of adjacent blocks
	local moves = {{1,0},{-1,0},{0,1},{0,-1}}
	--First Block and Last Block
	if i == 1 then
		for dir, move in pairs(moves) do
			if pathtable[2] == Textify(move[1]+x,move[2]+y) then
				block:FindFirstChild(Textify(move[1],move[2])):Destroy()
			end
		end
		continue
	elseif i == #pathtable then
		for dir, move in pairs(moves) do
			if pathtable[#pathtable-1] == Textify(move[1]+x,move[2]+y) then
				block:FindFirstChild(Textify(move[1],move[2])):Destroy()
			end
		end
		continue
	end
	--Middle blocks, remove walls towards one before/after in path
	for dir, move in pairs(moves) do
		if pathtable[i-1] == Textify(move[1]+x,move[2]+y) or pathtable[i+1] == Textify(move[1]+x,move[2]+y) then
			block:FindFirstChild(Textify(move[1],move[2])):Destroy()
		end
	end

end

--Making the decor blocks to fill in extra space
for i = 1,size do
	for j = 1,size do
		if labyrinth:FindFirstChild(Textify(i,j)) ~= nil then continue end
		local block = game.Workspace.Decor:Clone()
		block.Parent = labyrinth
		block.Name = Textify(i,j)
		block:PivotTo( CFrame.new(i*16,0.5,j*16))
	end
end
