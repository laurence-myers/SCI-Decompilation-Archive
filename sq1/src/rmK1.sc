;;; Sierra Script 1.0 - (do not remove this comment)
(script# 38)
(include game.sh)
(use Main)
(use SQRoom)
(use LoadMany)
(use Motion)
(use User)
(use System)

(public
	rmK1 0
)

(local
	local0
)
(instance rmK1 of SQRoom
	(properties
		lookStr {Guess what? More sand! Do you have any idea where you're headed?}
		picture 38
		style DISSOLVE
		horizon 125
	)
	
	(method (init)
		(switch (= local0 ((User alterEgo?) edgeHit?))
			(3
				(ego x: (Random 120 200))
				(= north prevRoomNum)
			)
			(1
				(ego x: (Random 120 200))
				(= south prevRoomNum)
			)
			(2
				(ego y: 170)
				(= west prevRoomNum)
			)
			(4
				(ego y: 170)
				(= east prevRoomNum)
			)
		)
		(LoadMany VIEW 18 420)
		(ego init:)
		(self setRegions: KERONA)
		(super init:)
	)
	
	(method (notify)
		(ego setScript: egoDead)
	)
)

(instance egoDead of Script
	(properties)
	
	(method (doit)
		(super doit: &rest)
		(cond 
			((not (User canControl:)) 0)
			((curRoom north?)
				(if
					(or
						(> (ego y?) 135)
						(< (ego x?) 30)
						(> (ego x?) 290)
					)
					(HandsOff)
					(ego setMotion: MoveTo (ego x?) 150 self)
				)
			)
			((curRoom south?)
				(if
					(or
						(< (ego y?) 170)
						(< (ego x?) 30)
						(> (ego x?) 290)
					)
					(HandsOff)
					(ego setMotion: MoveTo (ego x?) 160 self)
				)
			)
			((curRoom east?)
				(if
					(or
						(< (ego x?) 290)
						(< (ego y?) 140)
						(> (ego y?) 180)
					)
					(HandsOff)
					(ego setMotion: MoveTo 280 (ego y?) self)
				)
			)
			(
				(and
					(curRoom west?)
					(or
						(> (ego x?) 30)
						(< (ego y?) 140)
						(> (ego y?) 180)
					)
				)
				(HandsOff)
				(ego setMotion: MoveTo 30 (ego y?) self)
			)
		)
	)
	
	(method (changeState newState)
		(switch (= state newState)
			(0 0)
			(1
				(if (curRoom script?)
					(self dispose:)
				else
					(HandsOff)
					(music number: 810 loop: 1 play:)
					(ego
						view: 18
						setLoop: (if (< (ego heading?) 180) 0 else 1)
						cel: 0
						setMotion: 0
						cycleSpeed: 5
					)
					(= cycles 24)
				)
			)
			(2 (ego setCycle: EndLoop self))
			(3 (EgoDead 18 0 7 38 0))
		)
	)
)
