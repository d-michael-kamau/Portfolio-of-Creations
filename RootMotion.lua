local rootmotion = {}

function rootmotion.Animate(Anim : Animation, Keys : KeyframeSequence, Player : Player)
	--Sorting Keys by the time they happen in
	local sortedkeys = {}	
	for i, v in pairs(Keys:GetChildren()) do
		table.insert(sortedkeys, v)
	end
	table.sort(sortedkeys, function(a, b) return a.Time < b.Time end)
	
	--Moving the player by the keys
	local TweenServ = game:GetService("TweenService")
	local lastframe = sortedkeys[1]
	
	for i, f in pairs(sortedkeys) do
		if i == 1 then continue end
		--Some animations will use the torso, but have a weight of 0
		--These are skipped because they dont actually move the torso
		if f.HumanoidRootPart.Torso.Weight == 0 then continue end
		
		--Find how much to move them and in what time to move them
		local dTime = f.Time - lastframe.Time
		local dPos = f.HumanoidRootPart.Torso.CFrame.Y - lastframe.HumanoidRootPart.Torso.CFrame.Y
		
		--Tween/Move player Primary Part (Their root part)
		local tween = TweenServ:Create(Player.Character.PrimaryPart, 
				TweenInfo.new(dTime, Enum.EasingStyle.Linear), 
				{CFrame = Player.Character.PrimaryPart.CFrame * CFrame.new(0,0,dPos)})
		
		tween:Play()
		task.wait(dTime)
		lastframe = f
	end
end


function rootmotion.Extract(Keys : KeyframeSequence) 
	local extraction = Keys:Clone()
	extraction.Parent = script
	extraction.Name = "ExtractedKeys"

	--Getting all the Torso Movments and removing their Z movment 
	for _, key in pairs(extraction:GetDescendants()) do
		if key.Name == "Torso" then
			local pos = key.CFrame.Position
			local rx, ry, rz = key.CFrame:ToEulerAnglesYXZ()
			key.CFrame = (CFrame.new(key.CFrame.X, 0, key.CFrame.Z) * CFrame.Angles(rx, ry, rz))
		end
	end
end



return rootmotion
